package com.project.ConferenceManagement.service.impl;

import java.util.ArrayList;
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

import com.project.ConferenceManagement.entity.KeyEntity;
import com.project.ConferenceManagement.entity.PasswordResetToken;
import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.entity.VerificationToken;
import com.project.ConferenceManagement.model.AssignmentModel;
import com.project.ConferenceManagement.model.RefResponseModel;
import com.project.ConferenceManagement.model.UserKeyModel;
import com.project.ConferenceManagement.model.UserModel;
import com.project.ConferenceManagement.repository.PasswordResetTokenRepository;
import com.project.ConferenceManagement.repository.UserKeyRepository;
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
		
	@Autowired
	private UserKeyRepository userKeyRepository;
	
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
			userEntity.setRole(userModel.getRole());
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
	public String setUserRoleAndKey(UserKeyModel userKey) {
		String ret="";
		try {
			UserEntity user=  userRepository.findByEmail(userKey.getUserEmail());
			if (user!=null) {
				for (int i = 0; i < userKey.getKeyList().length; i++) {
					KeyEntity keyEntity= new KeyEntity();
					keyEntity.setUser(user);
					keyEntity.setMatchKey(userKey.getKeyList()[i]);;
					userKeyRepository.save(keyEntity);
				}
			}
			ret="Success";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			ret="Failed";
		}
		return ret;
	}

	@Override
	public Boolean getRefereInterestListCount(String userEmail) {
		boolean ret=false;
		try {
			UserEntity user= userRepository.findByEmail(userEmail);
			if(user!=null) {
				int count=userKeyRepository.findKeyCountByRef(user.getId());
				if(count>0) {
					ret= true;
				}else {
					ret=false;
				}				
			}
		} catch (IllegalArgumentException e) {
			return false;
		}
		return ret;
	}

	@Override
	public List<UserModel> getRefList() {
		List<UserModel> userModelList= new ArrayList<>();
		try {
			List<UserEntity> userList=userRepository.getRefList();
			if(userList!=null) {
				for (UserEntity userEntity : userList) {
					UserModel uModel= new UserModel();
					uModel.setFirstName(userEntity.getFirstName());
					uModel.setLastName(userEntity.getLastName());
					userModelList.add(uModel);
				}
			}
		} catch (IllegalArgumentException e) {
			
		}
		return userModelList;
	}
		@Override
		public List<UserModel> getAuthorList() {
			List<UserModel> userModelList= new ArrayList<>();
			try {
				List<UserEntity> userList=userRepository.getAuthorList();
				if(userList!=null) {
					for (UserEntity userEntity : userList) {
						UserModel uModel= new UserModel();
						uModel.setFirstName(userEntity.getFirstName());
						uModel.setLastName(userEntity.getLastName());
						userModelList.add(uModel);
					}
				}
			} catch (IllegalArgumentException e) {
				
			}
			return userModelList;
	}
}
