package com.zhuika.dao.impl;

import com.watch.ejb.RewardList;
import com.watch.ejb.RewardListService;
import com.zhuika.dao.IRewardListDao;
import com.zhuika.dao.IRtPositionDao;
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
 * <p>Description: t_reward_list Client Dao 处理类</p>
 * @author yangqinxu 电话：137****5317
 * @version 1.0 时间  2015-8-8 17:48:06
 */
@Repository("rewardListDao")
public class RewardListDaoIml  extends SHBaseDAO implements IRewardListDao{	


  	public void AddRewardList(RewardList rewardList) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		RewardListService serviceClient;
		try {
			serviceClient = (RewardListService) weblogicContext.lookup("RewardListBean/remote");
			serviceClient.AddRewardList(rewardList);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public int GetRewardListCount(HashMap<String, String> map)
	{
		int total = 0 ;			
		Context weblogicContext = ejbproxy.getInitialConnection();
		RewardListService serviceClient;
		try {
			serviceClient = (RewardListService)weblogicContext.lookup("RewardListBean/remote");
			total = serviceClient.GetRewardListCount(map);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return total;
	}
	

	public List<RewardList> ListRewardList(int offset, int length,HashMap<String, String> map)
	{		
		List<RewardList> listRewardList =  new ArrayList<RewardList>();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			RewardListService serviceClient = (RewardListService)weblogicContext.lookup("RewardListBean/remote");
			listRewardList = serviceClient.ListRewardList(offset, length,map);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return listRewardList;		
	}
    
    public RewardList findRewardList(String fid)
	{		
		RewardList rewardList = new RewardList();				
		try{
			Context weblogicContext = ejbproxy.getInitialConnection();			
			RewardListService serviceClient = (RewardListService)weblogicContext.lookup("RewardListBean/remote");
			rewardList = serviceClient.findRewardList(fid);	

		  } catch (NamingException ne) {
			   // TODO: handle exception
			   System.err.println("不能连接NamingException在："+ne.toString());
			   ne.printStackTrace();
			  }
		
		return rewardList;		
	}
    
  	public void UpdateRewardList(RewardList rewardList) {		
		Context weblogicContext = ejbproxy.getInitialConnection();
		RewardListService serviceClient;
		try {
			serviceClient = (RewardListService)weblogicContext.lookup("RewardListBean/remote");
			serviceClient.UpdateRewardList(rewardList);	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
   
	public void DeleteRewardList(String id) {
		Context weblogicContext = ejbproxy.getInitialConnection();
		RewardListService serviceClient;
		try {
			serviceClient = (RewardListService) weblogicContext
					.lookup("RewardListBean/remote");
			serviceClient.DeleteRewardList(id);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}       
}