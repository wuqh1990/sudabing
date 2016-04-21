package com.bj.remote.impl;

import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.bj.domain.MemCodeCacher;
import com.bj.remote.BaseRemoteClient;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPClient extends BaseRemoteClient {

	public static final Logger log = Logger.getLogger(SFTPClient.class);

	private static Session session = null;
	
	private static ChannelSftp channel = null;
	
	public SFTPClient(){
	}

	public ChannelSftp getChannel(){
		if (channel == null) {
			try {
				JSch jsch = new JSch(); // 创建JSch对象
				session = jsch.getSession(remoteConfig.getUser_name(), remoteConfig.getHost_name(),
						Integer.valueOf(remoteConfig.getPort())); // 根据用户名，主机ip，端口获取一个Session对象
				log.info("SFTP session  has been  created !");
				session.setPassword(remoteConfig.getPassword()); // 设置密码
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no"); // 设置该参数 跳过RSA key fingerprint输入yes/no
				session.setConfig(config); // 为Session对象设置properties
				session.setTimeout(remoteConfig.getTime_out()); // 设置timeout时间
				session.connect(); // 通过Session建立链接
				log.info("SFTP session  has been connected !");

				log.info("Start Opening Channel ....");
				channel = (ChannelSftp) session.openChannel("sftp"); // 打开SFTP通道
				channel.connect(); // 建立SFTP通道的连接
				log.info("Channel has been opened!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return  channel;
	}

	public void closeChannel() throws Exception {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	
	@SuppressWarnings("unchecked")
	public void getfiles() {
		
		try {
			ChannelSftp sftp = getChannel();
			log.info(remoteConfig.getSrc_dir()+"/*.txt");
			log.info(remoteConfig.getDst_dir());
			
			Vector<LsEntry> files = sftp.ls(remoteConfig.getSrc_dir()+"/*.txt");
			for(LsEntry e:files){
				log.info("attrs:" + e.getAttrs());
				log.info("longname:" + e.getLongname());
				log.info("filename:" + e.getFilename());
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public int getflag() {
		return MemCodeCacher.SFTP_FLAG;
	}

}
