package com.tydic.beijing.billing.util;

import java.io.File;

import org.junit.Test;

public class CreateFileThread {
	
	@Test
	public void  createFile(){
		
		try {
			int i = 0;
			while(true){
				File file = new File("D:/FTPTEST/aa" + i++ + ".txt" );
				file.createNewFile();
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
