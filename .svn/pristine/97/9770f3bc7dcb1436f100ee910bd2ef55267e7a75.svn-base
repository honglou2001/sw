package com.zhuika.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.users.ejb.BaseTypecode;
import com.watch.ejb.UserFeedback;
import com.zhuika.dao.IFeedbackDao;
import com.zhuika.service.FeedBackService;

@Service("feedBackService")
public class FeedBackServiceImpl  implements FeedBackService {

	@Resource
    private IFeedbackDao FeedbackDao;

	@Override
	public List<BaseTypecode> getCatetory(HashMap<String, String> maps) {
		 return this.FeedbackDao.getCatetory(maps);
	}

	@Override
	public void Add(UserFeedback userFeedbackInfo) {
		this.FeedbackDao.Add(userFeedbackInfo);
		
	}

	@Override
	public List<UserFeedback> ListUserFeedback(int offset, int length,
			HashMap<String, String> map) {
		return this.FeedbackDao.ListUserFeedback(offset, length, map);
	}
}


