package com.zhuika.service;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.SerialnumberEmailhistory;
import com.watch.ejb.SerialnumberEmailtemplate;

public interface EmailService {
	    public void AddSerialnumberEmailhistory(SerialnumberEmailhistory serialnumberEmailhistoryInfo) ;		
		public void UpdateSerialnumberEmailhistory(SerialnumberEmailhistory serialnumberEmailhistoryInfo) ;	    
	    public void UpdateSerialnumberEmailhistory(SerialnumberEmailhistory serialnumberEmailhistoryInfo,HashMap<String, String> map) ;		
		public void DeleteSerialnumberEmailhistory(String id) ;		
		public SerialnumberEmailhistory findSerialnumberEmailhistory(String id) ;		
		public int GetSerialnumberEmailhistoryCount(HashMap<String, String> map);		
		public List<SerialnumberEmailhistory> ListSerialnumberEmailhistory(int offset, int length,HashMap<String, String> map);

	    public void AddSerialnumberEmailtemplate(SerialnumberEmailtemplate serialnumberEmailtemplateInfo) ;
		
		public void UpdateSerialnumberEmailtemplate(SerialnumberEmailtemplate serialnumberEmailtemplateInfo) ;
	    
	    public void UpdateSerialnumberEmailtemplate(SerialnumberEmailtemplate serialnumberEmailtemplateInfo,HashMap<String, String> map) ;
		
		public void DeleteSerialnumberEmailtemplate(String id) ;
		
		public SerialnumberEmailtemplate findSerialnumberEmailtemplate(String id) ;
		
		public int GetSerialnumberEmailtemplateCount(HashMap<String, String> map);
		
		public List<SerialnumberEmailtemplate> ListSerialnumberEmailtemplate(int offset, int length,HashMap<String, String> map);
}
