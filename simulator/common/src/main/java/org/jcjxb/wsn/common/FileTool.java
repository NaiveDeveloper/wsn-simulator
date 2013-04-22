package org.jcjxb.wsn.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileTool {

	public static void storeFile(String dir, String fileName, byte[] data) {
		File dirFile = new File(dir);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File dataFile = new File(dirFile, fileName);
		try {
			OutputStream out = new FileOutputStream(dataFile);
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
