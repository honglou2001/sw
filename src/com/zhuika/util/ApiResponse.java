package com.zhuika.util;

import java.sql.Timestamp;

public class ApiResponse {
	private java.math.BigDecimal balance;
	private String clientNumber;
	private String clientPwd;
	private String createDate;
	private String clientType;
	private String mobile;
	private String friendlyName;	
	
	public java.math.BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(java.math.BigDecimal balance) {
		this.balance = balance;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getClientPwd() {
		return clientPwd;
	}
	public void setClientPwd(String clientPwd) {
		this.clientPwd = clientPwd;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
}
