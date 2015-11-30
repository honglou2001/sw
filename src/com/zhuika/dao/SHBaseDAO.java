package com.zhuika.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SHBaseDAO extends HibernateDaoSupport{
	@Resource//按类型匹配注入
	public void setMySessionFactory(SessionFactory sf){
		super.setSessionFactory(sf);
	}
}
