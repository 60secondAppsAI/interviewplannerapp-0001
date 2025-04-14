package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.ApplicationForm;
import com.interviewplannerapp.dto.ApplicationFormDTO;
import com.interviewplannerapp.dto.ApplicationFormSearchDTO;
import com.interviewplannerapp.dto.ApplicationFormPageDTO;
import com.interviewplannerapp.dto.ApplicationFormConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ApplicationFormService extends GenericService<ApplicationForm, Integer> {

	List<ApplicationForm> findAll();

	ResultDTO addApplicationForm(ApplicationFormDTO applicationFormDTO, RequestDTO requestDTO);

	ResultDTO updateApplicationForm(ApplicationFormDTO applicationFormDTO, RequestDTO requestDTO);

    Page<ApplicationForm> getAllApplicationForms(Pageable pageable);

    Page<ApplicationForm> getAllApplicationForms(Specification<ApplicationForm> spec, Pageable pageable);

	ResponseEntity<ApplicationFormPageDTO> getApplicationForms(ApplicationFormSearchDTO applicationFormSearchDTO);
	
	List<ApplicationFormDTO> convertApplicationFormsToApplicationFormDTOs(List<ApplicationForm> applicationForms, ApplicationFormConvertCriteriaDTO convertCriteria);

	ApplicationFormDTO getApplicationFormDTOById(Integer applicationFormId);







}





