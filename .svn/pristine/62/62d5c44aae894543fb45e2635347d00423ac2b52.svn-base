package com.zhuika.service;


import java.util.HashMap;
import java.util.List;

import com.users.ejb.SerialnumberJpush;
import com.watch.ejb.FriendContact;
import com.watch.ejb.FriendRelation;
import com.watch.ejb.Serialnumber;
import com.watch.ejb.UserWatch;
import com.zhuika.entity.User;
import com.zhuika.factory.DAOException;

public interface UserService {
	//用户登录
	User login(String userName);
	//User loginPhone(String phone);
	//用户注册
	String register(User user) throws DAOException;
	//获取用户
	String reg(UserWatch user) throws DAOException;
	User getUser(String serialNumber);
	void updateUser(User user);
	List<UserWatch> GetAll(int offset, int length,HashMap<String, String> map);
	UserWatch Find(String fid);
	void Update(UserWatch user);
	
	//好友信息
    public void Add(FriendRelation friendRelationInfo) ;	
	public void Update(FriendRelation friendRelationInfo) ;	
	public void Delete(String id) ;	
	public FriendRelation find(String id) ;	
	public int GetCount(HashMap<String, String> map);	
	public List<FriendRelation> ListFriendRelation(int offset, int length,HashMap<String, String> map);
	
	public void SendFriendToWatch(FriendRelation friendRelationInfo);
	public void SendToSerialNumber(String fromsnid,String tosnid,int calltype);
	public void saveToContact(UserWatch postUser,String contactName,String contactNum,int fproperties,int dialtype,Serialnumber serialNumTo);	
	public List<FriendContact> ListFriendContact(int offset, int length,HashMap<String, String> map);
	public FriendContact findFriendContact(String id) ;
	public String SendContactToWatch(FriendContact contactInfo,String toSerialNumber) ;
	public void UpdateFriendContact(FriendContact friendContact,String toSerialNumber);
	
	public void AddSerialnumberJpush(SerialnumberJpush serialnumberJpush);
 	public void UpdateSerialnumberJpush(SerialnumberJpush serialnumberJpush);
	public List<SerialnumberJpush> ListSerialnumberJpush(int offset, int length,HashMap<String, String> map);
}
