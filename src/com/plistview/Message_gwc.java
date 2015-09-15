package com.plistview;

public class Message_gwc {
	private String title;
	private String num;
	private String price;
	private String type;
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
	public String getnum() {
		return num;
	}
	public void setnum(String a) {
		this.num = a;
	}
	
	public String getprice() {
		return price;
	}
	public void setprice(String a) {
		this.price = a;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String a) {
		this.url = a;
	}
	public String gettype() {
		return type;
	}
	public void settype(String a) {
		this.type = a;
	}

}
