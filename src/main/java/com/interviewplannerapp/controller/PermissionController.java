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

import com.interviewplannerapp.domain.Permission;
import com.interviewplannerapp.dto.PermissionDTO;
import com.interviewplannerapp.dto.PermissionSearchDTO;
import com.interviewplannerapp.dto.PermissionPageDTO;
import com.interviewplannerapp.service.PermissionService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/permission")
@RestController
public class PermissionController {

	private final static Logger logger = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	PermissionService permissionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Permission> getAll() {

		List<Permission> permissions = permissionService.findAll();
		
		return permissions;	
	}

	@GetMapping(value = "/{permissionId}")
	@ResponseBody
	public PermissionDTO getPermission(@PathVariable Integer permissionId) {
		
		return (permissionService.getPermissionDTOById(permissionId));
	}

 	@RequestMapping(value = "/addPermission", method = RequestMethod.POST)
	public ResponseEntity<?> addPermission(@RequestBody PermissionDTO permissionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = permissionService.addPermission(permissionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/permissions")
	public ResponseEntity<PermissionPageDTO> getPermissions(PermissionSearchDTO permissionSearchDTO) {
 
		return permissionService.getPermissions(permissionSearchDTO);
	}	

	@RequestMapping(value = "/updatePermission", method = RequestMethod.POST)
	public ResponseEntity<?> updatePermission(@RequestBody PermissionDTO permissionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = permissionService.updatePermission(permissionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
