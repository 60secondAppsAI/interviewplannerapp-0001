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

import com.interviewplannerapp.domain.Skill;
import com.interviewplannerapp.dto.SkillDTO;
import com.interviewplannerapp.dto.SkillSearchDTO;
import com.interviewplannerapp.dto.SkillPageDTO;
import com.interviewplannerapp.service.SkillService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/skill")
@RestController
public class SkillController {

	private final static Logger logger = LoggerFactory.getLogger(SkillController.class);

	@Autowired
	SkillService skillService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Skill> getAll() {

		List<Skill> skills = skillService.findAll();
		
		return skills;	
	}

	@GetMapping(value = "/{skillId}")
	@ResponseBody
	public SkillDTO getSkill(@PathVariable Integer skillId) {
		
		return (skillService.getSkillDTOById(skillId));
	}

 	@RequestMapping(value = "/addSkill", method = RequestMethod.POST)
	public ResponseEntity<?> addSkill(@RequestBody SkillDTO skillDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = skillService.addSkill(skillDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/skills")
	public ResponseEntity<SkillPageDTO> getSkills(SkillSearchDTO skillSearchDTO) {
 
		return skillService.getSkills(skillSearchDTO);
	}	

	@RequestMapping(value = "/updateSkill", method = RequestMethod.POST)
	public ResponseEntity<?> updateSkill(@RequestBody SkillDTO skillDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = skillService.updateSkill(skillDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
