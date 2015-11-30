package com.zhuika.dao;

import com.zhuika.entity.VersionInfo;
import com.zhuika.factory.DAOException;

public interface IVersionInfoDao {
	
	VersionInfo findVersionInfo(String softName)throws DAOException;
	
	void updateVersionInfo(VersionInfo versionInfo)throws DAOException;
	
	void addVersionInfo(VersionInfo versionInfo)throws DAOException;
}
