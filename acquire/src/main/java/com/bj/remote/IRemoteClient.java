package com.bj.remote;

import com.bj.domain.BaseConfig;

public interface IRemoteClient {
	
	
	/**
	 * 设置主机配置信息
	 */
	void setConfig(BaseConfig remoteConfig);
	
	BaseConfig getConfig();
	
	/**
	 * 获取sftp/ftp标识
	 */
	
	int getflag();
	

}
