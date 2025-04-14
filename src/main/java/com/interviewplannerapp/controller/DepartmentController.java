package com.interviewplannerapp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.interviewplannerapp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.interviewplannerapp.domain.Department;
import com.interviewplannerapp.dto.DepartmentDTO;
import com.interviewplannerapp.dto.DepartmentSearchDTO;
import com.interviewplannerapp.dto.DepartmentPageDTO;
import com.interviewplannerapp.service.DepartmentService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/department")
@RestController
public class DepartmentController {

	private final static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Department> getAll() {

		List<Department> departments = departmentService.findAll();
		
		return departments;	
	}

	@GetMapping(value = "/{departmentId}")
	@ResponseBody
	public DepartmentDTO getDepartment(@PathVariable Integer departmentId) {
		
		return (departmentService.getDepartmentDTOById(departmentId));
	}

 	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public ResponseEntity<?> addDepartment(@RequestBody DepartmentDTO departmentDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = departmentService.addDepartment(departmentDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/departments")
	public ResponseEntity<DepartmentPageDTO> getDepartments(DepartmentSearchDTO departmentSearchDTO) {
 
		return departmentService.getDepartments(departmentSearchDTO);
	}	

	@RequestMapping(value = "/updateDepartment", method = RequestMethod.POST)
	public ResponseEntity<?> updateDepartment(@RequestBody DepartmentDTO departmentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = departmentService.updateDepartment(departmentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
