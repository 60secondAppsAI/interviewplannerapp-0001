package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Interviewer;
import com.interviewplannerapp.dto.InterviewerDTO;
import com.interviewplannerapp.dto.InterviewerSearchDTO;
import com.interviewplannerapp.dto.InterviewerPageDTO;
import com.interviewplannerapp.dto.InterviewerConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InterviewerService extends GenericService<Interviewer, Integer> {

	List<Interviewer> findAll();

	ResultDTO addInterviewer(InterviewerDTO interviewerDTO, RequestDTO requestDTO);

	ResultDTO updateInterviewer(InterviewerDTO interviewerDTO, RequestDTO requestDTO);

    Page<Interviewer> getAllInterviewers(Pageable pageable);

    Page<Interviewer> getAllInterviewers(Specification<Interviewer> spec, Pageable pageable);

	ResponseEntity<InterviewerPageDTO> getInterviewers(InterviewerSearchDTO interviewerSearchDTO);
	
	List<InterviewerDTO> convertInterviewersToInterviewerDTOs(List<Interviewer> interviewers, InterviewerConvertCriteriaDTO convertCriteria);

	InterviewerDTO getInterviewerDTOById(Integer interviewerId);







}





