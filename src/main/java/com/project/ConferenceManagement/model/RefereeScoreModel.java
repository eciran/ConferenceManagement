package com.project.ConferenceManagement.model;


public class RefereeScoreModel {
	
	private Long id;
	private Long assignment_id;
	private Long evaluation_id;
	private Long user_id;
	private String user_email;
	private String description;
	private String score;
	private String scoreFilePath;
	private String refName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAssignment_id() {
		return assignment_id;
	}
	public void setAssignment_id(Long assignment_id) {
		this.assignment_id = assignment_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getScoreFilePath() {
		return scoreFilePath;
	}
	public void setScoreFilePath(String scoreFilePath) {
		this.scoreFilePath = scoreFilePath;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getEvaluation_id() {
		return evaluation_id;
	}
	public void setEvaluation_id(Long evaluation_id) {
		this.evaluation_id = evaluation_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	
}
