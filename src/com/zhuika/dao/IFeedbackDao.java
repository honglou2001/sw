package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;


import com.users.ejb.BaseTypecode;
import com.watch.ejb.UserFeedback;


public interface IFeedbackDao {
	List<BaseTypecode> getCatetory(HashMap<String,String> maps);
	public void Add(UserFeedback userFeedbackInfo) ;
	public List<UserFeedback> ListUserFeedback(int offset, int length,HashMap<String, String> map);
}
