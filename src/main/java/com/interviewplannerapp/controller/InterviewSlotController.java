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

import com.interviewplannerapp.domain.InterviewSlot;
import com.interviewplannerapp.dto.InterviewSlotDTO;
import com.interviewplannerapp.dto.InterviewSlotSearchDTO;
import com.interviewplannerapp.dto.InterviewSlotPageDTO;
import com.interviewplannerapp.service.InterviewSlotService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/interviewSlot")
@RestController
public class InterviewSlotController {

	private final static Logger logger = LoggerFactory.getLogger(InterviewSlotController.class);

	@Autowired
	InterviewSlotService interviewSlotService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InterviewSlot> getAll() {

		List<InterviewSlot> interviewSlots = interviewSlotService.findAll();
		
		return interviewSlots;	
	}

	@GetMapping(value = "/{interviewSlotId}")
	@ResponseBody
	public InterviewSlotDTO getInterviewSlot(@PathVariable Integer interviewSlotId) {
		
		return (interviewSlotService.getInterviewSlotDTOById(interviewSlotId));
	}

 	@RequestMapping(value = "/addInterviewSlot", method = RequestMethod.POST)
	public ResponseEntity<?> addInterviewSlot(@RequestBody InterviewSlotDTO interviewSlotDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = interviewSlotService.addInterviewSlot(interviewSlotDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/interviewSlots")
	public ResponseEntity<InterviewSlotPageDTO> getInterviewSlots(InterviewSlotSearchDTO interviewSlotSearchDTO) {
 
		return interviewSlotService.getInterviewSlots(interviewSlotSearchDTO);
	}	

	@RequestMapping(value = "/updateInterviewSlot", method = RequestMethod.POST)
	public ResponseEntity<?> updateInterviewSlot(@RequestBody InterviewSlotDTO interviewSlotDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = interviewSlotService.updateInterviewSlot(interviewSlotDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
