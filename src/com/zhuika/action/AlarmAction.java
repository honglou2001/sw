package com.zhuika.action;



import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import com.watch.ejb.Serialnumber;
import com.watch.ejb.UserAlarm;
import com.zhuika.entity.Alarm;
import com.zhuika.factory.DAOException;
import com.zhuika.service.AlarmService;
import com.zhuika.service.SerialNumberService;
import com.zhuika.util.Hex;
import com.zhuika.util.Tools;


@Controller
public class AlarmAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 1L;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Resource
    private AlarmService alarmService;
	@Resource
	private SerialNumberService serialNumService;
    
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response=response;	
	}
	/**
	 * alarm/setAlarm?serialNumber=30020000000010&alarm=06:06.1,09:00.1,10:00.1
	 * 设置修改闹铃
	 */
	public void setAlarm(){
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String serialNumber=request.getParameter("serialNumber");		
			String alarm=request.getParameter("alarm");	
			Alarm a1=alarmService.getAlarm(serialNumber);
			if(a1!=null){
				//修改闹钟
				Alarm a=new Alarm();
				a.setId(a1.getId());
				a.setSerialNumber(serialNumber);
				a.setAlarm(alarm);
				a.setStatus("0");
				alarmService.updateAlarm(a);	
				
				json.put("state", 1);
				json.put("info", "成功更新此手环闹钟设置");
			}else{
				//设置闹钟
				System.out.println("2");
				Alarm a=new Alarm();
				a.setSerialNumber(serialNumber);
				a.setAlarm(alarm);
				a.setStatus("0");
				alarmService.addAlarm(a);	
				
				json.put("state", 1);
				json.put("info", "成功设置手环闹钟");
			}			

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
		} finally{	
			out.print(json);
			out.close();
		}
	}
	
	//配置闹钟
	public void newAlarm()
	{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			
			String serialnumid=request.getParameter("serialnumid");	
			String alarmName=Tools.DecodeUtf8String(request.getParameter("alarmName"));		
			String alarmVal=request.getParameter("alarmVal");	
//			String isValid=request.getParameter("isValid");
						
			Serialnumber serialnumber = serialNumService.Find(serialnumid);
			if(serialnumber == null)
			{								
				json.put("state", 2);
				json.put("info", "找不到此序列号的信息");
				
				out.print(json);
				out.close();
				
				return;
			}
			
			UserAlarm usrAlarm = new UserAlarm();
			usrAlarm.setFname(alarmName);
			usrAlarm.setFsnidstr(serialnumid);
			usrAlarm.setFtime(alarmVal);

			usrAlarm.setFalarmid(UUID.randomUUID().toString());
			
			usrAlarm.setFdatastatus(0);
			
//			String[] timeVal = alarmVal.split(":");
//			if(timeVal!=null && timeVal.length==3)
//			{
//				int t = Integer.parseInt(timeVal[0]); 
//				byte bWeekValid = (byte)t;
//				
//				byte[] weekAndValid = Hex.getBooleanArray(bWeekValid);
//				
//				if(weekAndValid[7]==1)
//				{
//					usrAlarm.setFisvalid(1);
//				}
//			}
			 
			alarmService.Add(usrAlarm);
						
			json.put("state", 1);
			json.put("info", "成功设置闹钟");
			json.put("data",  JSONObject.fromObject(usrAlarm,getJsonConfig()));
			
//			Alarm a1=alarmService.getAlarm(serialNumber);
//			if(a1!=null){
//				//修改闹钟
//				Alarm a=new Alarm();
//				a.setId(a1.getId());
//				a.setSerialNumber(serialNumber);
//				a.setAlarm(alarm);
//				a.setStatus("0");
//				alarmService.updateAlarm(a);	
//				
//				json.put("state", 1);
//				json.put("info", "成功更新此手环闹钟设置");
//			}else{
//				//设置闹钟
//				System.out.println("2");
//				Alarm a=new Alarm();
//				a.setSerialNumber(serialNumber);
//				a.setAlarm(alarm);
//				a.setStatus("0");
//				alarmService.addAlarm(a);	
//				
//				json.put("state", 1);
//				json.put("info", "成功设置手环闹钟");
//			}			

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{	
			out.print(json);
			out.close();
		}
	}
	
	private JsonConfig getJsonConfig()
	{
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

		
		 jsonConfig.setExcludes(new String[] { "faddtime", "ffieldstatus", "fincreaseid", "forder"
				 , "fremark", "fsnidstr", "fupdatetime", "fuseridstr"}); 	
		 return jsonConfig;
	}
	//配置闹钟
	public void updateAlarm()
	{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			
			String alarmid=request.getParameter("alarmid");	
			String alarmName=Tools.DecodeUtf8String(request.getParameter("alarmName"));		
			String alarmVal=request.getParameter("alarmVal");	
//			String isValid=request.getParameter("isValid");

			
			UserAlarm usrAlarm =alarmService.find(alarmid);
			
			if(usrAlarm == null)
			{								
				json.put("state", 2);
				json.put("info", "找不到此id的闹钟信息");
				
				out.print(json);
				out.close();
				
				return;
			}
			
			
			usrAlarm.setFname(alarmName);
			usrAlarm.setFtime(alarmVal);

			
			usrAlarm.setFdatastatus(0);
			
			alarmService.Update(usrAlarm);
			
			json.put("state", 1);
			json.put("info", "成功更新闹钟");
			json.put("data",  JSONObject.fromObject(usrAlarm,getJsonConfig()));
						

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{	
			out.print(json);
			out.close();
		}
	}

	//删除闹钟
	public void deleteAlarm()
	{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			
			String alarmid=request.getParameter("alarmid");	
			
			UserAlarm usrAlarm =alarmService.find(alarmid);
			
			if(usrAlarm == null)
			{								
				json.put("state", 2);
				json.put("info", "找不到此id的闹钟信息");
				
				out.print(json);
				out.close();
				
				return;
			}
						
			alarmService.Delete(alarmid);
			
			json.put("state", 1);
			json.put("info", "成功删除此闹钟");

						
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());

		} finally{	
			out.print(json);
			out.close();
		}
	}
	
	public void ListAlarm()
	{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			
			String serialnumid=request.getParameter("serialnumid");							
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("FSNIDStr", serialnumid);
			
			List<UserAlarm> listAlarm = alarmService.ListUserAlarm(0, 1, map);
			if(listAlarm == null)
			{								
				json.put("state", 2);
				json.put("info", "找不到此序列号id的闹钟信息");				
				out.print(json);
				out.close();				
				return;
			}
			json.put("state", 1);
			json.put("info", "成功查询闹钟信息");
			
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

			
			 jsonConfig.setExcludes(new String[] { "faddtime", "ffieldstatus", "fincreaseid", "forder"
					 , "fremark", "fsnidstr", "fupdatetime", "fuseridstr","fisvalid"}); 			

			json.put("data",  JSONArray.fromObject(listAlarm,jsonConfig));
					

		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{	
			out.print(json);
			out.close();
		}
	}
	/**
	 * 查询闹铃
	 */
	public void searchAlarm() throws DAOException{
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();			
			String serialNumber=request.getParameter("serialNumber");
			Alarm alarm=alarmService.getAlarm(serialNumber);
			JSONArray jsonArr=new JSONArray();
			if(alarm!=null){
				//06:06.1,09:00.1,10:00.1
				String[] str=alarm.getAlarm().split(",");
				for(String s:str){
					JSONObject json1=new JSONObject();
					json1.put("HH", s.substring(0, 2));
					json1.put("mm", s.substring(3, 5));
					json1.put("status", s.substring(6));
					jsonArr.add(json1);
				}				
			}
			json.put("state", 1);
			json.put("info", "成功查询");
			json.put("data", jsonArr);
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{
			out.print(json);
			out.close();
		}
	}
}
