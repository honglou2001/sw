package com.zhuika.dao.impl;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.zhuika.util.ejbproxy;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.NamingException;

import com.watch.ejb.UserAlarm;
import com.watch.ejb.UserAlarmService;
import com.zhuika.dao.IAlarmDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.Alarm;

@Repository("alarmDao")
public class AlarmDaoImpl extends SHBaseDAO implements IAlarmDao {

	public void save(Alarm alarm) {
		super.getHibernateTemplate().save(alarm);

	}

	public void update(Alarm alarm) {
		super.getHibernateTemplate().update(alarm);
	}

	@SuppressWarnings("unchecked")
	public Alarm findByName(String serialNumber) {
		String hql="from Alarm where serialNumber=?";
		Object[] params={serialNumber};
		List<Alarm> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public static void main(String[] args) {
		AlarmDaoImpl adi=new AlarmDaoImpl();
		System.out.println(adi.findByName("80680000000006"));
	}

	public void Add(UserAlarm userAlarm) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserAlarmService serviceClient;
		try {
			serviceClient = (UserAlarmService) weblogicContext.lookup("UserAlarmBean/remote");
			serviceClient.Add(userAlarm);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserAlarmService serviceClient;
		try {
			serviceClient = (UserAlarmService)weblogicContext.lookup("UserAlarmBean/remote");
			total = serviceClient.GetCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<UserAlarm> ListUserAlarm(int offset, int length,HashMap<String, String> map)
	{		
		List<UserAlarm> listUserAlarm =  new ArrayList<UserAlarm>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserAlarmService serviceClient = (UserAlarmService)weblogicContext.lookup("UserAlarmBean/remote");
			listUserAlarm = serviceClient.ListUserAlarm(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listUserAlarm;		
	}
    
    public UserAlarm find(String fid)
	{		
		UserAlarm userAlarm = new UserAlarm();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserAlarmService serviceClient = (UserAlarmService)weblogicContext.lookup("UserAlarmBean/remote");
			userAlarm = serviceClient.find(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return userAlarm;		
	}
    
  	public void Update(UserAlarm userAlarm) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserAlarmService serviceClient;
		try {
			serviceClient = (UserAlarmService)weblogicContext.lookup("UserAlarmBean/remote");
			serviceClient.Update(userAlarm);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void Delete(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserAlarmService serviceClient;
		try {
			serviceClient = (UserAlarmService) weblogicContext
					.lookup("UserAlarmBean/remote");
			serviceClient.Delete(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	 
}
