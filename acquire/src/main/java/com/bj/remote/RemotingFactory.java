package com.bj.remote;

import com.bj.domain.GetConfig;
import com.bj.domain.MemCodeCacher;
import com.bj.domain.PutConfig;
import com.bj.remote.impl.BaseFTPClient;
import com.bj.remote.impl.SFTPClient;

public final class RemotingFactory {
	
	
	public static SFTPClient newSFTPClient(){
		
		return new SFTPClient();
		
	}
	
	public static BaseFTPClient newFTPClient(){
		
		return new BaseFTPClient();
		
	}
	
	public static PutConfig newPutRemoteConfig(int serverFlag){
		
		PutConfig putConfig = new PutConfig();
		
		putConfig.setHost_name((String)MemCodeCacher.getFtParmValue(MemCodeCacher.DST_HOST_ADDRESS));
		
		putConfig.setUser_name((String)MemCodeCacher.getFtParmValue(MemCodeCacher.DST_LOGIN_USER));

		putConfig.setPassword((String)MemCodeCacher.getFtParmValue(MemCodeCacher.DST_LOGIN_PASSWORD));
		
		String dst_port = (String)MemCodeCacher.getFtParmValue(MemCodeCacher.DST_HOST_PORT);
		
		if(null==dst_port||"".equals(dst_port)){
			if(serverFlag == MemCodeCacher.SFTP_FLAG){
				putConfig.setPort(String.valueOf(MemCodeCacher.SFTP_DEFAULT_PORT_VALUE));
			}else if(serverFlag == MemCodeCacher.FTP_FLAG){
				putConfig.setPort(String.valueOf(MemCodeCacher.FTP_DEFAULT_PORT_VALUE));
			}
		}else{
			putConfig.setPort(dst_port);
		}
		
		putConfig.setSrc_dir((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_DIR));
		
		putConfig.setDst_dir((String)MemCodeCacher.getFtParmValue(MemCodeCacher.RULE_DST_DIR));
		
		putConfig.setTime_out(MemCodeCacher.TIME_OUT);
		
		putConfig.setFlag("put");
		
		putConfig.setPattern((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_PATTERN));
		
		return putConfig;
	}
	
	
	public static GetConfig newGetRemoteConfig(int serverFlag){
		
		GetConfig getConfig = new GetConfig();
		
		getConfig.setHost_name((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_HOST_ADDRESS));
		
		getConfig.setUser_name((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_LOGIN_USER));

		getConfig.setPassword((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_LOGIN_PASSWORD));
		
		String dst_port = (String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_HOST_PORT);
		
		if(null==dst_port||"".equals(dst_port)){
			if(serverFlag == MemCodeCacher.SFTP_FLAG){
				getConfig.setPort(String.valueOf(MemCodeCacher.SFTP_DEFAULT_PORT_VALUE));
			}else if(serverFlag == MemCodeCacher.FTP_FLAG){
				getConfig.setPort(String.valueOf(MemCodeCacher.FTP_DEFAULT_PORT_VALUE));
			}
		}else{
			getConfig.setPort(dst_port);
		}
		
		getConfig.setSrc_dir((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_DIR));
		
		getConfig.setDst_dir((String)MemCodeCacher.getFtParmValue(MemCodeCacher.RULE_DST_DIR));
		
		getConfig.setTime_out(MemCodeCacher.TIME_OUT);
		
		getConfig.setPattern((String)MemCodeCacher.getFtParmValue(MemCodeCacher.SRC_PATTERN));

		
		return getConfig;
	}
	
}
