package com.zhuika.util;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import com.users.ejb.EjbEnum;
import com.users.ejb.EjbEnum.SNParmEnum;
import com.watch.ejb.LocElectfence;
import com.watch.ejb.SerialnumberParam;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.processors.PropertyNameProcessor;

public class Jsonconf {

//	public void ll() {
//		JsonConfig jsonConfig = new JsonConfig();
//
//		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
//				new JsonValueProcessor() {
//					private SimpleDateFormat sd = new SimpleDateFormat(
//							"yyyy-MM-dd HH:mm:ss");// new
//													// SimpleDateFormat("yyyy-MM-dd");
//
//					public Object processObjectValue(String key, Object value,
//							JsonConfig jsonConfig) {
//						return value == null ? "" : sd.format(value);
//					}
//
//					public Object processArrayValue(Object value,
//							JsonConfig jsonConfig) {
//						return null;
//					}
//				});
//	}
//	
	public static JsonConfig getCommonJsonConf(){
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new JsonValueProcessor() {
					private SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// new
													// SimpleDateFormat("yyyy-MM-dd");

					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						return value == null ? "" : sd.format(value);
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});
		
		return jsonConfig;
	}
	
	public static void setParmJsonMapName(JsonConfig jConfig,final int category){
		jConfig.registerJsonPropertyNameProcessor(SerialnumberParam.class, new PropertyNameProcessor() {
				
				@Override
				public String processPropertyName(Class beanClass, String name) {
					
					if(category==SNParmEnum.silent.value()){
						if(name.equals("fchar1")){
							return "begintime";
						}
						if(name.equals("fchar2")){
							return "endtime";
						}
						if(name.equals("flong1")){
							return "weekAndValid";
						}
					}
					else if(category==SNParmEnum.callback.value()){
						if(name.equals("fchar1")){
							return "calltype";
						}
					
					}
					return name;
				}
			 });		
		
	}


	public static JsonConfig getSertialNumJsonConf(HttpServletRequest req){
		final String  imgUrl = Tools.getHost(req);				
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new JsonValueProcessor() {
					private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					public Object processObjectValue(String key,Object value, JsonConfig jsonConfig) {
						return value == null ? "" : sd.format(value);
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});
		
		jsonConfig.registerJsonValueProcessor(String.class,
				new JsonValueProcessor() {
					public Object processObjectValue(String key,Object value, JsonConfig jsonConfig) {								
					  if (key.equals("fpicture"))
						  	if (value ==null || value.toString().equals(""))
						  	{
						  		return "";
						  	}else{
						  		return imgUrl+value;
						  	}
                     else
                            return value;
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});	
		
		return jsonConfig;

	}
	
	public static JsonConfig getUserJsonConf(HttpServletRequest req){
		final String  imgUrl = Tools.getHost(req);				
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new JsonValueProcessor() {
					private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					public Object processObjectValue(String key,Object value, JsonConfig jsonConfig) {
						return value == null ? "" : sd.format(value);
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(String.class,
				new JsonValueProcessor() {
					public Object processObjectValue(String key,Object value, JsonConfig jsonConfig) {								
					  if (key.equals("picture")|| key.equals("frompic") || key.equals("topic"))
						  	if (value ==null || value.toString().equals(""))
						  	{
						  		return "";
						  	}else{
						  		return imgUrl+value;
						  	}
                     else
                            return value;
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});	
		
		return jsonConfig;

	}
	public static JsonConfig getSerialnumJsonConf(HttpServletRequest req){

		final String  imgUrl = Tools.getHost(req);	
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,
				new JsonValueProcessor() {
					private SimpleDateFormat sd = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// new
													// SimpleDateFormat("yyyy-MM-dd");

					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						return value == null ? "" : sd.format(value);
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(String.class,
				new JsonValueProcessor() {
					public Object processObjectValue(String key,Object value, JsonConfig jsonConfig) {								
					  if (key.equals("fpicture"))
						  	if (value ==null || value.toString().equals(""))
						  	{
						  		return "";
						  	}else{
						  		return imgUrl+value;
						  	}
                     else
                            return value;
					}

					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						return null;
					}
				});	
		
		return jsonConfig;
	}
}
