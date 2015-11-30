package com.zhuika.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.watch.ejb.RewardList;
import com.zhuika.dao.IRewardListDao;
import com.zhuika.service.RewardListService;

@Service("rewardListService")
public class RewardListServiceImpl  implements RewardListService{

	@Resource
    private IRewardListDao rewardListDao;
	  
	@Override
	public void AddRewardList(RewardList rewardListInfo) {
		// TODO Auto-generated method stub
		this.rewardListDao.AddRewardList(rewardListInfo);
	}

	@Override
	public void UpdateRewardList(RewardList rewardListInfo) {
		// TODO Auto-generated method stub
		this.rewardListDao.UpdateRewardList(rewardListInfo);
		
	}

	@Override
	public void DeleteRewardList(String id) {
		// TODO Auto-generated method stub
		this.rewardListDao.DeleteRewardList(id);
		
	}

	@Override
	public RewardList findRewardList(String id) {
		// TODO Auto-generated method stub
		return this.rewardListDao.findRewardList(id);
	}

	@Override
	public int GetRewardListCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.rewardListDao.GetRewardListCount(map);
	}

	@Override
	public List<RewardList> ListRewardList(int offset, int length,
			HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.rewardListDao.ListRewardList(offset, length, map);
	}

}
