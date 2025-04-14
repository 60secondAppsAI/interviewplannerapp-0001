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

import com.interviewplannerapp.domain.Question;
import com.interviewplannerapp.dto.QuestionDTO;
import com.interviewplannerapp.dto.QuestionSearchDTO;
import com.interviewplannerapp.dto.QuestionPageDTO;
import com.interviewplannerapp.service.QuestionService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/question")
@RestController
public class QuestionController {

	private final static Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	QuestionService questionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Question> getAll() {

		List<Question> questions = questionService.findAll();
		
		return questions;	
	}

	@GetMapping(value = "/{questionId}")
	@ResponseBody
	public QuestionDTO getQuestion(@PathVariable Integer questionId) {
		
		return (questionService.getQuestionDTOById(questionId));
	}

 	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
	public ResponseEntity<?> addQuestion(@RequestBody QuestionDTO questionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = questionService.addQuestion(questionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/questions")
	public ResponseEntity<QuestionPageDTO> getQuestions(QuestionSearchDTO questionSearchDTO) {
 
		return questionService.getQuestions(questionSearchDTO);
	}	

	@RequestMapping(value = "/updateQuestion", method = RequestMethod.POST)
	public ResponseEntity<?> updateQuestion(@RequestBody QuestionDTO questionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = questionService.updateQuestion(questionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
