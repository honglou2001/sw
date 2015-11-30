package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.SerialnumberParam;


public interface ISerialnumberParamDao {

    public void AddSerialnumberParam(SerialnumberParam serialnumberParamInfo) ;
	
	public void UpdateSerialnumberParam(SerialnumberParam serialnumberParamInfo) ;
	
	public void DeleteSerialnumberParam(String id) ;
	
	public SerialnumberParam findSerialnumberParam(String id) ;
	
	public int GetSerialnumberParamCount(HashMap<String, String> map);
	
	public List<SerialnumberParam> ListSerialnumberParam(int offset, int length,HashMap<String, String> map);
}
