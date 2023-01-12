package com.project.ConferenceManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller  
public class RegistrationController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String index() {
		return "login.html";
	}
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String register() {
		return "register.html";
	}
	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello.html";
	}
	@RequestMapping(value="/resetPassword", method = RequestMethod.GET)
	public String resetPassword() {
		return "resetPassword.html";
	}
	@RequestMapping(value="/savePassword" ,method = RequestMethod.GET ,params = {"token"})
	public String savePassword() {	
		return "savePassword.html";
	}	

}