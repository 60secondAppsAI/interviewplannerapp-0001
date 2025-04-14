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

import com.interviewplannerapp.domain.Candidate;
import com.interviewplannerapp.dto.CandidateDTO;
import com.interviewplannerapp.dto.CandidateSearchDTO;
import com.interviewplannerapp.dto.CandidatePageDTO;
import com.interviewplannerapp.service.CandidateService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/candidate")
@RestController
public class CandidateController {

	private final static Logger logger = LoggerFactory.getLogger(CandidateController.class);

	@Autowired
	CandidateService candidateService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Candidate> getAll() {

		List<Candidate> candidates = candidateService.findAll();
		
		return candidates;	
	}

	@GetMapping(value = "/{candidateId}")
	@ResponseBody
	public CandidateDTO getCandidate(@PathVariable Integer candidateId) {
		
		return (candidateService.getCandidateDTOById(candidateId));
	}

 	@RequestMapping(value = "/addCandidate", method = RequestMethod.POST)
	public ResponseEntity<?> addCandidate(@RequestBody CandidateDTO candidateDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = candidateService.addCandidate(candidateDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/candidates")
	public ResponseEntity<CandidatePageDTO> getCandidates(CandidateSearchDTO candidateSearchDTO) {
 
		return candidateService.getCandidates(candidateSearchDTO);
	}	

	@RequestMapping(value = "/updateCandidate", method = RequestMethod.POST)
	public ResponseEntity<?> updateCandidate(@RequestBody CandidateDTO candidateDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = candidateService.updateCandidate(candidateDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
