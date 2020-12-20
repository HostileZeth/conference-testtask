package com.sataev.conferencewebcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.Schedule;
import com.sataev.conferencewebcrud.repository.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired private ScheduleRepository scheduleRepository;
	
	public List<Schedule> getPresentations() {
		return scheduleRepository.findAll();
	}
	
	public List<Schedule> getPresentationsOdered() {
		return scheduleRepository.findAllByOrderByRoomAsc();
	}

}
