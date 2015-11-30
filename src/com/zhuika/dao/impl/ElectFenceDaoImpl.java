package com.zhuika.dao.impl;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuika.dao.IElectFenceDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.ElectFence;

@Repository("electFenceDao")
public class ElectFenceDaoImpl extends SHBaseDAO implements IElectFenceDao {

	public void save(ElectFence ef) {
		super.getHibernateTemplate().save(ef);
	}

	public void update(ElectFence ef) {
		super.getHibernateTemplate().update(ef);
	}

	public List<ElectFence> findBySerialNumber(String serialNumber) {
		String hql="from ElectFence where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<ElectFence> list=super.getHibernateTemplate().find(hql, params);
		return list;
	}
	
	public void delete(ElectFence ef) {
		super.getHibernateTemplate().delete(ef);		
	}

	public ElectFence findBySerialNumber( String serialNumber,  String areanum) {
		String hql="from ElectFence where serialNumber=? and id=?";
		Object[] params={serialNumber,Integer.parseInt(areanum)};
		@SuppressWarnings("unchecked")
		List<ElectFence> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			System.out.println(list.size());
			return list.get(0);
		}
		return null;	
	}
	
	public List<ElectFence> getElectFence(HashMap<String,String> maps){
		
		String serialNumber = maps.get("serialNumber");
		String name = maps.get("name");
		//表示为更新的查询
		if(maps.containsKey("areaNum") && !maps.get("areaNum").equals(""))
		{
			Integer id =Integer.parseInt(maps.get("areaNum"));
			
			String hql="from ElectFence where serialNumber=? and name =? and id <> ?";
			Object[] params={serialNumber,name,id};
			@SuppressWarnings("unchecked")
			List<ElectFence> list=super.getHibernateTemplate().find(hql, params);
			return list;
		
		}else
		{
			String hql="from ElectFence where serialNumber=? and name =? ";
			Object[] params={serialNumber,name};
			@SuppressWarnings("unchecked")
			List<ElectFence> list=super.getHibernateTemplate().find(hql, params);
			return list;
		}
		
	}
}
