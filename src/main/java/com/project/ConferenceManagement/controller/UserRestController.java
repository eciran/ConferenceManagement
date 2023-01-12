package com.project.ConferenceManagement.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.PasswordModel;
import com.project.ConferenceManagement.model.UserResponse;
import com.project.ConferenceManagement.repository.UserRepository;
import com.project.ConferenceManagement.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
		
	@RequestMapping(value = "/getUserInfo")
	public UserResponse getUserInfo(@RequestBody String email) {
		UserEntity user=new UserEntity();
		UserResponse userResponse= new UserResponse();
		if(email!=null && email.length()>0) {
			user=userRepository.findByEmail(email.substring(1,email.length()-1));
			userResponse.setFirstName(user.getFirstName());
			userResponse.setLastName(user.getLastName());
			userResponse.setEmail(email.substring(1,email.length()-1));
		}		
		return userResponse;
	}
	
	@RequestMapping(value= "/updatePasswordByUser")
	public String updatePasswordByUser(@RequestBody PasswordModel passwordModel ) {
		String res="Failed";
		UserEntity user=new UserEntity();
		boolean regex=regexMatch(passwordModel.getNewPassword());
		if(regex) {
			try {
				user=userRepository.findByEmail(passwordModel.getEmail());
				if(user != null ) {
					boolean passCheck=userService.checkIfValidOldPassword(user.getPassword(),passwordModel.getOldPassword());
					if(passCheck) {
						try {
							userService.changePassword(user, passwordModel.getNewPassword());
							res="Success";
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else {
						res="Red";								
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			res="regexCntrl";
		}
		return res;
	}
	
	@RequestMapping(value = "/updateUserByUser")
	public String updateUserByUser(@RequestBody UserResponse user) {
		String ret="Failed";
		if(user!=null) {
			UserEntity updateUser=userRepository.findByEmail(user.getEmail());
			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());
			try {
				userRepository.save(updateUser);
				ret="Success";
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	private boolean regexMatch(String password) {
		return Pattern.compile("^(?:(?=.*?\\p{N})(?=.*?[\\p{S}\\p{P} ])(?=.*?\\p{Lu})(?=.*?\\p{Ll}))[^\\p{C}]{8,16}$")
				.matcher(password).matches();
	}
}
