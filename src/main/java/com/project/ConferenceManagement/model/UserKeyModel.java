package com.project.ConferenceManagement.model;

public class UserKeyModel {

	private Long id;
	private String userEmail;
	private String[] keyList;
	private String role;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String[] getKeyList() {
		return keyList;
	}
	public void setKeyList(String[] keyList) {
		this.keyList = keyList;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
