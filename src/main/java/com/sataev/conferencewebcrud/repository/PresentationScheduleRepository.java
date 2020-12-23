package com.sataev.conferencewebcrud.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.entity.User;

public interface PresentationScheduleRepository extends JpaRepository<PresentationSchedule, Long> {
	
	public List<PresentationSchedule> findAllByCreator(User creator);
	
	public List<PresentationSchedule> findAllByOrderByRoomAscPresentationBeginAsc();
	
	@Query("SELECT COUNT(*) FROM PresentationSchedule ps " + 
			"WHERE ps.room = ?1 " + 
			"AND ((ps.presentationBegin BETWEEN ?2  AND ?3 ) OR " + 
			"(ps.presentationEnd BETWEEN ?2  AND ?3 ) OR " + 
			"( ?2 BETWEEN ps.presentationBegin AND ps.presentationEnd)) " +  
			"AND id <> ?4")	
	public int countByRoomByDateAndNotThis(Room room, LocalDateTime begin, LocalDateTime end, long id);
}