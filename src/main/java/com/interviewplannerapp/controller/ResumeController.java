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

import com.interviewplannerapp.domain.Resume;
import com.interviewplannerapp.dto.ResumeDTO;
import com.interviewplannerapp.dto.ResumeSearchDTO;
import com.interviewplannerapp.dto.ResumePageDTO;
import com.interviewplannerapp.service.ResumeService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/resume")
@RestController
public class ResumeController {

	private final static Logger logger = LoggerFactory.getLogger(ResumeController.class);

	@Autowired
	ResumeService resumeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Resume> getAll() {

		List<Resume> resumes = resumeService.findAll();
		
		return resumes;	
	}

	@GetMapping(value = "/{resumeId}")
	@ResponseBody
	public ResumeDTO getResume(@PathVariable Integer resumeId) {
		
		return (resumeService.getResumeDTOById(resumeId));
	}

 	@RequestMapping(value = "/addResume", method = RequestMethod.POST)
	public ResponseEntity<?> addResume(@RequestBody ResumeDTO resumeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = resumeService.addResume(resumeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/resumes")
	public ResponseEntity<ResumePageDTO> getResumes(ResumeSearchDTO resumeSearchDTO) {
 
		return resumeService.getResumes(resumeSearchDTO);
	}	

	@RequestMapping(value = "/updateResume", method = RequestMethod.POST)
	public ResponseEntity<?> updateResume(@RequestBody ResumeDTO resumeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = resumeService.updateResume(resumeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
