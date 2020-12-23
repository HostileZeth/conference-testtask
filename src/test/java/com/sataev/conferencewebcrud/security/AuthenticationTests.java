package com.sataev.conferencewebcrud.security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.sataev.conferencewebcrud.entity.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationTests {
	@Autowired private CustomAuthenticationProvider customAuthenticationProvider;
	@LocalServerPort private int port;
	@Autowired private MockMvc mockMvc;
	
	@Test
	void authProviderLoads() {
		assert(customAuthenticationProvider != null);
	}
	
	@Test
	void authSuccessful() {
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
		
		//auth with valid login-password
		Authentication recievedToken =
				customAuthenticationProvider.authenticate(authentication);
		assert(recievedToken!=null);
	}
	
	@Test
	void authFailedTest() {
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
		
		//auth with invalid login-password
		Exception exception = assertThrows(BadCredentialsException.class, () -> {
					customAuthenticationProvider.authenticate(authentication);
	    });
		assert(exception != null);
	}
	
	@Test 
	@Transactional
	public void whenAddingPresentation_thenRecievePresentationsList() throws Exception {
		String newUsername = "mock-user";
		String newPassword = "mock-user";
		String newDisplayName = "mock-user";
		
		User user = new User(newUsername, newPassword, newDisplayName);
		this.mockMvc.perform( post("/adduser/").flashAttr("user", user)).andExpect(redirectedUrl("/"));
		
		Authentication authentication = new Authentication() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getName() {
				return "mock-user";
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
				return "mock-user";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Authentication recievedToken =
				customAuthenticationProvider.authenticate(authentication);
		assert(recievedToken != null);
	}
}
