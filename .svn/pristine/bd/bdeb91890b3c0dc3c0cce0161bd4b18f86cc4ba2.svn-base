package com.zhuika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuika.dao.IInfoDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.Info;
@Repository("infoDao")
public class InfoDaoImpl extends SHBaseDAO implements IInfoDao {

	public void addInfo(Info info) {
		super.getHibernateTemplate().save(info);

	}
	public void updateInfo(Info info) {
		super.getHibernateTemplate().update(info);
	}
	public Info findBySerialNumber(String serialNumber) {
		String hql="from Info where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<Info> list=super.getHibernateTemplate().find(hql, params);
		System.out.println(list.size());
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
}
