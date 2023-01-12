package com.project.ConferenceManagement.service;

import java.util.List;
import java.util.Optional;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.model.UserModel;

public interface UserService {

	UserEntity registerUser(UserModel userModel);
	void saveVerificationTokenForUser(String token,UserEntity user);
	UserEntity findUserByEmail(String email);
	void createPasswordResetTokenForUser(UserEntity user, String token);
	String validatePasswordToken(String token);
	Optional<UserEntity> getUserByPasswordResetToken(String token);
	void changePassword(UserEntity user, String newPassword);
	boolean checkIfValidOldPassword(String newPassword, String oldPassword);
	void loginUser(UserEntity userModel );
	List<RefResponseModel> getRefListByEvaluation(List<RefResponseModel> list);
}
