package com.bj.acquire;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.bj.domain.GetConfig;
import com.bj.domain.MemCodeCacher;
import com.bj.remote.IRemoteClient;
import com.bj.remote.impl.BaseFTPClient;
import com.bj.remote.impl.SFTPClient;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;

public class GetFile {
	
	public static final Logger log = Logger.getLogger(GetFile.class);
	
	
	public  void getfiles(IRemoteClient client,int flag){
		
		if(flag == MemCodeCacher.FTP_FLAG){
			getfiles((BaseFTPClient)client);
		}else if(flag == MemCodeCacher.SFTP_FLAG){
			getfiles((SFTPClient)client);
		}
	}

	@SuppressWarnings("unchecked")
	private  void getfiles(SFTPClient get_trans) {
		ChannelSftp client = null;
		GetConfig config = null;
		try {
			client = get_trans.getChannel();
			config = (GetConfig) get_trans.getConfig();
			if(config == null){
				log.error("GETConfig is not Load！");
				throw new RuntimeException();
			}
			String src_dir = config.getSrc_dir();
			String dst_dir = config.getDst_dir();
			String last_char = src_dir.substring(src_dir.length()-1);
			if(!"/".equals(last_char)){
				src_dir = src_dir + "/";
			}
			String pattern = config.getPattern();	
			log.info(src_dir +pattern);
			final Vector<LsEntry> files  = new Vector<LsEntry>();
			LsEntrySelector selector = new LsEntrySelector(){
			       public int select(LsEntry entry){
			    	   if(entry.getFilename().equals("1.txt")){
					    	 files.addElement(entry);
			    	   }
			         return CONTINUE;
			       }
			};
			while(true){
				//Vector<LsEntry> files = client.ls(src_dir+pattern);
				client.ls(src_dir+pattern,selector);
				Iterator<LsEntry> iter = files.iterator();
				while(iter.hasNext()){
					LsEntry file = iter.next();
					String filename = file.getFilename();
					String src = src_dir + filename;
					try {
						client.get(src, dst_dir);
					} catch (Exception e) {
						log.error("从远程服务器下载文件出错[" + src + "]",e);
					}
					try {
						client.rm(src);
					} catch (Exception e) {
						log.error("从远程服务器删除文件出错[" + src + "]",e);
					}
				}
				try {
					log.info("No file found, sleep " + 5 + " seconds");
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException n) {
					// None
				}
			}
		} catch (Exception e) {
			log.error("SFTP文件下载异常",e);
			e.printStackTrace();
		}finally {
			if(client!=null){
				client.quit();
			}
		}
		
		
		
		

	}
	
	private void getfiles(BaseFTPClient get_trans) {
		
		FTPClient client = null;
		GetConfig config = null;
        BufferedOutputStream output = null;  
		try {
			client = get_trans.getFTPClient();
			config = (GetConfig) get_trans.getConfig();
			String src_dir = config.getSrc_dir();
			String dst_dir = config.getDst_dir();
			String pattern = config.getPattern();
	        boolean success = false;
	        // 检查本地路径  
	        boolean local_exist = get_trans.checkFileExist(dst_dir);  
	        if(!local_exist){
	        	log.error("本地路径不存在");  
	        	throw new RuntimeException();
	        }
            // 改变工作路径  
            if (!get_trans.changeDirectory(client,src_dir)) {  
                log.error("服务器路径不存在");  
                throw new RuntimeException();
            } 
			
            FTPClientConfig ftpconfig = new FTPClientConfig("com.tydic.beijing.billing.remote.AcquireFTPEntryParser");
            client.configure(ftpconfig);
            while(true){
            	//client.listFiles();
    			//FTPListParseEngine engine = client.initiateListParsing(pattern);
            	//List<FTPFile> ftpfiles = Arrays.asList(engine.getNext(10));
            	List<FTPFile> ftpfiles = Arrays.asList(client.listFiles(pattern));
            	if(ftpfiles.size()>0){
            		log.info("ftpfiles.size=" + ftpfiles.size());
                    Iterator<FTPFile> files = ftpfiles.iterator();
                    while(files.hasNext()){
                    	FTPFile file = files.next();
                    	String filename = file.getName();
                    	File localFilePath = new File(dst_dir + File.separator + filename);  
    	                output = new BufferedOutputStream(new FileOutputStream(localFilePath)); 
    	                success = client.retrieveFile(filename, output);  
	                	output.flush();
    	                if(success){
    	                	client.deleteFile(src_dir + "/" + filename);
    	                }else{
    	                	//do something
    	                }
                    }
            	}else{
            		try {
    					log.info("No file found, sleep " + 5 + " seconds");
    					TimeUnit.SECONDS.sleep(5);
    				} catch (InterruptedException n) {
    					// None
    				}
            	}
            }
		} catch (Exception e) {
			log.error("FTP下载异常",e);
			throw new RuntimeException();
		}finally {
			if(client!=null){
				try {
					client.quit();
				} catch (Exception e2) {
					log.error("FTP Client 退出异常!",e2);
				}
			}
			if(output!=null){
				try {
					output.close();
				} catch (Exception e2) {
					log.error("output关闭异常",e2);
				}
			}
		}
	}
}
