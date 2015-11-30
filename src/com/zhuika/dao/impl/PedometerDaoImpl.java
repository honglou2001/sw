package com.zhuika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhuika.dao.IPedoMeterDao;
import com.zhuika.dao.SHBaseDAO;
import com.zhuika.entity.PedoMeter;
import com.zhuika.factory.DAOException;
@Repository("pedometerDao")
public class PedometerDaoImpl extends SHBaseDAO implements IPedoMeterDao {

	public void addPedoMeter(PedoMeter pedoMeter) throws DAOException {
		

	}

	public void updatePedoMeter(PedoMeter pedoMeter) throws DAOException {
		

	}

	public PedoMeter findBySerialNumber(String serialNumber)
			throws DAOException {
		String hql="from PedoMeter where serialNumber=?";
		Object[] params={serialNumber};
		@SuppressWarnings("unchecked")
		List<PedoMeter> list=super.getHibernateTemplate().find(hql, params);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
