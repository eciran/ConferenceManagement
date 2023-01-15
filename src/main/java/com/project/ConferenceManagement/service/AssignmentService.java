package com.project.ConferenceManagement.service;

import java.util.List;

import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.EvaluationModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.model.RefereeScoreModel;
import com.project.ConferenceManagement.model.UserResponse;

public interface AssignmentService {
	
	public List<RefResponseModel> getRefList(EvaluationModel evaluationModel);

	public String setAssignmentList(AssignmentModel assignmentModel);

	public List<RefereeScoreModel> getRefListForEvaluation(Long id);

	public List<EvaluationModel> getEvaluationForScoring(UserResponse userRes);

	public String setAssignmentScoreByRef(RefereeScoreModel refScoreModel);

	public RefereeScoreModel getRefScoreResult(RefereeScoreModel refScoreModel);
}
