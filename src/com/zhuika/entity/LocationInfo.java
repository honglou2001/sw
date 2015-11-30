package com.zhuika.entity;





public class LocationInfo  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
    private String serialNumber;
    private String text;
    private String location;
    private String lng;
    private String lat;
    private String battery;
    private java.sql.Timestamp faddtime;
	private java.sql.Timestamp fupdatetime;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
    public java.sql.Timestamp getFaddtime() {
		return faddtime;
	}
	public void setFaddtime(java.sql.Timestamp faddtime) {
		this.faddtime = faddtime;
	}
	public java.sql.Timestamp getFupdatetime() {
		return fupdatetime;
	}
	public void setFupdatetime(java.sql.Timestamp fupdatetime) {
		this.fupdatetime = fupdatetime;
	}   
}