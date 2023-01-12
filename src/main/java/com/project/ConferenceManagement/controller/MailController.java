package com.project.ConferenceManagement.controller;


import java.util.ArrayList;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.personalize.model.Campaign;
import com.google.gson.JsonObject;
import com.project.ConferenceManagement.config.CustomPropertyConfig;
import com.project.ConferenceManagement.entity.UserEntity;
import com.project.ConferenceManagement.model.MailModel;
import com.project.ConferenceManagement.model.PasswordModel;
import com.project.ConferenceManagement.service.MailService;
import com.project.ConferenceManagement.service.MailServiceSendblueApi;
import com.project.ConferenceManagement.service.impl.EmailSenderServiceImpl;

import sibModel.GetSmtpTemplates;

@RestController
public class MailController {

	private EmailSenderServiceImpl emailSenderService;
	private CustomPropertyConfig customPropertyConfig;
	
	@Autowired
	MailServiceSendblueApi mailServiceSendblueApi;
	
	@Autowired
	MailService mailService;

	public MailController(EmailSenderServiceImpl emailSenderService, CustomPropertyConfig customPropertyConfig) {
		this.emailSenderService = emailSenderService;
		this.customPropertyConfig = customPropertyConfig;
	}
	
	@PostMapping(value = "/sendMail")
	public String sendMail(@RequestBody MailModel mail) throws MessagingException {
		String mailResult="";
		try {
			GetSmtpTemplates mailTemplateResponse= mailServiceSendblueApi.getTemplatesSendblueApi();
			if(mailTemplateResponse!=null) {
				mail.setMailContent(mailTemplateResponse.getTemplates().get(0).getHtmlContent());
			}
			emailSenderService.sendEmail(mail);
			mailResult="Success";
		} catch (Exception e) {
			mailResult="Failed";
			e.printStackTrace();
		}
		return mailResult;

	}
	
	@PostMapping("/contactMail")
	public String sendEmailByContactHtml(@RequestBody MailModel mailModel, HttpServletRequest request) {
		String response="";
			try {
				response=mailService.sendEmailByContactHtml(mailModel.getMailSubject(), mailModel.getMailTo(),mailModel.getMailContent());
			}
			catch (Exception e) {
				response="Failed";
				e.printStackTrace();
			}
		return response;
	}
}