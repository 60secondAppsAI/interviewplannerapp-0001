package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Role;
import com.interviewplannerapp.dto.RoleDTO;
import com.interviewplannerapp.dto.RoleSearchDTO;
import com.interviewplannerapp.dto.RolePageDTO;
import com.interviewplannerapp.dto.RoleConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RoleService extends GenericService<Role, Integer> {

	List<Role> findAll();

	ResultDTO addRole(RoleDTO roleDTO, RequestDTO requestDTO);

	ResultDTO updateRole(RoleDTO roleDTO, RequestDTO requestDTO);

    Page<Role> getAllRoles(Pageable pageable);

    Page<Role> getAllRoles(Specification<Role> spec, Pageable pageable);

	ResponseEntity<RolePageDTO> getRoles(RoleSearchDTO roleSearchDTO);
	
	List<RoleDTO> convertRolesToRoleDTOs(List<Role> roles, RoleConvertCriteriaDTO convertCriteria);

	RoleDTO getRoleDTOById(Integer roleId);







}





