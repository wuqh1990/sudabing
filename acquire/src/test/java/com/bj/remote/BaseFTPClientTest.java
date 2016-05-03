package com.bj.remote;

import org.junit.Test;

import com.bj.domain.BaseConfig;
import com.bj.remote.impl.BaseFTPClient;

import junit.framework.TestCase;

public class BaseFTPClientTest extends TestCase {
	
	
	@Test
	public static void getFTPClientTest(){
		
		BaseConfig baseConfig = new BaseConfig();
		
		baseConfig.setHost_name("101.200.177.101");
		baseConfig.setPort("21");
		baseConfig.setUser_name("test");
		baseConfig.setPassword("hfo8615");
		baseConfig.setTime_out(6000);
		baseConfig.setSrc_dir("/home/test/ftp");
		baseConfig.setDst_dir("D:/FTPTEST");
		BaseFTPClient ftpClient = new BaseFTPClient();
		ftpClient.setConfig(baseConfig);
		while(true){
			ftpClient.getfiles();
		}
	}
	
	public static void main(String[] args) {
		getFTPClientTest();
	}
	
	

}
