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

import com.interviewplannerapp.domain.Education;
import com.interviewplannerapp.dto.EducationDTO;
import com.interviewplannerapp.dto.EducationSearchDTO;
import com.interviewplannerapp.dto.EducationPageDTO;
import com.interviewplannerapp.service.EducationService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/education")
@RestController
public class EducationController {

	private final static Logger logger = LoggerFactory.getLogger(EducationController.class);

	@Autowired
	EducationService educationService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Education> getAll() {

		List<Education> educations = educationService.findAll();
		
		return educations;	
	}

	@GetMapping(value = "/{educationId}")
	@ResponseBody
	public EducationDTO getEducation(@PathVariable Integer educationId) {
		
		return (educationService.getEducationDTOById(educationId));
	}

 	@RequestMapping(value = "/addEducation", method = RequestMethod.POST)
	public ResponseEntity<?> addEducation(@RequestBody EducationDTO educationDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = educationService.addEducation(educationDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/educations")
	public ResponseEntity<EducationPageDTO> getEducations(EducationSearchDTO educationSearchDTO) {
 
		return educationService.getEducations(educationSearchDTO);
	}	

	@RequestMapping(value = "/updateEducation", method = RequestMethod.POST)
	public ResponseEntity<?> updateEducation(@RequestBody EducationDTO educationDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = educationService.updateEducation(educationDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
