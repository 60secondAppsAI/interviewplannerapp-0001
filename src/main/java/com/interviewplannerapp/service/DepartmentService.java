package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Department;
import com.interviewplannerapp.dto.DepartmentDTO;
import com.interviewplannerapp.dto.DepartmentSearchDTO;
import com.interviewplannerapp.dto.DepartmentPageDTO;
import com.interviewplannerapp.dto.DepartmentConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface DepartmentService extends GenericService<Department, Integer> {

	List<Department> findAll();

	ResultDTO addDepartment(DepartmentDTO departmentDTO, RequestDTO requestDTO);

	ResultDTO updateDepartment(DepartmentDTO departmentDTO, RequestDTO requestDTO);

    Page<Department> getAllDepartments(Pageable pageable);

    Page<Department> getAllDepartments(Specification<Department> spec, Pageable pageable);

	ResponseEntity<DepartmentPageDTO> getDepartments(DepartmentSearchDTO departmentSearchDTO);
	
	List<DepartmentDTO> convertDepartmentsToDepartmentDTOs(List<Department> departments, DepartmentConvertCriteriaDTO convertCriteria);

	DepartmentDTO getDepartmentDTOById(Integer departmentId);







}





