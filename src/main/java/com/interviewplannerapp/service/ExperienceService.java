package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Experience;
import com.interviewplannerapp.dto.ExperienceDTO;
import com.interviewplannerapp.dto.ExperienceSearchDTO;
import com.interviewplannerapp.dto.ExperiencePageDTO;
import com.interviewplannerapp.dto.ExperienceConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ExperienceService extends GenericService<Experience, Integer> {

	List<Experience> findAll();

	ResultDTO addExperience(ExperienceDTO experienceDTO, RequestDTO requestDTO);

	ResultDTO updateExperience(ExperienceDTO experienceDTO, RequestDTO requestDTO);

    Page<Experience> getAllExperiences(Pageable pageable);

    Page<Experience> getAllExperiences(Specification<Experience> spec, Pageable pageable);

	ResponseEntity<ExperiencePageDTO> getExperiences(ExperienceSearchDTO experienceSearchDTO);
	
	List<ExperienceDTO> convertExperiencesToExperienceDTOs(List<Experience> experiences, ExperienceConvertCriteriaDTO convertCriteria);

	ExperienceDTO getExperienceDTOById(Integer experienceId);







}





