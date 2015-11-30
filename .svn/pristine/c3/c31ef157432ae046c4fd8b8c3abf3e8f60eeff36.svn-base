package com.zhuika.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhuika.dao.ILocationInfoDao;
import com.zhuika.entity.LocationInfo;
import com.zhuika.service.LocationService;

@Service("locationService")
public class LocationServiceImpl implements LocationService {
    @Resource
    private ILocationInfoDao locationInfoDao;
	public LocationInfo getLocation(String serialNumber) {		
		return locationInfoDao.findBySeriaNumber(serialNumber);
	}

}
