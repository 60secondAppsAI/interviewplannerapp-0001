package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Education;





public interface EducationDAO extends GenericDAO<Education, Integer> {
  
	List<Education> findAll();
	






}


