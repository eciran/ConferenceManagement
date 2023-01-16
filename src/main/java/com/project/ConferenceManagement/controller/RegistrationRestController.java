package com.project.ConferenceManagement.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.JwtRequest;
import com.project.ConferenceManagement.model.PasswordModel;
import com.project.ConferenceManagement.model.UserModel;
import com.project.ConferenceManagement.service.MailService;
import com.project.ConferenceManagement.service.MailServiceSendblueApi;
import com.project.ConferenceManagement.service.UserService;
@RestController
public class RegistrationRestController {

	@Autowired
	private UserService userService; 
	
	@Autowired
	private MailService mailService; 
	
					
	@PostMapping("/register")
	public String registerUser(@RequestBody UserModel userModel) {
		String result="";
		if(regexMatch(userModel.getPassword())) {	
			UserEntity user=userService.registerUser(userModel);
			if(user==null) {
				result= "Failed";
			}
			else {
				result= "Success";
			}				
		}
		else {
			result="regexCntrl";
		}
		return result;
	} 
	
	private String applicationUrl(HttpServletRequest request) {
		return  "http://"+
					request.getServerName()+
					":"+request.getServerPort()+
					request.getContextPath();
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
		UserEntity user=userService.findUserByEmail(passwordModel.getEmail());
		String url="";
		String response="";
		if(user!=null) {
			String token= UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user,token);
			url= passwordResetTokenMail(user,applicationUrl(request),token);
			try {
				response=mailService.sendEmailForResetPassword(url,passwordModel.getEmail());
			} catch (Exception e) {
				response="Failed";
				e.printStackTrace();
			}
		}
		return response;
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestBody PasswordModel passwordModel) {
		if(regexMatch(passwordModel.getNewPassword())) {	
			UserEntity user=userService.findUserByEmail(passwordModel.getEmail());
			if(!userService.checkIfValidOldPassword(user.getPassword(),passwordModel.getOldPassword())) {
				return "Invalid Old Password";
			}
			userService.changePassword(user,passwordModel.getNewPassword());
			return "Password Change Succesfully";
		}
		else {
			return "regexCntrl";
		}
	}
	
	@PostMapping("/savePassword")
	public String savePassword(@RequestParam String token,@RequestBody PasswordModel passwordModel) {
		String ret=null;
		if(regexMatch(passwordModel.getNewPassword())) {	
			if(token!=null) {
				String result = userService.validatePasswordToken(token);
				if (!result.equalsIgnoreCase("valid")) {
					ret= "Invalid token ";
				}
				Optional<UserEntity> user =userService.getUserByPasswordResetToken(token);
				if(user.isPresent()) {
					userService.changePassword(user.get(),passwordModel.getNewPassword());
					ret= "Password Reset Succesfully";
				}else {
					 ret= "Invalid Token";
				}
			}
			else {
				ret="Token is null";
			}
		}
		else {
			ret="regexCntrl";
		}
			return ret;
	}
	private String passwordResetTokenMail(UserEntity user, String applicationUrl, String token) {
		String url=
				applicationUrl+
				"/savePassword?token="
				+token;
		return url;
	}
	
	private boolean regexMatch(String password) {
		return Pattern.compile("^(?:(?=.*?\\p{N})(?=.*?[\\p{S}\\p{P} ])(?=.*?\\p{Lu})(?=.*?\\p{Ll}))[^\\p{C}]{8,16}$")
				.matcher(password).matches();
	}
	
}
