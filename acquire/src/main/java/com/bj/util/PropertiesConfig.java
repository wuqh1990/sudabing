package com.bj.util;

import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesConfig {

	public static final Logger log = Logger.getLogger(PropertiesConfig.class);

	private static Properties conf = new Properties();

	static {
		InputStream is = null;
		try {
			is = ClassLoader.getSystemResourceAsStream("acquire.properties");
			if(is == null)
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream("rating.properties");
			conf.load(is);
			is.close();
			
			for (Entry<Object,Object> entry : conf.entrySet()) {
				log.info("系统配置参数:" + entry.getKey() + "=" + entry.getValue());
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException("加载配置出错:" + e.getMessage(),e);
			
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				log.error("关闭文件/数据库连接时出错：", e);
			}
		}
	}

	public static String getStrParaValue(String name) {
		try {
			return conf.getProperty(name);
		} catch (RuntimeException e) {
			log.error("获取字符串参数出错：" + name);
			throw e;
		}
	}

	public static int getInt(String name) {
		try {
			return Integer.valueOf(conf.getProperty(name)).intValue();
		} catch (Exception e) {
			log.error("系统配置int参数读取错误：" + name, e);
			throw new RuntimeException(e);
		}
	}

	public static boolean getBoolean(String name) {
		try {
			return Boolean.valueOf(conf.getProperty(name)).booleanValue();
		} catch (Exception e) {
			log.error("系统配置boolean参数读取错误：" + name, e);
			throw new RuntimeException(e);
		}
	}

}
