package com.zhuika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.zhuika.dao.IBindPhoneDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.BindPhone;
import com.zhuika.factory.DAOException;
@Repository("bindPhoneDao")
public class BindPhoneDaoImpl extends SHBaseDAO implements IBindPhoneDao {

	public void addPhone(BindPhone bp) throws DAOException {
		super.getHibernateTemplate().save(bp);
	}


	public void updatePhone(BindPhone bp) throws DAOException {
		super.getHibernateTemplate().update(bp);
	}

	public BindPhone findBySeriaNumber(String serialNumber) throws DAOException {
		String hql="from BindPhone where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<BindPhone> list=super.getHibernateTemplate().find(hql, params);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
