package com.bj.tlv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FileTypeUtils {

	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder strbuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			//System.out.println(v);
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				strbuilder.append(0);
			}
			strbuilder.append(hv);
		}
		return strbuilder.toString().toUpperCase();
	}

	/**
	 * 根据文件流读取图片文件真实类型
	 * 
	 * @param is
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getTypeByStream(FileInputStream is) throws FileNotFoundException {

		byte[] b = new byte[11293437];
		try {
			is.read(b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileCode = bytesToHexString(b);
		//System.out.println("文件十六进制代码：" + fileCode);
		return fileCode;
	}

	/**
	 * 防范用户通过篡改 文件后缀名的方式欺骗服务器,使用二进制流的方式读取文件头文件,将头文件转换为16 进制
	 * 
	 * @param args
	 * @throws IOException
	 */
	
	public static void main(String[] args) throws IOException {
		String src = "D:/dezip/igwb9_ywjs_gjmycfggsn_011-20160331083402-20160331083300-igwb92016033100000035.011.ORG";
		FileInputStream is = new FileInputStream(src);
		String hexString = getTypeByStream(is);
		//System.out.println(hexString);
		Map<String,Tlv> map = TlvUtils.builderTlvMap(hexString);
		System.out.println(map.size());
		
		for(Entry<String,Tlv> e : map.entrySet()){
			//System.out.println(e.getKey());
			Tlv tmp = e.getValue();
			System.out.println(tmp.getTag() + ":" + tmp.getValue() + ":" + tmp.getLength());
		}
		
	}

}
