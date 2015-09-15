package com.cmcc.attendance.bean;

public class AttendanceBean extends BaseBean {
	private static final long serialVersionUID = -8831290892043520450L;
	private String attendanceName;
	private int attendanceType;
	
	public String getAttendanceName() {
		return attendanceName;
	}
	
	public void setAttendanceName(String attendanceName) {
		this.attendanceName = attendanceName;
	}
	
	public int getAttendanceType() {
		return attendanceType;
	}
	
	public void setAttendanceType(int attendanceType) {
		this.attendanceType = attendanceType;
	}
}
