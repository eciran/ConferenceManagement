package com.project.ConferenceManagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mailService.yml")
public class CustomPropertyConfig {
  @Value("${mailFrom}")
  public String mailFrom;
  @Value("${apikey}")
  public String api_key;
 
}