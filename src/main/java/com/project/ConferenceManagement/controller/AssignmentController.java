package com.project.ConferenceManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.service.AssignmentService;

@RestController
public class AssignmentController {
	
	@Autowired
	AssignmentService assignmentService;
	
	@PostMapping("/getRefList")
	public List<RefResponseModel> getRefList(){
		return assignmentService.getRefList();
	}
}
