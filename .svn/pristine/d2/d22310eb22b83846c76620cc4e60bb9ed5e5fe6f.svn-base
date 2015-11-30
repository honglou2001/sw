package com.zhuika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuika.dao.IVersionInfoDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.VersionInfo;
import com.zhuika.factory.DAOException;
@Repository("versionInfoDao")
public class VersionInfoDaoImpl extends SHBaseDAO implements IVersionInfoDao {


	public void updateVersionInfo(VersionInfo versionInfo) throws DAOException {
		super.getHibernateTemplate().update(versionInfo);		
	}

	public void addVersionInfo(VersionInfo versionInfo) throws DAOException {
		super.getHibernateTemplate().save(versionInfo);
	}

	public VersionInfo findVersionInfo(String softName) throws DAOException {
		String hql="from VersionInfo where softName=?";
		Object[] params={softName};
		@SuppressWarnings("unchecked")
		List<VersionInfo> list=super.getHibernateTemplate().find(hql,params);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
