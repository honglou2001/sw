package com.zhuika.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuika.dao.IPedoMeterDao;
import com.zhuika.entity.PedoMeter;
import com.zhuika.factory.DAOException;
import com.zhuika.service.PedometerService;
@Service("pedometerService")
public class PedometerServiceImpl implements PedometerService {
    @Resource
    private IPedoMeterDao pedometerDao;
    
	@Override
	public PedoMeter getPedometer(String serialNumber) {	
		try {
			return pedometerDao.findBySerialNumber(serialNumber);
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
