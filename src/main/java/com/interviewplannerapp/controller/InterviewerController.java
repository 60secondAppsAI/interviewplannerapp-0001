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

import com.interviewplannerapp.domain.Interviewer;
import com.interviewplannerapp.dto.InterviewerDTO;
import com.interviewplannerapp.dto.InterviewerSearchDTO;
import com.interviewplannerapp.dto.InterviewerPageDTO;
import com.interviewplannerapp.service.InterviewerService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/interviewer")
@RestController
public class InterviewerController {

	private final static Logger logger = LoggerFactory.getLogger(InterviewerController.class);

	@Autowired
	InterviewerService interviewerService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Interviewer> getAll() {

		List<Interviewer> interviewers = interviewerService.findAll();
		
		return interviewers;	
	}

	@GetMapping(value = "/{interviewerId}")
	@ResponseBody
	public InterviewerDTO getInterviewer(@PathVariable Integer interviewerId) {
		
		return (interviewerService.getInterviewerDTOById(interviewerId));
	}

 	@RequestMapping(value = "/addInterviewer", method = RequestMethod.POST)
	public ResponseEntity<?> addInterviewer(@RequestBody InterviewerDTO interviewerDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = interviewerService.addInterviewer(interviewerDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/interviewers")
	public ResponseEntity<InterviewerPageDTO> getInterviewers(InterviewerSearchDTO interviewerSearchDTO) {
 
		return interviewerService.getInterviewers(interviewerSearchDTO);
	}	

	@RequestMapping(value = "/updateInterviewer", method = RequestMethod.POST)
	public ResponseEntity<?> updateInterviewer(@RequestBody InterviewerDTO interviewerDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = interviewerService.updateInterviewer(interviewerDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
