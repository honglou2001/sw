package com.zhuika.dao.impl;

import com.users.ejb.SerialnumberFee;
import com.users.ejb.SerialnumberFeeService;
import com.zhuika.dao.ISerialNumberDao;
import com.zhuika.dao.ISerialnumberFeeDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.util.ejbproxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.stereotype.Repository;


/**
 * <p>Title: ejb title </p>
 * <p>Description: t_serialnumber_fee Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-8-31 11:36:49
 */
@Repository("serialnumberFeeDao")
public class SerialnumberFeeDaoIml extends SHBaseDAO implements ISerialnumberFeeDao{	

  	public void AddSerialnumberFee(SerialnumberFee serialnumberFee) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeeService serviceClient;
		try {
			serviceClient = (SerialnumberFeeService) weblogicContext.lookup("SerialnumberFeeBean/remote");
			serviceClient.AddSerialnumberFee(serialnumberFee);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberFeeCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeeService serviceClient;
		try {
			serviceClient = (SerialnumberFeeService)weblogicContext.lookup("SerialnumberFeeBean/remote");
			total = serviceClient.GetSerialnumberFeeCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberFee> ListSerialnumberFee(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberFee> listSerialnumberFee =  new ArrayList<SerialnumberFee>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberFeeService serviceClient = (SerialnumberFeeService)weblogicContext.lookup("SerialnumberFeeBean/remote");
			listSerialnumberFee = serviceClient.ListSerialnumberFee(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberFee;		
	}
    
    public SerialnumberFee findSerialnumberFee(String fid)
	{		
		SerialnumberFee serialnumberFee = new SerialnumberFee();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberFeeService serviceClient = (SerialnumberFeeService)weblogicContext.lookup("SerialnumberFeeBean/remote");
			serialnumberFee = serviceClient.findSerialnumberFee(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberFee;		
	}
    
  	public void UpdateSerialnumberFee(SerialnumberFee serialnumberFee) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeeService serviceClient;
		try {
			serviceClient = (SerialnumberFeeService)weblogicContext.lookup("SerialnumberFeeBean/remote");
			serviceClient.UpdateSerialnumberFee(serialnumberFee);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteSerialnumberFee(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeeService serviceClient;
		try {
			serviceClient = (SerialnumberFeeService) weblogicContext
					.lookup("SerialnumberFeeBean/remote");
			serviceClient.DeleteSerialnumberFee(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public BigDecimal GetSerialnumberFeeBalance(HashMap<String, String> map) {
		BigDecimal balance = null ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeeService serviceClient;
		try {
			serviceClient = (SerialnumberFeeService)weblogicContext.lookup("SerialnumberFeeBean/remote");
			balance = serviceClient.GetSerialnumberFeeBalance(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return balance;
	}    
}
