package com.project.ConferenceManagement.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.config.CustomPropertyConfig;
import com.project.ConferenceManagement.model.MailModel;
import com.project.ConferenceManagement.service.MailService;
import com.project.ConferenceManagement.service.MailServiceSendblueApi;

import sibModel.GetSmtpTemplates;

@Service
public class EmailSenderServiceImpl implements MailService {

	@Autowired
	CustomPropertyConfig customPropertyConfig;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MailServiceSendblueApi mailServiceSendblueApi;

   public void sendEmail(MailModel mail) {
       MimeMessage mimeMessage = mailSender.createMimeMessage();
       try {
       
    	   MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
           mimeMessageHelper.setSubject(mail.getMailSubject());
           mimeMessageHelper.setFrom(customPropertyConfig.mailFrom);
           mimeMessageHelper.setTo(mail.getMailTo());
           mimeMessageHelper.setText(mail.getMailContent());

           mailSender.send(mimeMessageHelper.getMimeMessage());

       } catch (MessagingException e) {
           e.printStackTrace();
       }
   }
   @Override
   	public String sendEmailForResetPassword(String url, String email) {
		String result="Failed";
		GetSmtpTemplates mailTemplateResponse= mailServiceSendblueApi.getTemplatesSendblueApi();
		MailModel mail=new MailModel();
		if(mailTemplateResponse!=null) {
			for (int i = 0; i < mailTemplateResponse.getCount(); i++) {
				if(mailTemplateResponse.getTemplates().get(i).getName().equals("Reset Password Template") &&
					mailTemplateResponse.getTemplates().get(i).getHtmlContent().contains("href=\"https://signoraonline.com\" ")) {					
						mail.setMailTo(email);
						mail.setMailSubject(mailTemplateResponse.getTemplates().get(i).getSubject());
						mail.setMailContent(mailTemplateResponse.getTemplates().get(i).getHtmlContent().replace("href=\"https://signoraonline.com\" ",
								"href=\""+url+"\""));
						try {
							this.sendEmail(mail);
							result="Success";
						} catch (Exception e) {
							e.printStackTrace();
						}					
				}
			}
		}
		return result;
	}
   
   @Override
  	public String sendEmailForApplicationResult(String email,String template) {
		String result="Failed";
		GetSmtpTemplates mailTemplateResponse= mailServiceSendblueApi.getTemplatesSendblueApi();
		MailModel mail=new MailModel();
		if(mailTemplateResponse!=null) {
			for (int i = 0; i < mailTemplateResponse.getCount(); i++) {
				if(mailTemplateResponse.getTemplates().get(i).getName().equals(template)) {					
						mail.setMailTo(email);
						mail.setMailSubject(mailTemplateResponse.getTemplates().get(i).getSubject());
						mail.setMailContent(mailTemplateResponse.getTemplates().get(i).getHtmlContent());
						try {
							this.sendEmail(mail);
							result="Success";
						} catch (Exception e) {
							e.printStackTrace();
						}					
				}
			}
		}
		return result;
	}
}