package com.project.ConferenceManagement.controller;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.repository.UserRepository;

@RestController
public class AdminController {

	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value = "/userListAdmin")
	public List<UserEntity> userListAdmin(){
		List<UserEntity> userList=null;
		userList=userRepository.findAll();
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setPassword("");
		}
		return userList;
	}

	
	@PostMapping(value = "/userLastRegister")
	public String userLastRegisterAdmin(@RequestBody Date date){
		String ret=null;
		ret=userRepository.getuserLastRegister(date);
		return ret;
	}
	
	@PostMapping(value = "/userUpdateByAdmin")
	public String userUpdateByAdmin(@RequestBody UserEntity user){
		String ret="Failed";
		if(user!=null) {
			UserEntity updateUser=userRepository.findByEmail(user.getEmail());
			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());
			updateUser.setRole(user.getRole());
			try {
				userRepository.save(updateUser);
				ret="Success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	@GetMapping(value = "/admin/dashboard")
	public String adminDashoard(String token){
		UserEntity user = null;
		String ret="User";
		user= userRepository.findByEmail(user.getEmail());
		if(user.getRole().equals("ADMIN") || user.getRole().equals("Admin") || user.getRole().equals("admin")) {
			ret="Admin";
		}
		return ret;
	}
}
