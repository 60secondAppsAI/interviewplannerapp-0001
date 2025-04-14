package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.Department;





public interface DepartmentDAO extends GenericDAO<Department, Integer> {
  
	List<Department> findAll();
	






}


