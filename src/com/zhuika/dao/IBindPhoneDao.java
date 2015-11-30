package com.zhuika.dao;

import com.zhuika.entity.BindPhone;
import com.zhuika.factory.DAOException;

public interface IBindPhoneDao {
	
	void addPhone(BindPhone bp) throws DAOException;

	void updatePhone(BindPhone bp)throws DAOException;

	BindPhone findBySeriaNumber(String serialNumber)throws DAOException;
}
