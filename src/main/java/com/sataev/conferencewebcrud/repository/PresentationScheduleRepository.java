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
	
	public List<PresentationSchedule> findAllByListeners(User listener);
	
	//public List<PresentationSchedule> findByEffctDateAfterAndExpDateBefore(LocalDateTime date, Room room);
	
	//public List<PresentationSchedule> findByRoomByDateBetweenPresentationBeginAndPresentationEnd(LocalDateTime date, Room room);
	//public List<PresentationSchedule> findByDateBetweenPresentationBeginAndPresentationEnd(LocalDateTime date, Room room);
	
	public List<PresentationSchedule> findAllByOrderByRoomAscPresentationBeginAsc();
	
	@Query("SELECT COUNT(ps) FROM PresentationSchedule ps WHERE ?1 = ps.room AND (?2 BETWEEN ps.presentationBegin AND ps.presentationEnd) AND id <> ?3")
	public int countByRoomByDateAndNotThis(Room room, LocalDateTime date, long id);
	
}
