package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Skill;
import com.interviewplannerapp.dto.SkillDTO;
import com.interviewplannerapp.dto.SkillSearchDTO;
import com.interviewplannerapp.dto.SkillPageDTO;
import com.interviewplannerapp.dto.SkillConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SkillService extends GenericService<Skill, Integer> {

	List<Skill> findAll();

	ResultDTO addSkill(SkillDTO skillDTO, RequestDTO requestDTO);

	ResultDTO updateSkill(SkillDTO skillDTO, RequestDTO requestDTO);

    Page<Skill> getAllSkills(Pageable pageable);

    Page<Skill> getAllSkills(Specification<Skill> spec, Pageable pageable);

	ResponseEntity<SkillPageDTO> getSkills(SkillSearchDTO skillSearchDTO);
	
	List<SkillDTO> convertSkillsToSkillDTOs(List<Skill> skills, SkillConvertCriteriaDTO convertCriteria);

	SkillDTO getSkillDTOById(Integer skillId);







}





