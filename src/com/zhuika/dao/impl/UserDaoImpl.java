package com.zhuika.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.watch.ejb.FriendRelation;
import com.watch.ejb.FriendRelationService;
import com.watch.ejb.UserWatch;
import com.watch.ejb.UserWatchService;
import com.zhuika.dao.IUserDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.User;
import com.zhuika.util.ejbproxy;

@Repository("userDao")
public class UserDaoImpl extends SHBaseDAO implements IUserDao {
    
	public User findByNameAndPwd(String userName, String password) {
		String hql="from User where userName=? and password=?";
		Object[] params={userName,password};
		@SuppressWarnings("unchecked")
		List<User> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
  	
	public User findById(int id) {
	    User user = (User)this.getHibernateTemplate().get(User.class, id);
	    return user;
	}
	public void delete(Integer id) {
		User user=new User();
		user.setId(1);
		super.getHibernateTemplate().delete(user);
	}
	public List<User> findAll() {
		String hql  = "from User";
		@SuppressWarnings("unchecked")
		List<User> list = 
			super.getHibernateTemplate().find(hql);
		return list;
	}
	public User findByName(String userName) {
		String hql="from User where userName=?";
		Object[] params={userName};
		@SuppressWarnings("unchecked")
		List<User> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public void save(User user) {
		super.getHibernateTemplate().save(user);
		
//		Integer id = user.getId();  
	}
	public User findByPhone(String phone) {
		String hql="from User where fmobile=?";
		Object[] params={phone};
		@SuppressWarnings("unchecked")
		List<User> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public User findBySerialNumber(String serialNumber) {
		String hql="from User where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<User> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public static void main(String[] args) {
		User user=new User();
		user.setSerialNumber("131");
		user.setUserName("ww");
		user.setPhone("13512345612");
		user.setPassword("123456");
		user.setSex("male");
	    String conf = "applicationContext.xml";
	    ApplicationContext ac =
	           new ClassPathXmlApplicationContext(conf);
	    
	    IUserDao dao=(IUserDao) ac.getBean("userDao");
		dao.save(user);
	}
	public void update(User user) {
		super.getHibernateTemplate().update(user);		
	}
	public void deleteBySerialNumber(String serialNumber) {
		String hql="from User where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<User> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			
		}	
	}
	public void deleteBySerialNumber(User user) {
		super.getHibernateTemplate().delete(user);	
	}
	
  	public void Add(UserWatch user) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserWatchService serviceClient;
		try {
			serviceClient = (UserWatchService) weblogicContext.lookup("UserWatchBean/remote");
			serviceClient.Add(user);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
  	
  	
	public List<UserWatch> GetAll(int offset, int length,HashMap<String, String> map)
	{		
		List<UserWatch> listUser =  new ArrayList<UserWatch>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserWatchService serviceClient = (UserWatchService)weblogicContext.lookup("UserWatchBean/remote");
			listUser = serviceClient.ListUser(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listUser;		
	}
	
    public UserWatch Find(String fid)
	{		
    	UserWatch user = new UserWatch();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserWatchService serviceClient = (UserWatchService)weblogicContext.lookup("UserWatchBean/remote");
			user = serviceClient.find(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return user;		
	}
    
  	public void Update(UserWatch user) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserWatchService serviceClient;
		try {
			serviceClient = (UserWatchService)weblogicContext.lookup("UserWatchBean/remote");
			serviceClient.Update(user);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void Add(FriendRelation friendRelationInfo) {
		// TODO Auto-generated method stub
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendRelationService serviceClient;
		try {
			serviceClient = (FriendRelationService) weblogicContext.lookup("FriendRelationBean/remote");
			serviceClient.Add(friendRelationInfo);
		} catch (NamingException e) {
			e.printStackTrace();
		}	
	}


	@Override
	public void Update(FriendRelation friendRelationInfo) {
		// TODO Auto-generated method stub
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendRelationService serviceClient;
		try {
			serviceClient = (FriendRelationService)weblogicContext.lookup("FriendRelationBean/remote");
			serviceClient.Update(friendRelationInfo);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	@Override
	public void Delete(String id) {
		// TODO Auto-generated method stub
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendRelationService serviceClient;
		try {
			serviceClient = (FriendRelationService) weblogicContext
					.lookup("FriendRelationBean/remote");
			serviceClient.Delete(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public FriendRelation find(String id) {
		FriendRelation friendRelation = new FriendRelation();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			FriendRelationService serviceClient = (FriendRelationService)weblogicContext.lookup("FriendRelationBean/remote");
			friendRelation = serviceClient.find(id);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return friendRelation;
	}


	@Override
	public int GetCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<FriendRelation> ListFriendRelation(int offset, int length,
			HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<FriendRelation> listFriendRelation =  new ArrayList<FriendRelation>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			FriendRelationService serviceClient = (FriendRelationService)weblogicContext.lookup("FriendRelationBean/remote");
			listFriendRelation = serviceClient.ListFriendRelation(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listFriendRelation;	
	}   
}
