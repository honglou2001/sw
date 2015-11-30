package com.zhuika.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

import sun.misc.BASE64Decoder;

public class RectifyUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String lng="113.97994";//"113.984794";//"113.97985";113.98009,22.591232
		String lat="22.590942";//"22.587812";//"22.590736";  113.97994,22.590942
		String type="gps";
		System.out.println(getGps(lng, lat,type));
		String str=getGps(lng, lat,type);
		for(int i=0;i<100;i++){
			JSONObject json=JSONObject.fromObject(getGps(lng, lat,type));
			if(json.get("x")!=null&&json.get("y")!=null){
				System.out.println(getFromBASE64((String)json.get("x"))+","+getFromBASE64((String)json.get("y"))+":i:"+i);
			}
		}
		
//		System.out.println(json.get("x"));
//		if(str.split(",").length==3&&"0".equals(str.split(",")[0].split(":")[1])){		
//			String x=str.split(",")[1].split(":")[1].replaceAll("\"", "");
//			String y=str.split(",")[2].split(":")[1].replaceAll("\"", "").replaceAll("}", "");		
//		    String lng1=getFromBASE64(x);
//		    String lat1=getFromBASE64(y);
//		    System.out.println(lng1+","+lat1);
//		}
	}
	//http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=113.97985&y=22.590736
	//http://api.map.baidu.com/geoconv/v1/?coords=114.21892734521,29.575429778924&from=1&to=5&ak=4HG47GkckhK13Gz1fOpfidUt
	public static String getGps(String lng,String lat,String type){
		int from = 0;
		if("gaode".equals(type)){
			from=2;
		}else if("gps".equals(type)){
			from=0;
		}
		InputStream is=null;
 	    URL url;
		try {
			 //url = new URL("http://api.zdoz.net/transgps.aspx?lat="+lat+"&lng="+lon);
			 //01.http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=longitude&y=latitude   
			 //02.from: ��Դ���ϵ   ��0��ʾԭʼGPS��꣬2��ʾGoogle��꣩  �������ϵ
			 //03.to: ת��������  (4���ǰٶ��Լ������������������4���У�  
			 //04.x: ����  
			 //05.y: γ��  

			 url = new URL("http://api.map.baidu.com/ag/coord/convert?from="+from+"&to=4&x="+lng+"&y="+lat);
			 HttpURLConnection con=(HttpURLConnection) url.openConnection();
	         con.connect();
	         is=con.getInputStream();
	         BufferedReader reader=new BufferedReader(new InputStreamReader(is));
             String str=reader.readLine();  
             //�þ��ȷָ�ص���ҳ����  
             //System.out.println(str);
            
             return str;
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	public static String getFromBASE64(String s) { 
		   if (s == null) return null; 
		   BASE64Decoder decoder = new BASE64Decoder(); 
		   try { 
		      byte[] b = decoder.decodeBuffer(s); 
		      return new String(b); 
		   } catch (Exception e) { 
		      return null; 
		   }
    }  
}
