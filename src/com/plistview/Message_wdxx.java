package com.plistview;

public class Message_wdxx {
	private String title;
	private String time;
	private String msg;
	private String url;
	private String id;
    private Boolean ischeck;
	
	public Boolean getcheck() {
		return ischeck;
	}
	public void setcheck(Boolean a) {
		this.ischeck = a;
	}
	public String getid() {
		return id;
	}
	public void setid(String a) {
		this.id = a;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String a) {
		this.msg = a;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String a) {
		this.title = a;
	}
	public String gettime() {
		return time;
	}
	public void settime(String a) {
		this.time = a;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String a) {
		this.url = a;
	}

}
