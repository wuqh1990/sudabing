package com.bj.acquire;

import com.bj.domain.GetConfig;
import com.bj.domain.MemCodeCacher;
import com.bj.domain.PutConfig;
import com.bj.remote.IRemoteClient;
import com.bj.remote.RemotingFactory;

public class BaseFileThread implements Runnable {
	
	private IRemoteClient put_trans;
	private IRemoteClient get_trans;
	
	
	public void run() {
		
		String src_host_type = (String) MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_HOST_TYPE);
		String dst_host_type = (String) MemCodeCacher.getFtParmValue(MemCodeCacher.DST_HOST_TYPE);
		
		boolean is_put = (Boolean) MemCodeCacher.getFtParmValue(MemCodeCacher.FTP_PUT);
		boolean is_get = (Boolean) MemCodeCacher.getFtParmValue(MemCodeCacher.FTP_GET);
		boolean is_get_put = (Boolean) MemCodeCacher.getFtParmValue(MemCodeCacher.FTP_GET_PUT);
		
		String  ftp_flag;
		
		/**
		 * 
		 * 1&1   get方式sftp  put方式sftp
		 * 1&0   get方式sftp  put方式ftp
		 * 0&1   get方式ftp   put方式sftp
		 * 0&0   get方式ftp   put方式ftp
		 * 
		 */
		
		if("3".equals(src_host_type)||"4".equals(src_host_type)||"5".equals(src_host_type)){ 
			get_trans = RemotingFactory.newSFTPClient();
			ftp_flag = MemCodeCacher.SFTP_FLAG + "";
		}else{
			get_trans = RemotingFactory.newFTPClient();
			ftp_flag = MemCodeCacher.FTP_FLAG + "";
		}
		
		
		if("3".equals(dst_host_type)||"4".equals(dst_host_type)||"5".equals(dst_host_type)){
			put_trans = RemotingFactory.newSFTPClient();
			ftp_flag = ftp_flag + "&" +  MemCodeCacher.SFTP_FLAG;
		}else{
			put_trans = RemotingFactory.newFTPClient();
			ftp_flag = ftp_flag + "&" +  MemCodeCacher.FTP_FLAG;
		}
		
		int get_flag = Integer.parseInt(ftp_flag.split("&")[0]);
		int put_flag = Integer.parseInt(ftp_flag.split("&")[1]);
		
		if(is_put){
			
			PutConfig putConfig = RemotingFactory.newPutRemoteConfig(put_trans.getflag());
			put_trans.setConfig(putConfig);
			PutFile putfile = new PutFile();
			putfile.putfiles(put_trans,put_flag);
			
		}else if(is_get){
			
			GetConfig getConfig = RemotingFactory.newGetRemoteConfig(get_trans.getflag());
			get_trans.setConfig(getConfig);
			GetFile getfile = new GetFile();
			getfile.getfiles(get_trans,get_flag);
			
		}else if(is_get_put){
			
		}
		
	}
}
