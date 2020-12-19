package com.sataev.conferencewebcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sataev.conferencewebcrud.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {

}
