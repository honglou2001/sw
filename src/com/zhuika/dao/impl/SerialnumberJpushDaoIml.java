package com.zhuika.dao.impl;


import com.users.ejb.SerialnumberJpush;
import com.users.ejb.SerialnumberJpushService;
import com.zhuika.dao.ISerialnumberFeeDao;
import com.zhuika.dao.ISerialnumberJpushDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.util.ejbproxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.stereotype.Repository;


/**
 * <p>Title: ejb title </p>
 * <p>Description: t_serialnumber_jpush Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-9-21 18:10:53
 */
@Repository("serialnumberPushDao")
public class SerialnumberJpushDaoIml  extends SHBaseDAO implements ISerialnumberJpushDao{	

  	public void AddSerialnumberJpush(SerialnumberJpush serialnumberJpush) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberJpushService serviceClient;
		try {
			serviceClient = (SerialnumberJpushService) weblogicContext.lookup("SerialnumberJpushBean/remote");
			serviceClient.AddSerialnumberJpush(serialnumberJpush);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberJpushCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberJpushService serviceClient;
		try {
			serviceClient = (SerialnumberJpushService)weblogicContext.lookup("SerialnumberJpushBean/remote");
			total = serviceClient.GetSerialnumberJpushCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberJpush> ListSerialnumberJpush(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberJpush> listSerialnumberJpush =  new ArrayList<SerialnumberJpush>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberJpushService serviceClient = (SerialnumberJpushService)weblogicContext.lookup("SerialnumberJpushBean/remote");
			listSerialnumberJpush = serviceClient.ListSerialnumberJpush(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberJpush;		
	}
    
    public SerialnumberJpush findSerialnumberJpush(String fid)
	{		
		SerialnumberJpush serialnumberJpush = new SerialnumberJpush();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberJpushService serviceClient = (SerialnumberJpushService)weblogicContext.lookup("SerialnumberJpushBean/remote");
			serialnumberJpush = serviceClient.findSerialnumberJpush(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberJpush;		
	}
    
  	public void UpdateSerialnumberJpush(SerialnumberJpush serialnumberJpush) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberJpushService serviceClient;
		try {
			serviceClient = (SerialnumberJpushService)weblogicContext.lookup("SerialnumberJpushBean/remote");
			serviceClient.UpdateSerialnumberJpush(serialnumberJpush);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteSerialnumberJpush(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberJpushService serviceClient;
		try {
			serviceClient = (SerialnumberJpushService) weblogicContext
					.lookup("SerialnumberJpushBean/remote");
			serviceClient.DeleteSerialnumberJpush(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}