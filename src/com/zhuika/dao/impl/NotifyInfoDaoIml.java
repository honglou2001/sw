package com.zhuika.dao.impl;

import com.users.ejb.NotifyInfo;
import com.users.ejb.NotifyInfoService;
import com.zhuika.util.ejbproxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;


/**
 * <p>Title: ejb title </p>
 * <p>Description: t_notify_info Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-8-30 14:29:50
 */

public class NotifyInfoDaoIml {	

  	public void AddNotifyInfo(NotifyInfo notifyInfo) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		NotifyInfoService serviceClient;
		try {
			serviceClient = (NotifyInfoService) weblogicContext.lookup("NotifyInfoBean/remote");
			serviceClient.AddNotifyInfo(notifyInfo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetNotifyInfoCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		NotifyInfoService serviceClient;
		try {
			serviceClient = (NotifyInfoService)weblogicContext.lookup("NotifyInfoBean/remote");
			total = serviceClient.GetNotifyInfoCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<NotifyInfo> ListNotifyInfo(int offset, int length,HashMap<String, String> map)
	{		
		List<NotifyInfo> listNotifyInfo =  new ArrayList<NotifyInfo>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			NotifyInfoService serviceClient = (NotifyInfoService)weblogicContext.lookup("NotifyInfoBean/remote");
			listNotifyInfo = serviceClient.ListNotifyInfo(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listNotifyInfo;		
	}
    
    public NotifyInfo findNotifyInfo(String fid)
	{		
		NotifyInfo notifyInfo = new NotifyInfo();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			NotifyInfoService serviceClient = (NotifyInfoService)weblogicContext.lookup("NotifyInfoBean/remote");
			notifyInfo = serviceClient.findNotifyInfo(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return notifyInfo;		
	}
    
  	public void UpdateNotifyInfo(NotifyInfo notifyInfo) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		NotifyInfoService serviceClient;
		try {
			serviceClient = (NotifyInfoService)weblogicContext.lookup("NotifyInfoBean/remote");
			serviceClient.UpdateNotifyInfo(notifyInfo);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteNotifyInfo(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		NotifyInfoService serviceClient;
		try {
			serviceClient = (NotifyInfoService) weblogicContext
					.lookup("NotifyInfoBean/remote");
			serviceClient.DeleteNotifyInfo(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}