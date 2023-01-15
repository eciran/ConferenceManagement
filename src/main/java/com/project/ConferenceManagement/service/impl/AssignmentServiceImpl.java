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
import com.project.ConferenceManagement.entity.KeyEntity;
import com.project.ConferenceManagement.entity.RefereeScoreEntity;
import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.EvaluationModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.model.RefereeScoreModel;
import com.project.ConferenceManagement.model.UserResponse;
import com.project.ConferenceManagement.repository.AssignmentRepository;
import com.project.ConferenceManagement.repository.EvaluationRepository;
import com.project.ConferenceManagement.repository.RefScoreRepository;
import com.project.ConferenceManagement.repository.UserKeyRepository;
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
	
	@Autowired
	UserKeyRepository keyRepository;
	
	@Autowired 
	RefScoreRepository refScoreRepository;
	
	@Override
	public List<RefResponseModel> getRefList(EvaluationModel evaluationModel) {
		List<RefResponseModel> refList=new ArrayList<>();
		List<KeyEntity> keyList= new ArrayList<>();
		try {
			keyList=keyRepository.findAllKeyByRef(evaluationModel.getKey());
			if(keyList!=null&&keyList.size()>0) {
					for (KeyEntity keyEntity : keyList) {
						if(!(keyEntity.getUser().getEmail().equals(evaluationModel.getEmail()))) {
							RefResponseModel ref=new RefResponseModel();
							ref.setRefId(keyEntity.getUser().getId());
							ref.setRefLastName(keyEntity.getUser().getLastName());
							ref.setRefName(keyEntity.getUser().getFirstName());
							refList.add(ref);
						}	
					}
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
					assEntity.setStatus(false);
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
	public List<RefereeScoreModel> getRefListForEvaluation(Long id) {
		List<AssignmentEntity> assEntity= null;
		List<RefereeScoreModel> refList=new ArrayList<>();
		try {
			assEntity= assignmentRepository.findAllEvaluationForScoring(id);
			for (AssignmentEntity assignmentEntity : assEntity) {
				RefereeScoreEntity refScore = refScoreRepository.findByAssignmentId(assignmentEntity.getId());
				RefereeScoreModel ref=new RefereeScoreModel();
				if(refScore!=null) {
					ref.setRefName(refScore.getAssignmentId().getReferee().getFirstName()+" "+refScore.getAssignmentId().getReferee().getLastName());
					ref.setDescription(refScore.getDescription());
					ref.setScore(refScore.getScore());
					ref.setScoreFilePath(refScore.getScoreFilePath());
				}
				else {
					Optional<UserEntity> user= userRepository.findById(assignmentEntity.getReferee().getId());
					if(!user.isEmpty()) {
						ref.setRefName(user.get().getFirstName()+" "+user.get().getLastName());
					}
				}
				refList.add(ref);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		 catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			}
		return refList;
	}

	@Override
	public List<EvaluationModel> getEvaluationForScoring(UserResponse userRes) {
		UserEntity user=null;
		List<EvaluationModel> evaModel=new ArrayList<>();
		try {
			user= userRepository.findByEmail(userRes.getEmail());
			if(user!=null) {
				List<AssignmentEntity> assignmentEntity= assignmentRepository.findAllEvaluationForScoring(user.getId());
				for (AssignmentEntity assEntity : assignmentEntity) {
					EvaluationModel eva=new EvaluationModel();
					eva.setId(assEntity.getEvaluation().getId());
					eva.setFilePath(assEntity.getEvaluation().getFilePath());
					eva.setKey(assEntity.getEvaluation().getMatchKey());
					eva.setDescription(assEntity.getEvaluation().getDescription());
					eva.setTitle(assEntity.getEvaluation().getTitle());
					eva.setUniName(assEntity.getEvaluation().getUniName());
					eva.setRefScoreStat(assEntity.isStatus());
					evaModel.add(eva);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return null;
		}
		return evaModel;
	}

	@Override
	public String setAssignmentScoreByRef(RefereeScoreModel refScoreModel) {
		String ret="";
		RefereeScoreEntity refEntity=null;
		try {
			UserEntity user= userRepository.findByEmail(refScoreModel.getUser_email());
			Optional<EvaluationEntity> eva=evaluationRepository.findById(refScoreModel.getEvaluation_id());
			if(user!=null && !eva.isEmpty()) {
				AssignmentEntity assignment = assignmentRepository.findAllAssignmentForScoring(user.getId(),eva.get().getId());
				if(assignment!=null) {
					refEntity= new RefereeScoreEntity();
					refEntity.setAssignmentId(assignment);
					refEntity.setDescription(refScoreModel.getDescription());
					refEntity.setScore(refScoreModel.getScore());
					refEntity.setScoreFilePath("\\ref\\"+refScoreModel.getScoreFilePath());
					refScoreRepository.save(refEntity);
				}
				assignment.setStatus(true);
				assignmentRepository.save(assignment);
			}
			ret="Success";
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			ret="Failed";
		}catch (NullPointerException e) {
			e.printStackTrace();
			ret="Failed";
		}
		return ret;
	}

	@Override
	public RefereeScoreModel getRefScoreResult(RefereeScoreModel refScoreModel) {
		try {
			RefereeScoreModel refModel=new RefereeScoreModel();
			UserEntity user= userRepository.findByEmail(refScoreModel.getUser_email());
			Optional<EvaluationEntity> eva=evaluationRepository.findById(refScoreModel.getEvaluation_id());
			if(user!=null && !eva.isEmpty()) {
				AssignmentEntity assignment = assignmentRepository.findAllAssignmentForScoring(user.getId(),eva.get().getId());
				if(assignment!=null) {
					RefereeScoreEntity refScore= refScoreRepository.findByAssignmentId(assignment.getId());
					if(refScore!=null) {
						refScoreModel.setDescription(refScore.getDescription());
						refScoreModel.setScore(refScore.getScore());
						refScoreModel.setScoreFilePath(refScore.getScoreFilePath());
					}
				}
			}
		}catch (NullPointerException e) {
			return null;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
		return refScoreModel;
	}
}
