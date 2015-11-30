package com.zhuika.dao;

import java.util.HashMap;
import java.util.List;

import com.watch.ejb.FriendRelation;
import com.watch.ejb.UserWatch;
import com.zhuika.entity.User;

public interface IUserDao {
	User findByNameAndPwd(String userName,String password);
	User findByName(String userName);
	User findByPhone(String phone);
	User findBySerialNumber(String serialNumber);
	public User findById(int id) ;
	void delete(Integer id);   
	List<User> findAll();
	void save(User user);
	void update(User user);
	void deleteBySerialNumber(User user);
	public void Add(UserWatch userInfo) ;
	public List<UserWatch> GetAll(int offset, int length,HashMap<String, String> map);
	public UserWatch Find(String fid);
	public void Update(UserWatch user);
	
	//好友信息
    public void Add(FriendRelation friendRelationInfo) ;	
	public void Update(FriendRelation friendRelationInfo) ;	
	public void Delete(String id) ;	
	public FriendRelation find(String id) ;	
	public int GetCount(HashMap<String, String> map);	
	public List<FriendRelation> ListFriendRelation(int offset, int length,HashMap<String, String> map);
	
}
