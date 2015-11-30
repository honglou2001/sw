package com.zhuika.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;


public class UploadUtil {

	public String[] allowedExt = new String[] { "jpg", "jpeg", "gif","png"};
	
	public String SaveImg(HttpServletRequest httpServletRequest, refData refdata) throws Exception {

		try {
			MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) httpServletRequest;
			File file = wrapper.getFiles("usrimg")[0];
			String fileName = wrapper.getFileNames("usrimg")[0];

			String ext = getFileExtension(fileName).toLowerCase();
			
			int allowFlag = 0;
			int allowedExtCount = allowedExt.length;
			for (; allowFlag < allowedExtCount; allowFlag++) {
				if (allowedExt[allowFlag].toLowerCase().equals(ext)){					
					break;
				}
			}
			if (allowFlag == allowedExtCount) {
				//上传文件后缀不正确
				refdata.setStatus(2);
				return null;
			}			
			// 检查文件大小
			long maxSize = 1024 * 1024 * 1;
			if (file.length() > maxSize) {
				// "上传文件大小超过限制。"
				refdata.setStatus(3);
				return null;
			}
			fileName = refdata.getPicurlid() + "." + ext;
			// 检查扩展名
			String upload = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			upload = upload.replace("/WEB-INF/classes/", "/"+refdata.getImgurl()+"/");
			String path = "/"+refdata.getImgurl()+"/" + fileName;
			String savePath = upload + "/" + fileName;
			InputStream in;
			try {
				in = new FileInputStream(file);
				inputStream2File(in, savePath);				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return path;
		} catch (Exception ex) {
			throw ex;
		}

	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param file
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	private static void inputStream2File(InputStream is, String savePath)
			throws Exception {
		System.out.println("文件保存路径为:" + savePath);
		InputStream inputSteam = null;
		BufferedInputStream fis = null;
		FileOutputStream fos = null;
		File file = new File(savePath);
		try {
			inputSteam = is;
			fis = new BufferedInputStream(inputSteam);
			fos = new FileOutputStream(file);
			int f;
			while ((f = fis.read()) != -1) {
				fos.write(f);
			}
		} finally {
			if (fos != null) {
				fos.flush();
				fos.close();
			}

			if (fis != null) {
				fis.close();
			}
			if (inputSteam != null) {
				inputSteam.close();
			}
		}

	}

	public class refData
	{
		private int status = 1;
		private String imgurl = "upusrimg";
		private String picurlid = "";

		public String getPicurlid() {
			return picurlid;
		}

		public void setPicurlid(String picurlid) {
			this.picurlid = picurlid;
		}

		public String getImgurl() {
			return imgurl;
		}

		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
		
	}
}
