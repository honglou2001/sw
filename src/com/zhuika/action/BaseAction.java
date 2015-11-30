package com.zhuika.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 *	Action的父类，将Action通用的逻辑或数据
 *	封装在这里。
 */
public class BaseAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	protected Map<String, Object> session;
	
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

}
