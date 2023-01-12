package com.project.ConferenceManagement.service;

import java.util.List;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.EvaluationModel;

public interface EvaluationService {
	
	public String toApply(EvaluationModel evaluationModel);
	
	public List<EvaluationModel> getEvaluationTable(UserEntity user);

	public List<EvaluationModel> getEvaluationTableForAll();

}
