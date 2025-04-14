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

import com.interviewplannerapp.domain.JobDescription;
import com.interviewplannerapp.dto.JobDescriptionDTO;
import com.interviewplannerapp.dto.JobDescriptionSearchDTO;
import com.interviewplannerapp.dto.JobDescriptionPageDTO;
import com.interviewplannerapp.service.JobDescriptionService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/jobDescription")
@RestController
public class JobDescriptionController {

	private final static Logger logger = LoggerFactory.getLogger(JobDescriptionController.class);

	@Autowired
	JobDescriptionService jobDescriptionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<JobDescription> getAll() {

		List<JobDescription> jobDescriptions = jobDescriptionService.findAll();
		
		return jobDescriptions;	
	}

	@GetMapping(value = "/{jobDescriptionId}")
	@ResponseBody
	public JobDescriptionDTO getJobDescription(@PathVariable Integer jobDescriptionId) {
		
		return (jobDescriptionService.getJobDescriptionDTOById(jobDescriptionId));
	}

 	@RequestMapping(value = "/addJobDescription", method = RequestMethod.POST)
	public ResponseEntity<?> addJobDescription(@RequestBody JobDescriptionDTO jobDescriptionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = jobDescriptionService.addJobDescription(jobDescriptionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/jobDescriptions")
	public ResponseEntity<JobDescriptionPageDTO> getJobDescriptions(JobDescriptionSearchDTO jobDescriptionSearchDTO) {
 
		return jobDescriptionService.getJobDescriptions(jobDescriptionSearchDTO);
	}	

	@RequestMapping(value = "/updateJobDescription", method = RequestMethod.POST)
	public ResponseEntity<?> updateJobDescription(@RequestBody JobDescriptionDTO jobDescriptionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = jobDescriptionService.updateJobDescription(jobDescriptionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
