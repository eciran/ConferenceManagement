package com.project.ConferenceManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.repository.AssignmentRepository;
import com.project.ConferenceManagement.repository.UserRepository;
import com.project.ConferenceManagement.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	UserRepository userRepository;
	
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

}
