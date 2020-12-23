package com.sataev.conferencewebcrud.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;


//@SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainMenuControllerTests {
	
	@LocalServerPort private int port;
	@Autowired private MockMvc mockMvc;
	@Autowired private MainMenuController mainMenuController;
	
	@Test
	void contextLoads() {
		assert (mainMenuController !=null);
	}
	
	@Test
	void jsonAvailabilityTest() {
		String jsonMimeType = "application/json";
		HttpUriRequest request = new HttpGet( "http://localhost:" + port +  "/api/v1/schedule/" );
				   
		CloseableHttpResponse response = null;
		try {
		response = HttpClientBuilder.create().build().execute( request );
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
		assert( jsonMimeType.contentEquals(mimeType));
	}
	
	@Test
	public void shouldReturnMainMenu() throws Exception {
		this.mockMvc.perform( get("/")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")))
			.andExpect(content().string(containsString("505")));
	}
	
	@Test
	public void webRegistrationAvailabilityTest() throws Exception {	  
		this.mockMvc.perform( get("/signup")).andDo(print()).andExpect(status().isOk());

	}
}
