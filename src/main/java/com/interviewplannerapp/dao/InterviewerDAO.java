package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Interviewer;





public interface InterviewerDAO extends GenericDAO<Interviewer, Integer> {
  
	List<Interviewer> findAll();
	






}


