package com.sataev.conferencewebcrud.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.sataev.conferencewebcrud" })
//@Import({ AppSecurityConfig.class })
public class WebConfig {
 

	/*
@Bean
 public InternalResourceViewResolver viewResolver() {
 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
 viewResolver.setViewClass(JstlView.class);
 //viewResolver.setPrefix("/WEB-INF/view/");
 viewResolver.setPrefix("/templates/");
 viewResolver.setSuffix(".jsp");
 return viewResolver;
 }*/
}