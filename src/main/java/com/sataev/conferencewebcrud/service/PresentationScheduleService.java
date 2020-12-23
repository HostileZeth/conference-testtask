package com.sataev.conferencewebcrud.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.repository.PresentationScheduleRepository;

@Service
public class PresentationScheduleService {

	@Autowired private PresentationScheduleRepository presentationScheduleRepository;
	
	public List<PresentationSchedule> getPresentationSchedules(){
		return presentationScheduleRepository.findAll();
	}

	public void save(PresentationSchedule presentationSchedule) {
		presentationScheduleRepository.save(presentationSchedule);
	}
	
	public Optional<PresentationSchedule> findById(long id) {
		return presentationScheduleRepository.findById(id);
	}
	
	public boolean isRoomAvailable(Room room, LocalDateTime begin, LocalDateTime end, long id) {
		return presentationScheduleRepository.countByRoomByDateAndNotThis(room, begin, end, id) > 0 ? false : true;
	}
	
	public List<PresentationSchedule> findAllByOrderByRoomAscPresentationBeginAsc(){
		return presentationScheduleRepository.findAllByOrderByRoomAscPresentationBeginAsc();
	}
	
	public void delete(PresentationSchedule presentationSchedule) {
		presentationScheduleRepository.delete(presentationSchedule);
	}

}
