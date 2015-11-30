package com.zhuika.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class GaodeRectifyUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String lng="113.97985";
		String lat="22.590736";
		JSONObject json=JSONObject.fromObject(getGps(lng, lat,"gps"));
		System.out.println(json.get("locations"));		
	}
	public static String getGps(String lng,String lat,String type){
		InputStream is=null;
 	    URL url;
		try {
			 url = new URL("http://restapi.amap.com/v3/assistant/coordinate/convert?locations="+lng+","+lat+"&coordsys="+type+"&output=json&key=88edc8fc9766ba003dd6f812bff86623");
			 HttpURLConnection con=(HttpURLConnection) url.openConnection();
	         con.connect();
	         is=con.getInputStream();
	         BufferedReader reader=new BufferedReader(new InputStreamReader(is));
             String str=reader.readLine();  
             //System.out.println(str);
             return str;
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
