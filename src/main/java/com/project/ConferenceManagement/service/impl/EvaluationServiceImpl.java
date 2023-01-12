package com.project.ConferenceManagement.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.entity.EvaluationEntity;
import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.EvaluationModel;
import com.project.ConferenceManagement.repository.EvaluationRepository;
import com.project.ConferenceManagement.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService{
	
	@Autowired
	EvaluationRepository evaluationRepository;
	@Override
	public String toApply(EvaluationModel evaluationModel) {
		try {
			EvaluationEntity entity=new EvaluationEntity();
			entity.setUser(evaluationModel.getUser());
			entity.setDescription(evaluationModel.getDescription());
			entity.setFilePath(evaluationModel.getFilePath());
			entity.setMatchKey(evaluationModel.getKey());
			entity.setTitle(evaluationModel.getTitle());
			entity.setUniName(evaluationModel.getUniName());
			entity.setStatus(0);
			evaluationRepository.save(entity);
			return "Success";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return "Failed";
		}
		
	}

	@Override
	public List<EvaluationModel> getEvaluationTable(UserEntity user) {
		List<EvaluationEntity> list= new ArrayList<>();
		list=evaluationRepository.findAllEvaluationOfUsers(user);
		List<EvaluationModel> evaModelList = null;
		if(list!=null && list.size()>0) {
			evaModelList=new ArrayList<>();
			try {
				user.setPassword("");
				for (EvaluationEntity evaluationEntity : list) {
					EvaluationModel eva= new EvaluationModel();
					eva.setId(evaluationEntity.getId());
					eva.setFilePath(evaluationEntity.getFilePath());
					eva.setKey(evaluationEntity.getMatchKey());
					eva.setTitle(evaluationEntity.getTitle());
					eva.setStatus(evaluationEntity.getStatus());
					eva.setPoint(evaluationEntity.getPoint());
					eva.setUser(user);
					eva.setUniName(evaluationEntity.getUniName());
					eva.setDescription(evaluationEntity.getDescription());
					evaModelList.add(eva);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return evaModelList;
	}

	@Override
	public List<EvaluationModel> getEvaluationTableForAll() {
		List<EvaluationEntity> allEntityList=new ArrayList<>();
		List<EvaluationModel> allList= new ArrayList<>();
		try {
			allEntityList=evaluationRepository.findAll();
			for (EvaluationEntity evaluationEntity : allEntityList) {
				EvaluationModel eva=new EvaluationModel();
				eva.setId(evaluationEntity.getId());
				eva.setFilePath(evaluationEntity.getFilePath());
				eva.setKey(evaluationEntity.getMatchKey());
				eva.setTitle(evaluationEntity.getTitle());
				evaluationEntity.getUser().setPassword("");
				eva.setStatus(evaluationEntity.getStatus());
				eva.setPoint(evaluationEntity.getPoint());
				eva.setUser(evaluationEntity.getUser());
				eva.setUniName(evaluationEntity.getUniName());
				eva.setDescription(evaluationEntity.getDescription());
				allList.add(eva);
			}
		} catch (Exception e) {
			return null;
		}
		
		return allList;
	}


}
