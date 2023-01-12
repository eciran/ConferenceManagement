package com.project.ConferenceManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ConferenceManagement.config.CustomPropertyConfig;
import com.project.ConferenceManagement.service.MailServiceSendblueApi;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.GetSmtpTemplates;

@Service
public class MailServiceSendblueApiImpl implements MailServiceSendblueApi{
	
	@Autowired
	CustomPropertyConfig customPropertyConfig;

//	public MailServiceSendblueApiImpl() {
//		
//		ApiClient defaultClient = Configuration.getDefaultApiClient();
//		ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
//		apiKey.setApiKey(customPropertyConfig.api_key);
//	}

	@Override
	public GetSmtpTemplates getTemplatesSendblueApi() {
		ApiClient defaultClient = Configuration.getDefaultApiClient();
		ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
		apiKey.setApiKey(customPropertyConfig.api_key);
		GetSmtpTemplates mailTemplateResponse=null;
		try {
			TransactionalEmailsApi api = new TransactionalEmailsApi();
            Boolean templateStatus = true;
            Long limit = 5l;
            Long offset = 0l;
            String sort = null;
            mailTemplateResponse = api.getSmtpTemplates(templateStatus, limit, offset, sort);
            System.out.println(mailTemplateResponse.toString());
        } catch (Exception e) {
            System.out.println("Exception occurred:- " + e.getMessage());
        }
		return mailTemplateResponse;
	}

	
}
