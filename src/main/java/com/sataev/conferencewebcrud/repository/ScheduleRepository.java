package com.sataev.conferencewebcrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sataev.conferencewebcrud.entity.Presentation;
import com.sataev.conferencewebcrud.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Presentation> {

	public List<Schedule> findAllByOrderByRoomAsc();
	public List<Schedule> findAllByOrderByRoomAscPresentationBeginAsc();
	
}