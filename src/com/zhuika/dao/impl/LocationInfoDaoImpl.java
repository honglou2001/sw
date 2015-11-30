package com.zhuika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuika.dao.ILocationInfoDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.LocationInfo;
@Repository("liDao")
public class LocationInfoDaoImpl extends SHBaseDAO implements ILocationInfoDao {

	public LocationInfo findBySeriaNumber(String serialNumber) {
		String hql="from LocationInfo where serialNumber=?";
		Object[] params={serialNumber};
		
		@SuppressWarnings("unchecked")
		List<LocationInfo> list=super.getHibernateTemplate().find(hql, params);
		System.out.println(list.size());
		if(!list.isEmpty()){
			
			return list.get(0);
		}
		
		return null;
	}

	public void save(LocationInfo li) {
		

	}

	public void update(LocationInfo li) {
		

	}

	@Override
	public void addLocationInfo(String serialNumber) {
		// TODO Auto-generated method stub
		
	}

}
