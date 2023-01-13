package com.project.ConferenceManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ConferenceManagement.entity.AssignmentEntity;
import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.EvaluationModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.service.AssignmentService;
import com.project.ConferenceManagement.service.UserService;

@RestController
public class AssignmentController {
	
	@Autowired
	AssignmentService assignmentService;
	
	@Autowired
	UserService userServiceImpl;
	
	@PostMapping("/getRefList")
	public List<RefResponseModel> getRefList(@RequestBody EvaluationModel evaluationModel){
		return assignmentService.getRefList(evaluationModel);
	}
	
	@PostMapping("/setAssignmentList")
	public String setAssignmentList(@RequestBody AssignmentModel assignmentModel) {
		return assignmentService.setAssignmentList(assignmentModel);
	}
	@PostMapping("/getRefListByEvaluation")
	public List<RefResponseModel> getRefListByEvaluation(@RequestBody AssignmentModel assignmentModel) {
		List<RefResponseModel> list=assignmentService.getRefListForEvaluation(assignmentModel.getEvaluationId());
		return userServiceImpl.getRefListByEvaluation(list);
	}
}
