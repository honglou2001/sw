package com.zhuika.service;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.UserAlarm;
import com.zhuika.entity.Alarm;

public interface AlarmService {
	public void addAlarm(Alarm alarm);
	public void updateAlarm(Alarm alarm);
	public Alarm getAlarm(String serialNumber);
	
    public void Add(UserAlarm userAlarmInfo) ;
	
	public void Update(UserAlarm userAlarmInfo) ;
	
	public void Delete(String id) ;
	
	public UserAlarm find(String id) ;
	
	public int GetCount(HashMap<String, String> map);
	
	public List<UserAlarm> ListUserAlarm(int offset, int length,HashMap<String, String> map);
}
