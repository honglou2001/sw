package com.zhuika.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.users.ejb.SerialnumberJpush;
import com.watch.ejb.UserWatch;
import com.zhuika.service.SerialNumberService;
import com.zhuika.service.UserService;
import com.zhuika.util.Jsonconf;
import com.zhuika.util.Tools;


public class PushAction  extends BaseAction implements ServletRequestAware,ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
//	private static final String APP_KEY ="3e01f651247e71e14e3b4cec";
//	private static final String MASTER_SECRET = "e95a9dee702e4fcbf4f51467";
	
	private static final String APP_KEY ="e1569bdd69a17c156b6cab08";
	private static final String MASTER_SECRET = "18ed902faa3d67976582dcb2";
	
	private static final String defaultTag = "1001";


	@Resource
	private UserService userService;
	
	public void setServletRequest(HttpServletRequest request) {
	this.request=request;		
	}
	
	public void setServletResponse(HttpServletResponse response) {
	this.response=response;	
	}
	
	//查询用户id的别名及标签
	public void queryInfo(){
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			
			String action=request.getParameter("action");	
			
			int nAction = Integer.parseInt(action);
			
			String usrid=request.getParameter("usrid");	
			String alias = usrid.replace("-", "_");
			
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if (user == null) {
				
				json.put("state", 2);
				json.put("info", "此用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("FUserID", usrid);
			

			String tag = "";
			
			String info = "成功查询用户推送配置信息,首次查询";
			
			List<SerialnumberJpush> lists = this.userService.ListSerialnumberJpush(0, 100, map);
			if(lists!=null && lists.size()==0)
			{			
				SerialnumberJpush pushObj = new SerialnumberJpush();
				pushObj.setFuserid(usrid);
				pushObj.setFalias(alias);
				pushObj.setFtag(defaultTag);
				
				if(pushObj.getFdatastatus()==null)
				{
					pushObj.setFdatastatus(nAction);
				}
				else
				{
					pushObj.setFdatastatus(nAction|pushObj.getFdatastatus());
				}

				userService.AddSerialnumberJpush(pushObj);								
				tag = defaultTag;				
			}
			else
			{
				SerialnumberJpush pushObj = lists.get(0);
				
				if(pushObj.getFdatastatus()==null)
				{
					pushObj.setFdatastatus(nAction);
				}
				else
				{
					pushObj.setFdatastatus(nAction|pushObj.getFdatastatus());
				}

				userService.UpdateSerialnumberJpush(pushObj);				
				alias = pushObj.getFalias();
				tag = pushObj.getFtag();				
				info = "成功查询用户推送配置信息";
			
			}
			
			json.put("state", 1);
			json.put("info", info);
			json.put("alias", alias);
			json.put("tag", tag);
					
		
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
			json.put("data", null);
		} finally{	
			out.print(json);
			out.close();
		}
	}
	public void setInfo(){
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
			String alias=request.getParameter("alias");	
			String tag=request.getParameter("tag");	
			String usrid=request.getParameter("usrid");	
			UserWatch user =null;
			if(usrid!=null && usrid.length()>0){
				user = userService.Find(usrid);
			}
			
			if(alias==null || alias.equals("")){
				json.put("state", 4);
				json.put("info", "别名不能为空");		
				out.print(json);
				out.close();				
				return ;
			}
			
			if (user == null) {
				
				json.put("state", 2);
				json.put("info", "此用户不存在");		
				out.print(json);
				out.close();				
				return ;
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("FUserID", usrid);
			
			String info= "成功新增用户别名,标签信息";
			SerialnumberJpush pushObj  = null;
			List<SerialnumberJpush> lists = this.userService.ListSerialnumberJpush(0, 100, map);
			if(lists!=null && lists.size()==1)
			{
				alias = alias.replace("-", "_");
				pushObj = lists.get(0);
				pushObj.setFalias(alias);
				pushObj.setFtag(tag);
				userService.UpdateSerialnumberJpush(pushObj);
				info = "服务器已存在此用户id的配置信息，成功修改。";			
			}
			else{
			
				pushObj = new SerialnumberJpush();
				pushObj.setFuserid(usrid);
				pushObj.setFalias(alias);
				pushObj.setFtag(tag);
				userService.AddSerialnumberJpush(pushObj);
			}
				
			json.put("state", 1);
			json.put("info", info);			
			JsonConfig  jconfig = Jsonconf.getCommonJsonConf();		
			json.put("data", JSONArray.fromObject(pushObj, jconfig));		
		
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
		} finally{	
			out.print(json);
			out.close();
		}
	}
	
	public void pushMsg(){
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/json"); 
			response.setCharacterEncoding("utf-8");
			out=response.getWriter();
					
			String mastersecret=request.getParameter("mastersecret");
			String appkey=request.getParameter("appkey");
			
			String type=request.getParameter("type");
			String toalias=request.getParameter("toalias");	
			String totag=request.getParameter("totag");				
			String title= Tools.DecodeUtf8String(request.getParameter("title"));
			String content= Tools.DecodeUtf8String(request.getParameter("content"));
			
        	if(title!=null && title.equals(""))
        	{
        		title = null;
        	}
			String MASTER_SECRET1 = MASTER_SECRET;
			String APP_KEY1= APP_KEY;
			
			if(mastersecret!=null && appkey!=null && mastersecret.length()>0&&appkey.length()>0)
			{
				MASTER_SECRET1 = mastersecret;
				APP_KEY1 = appkey;
				
			}
			
	        PushClient pushClient = new PushClient(MASTER_SECRET1, APP_KEY1);
	        PushResult result =null;
	
	        if(type.equals("1")){
	        	 PushPayload payload =JPushClient.buildPushObject_all_all_alert(content);
	        	 result =pushClient.sendPush(payload);
	        }
	        else  if(type.equals("2")){

	        	PushPayload payload=JPushClient.buildPushObject_allAlias_audienceMore_alertWithExtras(toalias,title,content);
	        	result =pushClient.sendPush(payload);
	        }
	        else  if(type.equals("3")){
	        	PushPayload payload=JPushClient.buildPushObject_allTag_audienceMore_alertWithExtras(totag,title,content);
	        	result =pushClient.sendPush(payload);
	        }
	        else  if(type.equals("4")){
	        	PushPayload payload=JPushClient.buildPushObject_all_messageWithExtras(content);
	        	result =pushClient.sendPush(payload);
	        }	        
	        else  if(type.equals("5")){
	        	PushPayload payload=JPushClient.buildPushObject_allAlias_audienceMore_messageWithExtras(toalias,title,content);
	        	result =pushClient.sendPush(payload);
	        }
	        else  if(type.equals("6")){
	        	PushPayload payload=JPushClient.buildPushObject_allTag_audienceMore_messageWithExtras(totag,title,content);
	        	result =pushClient.sendPush(payload);
	        }
	        
			json.put("state", 1);
			json.put("info", "成功推送消息");
	        json.put("PushResult", result);
		
		} catch (Exception e) {
			json.put("state", -1);
			json.put("info", e.getMessage());
		} finally{	
			out.print(json);
			out.close();
		}
	}
    
}
