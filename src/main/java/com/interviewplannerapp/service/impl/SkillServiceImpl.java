package com.interviewplannerapp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.service.impl.GenericServiceImpl;
import com.interviewplannerapp.dao.SkillDAO;
import com.interviewplannerapp.domain.Skill;
import com.interviewplannerapp.dto.SkillDTO;
import com.interviewplannerapp.dto.SkillSearchDTO;
import com.interviewplannerapp.dto.SkillPageDTO;
import com.interviewplannerapp.dto.SkillConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.SkillService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class SkillServiceImpl extends GenericServiceImpl<Skill, Integer> implements SkillService {

    private final static Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);

	@Autowired
	SkillDAO skillDao;

	


	@Override
	public GenericDAO<Skill, Integer> getDAO() {
		return (GenericDAO<Skill, Integer>) skillDao;
	}
	
	public List<Skill> findAll () {
		List<Skill> skills = skillDao.findAll();
		
		return skills;	
		
	}

	public ResultDTO addSkill(SkillDTO skillDTO, RequestDTO requestDTO) {

		Skill skill = new Skill();

		skill.setSkillId(skillDTO.getSkillId());


		skill.setName(skillDTO.getName());


		skill.setLevel(skillDTO.getLevel());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		skill = skillDao.save(skill);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Skill> getAllSkills(Pageable pageable) {
		return skillDao.findAll(pageable);
	}

	public Page<Skill> getAllSkills(Specification<Skill> spec, Pageable pageable) {
		return skillDao.findAll(spec, pageable);
	}

	public ResponseEntity<SkillPageDTO> getSkills(SkillSearchDTO skillSearchDTO) {
	
			Integer skillId = skillSearchDTO.getSkillId(); 
 			String name = skillSearchDTO.getName(); 
 			String level = skillSearchDTO.getLevel(); 
 			String sortBy = skillSearchDTO.getSortBy();
			String sortOrder = skillSearchDTO.getSortOrder();
			String searchQuery = skillSearchDTO.getSearchQuery();
			Integer page = skillSearchDTO.getPage();
			Integer size = skillSearchDTO.getSize();

	        Specification<Skill> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, skillId, "skillId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, level, "level"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("level")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Skill> skills = this.getAllSkills(spec, pageable);
		
		//System.out.println(String.valueOf(skills.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(skills.getTotalPages()));
		
		List<Skill> skillsList = skills.getContent();
		
		SkillConvertCriteriaDTO convertCriteria = new SkillConvertCriteriaDTO();
		List<SkillDTO> skillDTOs = this.convertSkillsToSkillDTOs(skillsList,convertCriteria);
		
		SkillPageDTO skillPageDTO = new SkillPageDTO();
		skillPageDTO.setSkills(skillDTOs);
		skillPageDTO.setTotalElements(skills.getTotalElements());
		return ResponseEntity.ok(skillPageDTO);
	}

	public List<SkillDTO> convertSkillsToSkillDTOs(List<Skill> skills, SkillConvertCriteriaDTO convertCriteria) {
		
		List<SkillDTO> skillDTOs = new ArrayList<SkillDTO>();
		
		for (Skill skill : skills) {
			skillDTOs.add(convertSkillToSkillDTO(skill,convertCriteria));
		}
		
		return skillDTOs;

	}
	
	public SkillDTO convertSkillToSkillDTO(Skill skill, SkillConvertCriteriaDTO convertCriteria) {
		
		SkillDTO skillDTO = new SkillDTO();
		
		skillDTO.setSkillId(skill.getSkillId());

	
		skillDTO.setName(skill.getName());

	
		skillDTO.setLevel(skill.getLevel());

	

		
		return skillDTO;
	}

	public ResultDTO updateSkill(SkillDTO skillDTO, RequestDTO requestDTO) {
		
		Skill skill = skillDao.getById(skillDTO.getSkillId());

		skill.setSkillId(ControllerUtils.setValue(skill.getSkillId(), skillDTO.getSkillId()));

		skill.setName(ControllerUtils.setValue(skill.getName(), skillDTO.getName()));

		skill.setLevel(ControllerUtils.setValue(skill.getLevel(), skillDTO.getLevel()));



        skill = skillDao.save(skill);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SkillDTO getSkillDTOById(Integer skillId) {
	
		Skill skill = skillDao.getById(skillId);
			
		
		SkillConvertCriteriaDTO convertCriteria = new SkillConvertCriteriaDTO();
		return(this.convertSkillToSkillDTO(skill,convertCriteria));
	}







}
