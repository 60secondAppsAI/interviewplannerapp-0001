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

import com.interviewplannerapp.domain.Documentation;
import com.interviewplannerapp.dto.DocumentationDTO;
import com.interviewplannerapp.dto.DocumentationSearchDTO;
import com.interviewplannerapp.dto.DocumentationPageDTO;
import com.interviewplannerapp.service.DocumentationService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/documentation")
@RestController
public class DocumentationController {

	private final static Logger logger = LoggerFactory.getLogger(DocumentationController.class);

	@Autowired
	DocumentationService documentationService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Documentation> getAll() {

		List<Documentation> documentations = documentationService.findAll();
		
		return documentations;	
	}

	@GetMapping(value = "/{documentationId}")
	@ResponseBody
	public DocumentationDTO getDocumentation(@PathVariable Integer documentationId) {
		
		return (documentationService.getDocumentationDTOById(documentationId));
	}

 	@RequestMapping(value = "/addDocumentation", method = RequestMethod.POST)
	public ResponseEntity<?> addDocumentation(@RequestBody DocumentationDTO documentationDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = documentationService.addDocumentation(documentationDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/documentations")
	public ResponseEntity<DocumentationPageDTO> getDocumentations(DocumentationSearchDTO documentationSearchDTO) {
 
		return documentationService.getDocumentations(documentationSearchDTO);
	}	

	@RequestMapping(value = "/updateDocumentation", method = RequestMethod.POST)
	public ResponseEntity<?> updateDocumentation(@RequestBody DocumentationDTO documentationDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = documentationService.updateDocumentation(documentationDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
