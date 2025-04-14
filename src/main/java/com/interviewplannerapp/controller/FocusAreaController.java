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

import com.interviewplannerapp.domain.FocusArea;
import com.interviewplannerapp.dto.FocusAreaDTO;
import com.interviewplannerapp.dto.FocusAreaSearchDTO;
import com.interviewplannerapp.dto.FocusAreaPageDTO;
import com.interviewplannerapp.service.FocusAreaService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/focusArea")
@RestController
public class FocusAreaController {

	private final static Logger logger = LoggerFactory.getLogger(FocusAreaController.class);

	@Autowired
	FocusAreaService focusAreaService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<FocusArea> getAll() {

		List<FocusArea> focusAreas = focusAreaService.findAll();
		
		return focusAreas;	
	}

	@GetMapping(value = "/{focusAreaId}")
	@ResponseBody
	public FocusAreaDTO getFocusArea(@PathVariable Integer focusAreaId) {
		
		return (focusAreaService.getFocusAreaDTOById(focusAreaId));
	}

 	@RequestMapping(value = "/addFocusArea", method = RequestMethod.POST)
	public ResponseEntity<?> addFocusArea(@RequestBody FocusAreaDTO focusAreaDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = focusAreaService.addFocusArea(focusAreaDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/focusAreas")
	public ResponseEntity<FocusAreaPageDTO> getFocusAreas(FocusAreaSearchDTO focusAreaSearchDTO) {
 
		return focusAreaService.getFocusAreas(focusAreaSearchDTO);
	}	

	@RequestMapping(value = "/updateFocusArea", method = RequestMethod.POST)
	public ResponseEntity<?> updateFocusArea(@RequestBody FocusAreaDTO focusAreaDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = focusAreaService.updateFocusArea(focusAreaDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
