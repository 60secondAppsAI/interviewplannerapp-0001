package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.JobDescription;





public interface JobDescriptionDAO extends GenericDAO<JobDescription, Integer> {
  
	List<JobDescription> findAll();
	






}


