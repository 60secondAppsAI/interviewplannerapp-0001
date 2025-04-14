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

import com.interviewplannerapp.domain.Interview;
import com.interviewplannerapp.dto.InterviewDTO;
import com.interviewplannerapp.dto.InterviewSearchDTO;
import com.interviewplannerapp.dto.InterviewPageDTO;
import com.interviewplannerapp.service.InterviewService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/interview")
@RestController
public class InterviewController {

	private final static Logger logger = LoggerFactory.getLogger(InterviewController.class);

	@Autowired
	InterviewService interviewService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Interview> getAll() {

		List<Interview> interviews = interviewService.findAll();
		
		return interviews;	
	}

	@GetMapping(value = "/{interviewId}")
	@ResponseBody
	public InterviewDTO getInterview(@PathVariable Integer interviewId) {
		
		return (interviewService.getInterviewDTOById(interviewId));
	}

 	@RequestMapping(value = "/addInterview", method = RequestMethod.POST)
	public ResponseEntity<?> addInterview(@RequestBody InterviewDTO interviewDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = interviewService.addInterview(interviewDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/interviews")
	public ResponseEntity<InterviewPageDTO> getInterviews(InterviewSearchDTO interviewSearchDTO) {
 
		return interviewService.getInterviews(interviewSearchDTO);
	}	

	@RequestMapping(value = "/updateInterview", method = RequestMethod.POST)
	public ResponseEntity<?> updateInterview(@RequestBody InterviewDTO interviewDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = interviewService.updateInterview(interviewDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
