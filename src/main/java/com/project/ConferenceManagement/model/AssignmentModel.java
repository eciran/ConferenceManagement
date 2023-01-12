package com.project.ConferenceManagement.model;

public class AssignmentModel {

	private Long id;
	private EvaluationModel evaluation;
	private UserModel referee;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EvaluationModel getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(EvaluationModel evaluation) {
		this.evaluation = evaluation;
	}
	public UserModel getReferee() {
		return referee;
	}
	public void setReferee(UserModel referee) {
		this.referee = referee;
	}
	
	
}
