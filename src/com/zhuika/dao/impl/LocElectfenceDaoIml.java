package com.zhuika.dao.impl;

import com.watch.ejb.LocElectfence;
import com.watch.ejb.LocElectfenceService;
import com.zhuika.util.ejbproxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;




/**
 * <p>Title: ejb title </p>
 * <p>Description: t_loc_electfence Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-6-25 10:05:12
 */

public class LocElectfenceDaoIml {	

  	public void Add(LocElectfence locElectfence) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		LocElectfenceService serviceClient;
		try {
			serviceClient = (LocElectfenceService) weblogicContext.lookup("LocElectfenceBean/remote");
			serviceClient.Add(locElectfence);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		LocElectfenceService serviceClient;
		try {
			serviceClient = (LocElectfenceService)weblogicContext.lookup("LocElectfenceBean/remote");
			total = serviceClient.GetCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	
	public List<LocElectfence> GetAllData(int offset, int length,HashMap<String, String> map)
	{		
		List<LocElectfence> listLocElectfence =  new ArrayList<LocElectfence>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			LocElectfenceService serviceClient = (LocElectfenceService)weblogicContext.lookup("LocElectfenceBean/remote");
			listLocElectfence = serviceClient.ListLocElectfence(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listLocElectfence;		
	}
	

	public List<LocElectfence> GetAll(int offset, int length,HashMap<String, String> map)
	{		
		List<LocElectfence> listLocElectfence =  new ArrayList<LocElectfence>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			LocElectfenceService serviceClient = (LocElectfenceService)weblogicContext.lookup("LocElectfenceBean/remote");
			listLocElectfence = serviceClient.ListLocElectfenceTop1(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();

			  }
//		 catch (Exception ne) {
//			   // TODO: handle exception
//			   System.err.println("Exception EJB："+ne.toString());
//			   ne.printStackTrace();
//			  }
		
		return listLocElectfence;		
	}
    
    public LocElectfence Find(String fid)
	{		
		LocElectfence locElectfence = new LocElectfence();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			LocElectfenceService serviceClient = (LocElectfenceService)weblogicContext.lookup("LocElectfenceBean/remote");
			locElectfence = serviceClient.find(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return locElectfence;		
	}
    
  	public void Update(LocElectfence locElectfence) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		LocElectfenceService serviceClient;
		try {
			serviceClient = (LocElectfenceService)weblogicContext.lookup("LocElectfenceBean/remote");
			serviceClient.Update(locElectfence);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void Delete(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		LocElectfenceService serviceClient;
		try {
			serviceClient = (LocElectfenceService) weblogicContext
					.lookup("LocElectfenceBean/remote");
			serviceClient.Delete(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取最新定位
	public List<LocElectfence> ListLatestLocaltion(HashMap<String, String> map)
	{		
		List<LocElectfence> listLocElectfence =  new ArrayList<LocElectfence>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			LocElectfenceService serviceClient = (LocElectfenceService)weblogicContext.lookup("LocElectfenceBean/remote");
			listLocElectfence = serviceClient.ListLatestLocaltion(map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();

			  }

		return listLocElectfence;		
	}
    	
}