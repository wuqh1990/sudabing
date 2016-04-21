package com.bj.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemCodeCacher {
	
	//FTP默认端口
	public static final String FTP_DEFAULT_PORT = "FTP_DEFAULT_PORT";
	public static final int FTP_DEFAULT_PORT_VALUE = 21;
	
	//SFTP默认端口
	public static final String SFTP_DEFAULT_PORT = "SFTP_DEFAULT_PORT";
	public static final int SFTP_DEFAULT_PORT_VALUE = 22;	
	
	//设置超时时间
	public static final int TIME_OUT = 6000;
	
	public static final int FTP_FLAG = 0;  //ftp方式
	
	public static final int SFTP_FLAG = 1;  //sftp方式
	
	//ft_info_data
	public static final String SRC_ID = "SRC_ID";   //
	public static final String LOG_TABLE_NAME = "LOG_TABLE_NAME"; //日志表名
	public static final String RULE_TMP_DIR = "RULE_TMP_DIR"; //临时目录路径
	
	//ft_info_host
	public static final String SRC_HOST_ID = "SRC_HOST_ID"; //源主机ID
	public static final String SRC_HOST_ADDRESS = "SRC_HOST_ADDRESS"; //源主机地址 ip_address
	public static final String SRC_HOST_PORT = "SRC_HOST_PORT"; //源主机端口
	public static final String SRC_LOGIN_USER = "SRC_LOGIN_USER";//源主机登录用户
	public static final String SRC_LOGIN_PASSWORD = "SRC_LOGIN_PASSWORD";//源主机登录密码
	public static final String SRC_HOST_TYPE = "SRC_HOST_TYPE"; //源主机类型 采集使用 ftp 还是sftp
	public static final String SRC_TRANS_MODE = "SRC_TRANS_MODE";//wait add 
	public static final String SRC_DIR = "SRC_DIR"; //源文件路径
	public static final String SRC_PATTERN = "SRC_PATTERN"; //源文件路径校验正则

	public static final String DST_HOST_ID = "DST_HOST_ID"; //目标主机ID
	public static final String DST_HOST_ADDRESS = "DST_HOST_ADDRESS"; //目标主机地址 ip_address
	public static final String DST_HOST_PORT = "DST_HOST_PORT"; //目标主机端口号
	public static final String DST_LOGIN_USER = "DST_LOGIN_USER";//目标主机登录用户
	public static final String DST_LOGIN_PASSWORD = "DST_LOGIN_PASSWORD"; //目标主机登录密码
	public static final String DST_HOST_TYPE = "DST_HOST_TYPE"; //目标主机类型 采集使用 ftp 还是sftp
	public static final String DST_TRANS_MODE = "DST_TRANS_MODE"; //wait add
	public static final String RULE_DST_DIR = "RULE_DST_DIR"; //目标文件路径

	
	//conf_host_para
	public static final String HOST_ID = "HOST_ID";//应用主机ID
	public static final String HOST_IP = "HOST_IP";//
	public static final String TCP_IP = "TCP_IP";
	public static final String TCP_PORT = "TCP_PORT";
	public static final String UDP_PORT = "UDP_PORT";
	public static final String LOG_PATH = "LOG_PATH";
	public static final String SWITCH_MODE = "SWITCH_MODE";
	public static final String USER_NAME = "USER_NAME";
	public static final String CONNECT_STRING = "CONNECT_STRING";
	
	
	public static final String FTP_GET = "FTP_GET"; //get方式
	
	public static final String FTP_PUT = "FTP_PUT"; //put方式
	
	public static final String FTP_GET_PUT = "FTP_GET_PUT"; //get-put方式
	
	
	
	public static Map<String,Object> host_parm = new ConcurrentHashMap<String, Object>();
	
	public static Map<String,Object> ft_parm = new ConcurrentHashMap<String,Object>();
	
	public static Object getHostParmValue(String key){
		return host_parm.get(key);
	}
	
	public static Object getFtParmValue(String key){
		return ft_parm.get(key);
	}
	
}
