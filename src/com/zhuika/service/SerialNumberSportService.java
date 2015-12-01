package com.zhuika.service;

import java.util.HashMap;
import java.util.List;

import com.users.ejb.SerialnumberDatarelate;
import com.users.ejb.SerialnumberDatauser;
import com.users.ejb.SerialnumberSport;
import com.users.ejb.SerialnumberWeight;

public interface SerialNumberSportService {

    public void AddSerialnumberSport(SerialnumberSport serialnumberSportInfo) ;
	
	public void UpdateSerialnumberSport(SerialnumberSport serialnumberSportInfo) ;
	
	public void DeleteSerialnumberSport(String id) ;
	
	public SerialnumberSport findSerialnumberSport(String id) ;
	
	public int GetSerialnumberSportCount(HashMap<String, String> map);
	
	public List<SerialnumberSport> ListSerialnumberSport(int offset, int length,HashMap<String, String> map);
	
    public void AddSerialnumberDatauser(SerialnumberDatauser serialnumberDatauserInfo) ;
	
	public void UpdateSerialnumberDatauser(SerialnumberDatauser serialnumberDatauserInfo) ;
    
    public void UpdateSerialnumberDatauser(SerialnumberDatauser serialnumberDatauserInfo,HashMap<String, String> map) ;
	
	public void DeleteSerialnumberDatauser(String id) ;
	
	public SerialnumberDatauser findSerialnumberDatauser(String id) ;
	
	public int GetSerialnumberDatauserCount(HashMap<String, String> map);
	
	public List<SerialnumberDatauser> ListSerialnumberDatauser(int offset, int length,HashMap<String, String> map);
	
    public void AddSerialnumberDatarelate(SerialnumberDatarelate serialnumberDatarelateInfo) ;
	
	public void UpdateSerialnumberDatarelate(SerialnumberDatarelate serialnumberDatarelateInfo) ;
    
    public void UpdateSerialnumberDatarelate(SerialnumberDatarelate serialnumberDatarelateInfo,HashMap<String, String> map) ;
	
	public void DeleteSerialnumberDatarelate(String id) ;
	
	public SerialnumberDatarelate findSerialnumberDatarelate(String id) ;
	
	public int GetSerialnumberDatarelateCount(HashMap<String, String> map);
	
	public List<SerialnumberDatarelate> ListSerialnumberDatarelate(int offset, int length,HashMap<String, String> map);
	
    public void AddSerialnumberWeight(SerialnumberWeight serialnumberWeightInfo) ;
	
	public void UpdateSerialnumberWeight(SerialnumberWeight serialnumberWeightInfo) ;
    
    public void UpdateSerialnumberWeight(SerialnumberWeight serialnumberWeightInfo,HashMap<String, String> map) ;
	
	public void DeleteSerialnumberWeight(String id) ;
	
	public SerialnumberWeight findSerialnumberWeight(String id) ;
	
	public int GetSerialnumberWeightCount(HashMap<String, String> map);
	
	public List<SerialnumberWeight> ListSerialnumberWeight(int offset, int length,HashMap<String, String> map);
}
