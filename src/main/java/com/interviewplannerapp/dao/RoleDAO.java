package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Role;





public interface RoleDAO extends GenericDAO<Role, Integer> {
  
	List<Role> findAll();
	






}


