package com.zhuika.service;

import java.util.HashMap;
import java.util.List;

import com.zhuika.entity.ElectFence;

public interface ElectFenceService {
	public void addElectFence(ElectFence electFence);
	public void updateElectFence(ElectFence electFence);
	public List<ElectFence> findElectFence(String serialNumber);
	public void deleteElectFence(ElectFence electFence);
	public ElectFence getElectFence(String serialNumber,String areaNum);
	public List<ElectFence> getElectFence(HashMap<String,String> maps);
}
