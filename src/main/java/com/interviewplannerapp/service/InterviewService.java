package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Interview;
import com.interviewplannerapp.dto.InterviewDTO;
import com.interviewplannerapp.dto.InterviewSearchDTO;
import com.interviewplannerapp.dto.InterviewPageDTO;
import com.interviewplannerapp.dto.InterviewConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InterviewService extends GenericService<Interview, Integer> {

	List<Interview> findAll();

	ResultDTO addInterview(InterviewDTO interviewDTO, RequestDTO requestDTO);

	ResultDTO updateInterview(InterviewDTO interviewDTO, RequestDTO requestDTO);

    Page<Interview> getAllInterviews(Pageable pageable);

    Page<Interview> getAllInterviews(Specification<Interview> spec, Pageable pageable);

	ResponseEntity<InterviewPageDTO> getInterviews(InterviewSearchDTO interviewSearchDTO);
	
	List<InterviewDTO> convertInterviewsToInterviewDTOs(List<Interview> interviews, InterviewConvertCriteriaDTO convertCriteria);

	InterviewDTO getInterviewDTOById(Integer interviewId);







}





