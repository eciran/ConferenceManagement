package com.project.ConferenceManagement.service;

import com.project.ConferenceManagement.model.MailModel;

public interface MailService {
	
	public void sendEmail(MailModel mail);
	
	public String sendEmailForResetPassword(String url, String email);
	
	public String sendEmailByContactHtml(String subject, String email, String messageText);
}
