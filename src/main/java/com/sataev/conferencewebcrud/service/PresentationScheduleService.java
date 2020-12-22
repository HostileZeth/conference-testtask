package com.sataev.conferencewebcrud.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.entity.User;
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
	
	public List<PresentationSchedule> getPresentationSchedulesByCreator(User creator) {
		return presentationScheduleRepository.findAllByCreator(creator);
	}
	
	public List<PresentationSchedule> getPresentationSchedulesByListener(User listener) {
		return presentationScheduleRepository.findAllByListeners(listener);
		//return listener.getSignUps();
	}
	
	public boolean isRoomAvailable(Room room, LocalDateTime date, long id) {
		return presentationScheduleRepository.countByRoomByDateAndNotThis(room, date, id) > 0 ? false : true;
	}
	
	public List<PresentationSchedule> findAllByOrderByRoomAscPresentationBeginAsc(){
		return presentationScheduleRepository.findAllByOrderByRoomAscPresentationBeginAsc();
	}
	
	public void delete(PresentationSchedule presentationSchedule) {
		presentationScheduleRepository.delete(presentationSchedule);
	}

}
