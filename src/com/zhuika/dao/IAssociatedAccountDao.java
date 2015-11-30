package com.zhuika.dao;

import java.util.List;

import com.zhuika.entity.AssociatedAccount;
import com.zhuika.factory.DAOException;

public interface IAssociatedAccountDao {
	
	void addAssociatedAccount(AssociatedAccount assAccount)throws DAOException;
	
	List<AssociatedAccount> findAssociatedAccount(String serialNumber)throws DAOException;
	
	void deleteAssociatedAccount(AssociatedAccount assAccount)throws DAOException;
	/*
	 * 
	 */
	AssociatedAccount findAssociatedAccount(AssociatedAccount assAccount)throws DAOException;
	
	void updateAssociatedAccount(String userName,String password)throws DAOException;
}
