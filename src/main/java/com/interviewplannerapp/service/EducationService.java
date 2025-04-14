package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Education;
import com.interviewplannerapp.dto.EducationDTO;
import com.interviewplannerapp.dto.EducationSearchDTO;
import com.interviewplannerapp.dto.EducationPageDTO;
import com.interviewplannerapp.dto.EducationConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface EducationService extends GenericService<Education, Integer> {

	List<Education> findAll();

	ResultDTO addEducation(EducationDTO educationDTO, RequestDTO requestDTO);

	ResultDTO updateEducation(EducationDTO educationDTO, RequestDTO requestDTO);

    Page<Education> getAllEducations(Pageable pageable);

    Page<Education> getAllEducations(Specification<Education> spec, Pageable pageable);

	ResponseEntity<EducationPageDTO> getEducations(EducationSearchDTO educationSearchDTO);
	
	List<EducationDTO> convertEducationsToEducationDTOs(List<Education> educations, EducationConvertCriteriaDTO convertCriteria);

	EducationDTO getEducationDTOById(Integer educationId);







}





