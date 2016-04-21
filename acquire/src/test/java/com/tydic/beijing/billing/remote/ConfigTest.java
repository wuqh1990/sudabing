package com.tydic.beijing.billing.remote;

import org.junit.Test;

import com.bj.acquire.PutFile;
import com.bj.domain.PutConfig;
import com.bj.remote.impl.SFTPClient;

public class ConfigTest {
	
	@Test
	public void testPutConfig(){
		
		PutConfig putConfig = new PutConfig();
		
		putConfig.setHost_name("test");
		putConfig.setFlag("put");
		
		SFTPClient client = new SFTPClient();
		client.setConfig(putConfig);
		
		PutFile putFile = new PutFile();
		
		putFile.putfiles(client, 1);
		
	}

}
