package com.tydic.beijing.billing.remote;

import java.util.Vector;

import com.bj.domain.BaseConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
import com.jcraft.jsch.SftpException;

public class SftpTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SftpException {
		
		/*BaseConfig remoteConfig = new BaseConfig();
		remoteConfig.setHost_name("101.200.177.101");
		remoteConfig.setUser_name("root");
		remoteConfig.setPassword("1qaz2WSX");
		remoteConfig.setPort(22);
		
		RemoteSFTP  remoteSFtp = new DefaultRemoteSFTP(remoteConfig);
		ChannelSftp sftp =  remoteSFtp.getChannel(6000);
		
		Vector<Object> fileNames = sftp.ls("/acquire/*.txt");
		
		for(Object e:fileNames){
			
			LsEntry le = (LsEntry) e;
						
			
			System.out.println("fileNmae = " + le.getFilename());
			System.out.println("longNmae = " + le.getLongname());

			
		}
		
		System.out.println(fileNames);*/
		
		
	}
}
