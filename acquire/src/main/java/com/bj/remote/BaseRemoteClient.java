package com.bj.remote;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.Logger;

import com.bj.domain.BaseConfig;
import com.bj.remote.IRemoteClient;

public abstract class BaseRemoteClient implements IRemoteClient{
	
	public static final Logger log = Logger.getLogger(BaseRemoteClient.class);
			
	protected BaseConfig remoteConfig;
	
	
	public void setConfig(BaseConfig _remoteConfig) {
		this.remoteConfig = _remoteConfig;
	}
	
	public BaseConfig getConfig() {
		
		return this.remoteConfig;
	}
	
	/** 
     * 检查本地路径是否存在 
     *  
     * @param filePath 
     * @return 
     * @throws Exception 
     */  
    public boolean checkFileExist(String filePath) throws Exception {  
        boolean flag = false;  
        File file = new File(filePath);  
        if (file.exists()){  
            flag = true;  
        }  
        return flag;  
    } 
	
	public void listLocalFiles(List<Path> paths,Filter<Path> filter,Path localPath){
		
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(localPath, filter)) {
			for (Path path : ds) {
				paths.add(path);
			}
		} catch (Exception e) {
			log.error("扫描本地文件异常",e);
		}
		
	}
	
	

}
