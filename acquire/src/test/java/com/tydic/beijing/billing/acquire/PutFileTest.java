package com.tydic.beijing.billing.acquire;

import org.junit.Test;

import com.bj.acquire.PutFile;
import com.bj.domain.PutConfig;
import com.bj.remote.RemotingFactory;
import com.bj.remote.impl.BaseFTPClient;
import com.bj.remote.impl.SFTPClient;

public class PutFileTest {
	
	@Test
	public void stpfPutTest(){
		
		SFTPClient client = RemotingFactory.newSFTPClient();
		PutConfig config = new PutConfig();
		config.setHost_name("101.200.145.190");
		config.setPort("22");
		config.setUser_name("newbilling");
		config.setPassword("1qaz2wsx");
		config.setSrc_dir("D:/FTPTEST");
		config.setDst_dir("/app/ftpdata/QNTEPROD/HD/SJ/downfiles");
		config.setTime_out(6000);
		config.setPattern("[\\w]+.txt");
		client.setConfig(config);
		PutFile putFile = new PutFile();
		putFile.putfiles(client, 1);
	}
	
	@Test
	public void ftpPutTest(){
		
		BaseFTPClient client = RemotingFactory.newFTPClient();
		PutConfig config = new PutConfig();
		config.setHost_name("101.200.177.101");
		config.setPort("21");
		config.setUser_name("test");
		config.setPassword("hfo8615");
		config.setSrc_dir("D:/FTPTEST");
		config.setDst_dir("/home/test/");
		config.setTime_out(6000);
		config.setPattern("[\\w]+.txt");
		client.setConfig(config);
		PutFile putFile = new PutFile();
		putFile.putfiles(client, 0);
	}
	
	
}
