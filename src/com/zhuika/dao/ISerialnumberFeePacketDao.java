package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.SerialnumberFeepacket;

public interface ISerialnumberFeePacketDao {
    public void AddSerialnumberFeepacket(SerialnumberFeepacket serialnumberFeepacketInfo) ;
	
	public void UpdateSerialnumberFeepacket(SerialnumberFeepacket serialnumberFeepacketInfo) ;
	
	public void DeleteSerialnumberFeepacket(String id) ;
	
	public SerialnumberFeepacket findSerialnumberFeepacket(String id) ;
	
	public int GetSerialnumberFeepacketCount(HashMap<String, String> map);
	
	public List<SerialnumberFeepacket> ListSerialnumberFeepacket(int offset, int length,HashMap<String, String> map);

}
