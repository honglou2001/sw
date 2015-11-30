package com.zhuika.dao.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;

import com.watch.ejb.UserSnrelate;
import com.watch.ejb.UserSnrelateService;
import com.zhuika.util.ejbproxy;


/**
 * <p>Title: ejb title </p>
 * <p>Description: t_user_snrelate Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-7-5 16:39:25
 */

public class UserSnrelateDaoIml {	

  	public void Add(UserSnrelate userSnrelate) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserSnrelateService serviceClient;
		try {
			serviceClient = (UserSnrelateService) weblogicContext.lookup("UserSnrelateBean/remote");
			serviceClient.Add(userSnrelate);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserSnrelateService serviceClient;
		try {
			serviceClient = (UserSnrelateService)weblogicContext.lookup("UserSnrelateBean/remote");
			total = serviceClient.GetCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<UserSnrelate> GetAll(int offset, int length,HashMap<String, String> map)
	{		
		List<UserSnrelate> listUserSnrelate =  new ArrayList<UserSnrelate>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserSnrelateService serviceClient = (UserSnrelateService)weblogicContext.lookup("UserSnrelateBean/remote");
			listUserSnrelate = serviceClient.ListUserSnrelate(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listUserSnrelate;		
	}
    
    public UserSnrelate Find(String fid)
	{		
		UserSnrelate userSnrelate = new UserSnrelate();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserSnrelateService serviceClient = (UserSnrelateService)weblogicContext.lookup("UserSnrelateBean/remote");
			userSnrelate = serviceClient.find(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return userSnrelate;		
	}
    
  	public void Update(UserSnrelate userSnrelate) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserSnrelateService serviceClient;
		try {
			serviceClient = (UserSnrelateService)weblogicContext.lookup("UserSnrelateBean/remote");
			serviceClient.Update(userSnrelate);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void Delete(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserSnrelateService serviceClient;
		try {
			serviceClient = (UserSnrelateService) weblogicContext
					.lookup("UserSnrelateBean/remote");
			serviceClient.Delete(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}