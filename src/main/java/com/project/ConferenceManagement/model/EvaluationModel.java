package com.project.ConferenceManagement.model;

import com.project.ConferenceManagement.entity.UserEntity;

public class EvaluationModel {

	private Long id;
	
	private UserEntity user;
	
	private String email;
	
	private String title;
	
	private String uniName;
	
	private String key;
	
	private String filePath;
	
	private String description;
	
	private int point;
	
	private int status;
	
	private boolean refScoreStat;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUniName() {
		return uniName;
	}
	public void setUniName(String uniName) {
		this.uniName = uniName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDescription() {
		return description;
	}	
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isRefScoreStat() {
		return refScoreStat;
	}
	public void setRefScoreStat(boolean refScoreStat) {
		this.refScoreStat = refScoreStat;
	}
	
	
}
