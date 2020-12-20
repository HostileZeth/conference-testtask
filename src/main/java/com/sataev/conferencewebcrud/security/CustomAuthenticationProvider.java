package com.sataev.conferencewebcrud.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.service.UserService;


public class CustomAuthenticationProvider implements AuthenticationProvider  {
	
	@Autowired private UserService userService;
	@Autowired private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        User dbStoredUser = userService.findById(username).orElseThrow(() -> new BadCredentialsException("1000"));
        
        String role = dbStoredUser.getRole().toString();
        
        if (!passwordEncoder.matches(password, dbStoredUser.getPassword()))
        	throw new BadCredentialsException("1000");
        
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_"+role));
		
		return new UsernamePasswordAuthenticationToken(username, null, grantedAuths);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	

}
