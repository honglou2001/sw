package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.users.ejb.SerialnumberJpush;

public interface ISerialnumberJpushDao {

    public void AddSerialnumberJpush(SerialnumberJpush serialnumberJpushInfo) ;
	
	public void UpdateSerialnumberJpush(SerialnumberJpush serialnumberJpushInfo) ;
	
	public void DeleteSerialnumberJpush(String id) ;
	
	public SerialnumberJpush findSerialnumberJpush(String id) ;
	
	public int GetSerialnumberJpushCount(HashMap<String, String> map);
	
	public List<SerialnumberJpush> ListSerialnumberJpush(int offset, int length,HashMap<String, String> map);
}
