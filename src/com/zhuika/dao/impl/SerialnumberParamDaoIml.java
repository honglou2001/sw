package com.zhuika.dao.impl;


import com.watch.ejb.SerialnumberParam;
import com.watch.ejb.SerialnumberParamService;
import com.zhuika.dao.ISerialnumberParamDao;
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
 * <p>Description: t_serialnumber_param Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-8-19 9:43:46
 */

@Repository("serialNumberParamDao")
public class SerialnumberParamDaoIml  extends SHBaseDAO implements  ISerialnumberParamDao{	

  	public void AddSerialnumberParam(SerialnumberParam serialnumberParam) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberParamService serviceClient;
		try {
			serviceClient = (SerialnumberParamService) weblogicContext.lookup("SerialnumberParamBean/remote");
			serviceClient.AddSerialnumberParam(serialnumberParam);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberParamCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberParamService serviceClient;
		try {
			serviceClient = (SerialnumberParamService)weblogicContext.lookup("SerialnumberParamBean/remote");
			total = serviceClient.GetSerialnumberParamCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberParam> ListSerialnumberParam(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberParam> listSerialnumberParam =  new ArrayList<SerialnumberParam>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberParamService serviceClient = (SerialnumberParamService)weblogicContext.lookup("SerialnumberParamBean/remote");
			listSerialnumberParam = serviceClient.ListSerialnumberParam(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberParam;		
	}
    
    public SerialnumberParam findSerialnumberParam(String fid)
	{		
		SerialnumberParam serialnumberParam = new SerialnumberParam();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberParamService serviceClient = (SerialnumberParamService)weblogicContext.lookup("SerialnumberParamBean/remote");
			serialnumberParam = serviceClient.findSerialnumberParam(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberParam;		
	}
    
  	public void UpdateSerialnumberParam(SerialnumberParam serialnumberParam) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberParamService serviceClient;
		try {
			serviceClient = (SerialnumberParamService)weblogicContext.lookup("SerialnumberParamBean/remote");
			serviceClient.UpdateSerialnumberParam(serialnumberParam);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteSerialnumberParam(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberParamService serviceClient;
		try {
			serviceClient = (SerialnumberParamService) weblogicContext
					.lookup("SerialnumberParamBean/remote");
			serviceClient.DeleteSerialnumberParam(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}