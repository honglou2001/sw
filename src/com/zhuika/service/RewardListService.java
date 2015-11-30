package com.zhuika.service;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.RewardList;

public interface RewardListService {

    public void AddRewardList(RewardList rewardListInfo) ;
	
	public void UpdateRewardList(RewardList rewardListInfo) ;
	
	public void DeleteRewardList(String id) ;
	
	public RewardList findRewardList(String id) ;
	
	public int GetRewardListCount(HashMap<String, String> map);
	
	public List<RewardList> ListRewardList(int offset, int length,HashMap<String, String> map);
}
