package com.zhuika.dao.impl;


import com.watch.ejb.FriendContact;
import com.watch.ejb.FriendContactService;
import com.zhuika.dao.IFriendContactDao;
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
 * <p>Description: t_friend_contact Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-8-19 11:42:14
 */
@Repository("friendContactDao")
public class FriendContactDaoIml   extends SHBaseDAO implements  IFriendContactDao{	

  	public void AddFriendContact(FriendContact friendContact) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendContactService serviceClient;
		try {
			serviceClient = (FriendContactService) weblogicContext.lookup("FriendContactBean/remote");
			serviceClient.AddFriendContact(friendContact);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetFriendContactCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendContactService serviceClient;
		try {
			serviceClient = (FriendContactService)weblogicContext.lookup("FriendContactBean/remote");
			total = serviceClient.GetFriendContactCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<FriendContact> ListFriendContact(int offset, int length,HashMap<String, String> map)
	{		
		List<FriendContact> listFriendContact =  new ArrayList<FriendContact>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			FriendContactService serviceClient = (FriendContactService)weblogicContext.lookup("FriendContactBean/remote");
			listFriendContact = serviceClient.ListFriendContact(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listFriendContact;		
	}
    
    public FriendContact findFriendContact(String fid)
	{		
		FriendContact friendContact = new FriendContact();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			FriendContactService serviceClient = (FriendContactService)weblogicContext.lookup("FriendContactBean/remote");
			friendContact = serviceClient.findFriendContact(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return friendContact;		
	}
    
  	public void UpdateFriendContact(FriendContact friendContact) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendContactService serviceClient;
		try {
			serviceClient = (FriendContactService)weblogicContext.lookup("FriendContactBean/remote");
			serviceClient.UpdateFriendContact(friendContact);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteFriendContact(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		FriendContactService serviceClient;
		try {
			serviceClient = (FriendContactService) weblogicContext
					.lookup("FriendContactBean/remote");
			serviceClient.DeleteFriendContact(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}
