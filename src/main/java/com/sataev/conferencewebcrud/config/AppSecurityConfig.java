package com.sataev.conferencewebcrud.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sataev.conferencewebcrud.security.CustomAuthenticationProvider;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired private DataSource dataSource;
	@Autowired private AuthenticationProvider customAuthenticationProvider;
	
	public AppSecurityConfig() {
	    super(false);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http.authorizeRequests()
		.antMatchers("/presenter/**").access("hasRole('ROLE_PRESENTER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/").permitAll() //enabling
        .antMatchers("/h2-console/**").permitAll() //console
		.and().formLogin().defaultSuccessUrl("/index", false)
		.and().exceptionHandling().accessDeniedPage("/forbidden");
		
		/*http.authorizeRequests()
				.antMatchers("/protected/**").access("hasRole('ROLE_PRESENTER')")
				.antMatchers("/confidential/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/").permitAll() //enabling
                .antMatchers("/h2-console/**").permitAll() //console
				.and().formLogin().defaultSuccessUrl("/index", false)
				.and().exceptionHandling().accessDeniedPage("/forbidden")
				;*/
		
		
		http.csrf().disable(); //enabling
        http.headers().frameOptions().disable(); //console

	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider()
	{
		return new CustomAuthenticationProvider();
	}
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
	    return new ProviderManager(Arrays.asList(customAuthenticationProvider));
	}
	
	/*List<User> users = userService.findAll();
	for (User u : users) {
		auth.inMemoryAuthentication()
		.withUser(u.getUsername())
		.password(u.getPassword())
		.roles(u.getRole().toString());
	}*/
	
}