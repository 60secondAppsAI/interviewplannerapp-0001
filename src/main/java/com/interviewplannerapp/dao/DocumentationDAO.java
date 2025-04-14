package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Documentation;





public interface DocumentationDAO extends GenericDAO<Documentation, Integer> {
  
	List<Documentation> findAll();
	






}


