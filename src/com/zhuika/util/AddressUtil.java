package com.zhuika.util;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class AddressUtil {
	 
	public static String getPoint(String location){ 
    	try {
			location=URLEncoder.encode(location, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
//    	System.out.println("aaaaaaa");
    	
	    HttpClient httpClient = new HttpClient();
	  
	    GetMethod getMethod = new GetMethod("http://api.map.baidu.com/geocoder/v2/?address="+location.trim()+"&output=json&ak=4HG47GkckhK13Gz1fOpfidUt");
	    
	    getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
	      new DefaultHttpMethodRetryHandler());
	    String l;
	    try{
	    	
	    	int statusCode = httpClient.executeMethod(getMethod);
	    	if (statusCode != HttpStatus.SC_OK){
	    		System.err.println("Method failed: "
	    			      + getMethod.getStatusLine());
	    	}
	    	//System.out.println("charset="+getMethod.getResponseCharSet());
	    	
	    	
	    	String str = new String(getMethod.getResponseBodyAsString().getBytes(),"UTF-8");
	    	System.out.println(str);
	    	if(str!=null){
            	String[] ss=str.split(":");
                if(ss.length==5){
               	System.out.println("错误地址");
               	return null;
             }else {
              	String s=","+"\""+"lat"+"\""+":";                         
                String strs[]=str.split(s);
                l=strs[0].split(":")[strs[0].split(":").length-1]+","+strs[1].split("}")[0];
                return l;
             }
          }
	    }catch(HttpException e){
	    	
    	   System.out.println("Please check your provided http address!");
    	   e.printStackTrace();
	    }catch(IOException e){
	    
	    	e.printStackTrace();
	    }finally{
	    	
	    	getMethod.releaseConnection();
	    }
	    return location;
    }
    public static void main(String[] args) {
    	System.out.println(AddressUtil.getPoint("深圳北站"));
		//114.035606,22.613418
		//114.0370710541,22.616274725778
		//114.036937,22.616579
		//114.036856,22.618068
		
	}
}
