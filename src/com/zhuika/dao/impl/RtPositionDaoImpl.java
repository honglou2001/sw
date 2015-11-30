package com.zhuika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuika.dao.IRtPositionDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.RtPosition;
@Repository("rtpDao")
public class RtPositionDaoImpl extends SHBaseDAO implements IRtPositionDao {

	public void addRtPosition(RtPosition rtp) {
		super.getHibernateTemplate().save(rtp);

	}

	public void updateRtPosition(RtPosition rtp) {
		super.getHibernateTemplate().update(rtp);
	}

	public RtPosition findBySerialNumber(String serialNumber) {
		String hql="from RtPosition where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<RtPosition> list=super.getHibernateTemplate().find(hql, params);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
