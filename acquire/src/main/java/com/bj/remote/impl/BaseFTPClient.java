package com.bj.remote.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.bj.domain.MemCodeCacher;
import com.bj.remote.BaseRemoteClient;

public class BaseFTPClient extends BaseRemoteClient{
	
	public static final Logger log = Logger.getLogger(BaseFTPClient.class);
	
	private static FTPClient ftp;
	
	public BaseFTPClient(){
		
	}

	
	public FTPClient getFTPClient(){  
        try {
        	if(ftp == null){
        		ftp = new FTPClient();
        		ftp.connect(remoteConfig.getHost_name(), Integer.valueOf(remoteConfig.getPort()));
        		boolean logFlag = ftp.login(remoteConfig.getUser_name(), remoteConfig.getPassword());
        		log.info(logFlag);
        		if(!ftp.login(remoteConfig.getUser_name(), remoteConfig.getPassword())){
        			closeFTPClient(ftp);
        			return null;
        		}
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  // 文件类型,默认是ASCII 
                ftp.setControlEncoding("GBK");  
                ftp.setConnectTimeout(remoteConfig.getTime_out()); 
                ftp.setBufferSize(1024);  
                ftp.enterLocalPassiveMode();  // 设置被动模式  
                // 响应信息   
                if ((!FTPReply.isPositiveCompletion(ftp.getReplyCode()))) {  
                    ftp.disconnect();
                    log.info("FTP server refused connection.");
                } else {  
                    return ftp;  
                }  
        	}
        } catch (Exception e) {
        	closeFTPClient(ftp);
        	e.printStackTrace();
        	//throw new RuntimeException();
        }  
        return ftp;
    }  
	

	public void getfiles() {
		
		try {
			FTPClient ftpClient  = getFTPClient();
	        BufferedOutputStream output = null;  
	        boolean success = false;
	        
	        // 检查本地路径  
            this.checkFileExist(remoteConfig.getDst_dir());  
            // 改变工作路径  
            if (!this.changeDirectory(ftpClient,remoteConfig.getSrc_dir())) {  
                System.out.println("服务器路径不存在");  
            }  

            List<FTPFile> ftpfiles = Arrays.asList(ftpClient.listFiles(remoteConfig.getSrc_dir() + "/" + "*.txt"));
	        
	        if(ftpfiles.size() == 0){
	        	log.info("no file sleep 5 sec!");
	        	Thread.sleep(5000);
	        }else{
	        	for (FTPFile ftpfile : ftpfiles) {  
	            	log.info("ftpfile.getName=" + ftpfile.getName());
	                
	            	File localFilePath = new File(remoteConfig.getDst_dir() + File.separator  
	                        + ftpfile.getName());  
	            	
	                output = new BufferedOutputStream(new FileOutputStream(localFilePath)); 
	                success = ftpClient.retrieveFile(ftpfile.getName(), output);  
	                if(success){
	                	deleteFtpServerFile(remoteConfig.getSrc_dir() + "/" + ftpfile.getName(),ftpClient);
	                }
	            }  
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void putfiles() {
		
	}

	public int getflag() {
		return MemCodeCacher.FTP_FLAG;
	}
	
	/** 
     * 关闭FTP连接 
     *  
     * @param ftp 
     * @throws Exception 
     */  
    public void closeFTPClient(FTPClient _ftp){  
        try {  
            if (_ftp.isConnected())  
                _ftp.logout();  
                _ftp.disconnect();  
        } catch (Exception e) {  
            log.info("关闭FTP服务出错!");  
        }  
    } 
    
    
    /** 
     * 删除文件 
     *  
     * @param remoteFilePath 
     * @return 
     * @throws Exception 
     */ 
    
    public Boolean deleteFtpServerFile(String remoteFilePath,FTPClient _ftp) throws Exception {  
    	boolean success = _ftp.deleteFile(remoteFilePath);
        return  success;
    } 
    
    
    /** 
     * 改变FTP服务器工作路径  
     *  
     * @param remoteFoldPath 
     */  
    public Boolean changeDirectory(FTPClient _ftp,String remoteFoldPath) throws Exception {  
  
        return _ftp.changeWorkingDirectory(remoteFoldPath);  
    }  

}
