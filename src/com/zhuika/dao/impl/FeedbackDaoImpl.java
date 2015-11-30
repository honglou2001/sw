package com.zhuika.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.stereotype.Repository;

import com.users.ejb.BaseTypecode;
import com.users.ejb.BaseTypecodeService;
import com.watch.ejb.UserFeedback;
import com.watch.ejb.UserFeedbackService;
import com.zhuika.dao.IFeedbackDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.util.ejbproxy;

@Repository("FeedbackDao")
public class FeedbackDaoImpl  extends SHBaseDAO implements IFeedbackDao {

	@Override
	public List<BaseTypecode> getCatetory(HashMap<String, String> maps) {
		
		List<BaseTypecode> listBaseTypecode =  new ArrayList<BaseTypecode>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			BaseTypecodeService serviceClient = (BaseTypecodeService)weblogicContext.lookup("BaseTypecodeBean/remote");
			listBaseTypecode = serviceClient.ListBaseTypecode(0, 100,maps);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listBaseTypecode;		
	}

	@Override
	public void Add(UserFeedback userFeedbackInfo) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		UserFeedbackService serviceClient;
		try {
			serviceClient = (UserFeedbackService) weblogicContext.lookup("UserFeedbackBean/remote");
			serviceClient.Add(userFeedbackInfo);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<UserFeedback> ListUserFeedback(int offset, int length,
			HashMap<String, String> map) {
		List<UserFeedback> listUserFeedback =  new ArrayList<UserFeedback>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			UserFeedbackService serviceClient = (UserFeedbackService)weblogicContext.lookup("UserFeedbackBean/remote");
			listUserFeedback = serviceClient.ListUserFeedback(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listUserFeedback;
	}
}
