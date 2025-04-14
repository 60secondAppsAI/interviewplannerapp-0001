package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Resume;
import com.interviewplannerapp.dto.ResumeDTO;
import com.interviewplannerapp.dto.ResumeSearchDTO;
import com.interviewplannerapp.dto.ResumePageDTO;
import com.interviewplannerapp.dto.ResumeConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ResumeService extends GenericService<Resume, Integer> {

	List<Resume> findAll();

	ResultDTO addResume(ResumeDTO resumeDTO, RequestDTO requestDTO);

	ResultDTO updateResume(ResumeDTO resumeDTO, RequestDTO requestDTO);

    Page<Resume> getAllResumes(Pageable pageable);

    Page<Resume> getAllResumes(Specification<Resume> spec, Pageable pageable);

	ResponseEntity<ResumePageDTO> getResumes(ResumeSearchDTO resumeSearchDTO);
	
	List<ResumeDTO> convertResumesToResumeDTOs(List<Resume> resumes, ResumeConvertCriteriaDTO convertCriteria);

	ResumeDTO getResumeDTOById(Integer resumeId);







}





