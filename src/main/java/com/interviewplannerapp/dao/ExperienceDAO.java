package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Experience;





public interface ExperienceDAO extends GenericDAO<Experience, Integer> {
  
	List<Experience> findAll();
	






}


