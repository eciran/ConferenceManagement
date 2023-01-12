package com.project.ConferenceManagement.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.entity.AssignmentEntity;
import com.project.ConferenceManagement.entity.EvaluationEntity;
import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.repository.AssignmentRepository;
import com.project.ConferenceManagement.repository.EvaluationRepository;
import com.project.ConferenceManagement.repository.UserRepository;
import com.project.ConferenceManagement.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EvaluationRepository evaluationRepository;
	
	@Override
	public List<RefResponseModel> getRefList() {
		List<UserEntity> userList= new ArrayList<>();
		List<RefResponseModel> refList=new ArrayList<>();
		try {
			userList=userRepository.getRefList();
			for (UserEntity userEntity : userList) {
				RefResponseModel ref=new RefResponseModel();
				ref.setRefId(userEntity.getId());
				ref.setRefLastName(userEntity.getLastName());
				ref.setRefName(userEntity.getFirstName());
				refList.add(ref);
			}
		}catch (IllegalArgumentException e) {
			return null;
		}catch (NullPointerException ex) {
			return null;
		}
		
		return refList;
	}

	@Override
	public String setAssignmentList(AssignmentModel assignmentModel) {
		String ret="";
		try {
			Optional<EvaluationEntity> evaluationEntity= evaluationRepository.findById(assignmentModel.getEvaluationId());
			for (int i = 0; i < assignmentModel.getRefereeList().length; i++) {
				AssignmentEntity assEntity= new AssignmentEntity();
				Optional<UserEntity> user= userRepository.findById(assignmentModel.getRefereeList()[i]);
				if(!(evaluationEntity.isEmpty() && user.isEmpty()) && evaluationEntity.get().getStatus()==0){
					assEntity.setEvaluation(evaluationEntity.get());
					assEntity.setReferee(user.get());
					assignmentRepository.save(assEntity);
				}
			}
			evaluationEntity.get().setStatus(1);
			evaluationRepository.save(evaluationEntity.get());
			ret="Success";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			ret="Failed";
		}
		return ret;
	}

	@Override
	public List<RefResponseModel> getRefListForEvaluation(Long id) {
		List<AssignmentEntity> assEntity= assignmentRepository.findAllRefForEvaluation();
		List<RefResponseModel> refList=new ArrayList<>();
		try {
			for (AssignmentEntity assignmentEntity : assEntity) {
				RefResponseModel ref=new RefResponseModel();
				ref.setRefId(assignmentEntity.getReferee().getId());
				refList.add(ref);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return refList;
	}

}
