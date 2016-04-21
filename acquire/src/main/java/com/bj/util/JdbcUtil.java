package com.bj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class JdbcUtil {
	
	public static final Logger log = Logger.getLogger(JdbcUtil.class);
	
	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName(PropertiesConfig.getStrParaValue("jdbc.driver"));
			conn =	DriverManager.getConnection(PropertiesConfig.getStrParaValue("jdbc.url"),PropertiesConfig.getStrParaValue("jdbc.username"),
					PropertiesConfig.getStrParaValue("jdbc.password"));
		} catch (Exception e) {
			log.error("数据库连接异常", e);
		}
		return conn;
	}
	
	
	public static void psClose(PreparedStatement preparedStatement){
			try {
				if(preparedStatement!=null)
					preparedStatement.close();
			} catch (SQLException e) {
				log.error("PreparedStatement关闭异常",e);
			}
	}
	
	public static void connClose(Connection connection){
		
		try {
			if(connection!=null)
				connection.close();
		} catch (SQLException e) {
			log.error("Connection关闭异常", e);
		}
	}
	
	public static void rsClose(ResultSet rs){
		
		try {
			if(rs!=null)
				rs.close();
		} catch (SQLException e) {
			log.error("ResultSet关闭异常", e);
		}
	}
}
