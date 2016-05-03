package com.bj.acquire;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import com.bj.acquire.GetFile;
import com.bj.domain.GetConfig;
import com.bj.remote.RemotingFactory;
import com.bj.remote.impl.BaseFTPClient;
import com.bj.remote.impl.SFTPClient;

public class GetFileTest {
	
	@Test
	public void testGetFile(){
		
		
		//IRemoteClient get_trans_sftp = RemotingFactory.newSFTPClient();
		//GetFile.getfiles(get_trans_sftp,1);
		//IRemoteClient get_trans_ftp = RemotingFactory.newFTPClient();
		//GetFile.getfiles(get_trans_ftp,0);	
		
	}
	
	@Test
	public void  testListLocalFiles(){
		
		List<Path> paths = new ArrayList<Path>();
		
		Path localPath = Paths.get("D:/FTPTEST");
		
		final Pattern patternJ = Pattern.compile("[\\w]+.txt");
		
		Filter<Path> filter = new Filter<Path>() {
			public boolean accept(Path file) throws IOException {
				
					if (patternJ.matcher(file.getFileName().toString()).matches()) {
						return true;
					}
				return false;
			}
			
		};
		
		SFTPClient client = new SFTPClient();
		
		client.listLocalFiles(paths, filter, localPath);
		
		for(Path p : paths){
			
			System.out.println(p.toString());
			
		}
	}
	
	@Test
	public void sftpGetTest(){
		
		SFTPClient client = RemotingFactory.newSFTPClient();
		GetConfig config = new GetConfig();
		config.setHost_name("101.200.145.190");
		config.setPort("22");
		config.setUser_name("newbilling");
		config.setPassword("1qaz2wsx");
		config.setDst_dir("D:/FTPTEST");
		config.setSrc_dir("/app/ftpdata/QNTEPROD/HD/SJ/downfiles");
		config.setTime_out(6000);
		config.setPattern("*.txt");
		client.setConfig(config);
		GetFile getFile = new GetFile();
		getFile.getfiles(client, 1);
	}
	
	@Test
	public void ftpGetTest(){
		
		BaseFTPClient client = RemotingFactory.newFTPClient();
		GetConfig config = new GetConfig();
		config.setHost_name("101.200.177.101");
		config.setPort("21");
		config.setUser_name("test");
		config.setPassword("hfo8615");
		config.setDst_dir("D:/FTPTEST");
		config.setSrc_dir("/home/test/");
		config.setTime_out(6000);
		config.setPattern("*.txt");
		client.setConfig(config);
		GetFile getFile = new GetFile();
		getFile.getfiles(client, 0);
	}

}
