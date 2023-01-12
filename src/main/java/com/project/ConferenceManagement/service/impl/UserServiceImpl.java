package com.project.ConferenceManagement.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.entity.PasswordResetToken;
import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.entity.VerificationToken;
import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.model.UserModel;
import com.project.ConferenceManagement.repository.PasswordResetTokenRepository;
import com.project.ConferenceManagement.repository.UserRepository;
import com.project.ConferenceManagement.repository.VerificationTokenRepository;
import com.project.ConferenceManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;	
		
	@Override
	public void loginUser(UserEntity userModel) {

	}
	
	@Override
	public void saveVerificationTokenForUser(String token, UserEntity user) {
		VerificationToken verificationToken= new VerificationToken(user,token);
		verificationTokenRepository.save(verificationToken);	
	}
	
	@Override
	public UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void createPasswordResetTokenForUser(UserEntity user, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
		passwordResetTokenRepository.save(passwordResetToken);
	}
	@Override
	public String validatePasswordToken(String token) {
		PasswordResetToken passwordResetToken=passwordResetTokenRepository.findByToken(token);
		String res=null;
		if (passwordResetToken==null) {
			res="invalid";
		}
		else {
			res="valid";
		}
//		UserEntity user = passwordResetToken.getUser();
		Calendar calendar= Calendar.getInstance();
		if ((res!=null && res.trim().length()>0) && (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()<=0)) {
			passwordResetTokenRepository.delete(passwordResetToken);
			return "expired";
		}
		return res;
	}
	@Override
	public Optional<UserEntity> getUserByPasswordResetToken(String token) {
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
	}

	@Override
	public UserEntity registerUser(UserModel userModel) {
		UserEntity userEntity=new UserEntity();
		try {
			
			userEntity.setEmail(userModel.getEmail());
			userEntity.setEnabled(true);
			userEntity.setFirstName(userModel.getFirstName());
			userEntity.setLastName(userModel.getLastName());
//			userEntity.setRole("USER");
			userEntity.setRegisterDate(new Date());
			userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));			
			userRepository.save(userEntity);
		} catch (Exception e) {
			userEntity=null;
		}
		return userEntity;
	}

	@Override
	public void changePassword(UserEntity user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		
	}

	@Override
	public boolean checkIfValidOldPassword(String password, String oldPassword) {
		return passwordEncoder.matches(oldPassword, password);
	}
	@Override
	public List<RefResponseModel> getRefListByEvaluation(List<RefResponseModel> list) {
		for (RefResponseModel refResponseModel : list) {
			Optional<UserEntity> user = userRepository.findById(refResponseModel.getRefId());
			if(!user.isEmpty()) {
				refResponseModel.setRefName(user.get().getFirstName());
				refResponseModel.setRefLastName(user.get().getLastName());
			}
		}	
		return list;
	}
}
