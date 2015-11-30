package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.users.ejb.SerialnumberDetach;
import com.watch.ejb.Serialnumber;
import com.zhuika.entity.SerialNumber;
import com.zhuika.factory.DAOException;

public interface ISerialNumberDao {
	void addSerialNumber(SerialNumber serialNumber) throws DAOException;
	SerialNumber findBySerialNumber(String serialNumber)throws DAOException;
	void updateSerialNumber(SerialNumber serialNumber)throws DAOException;
	Integer getOnlineNo();
	Integer getNotOnlineNo();
	
	void Add(Serialnumber serialnumber); 
	Serialnumber Find(String serialnumid) ;
	Serialnumber findBySNNumber(String serialnumid);
	void Update(Serialnumber serialnumber); 
	List<Serialnumber> GetAll(int offset, int length,HashMap<String, String> map);
	public List<SerialnumberDetach> ListSerialnumberDetach(int offset, int length,HashMap<String, String> map);
	List<Serialnumber> ListSerialnumberAll(int offset, int length,HashMap<String, String> map);
}
