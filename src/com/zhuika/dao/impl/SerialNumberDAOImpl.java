package com.zhuika.dao.impl;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;

import com.users.ejb.SerialnumberDetach;
import com.users.ejb.SerialnumberDetachService;
import com.watch.ejb.Serialnumber;
import com.watch.ejb.SerialnumberService;
import com.zhuika.dao.ISerialNumberDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.SerialNumber;
import com.zhuika.factory.DAOException;
import com.zhuika.util.DBUtil;
import com.zhuika.util.ejbproxy;
@Repository("serialNumberDao")
public class SerialNumberDAOImpl extends SHBaseDAO implements ISerialNumberDao{	    
	public void addSerialNumber(SerialNumber serialNumber) {
		super.getHibernateTemplate().save(serialNumber);
		try {
			System.out.println("123");
			String sql="insert into locationinfo (serialnumber) values(?);";
			Connection con=DBUtil.getConnection();
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setObject(1, serialNumber.getSerialNumber());
			ps.executeUpdate();
		} catch (Exception e) {
			
		} finally{
			DBUtil.close();
		}	
	}

	public SerialNumber findBySerialNumber(String serialNumber) {
		System.out.println("123456789");
		String hql="from SerialNumber where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<SerialNumber> list=super.getHibernateTemplate().find(hql, params);
		System.out.println(list.size());
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public void updateSerialNumber(SerialNumber serialNumber)
			throws DAOException {
		super.getHibernateTemplate().update(serialNumber);	
	}

	public Integer getOnlineNo() {
		String hql="select count(*) from SerialNumber where online=1";
		@SuppressWarnings("rawtypes")
		List list=super.getHibernateTemplate().find(hql);
		return Integer.parseInt(list.get(0).toString());
	}

	public Integer getNotOnlineNo() {
		String hql="select count(*) from SerialNumber where online=0";
		@SuppressWarnings("rawtypes")
		List list=super.getHibernateTemplate().find(hql);
		return Integer.parseInt(list.get(0).toString());
	}

	public void Add(Serialnumber serialnumber) 
	{
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberService serviceClient;
		try {
			serviceClient = (SerialnumberService) weblogicContext.lookup("SerialnumberBean/remote");
			serviceClient.Add(serialnumber);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}	
	public Serialnumber Find(String serialnumid) 
	{
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberService serviceClient;
		try {
			serviceClient = (SerialnumberService) weblogicContext.lookup("SerialnumberBean/remote");
			return  serviceClient.find(serialnumid);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}		
		
		return null;
	}
	
	public Serialnumber findBySNNumber(String serialnumid) 
	{
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberService serviceClient;
		try {
			serviceClient = (SerialnumberService) weblogicContext.lookup("SerialnumberBean/remote");
			List<Serialnumber> lists = serviceClient.findBySNNumber(serialnumid);
			if(lists!=null && lists.size()>0)
			{
				return lists.get(0);
			}
			return null;
			
		} catch (NamingException e) {
			e.printStackTrace();
		}		
		
		return null;
	}
	
	public void Update(Serialnumber serialnumber)
	{
		Context weblogicContext = ejbproxy.getInitialConnection();
		SerialnumberService serviceClient;
		try {
			serviceClient = (SerialnumberService) weblogicContext.lookup("SerialnumberBean/remote");
			serviceClient.Update(serialnumber);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public List<Serialnumber> GetAll(int offset, int length,HashMap<String, String> map)
	{		
		List<Serialnumber> listSerialnumber =  new ArrayList<Serialnumber>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberService serviceClient = (SerialnumberService)weblogicContext.lookup("SerialnumberBean/remote");
			listSerialnumber = serviceClient.ListSerialnumber(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumber;		
	}
	
	@Override
	public List<Serialnumber> ListSerialnumberAll(int offset, int length,HashMap<String, String> map)
	{		
		List<Serialnumber> listSerialnumber =  new ArrayList<Serialnumber>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberService serviceClient = (SerialnumberService)weblogicContext.lookup("SerialnumberBean/remote");
			listSerialnumber = serviceClient.ListSerialnumberAll(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumber;		
	}
    

	@Override
	public List<SerialnumberDetach> ListSerialnumberDetach(int offset,
			int length, HashMap<String, String> map) {
		List<SerialnumberDetach> listSerialnumberDetach =  new ArrayList<SerialnumberDetach>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			SerialnumberDetachService serviceClient = (SerialnumberDetachService)weblogicContext.lookup("SerialnumberDetachBean/remote");
			listSerialnumberDetach = serviceClient.ListSerialnumberDetach(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listSerialnumberDetach;	
	}
}
