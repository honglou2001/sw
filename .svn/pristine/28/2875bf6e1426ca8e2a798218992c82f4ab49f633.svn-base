package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.UserAlarm;
import com.zhuika.entity.Alarm;

public interface IAlarmDao{
	
	void save(Alarm alarm);
	void update(Alarm alarm);
	Alarm findByName(String serialNumber);
	
    public void Add(UserAlarm userAlarmInfo) ;
	
	public void Update(UserAlarm userAlarmInfo) ;
	
	public void Delete(String id) ;
	
	public UserAlarm find(String id) ;
	
	public int GetCount(HashMap<String, String> map);
	
	public List<UserAlarm> ListUserAlarm(int offset, int length,HashMap<String, String> map);	
}
