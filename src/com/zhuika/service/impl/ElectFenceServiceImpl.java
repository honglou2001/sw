package com.zhuika.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.zhuika.dao.IElectFenceDao;
import com.zhuika.entity.ElectFence;
import com.zhuika.service.ElectFenceService;
@Service("electFenceService")
public class ElectFenceServiceImpl implements ElectFenceService {
    @Resource
    private IElectFenceDao electFenceDao;
    
	public void addElectFence(ElectFence electFence) {
		electFenceDao.save(electFence);
	}

	public List<ElectFence> findElectFence(String serialNumber) {		
		return electFenceDao.findBySerialNumber(serialNumber);
	}

	public void deleteElectFence(ElectFence electFence) {
		electFenceDao.delete(electFence);

	}

	public ElectFence getElectFence(String serialNumber,String areaNum) {
		
		return electFenceDao.findBySerialNumber(serialNumber,areaNum);
	}

	public void updateElectFence(ElectFence electFence) {
		electFenceDao.update(electFence);		
	}
	
	public List<ElectFence> getElectFence(HashMap<String,String> maps){
		return electFenceDao.getElectFence(maps);
	}
}
