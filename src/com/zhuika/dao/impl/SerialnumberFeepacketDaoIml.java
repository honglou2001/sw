package com.zhuika.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.watch.ejb.SerialnumberFeepacket;
import com.watch.ejb.SerialnumberFeepacketService;
import com.zhuika.dao.ISerialnumberFeePacketDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.util.ejbproxy;


/**
 * <p>Title: ejb title </p>
 * <p>Description: t_serialnumber_feepacket Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-11-7 14:21:23
 */
@Repository("serialnumberFeePacketDao")
public class SerialnumberFeepacketDaoIml  extends SHBaseDAO implements ISerialnumberFeePacketDao{	

  	public void AddSerialnumberFeepacket(SerialnumberFeepacket serialnumberFeepacket) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeepacketService serviceClient;
		try {
			serviceClient = (SerialnumberFeepacketService) weblogicContext.lookup("SerialnumberFeepacketBean/remote");
			serviceClient.AddSerialnumberFeepacket(serialnumberFeepacket);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetSerialnumberFeepacketCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeepacketService serviceClient;
		try {
			serviceClient = (SerialnumberFeepacketService)weblogicContext.lookup("SerialnumberFeepacketBean/remote");
			total = serviceClient.GetSerialnumberFeepacketCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<SerialnumberFeepacket> ListSerialnumberFeepacket(int offset, int length,HashMap<String, String> map)
	{		
		List<SerialnumberFeepacket> listSerialnumberFeepacket =  new ArrayList<SerialnumberFeepacket>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberFeepacketService serviceClient = (SerialnumberFeepacketService)weblogicContext.lookup("SerialnumberFeepacketBean/remote");
			listSerialnumberFeepacket = serviceClient.ListSerialnumberFeepacket(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberFeepacket;		
	}
    
    public SerialnumberFeepacket findSerialnumberFeepacket(String fid)
	{		
		SerialnumberFeepacket serialnumberFeepacket = new SerialnumberFeepacket();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberFeepacketService serviceClient = (SerialnumberFeepacketService)weblogicContext.lookup("SerialnumberFeepacketBean/remote");
			serialnumberFeepacket = serviceClient.findSerialnumberFeepacket(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return serialnumberFeepacket;		
	}
    
  	public void UpdateSerialnumberFeepacket(SerialnumberFeepacket serialnumberFeepacket) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeepacketService serviceClient;
		try {
			serviceClient = (SerialnumberFeepacketService)weblogicContext.lookup("SerialnumberFeepacketBean/remote");
			serviceClient.UpdateSerialnumberFeepacket(serialnumberFeepacket);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteSerialnumberFeepacket(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberFeepacketService serviceClient;
		try {
			serviceClient = (SerialnumberFeepacketService) weblogicContext
					.lookup("SerialnumberFeepacketBean/remote");
			serviceClient.DeleteSerialnumberFeepacket(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}