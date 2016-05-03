package com.bj.acquire;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.bj.domain.MemCodeCacher;
import com.bj.domain.PutConfig;
import com.bj.remote.IRemoteClient;
import com.bj.remote.impl.BaseFTPClient;
import com.bj.remote.impl.SFTPClient;
import com.jcraft.jsch.ChannelSftp;

public class PutFile {
	
	public static final Logger log = Logger.getLogger(PutFile.class);
	
	/**
	 * @param client
	 * @param flag  0 FTP  1 SFTP
	 */
	public void putfiles(IRemoteClient client,int flag){
		if(flag == MemCodeCacher.FTP_FLAG){
			putfiles((BaseFTPClient)client);
		}else if(flag == MemCodeCacher.SFTP_FLAG){
			putfiles((SFTPClient)client);
		}
				
	}
	
	
	private void putfiles(SFTPClient put_trans) {
		ChannelSftp sftp = null;
		try {
			sftp = put_trans.getChannel();
			PutConfig config = (PutConfig) put_trans.getConfig();
			String src_dir = config.getSrc_dir();
			String dst_dir = config.getDst_dir();
			List<Path> paths = new ArrayList<Path>(); 
			Path localPath = Paths.get(src_dir);
			final Pattern patternJ = Pattern.compile(config.getPattern());
			Filter<Path> filter = new Filter<Path>() {
				public boolean accept(Path file) throws IOException {
						if (patternJ.matcher(file.getFileName().toString()).matches()) {
							return true;
						}
					return false;
				}
			};
			while(true){
				put_trans.listLocalFiles(paths, filter, localPath);
				
				if(!paths.isEmpty()){
					Iterator<Path> iter = paths.iterator();
					
					while(iter.hasNext()){
						Path tmp = iter.next();
						try {
							sftp.put(tmp.toString(), dst_dir);
						} catch (Exception e) {
							log.error("上传文件出错,出错文件[" + tmp.toString() + "]",e);
						}
						
						try {
							Files.delete(tmp);
						} catch (Exception e) {
							log.error("删除文件出错,出错文件[" + tmp.toString() + "]",e);
						}
						iter.remove();
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
			e.printStackTrace();
		}finally {
			if(sftp != null){
				sftp.quit();
			}
		}
	}
	
	
	private void putfiles(BaseFTPClient put_trans) {
		
		FTPClient client = null;
		PutConfig config = null;
		try {
			client = put_trans.getFTPClient();
			config = (PutConfig) put_trans.getConfig();
			String src_dir = config.getSrc_dir();
			String dst_src = config.getDst_dir();
			//检查本地路径
			if(!put_trans.checkFileExist(src_dir)){
				log.info("本地路径不存在");  
                throw new RuntimeException();
			}
			 // 改变工作路径  
            if (!put_trans.changeDirectory(client,dst_src)) {  
                log.info("服务器路径不存在");  
                throw new RuntimeException();
            } 
            List<Path> paths = new ArrayList<Path>(); 
			Path localPath = Paths.get(src_dir);
			final Pattern patternJ = Pattern.compile(config.getPattern());
			Filter<Path> filter = new Filter<Path>() {
				public boolean accept(Path file) throws IOException {
						if (patternJ.matcher(file.getFileName().toString()).matches()) {
							return true;
						}
					return false;
				}
			};
			InputStream input = null;  
	        boolean success = false; 
	        File file = null;  
			while(true){
				put_trans.listLocalFiles(paths, filter, localPath);
				if(!paths.isEmpty()){
					Iterator<Path> iter = paths.iterator();
					while(iter.hasNext()){
						Path tmp = iter.next();
						if (!put_trans.checkFileExist(tmp.toString())) {  
			                log.info(tmp.getFileName() + "不存在"); 
			                continue;
			            }  
						file = new File(tmp.toString());
						input = new FileInputStream(file);
						success = client.storeFile(file.getName(), input);
						if(!success){
							log.info(tmp + "上传失败"); 
						}else{
							try {
								input.close();
								Files.delete(tmp);
							} catch (Exception e) {
								log.error("删除文件出错,出错文件[" + tmp.toString() + "]",e);
							}
						}
						iter.remove();
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
			e.printStackTrace();
		}finally {
			try {
				client.quit();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	

}
