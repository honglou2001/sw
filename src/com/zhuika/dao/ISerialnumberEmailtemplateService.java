package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.SerialnumberEmailtemplate;

public interface ISerialnumberEmailtemplateService {
    public void AddSerialnumberEmailtemplate(SerialnumberEmailtemplate serialnumberEmailtemplateInfo) ;
	
	public void UpdateSerialnumberEmailtemplate(SerialnumberEmailtemplate serialnumberEmailtemplateInfo) ;
    
    public void UpdateSerialnumberEmailtemplate(SerialnumberEmailtemplate serialnumberEmailtemplateInfo,HashMap<String, String> map) ;
	
	public void DeleteSerialnumberEmailtemplate(String id) ;
	
	public SerialnumberEmailtemplate findSerialnumberEmailtemplate(String id) ;
	
	public int GetSerialnumberEmailtemplateCount(HashMap<String, String> map);
	
	public List<SerialnumberEmailtemplate> ListSerialnumberEmailtemplate(int offset, int length,HashMap<String, String> map);
}
