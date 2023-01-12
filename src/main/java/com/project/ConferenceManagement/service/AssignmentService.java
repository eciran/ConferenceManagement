package com.project.ConferenceManagement.service;

import java.util.List;

import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.RefResponseModel;

public interface AssignmentService {
	
	public List<RefResponseModel> getRefList();

	public String setAssignmentList(AssignmentModel assignmentModel);

	public List<RefResponseModel> getRefListForEvaluation(Long id);
}
