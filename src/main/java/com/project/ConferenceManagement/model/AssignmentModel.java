package com.project.ConferenceManagement.model;

public class AssignmentModel {

	private Long id;
	private Long evaluationId;
	private Long[] refereeList;
	private boolean status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}
	public Long[] getRefereeList() {
		return refereeList;
	}
	public void setRefereeList(Long[] refereeList) {
		this.refereeList = refereeList;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	

	
}
