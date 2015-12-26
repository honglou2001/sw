package com.zhuika.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class TextFileReader {

	/**
	 * 
	 * 功能：Java读取txt文件的内容
	 * 
	 * 步骤：1：先获得文件句柄
	 * 
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 
	 * 3：读取到输入流后，需要读取生成字节流
	 * 
	 * 4：一行一行的输出。readline()。
	 * 
	 * 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */

	public static String readTxtFile(String filePath) {
		
		StringBuilder sbuilder = new StringBuilder();
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					sbuilder.append(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}

		} catch (Exception e) {

			System.out.println("读取文件内容出错");

			e.printStackTrace();

		}

		return sbuilder.toString();
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
