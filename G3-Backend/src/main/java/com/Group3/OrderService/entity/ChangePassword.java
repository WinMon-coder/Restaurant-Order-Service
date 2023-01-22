package com.Group3.OrderService.entity;

public class ChangePassword {
	int oriId;
	String currentPwd;
	String newPwd;
	String conNewPwd;
	public int getOriId() {
		return oriId;
	}
	public void setOriId(int oriId) {
		this.oriId = oriId;
	}
	public String getCurrentPwd() {
		return currentPwd;
	}
	public void setCurrentPwd(String currenPwd) {
		this.currentPwd = currenPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getConNewPwd() {
		return conNewPwd;
	}
	public void setConNewPwd(String conNewPwd) {
		this.conNewPwd = conNewPwd;
	}	
	
}
