package com.sataev.conferencewebcrud;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.sataev.conferencewebcrud.security.CustomAuthenticationProvider;

@SpringBootTest
public class AuthenticationTests {

	@Autowired private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Test
	void logInTest() {
		
		//defaul listener account with username "listener" and password "listener"
		Authentication authentication = new Authentication() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getName() {
				return "listener";
			}
			
			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isAuthenticated() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getCredentials() {
				return "listener";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Authentication recievedToken =
				customAuthenticationProvider.authenticate(authentication);
		
		assert(recievedToken!=null);
	}
	
	@Test
	void logInFailTest() {
		
		//no such listener
		Authentication authentication = new Authentication() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getName() {
				return "listoener";
			}
			
			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isAuthenticated() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getCredentials() {
				return "listener";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Exception exception = assertThrows(BadCredentialsException.class, () -> {
					customAuthenticationProvider.authenticate(authentication);
	    });
		
		assert(exception != null);
	}
	
}
