package com.zhuika.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.watch.ejb.SerialnumberEmailhistory;
import com.watch.ejb.SerialnumberEmailtemplate;
import com.zhuika.dao.ISerialnumberEmailhistoryService;
import com.zhuika.dao.ISerialnumberEmailtemplateService;
import com.zhuika.service.EmailService;


@Service("emailService")
public class EmailServiceImpl  implements EmailService{

	@Resource
    private ISerialnumberEmailhistoryService serialnumberEmailhistoryDao;
	
	@Resource
    private ISerialnumberEmailtemplateService serialnumberEmailtemplateDao;
	
	@Override
	public void AddSerialnumberEmailhistory(
			SerialnumberEmailhistory serialnumberEmailhistoryInfo) {
		// TODO Auto-generated method stub
		this.serialnumberEmailhistoryDao.AddSerialnumberEmailhistory(serialnumberEmailhistoryInfo);
	}

	@Override
	public void UpdateSerialnumberEmailhistory(
			SerialnumberEmailhistory serialnumberEmailhistoryInfo) {
		// TODO Auto-generated method stub
		this.serialnumberEmailhistoryDao.UpdateSerialnumberEmailhistory(serialnumberEmailhistoryInfo);
	}

	@Override
	public void UpdateSerialnumberEmailhistory(
			SerialnumberEmailhistory serialnumberEmailhistoryInfo,
			HashMap<String, String> map) {
		// TODO Auto-generated method stub
		this.serialnumberEmailhistoryDao.UpdateSerialnumberEmailhistory(serialnumberEmailhistoryInfo, map);
	}

	@Override
	public void DeleteSerialnumberEmailhistory(String id) {
		// TODO Auto-generated method stub
		this.serialnumberEmailhistoryDao.DeleteSerialnumberEmailhistory(id);
	}

	@Override
	public SerialnumberEmailhistory findSerialnumberEmailhistory(String id) {
		// TODO Auto-generated method stub
		return this.serialnumberEmailhistoryDao.findSerialnumberEmailhistory(id);
	}

	@Override
	public int GetSerialnumberEmailhistoryCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.serialnumberEmailhistoryDao.GetSerialnumberEmailhistoryCount(map);
	}

	@Override
	public List<SerialnumberEmailhistory> ListSerialnumberEmailhistory(
			int offset, int length, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.serialnumberEmailhistoryDao.ListSerialnumberEmailhistory(offset, length, map);
	}

	@Override
	public void AddSerialnumberEmailtemplate(
			SerialnumberEmailtemplate serialnumberEmailtemplateInfo) {
		// TODO Auto-generated method stub
		this.serialnumberEmailtemplateDao.AddSerialnumberEmailtemplate(serialnumberEmailtemplateInfo);
	}

	@Override
	public void UpdateSerialnumberEmailtemplate(
			SerialnumberEmailtemplate serialnumberEmailtemplateInfo) {
		// TODO Auto-generated method stub
		this.serialnumberEmailtemplateDao.UpdateSerialnumberEmailtemplate(serialnumberEmailtemplateInfo);
	}

	@Override
	public void UpdateSerialnumberEmailtemplate(
			SerialnumberEmailtemplate serialnumberEmailtemplateInfo,
			HashMap<String, String> map) {
		// TODO Auto-generated method stub
		this.serialnumberEmailtemplateDao.UpdateSerialnumberEmailtemplate(serialnumberEmailtemplateInfo, map);
	}

	@Override
	public void DeleteSerialnumberEmailtemplate(String id) {
		// TODO Auto-generated method stub
		this.serialnumberEmailtemplateDao.DeleteSerialnumberEmailtemplate(id);
	}

	@Override
	public SerialnumberEmailtemplate findSerialnumberEmailtemplate(String id) {
		// TODO Auto-generated method stub
		return this.serialnumberEmailtemplateDao.findSerialnumberEmailtemplate(id);
	}

	@Override
	public int GetSerialnumberEmailtemplateCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.serialnumberEmailtemplateDao.GetSerialnumberEmailtemplateCount(map);
	}

	@Override
	public List<SerialnumberEmailtemplate> ListSerialnumberEmailtemplate(
			int offset, int length, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.serialnumberEmailtemplateDao.ListSerialnumberEmailtemplate(offset, length, map);
	}

}
