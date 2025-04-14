package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.FocusArea;





public interface FocusAreaDAO extends GenericDAO<FocusArea, Integer> {
  
	List<FocusArea> findAll();
	






}


