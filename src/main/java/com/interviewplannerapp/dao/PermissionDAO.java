package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Permission;





public interface PermissionDAO extends GenericDAO<Permission, Integer> {
  
	List<Permission> findAll();
	






}


