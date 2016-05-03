package com.bj.domain;

public class BaseConfig {
	
	
	private String host_name;
	
	private String port;
	
	private String user_name;
	
	private String password;
	
	private String src_dir;
	
	private String dst_dir;
	
	private int time_out;
	

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSrc_dir() {
		return src_dir;
	}

	public void setSrc_dir(String src_dir) {
		this.src_dir = src_dir;
	}

	public String getDst_dir() {
		return dst_dir;
	}

	public void setDst_dir(String dst_dir) {
		this.dst_dir = dst_dir;
	}

	public int getTime_out() {
		return time_out;
	}

	public void setTime_out(int time_out) {
		this.time_out = time_out;
	}
	
}
