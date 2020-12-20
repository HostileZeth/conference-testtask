package com.sataev.conferencewebcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.Presentation;
import com.sataev.conferencewebcrud.repository.PresentationRepository;

@Service
public class PresentationService {

	@Autowired
	private PresentationRepository presentationRepository;
	
	public List<Presentation> getPresentations() {
		return presentationRepository.findAll();
	}
	
	public void save(Presentation presentation) {
		presentationRepository.save(presentation);
	}

}
