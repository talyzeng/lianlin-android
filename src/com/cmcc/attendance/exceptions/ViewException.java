package com.cmcc.attendance.exceptions;


public class ViewException extends AfinalException {
	private static final long serialVersionUID = 1L;
	private String strMsg = null;
	public ViewException(String strExce) {
		strMsg = strExce;
	}
	
	public void printStackTrace() {
		if(strMsg!=null)
			System.err.println(strMsg);
		
		super.printStackTrace();
	}
}
