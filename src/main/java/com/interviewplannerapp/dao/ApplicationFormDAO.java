package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.ApplicationForm;





public interface ApplicationFormDAO extends GenericDAO<ApplicationForm, Integer> {
  
	List<ApplicationForm> findAll();
	






}


