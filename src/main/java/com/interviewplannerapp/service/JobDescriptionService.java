package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.JobDescription;
import com.interviewplannerapp.dto.JobDescriptionDTO;
import com.interviewplannerapp.dto.JobDescriptionSearchDTO;
import com.interviewplannerapp.dto.JobDescriptionPageDTO;
import com.interviewplannerapp.dto.JobDescriptionConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface JobDescriptionService extends GenericService<JobDescription, Integer> {

	List<JobDescription> findAll();

	ResultDTO addJobDescription(JobDescriptionDTO jobDescriptionDTO, RequestDTO requestDTO);

	ResultDTO updateJobDescription(JobDescriptionDTO jobDescriptionDTO, RequestDTO requestDTO);

    Page<JobDescription> getAllJobDescriptions(Pageable pageable);

    Page<JobDescription> getAllJobDescriptions(Specification<JobDescription> spec, Pageable pageable);

	ResponseEntity<JobDescriptionPageDTO> getJobDescriptions(JobDescriptionSearchDTO jobDescriptionSearchDTO);
	
	List<JobDescriptionDTO> convertJobDescriptionsToJobDescriptionDTOs(List<JobDescription> jobDescriptions, JobDescriptionConvertCriteriaDTO convertCriteria);

	JobDescriptionDTO getJobDescriptionDTOById(Integer jobDescriptionId);







}





