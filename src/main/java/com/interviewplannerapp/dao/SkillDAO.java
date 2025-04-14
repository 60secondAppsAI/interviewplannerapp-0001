package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Skill;





public interface SkillDAO extends GenericDAO<Skill, Integer> {
  
	List<Skill> findAll();
	






}


