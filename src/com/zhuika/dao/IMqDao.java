package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.MqTask;

public interface IMqDao {

    public void AddMqTask(MqTask mqTaskInfo) ;
	
	public void UpdateMqTask(MqTask mqTaskInfo) ;
	
	public void DeleteMqTask(String id) ;
	
	public MqTask findMqTask(String id) ;
	
	public int GetMqTaskCount(HashMap<String, String> map);
	
	public List<MqTask> ListMqTask(int offset, int length,HashMap<String, String> map);
}
