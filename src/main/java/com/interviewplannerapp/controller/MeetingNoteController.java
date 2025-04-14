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

import com.interviewplannerapp.domain.MeetingNote;
import com.interviewplannerapp.dto.MeetingNoteDTO;
import com.interviewplannerapp.dto.MeetingNoteSearchDTO;
import com.interviewplannerapp.dto.MeetingNotePageDTO;
import com.interviewplannerapp.service.MeetingNoteService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/meetingNote")
@RestController
public class MeetingNoteController {

	private final static Logger logger = LoggerFactory.getLogger(MeetingNoteController.class);

	@Autowired
	MeetingNoteService meetingNoteService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<MeetingNote> getAll() {

		List<MeetingNote> meetingNotes = meetingNoteService.findAll();
		
		return meetingNotes;	
	}

	@GetMapping(value = "/{meetingNoteId}")
	@ResponseBody
	public MeetingNoteDTO getMeetingNote(@PathVariable Integer meetingNoteId) {
		
		return (meetingNoteService.getMeetingNoteDTOById(meetingNoteId));
	}

 	@RequestMapping(value = "/addMeetingNote", method = RequestMethod.POST)
	public ResponseEntity<?> addMeetingNote(@RequestBody MeetingNoteDTO meetingNoteDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = meetingNoteService.addMeetingNote(meetingNoteDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/meetingNotes")
	public ResponseEntity<MeetingNotePageDTO> getMeetingNotes(MeetingNoteSearchDTO meetingNoteSearchDTO) {
 
		return meetingNoteService.getMeetingNotes(meetingNoteSearchDTO);
	}	

	@RequestMapping(value = "/updateMeetingNote", method = RequestMethod.POST)
	public ResponseEntity<?> updateMeetingNote(@RequestBody MeetingNoteDTO meetingNoteDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = meetingNoteService.updateMeetingNote(meetingNoteDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
