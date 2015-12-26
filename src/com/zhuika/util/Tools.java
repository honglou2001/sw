package com.zhuika.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class Tools {

	public static String DecodeUtf8String(String strIn) 
	{
		if(strIn == null || strIn.length() == 0)
			return strIn;
		
		try {
			return new String(strIn.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}  
		
		return strIn;
	}
	
	
	public static boolean checkIsNotEmpty(String strIn)
	{
		if(strIn!=null&& strIn.length() != 0)
		{
			
			return true;
		}
		
		return false;
	}
	
	public static String getUrl(HttpServletRequest request)
	{
		HttpServletRequest httpRequest=(HttpServletRequest)request;
        
		String url = "http://" + request.getServerName() //服务器地址
		                    + ":" 
		                    + request.getServerPort()           //端口号
		                    + httpRequest.getContextPath()      //项目名称
		                    + httpRequest.getServletPath()      //请求页面或其他地址
		        	    + "?" + (httpRequest.getQueryString()); //参数
		return url;
	}
	
	public static String getHost(HttpServletRequest request)
	{
		HttpServletRequest httpRequest=(HttpServletRequest)request;       
		String url = "http://" + request.getServerName() //服务器地址
		                    + ":" 
		                    + request.getServerPort()         //端口号
		                    + httpRequest.getContextPath();

		return url;
	}
	
	public static HashMap<String, String> getUrlParams(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

//        Set<Map.Entry<String, String>> set = map.entrySet();
//        System.out.println("------------------------------");
//        for (Map.Entry entry : set) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//        System.out.println("------------------------------");
        
        return map;
    }
	
	public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        String value = request.getHeader(key);
	        map.put(key, value);
	    }
	    return map;	    	   
	  }
	
	public static StringBuffer getInputStream(HttpServletRequest request) {
		StringBuffer recieveData = new StringBuffer();
		String inputLine = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			while ((inputLine = in.readLine()) != null) {
				recieveData.append(inputLine);
			}
		} catch (Throwable e) {
			//System.out.println("【回调测试】失败");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Throwable e) {
				//System.out.println("【回调测试】关闭失败");
			}
		}
		//System.out.println("回调请求结果"+recieveData);
		
		return recieveData;
 	   
	  }
}
