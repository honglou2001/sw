package com.zhuika.entity;

public class BindPhone implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String serialNumber;
	private String sos;
	private String status;
    private String masterNo;
    private String listenNo;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSos() {
		return sos;
	}

	public void setSos(String sos) {
		this.sos = sos;
	}

	public String getMasterNo() {
		return masterNo;
	}

	public void setMasterNo(String masterNo) {
		this.masterNo = masterNo;
	}

	public String getListenNo() {
		return listenNo;
	}

	public void setListenNo(String listenNo) {
		this.listenNo = listenNo;
	}

	@Override
	public String toString() {
		return "BindPhone [id=" + id + ", serialNumber=" + serialNumber
				+ ", sos=" + sos + ", status=" + status + ", masterNo="
				+ masterNo + ", listenNo=" + listenNo + "]";
	}
    
}
