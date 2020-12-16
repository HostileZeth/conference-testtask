package com.sataev.conferencewebcrud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		//manually authentication ?
		
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("presenter").password("presenter").roles("PRESENTER");
		auth.inMemoryAuthentication().withUser("listener").password("listener").roles("LISTENER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/protected/**").access("hasRole('ROLE_PRESENTER')")
				.antMatchers("/confidential/**").access("hasRole('ROLE_ADMIN')")
				.and().formLogin().defaultSuccessUrl("/index", false);

	}
}