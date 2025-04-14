package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Candidate;
import com.interviewplannerapp.dto.CandidateDTO;
import com.interviewplannerapp.dto.CandidateSearchDTO;
import com.interviewplannerapp.dto.CandidatePageDTO;
import com.interviewplannerapp.dto.CandidateConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CandidateService extends GenericService<Candidate, Integer> {

	List<Candidate> findAll();

	ResultDTO addCandidate(CandidateDTO candidateDTO, RequestDTO requestDTO);

	ResultDTO updateCandidate(CandidateDTO candidateDTO, RequestDTO requestDTO);

    Page<Candidate> getAllCandidates(Pageable pageable);

    Page<Candidate> getAllCandidates(Specification<Candidate> spec, Pageable pageable);

	ResponseEntity<CandidatePageDTO> getCandidates(CandidateSearchDTO candidateSearchDTO);
	
	List<CandidateDTO> convertCandidatesToCandidateDTOs(List<Candidate> candidates, CandidateConvertCriteriaDTO convertCriteria);

	CandidateDTO getCandidateDTOById(Integer candidateId);







}





