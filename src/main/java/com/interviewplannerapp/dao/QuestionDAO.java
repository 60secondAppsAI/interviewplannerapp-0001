package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Question;





public interface QuestionDAO extends GenericDAO<Question, Integer> {
  
	List<Question> findAll();
	






}


