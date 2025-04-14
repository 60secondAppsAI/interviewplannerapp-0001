package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Resume;





public interface ResumeDAO extends GenericDAO<Resume, Integer> {
  
	List<Resume> findAll();
	






}


