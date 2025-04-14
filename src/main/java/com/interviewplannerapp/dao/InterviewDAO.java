package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Interview;





public interface InterviewDAO extends GenericDAO<Interview, Integer> {
  
	List<Interview> findAll();
	






}


