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

import com.interviewplannerapp.domain.ApplicationForm;
import com.interviewplannerapp.dto.ApplicationFormDTO;
import com.interviewplannerapp.dto.ApplicationFormSearchDTO;
import com.interviewplannerapp.dto.ApplicationFormPageDTO;
import com.interviewplannerapp.service.ApplicationFormService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/applicationForm")
@RestController
public class ApplicationFormController {

	private final static Logger logger = LoggerFactory.getLogger(ApplicationFormController.class);

	@Autowired
	ApplicationFormService applicationFormService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ApplicationForm> getAll() {

		List<ApplicationForm> applicationForms = applicationFormService.findAll();
		
		return applicationForms;	
	}

	@GetMapping(value = "/{applicationFormId}")
	@ResponseBody
	public ApplicationFormDTO getApplicationForm(@PathVariable Integer applicationFormId) {
		
		return (applicationFormService.getApplicationFormDTOById(applicationFormId));
	}

 	@RequestMapping(value = "/addApplicationForm", method = RequestMethod.POST)
	public ResponseEntity<?> addApplicationForm(@RequestBody ApplicationFormDTO applicationFormDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = applicationFormService.addApplicationForm(applicationFormDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/applicationForms")
	public ResponseEntity<ApplicationFormPageDTO> getApplicationForms(ApplicationFormSearchDTO applicationFormSearchDTO) {
 
		return applicationFormService.getApplicationForms(applicationFormSearchDTO);
	}	

	@RequestMapping(value = "/updateApplicationForm", method = RequestMethod.POST)
	public ResponseEntity<?> updateApplicationForm(@RequestBody ApplicationFormDTO applicationFormDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = applicationFormService.updateApplicationForm(applicationFormDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
