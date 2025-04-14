package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.InterviewSlot;





public interface InterviewSlotDAO extends GenericDAO<InterviewSlot, Integer> {
  
	List<InterviewSlot> findAll();
	






}


