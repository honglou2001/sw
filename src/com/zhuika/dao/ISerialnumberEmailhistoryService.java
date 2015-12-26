package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.SerialnumberEmailhistory;

public interface ISerialnumberEmailhistoryService {
    public void AddSerialnumberEmailhistory(SerialnumberEmailhistory serialnumberEmailhistoryInfo) ;
	
	public void UpdateSerialnumberEmailhistory(SerialnumberEmailhistory serialnumberEmailhistoryInfo) ;
    
    public void UpdateSerialnumberEmailhistory(SerialnumberEmailhistory serialnumberEmailhistoryInfo,HashMap<String, String> map) ;
	
	public void DeleteSerialnumberEmailhistory(String id) ;
	
	public SerialnumberEmailhistory findSerialnumberEmailhistory(String id) ;
	
	public int GetSerialnumberEmailhistoryCount(HashMap<String, String> map);
	
	public List<SerialnumberEmailhistory> ListSerialnumberEmailhistory(int offset, int length,HashMap<String, String> map);
}
