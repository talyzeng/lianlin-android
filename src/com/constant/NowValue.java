package com.constant;

public class NowValue {

	private String now_userid;
	private String now_typeid ;
	private String now_pinpaiid;
	private String now_pricemin;
	private String now_pricemax;
	private String id;
	private String url;
	
	public String getnow_pricemin() {
		return now_pricemin;
	}
	public void setnow_pricemin(String a) {
		this.now_pricemin = a;
	}
	public String getnow_userid() {
		return now_userid;
	}
	public void setnow_userid(String a) {
		this.now_userid = a;
	}
	public String getnow_typeid() {
		return now_typeid;
	}
	public void setnow_typeid(String a) {
		this.now_typeid = a;
	}
	public String getnow_pinpaiid() {
		return now_pinpaiid;
	}
	public void setnow_pinpaiid(String a) {
		this.now_pinpaiid = a;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String a) {
		this.id = a;
	}
	public String getnow_pricemax() {
		return now_pricemax;
	}
	public void setnow_pricemax(String a) {
		this.now_pricemax = a;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String a) {
		this.url = a;
	}
	
}
