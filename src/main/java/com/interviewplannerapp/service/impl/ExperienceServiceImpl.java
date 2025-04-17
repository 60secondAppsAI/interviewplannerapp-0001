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
import com.interviewplannerapp.dao.ExperienceDAO;
import com.interviewplannerapp.domain.Experience;
import com.interviewplannerapp.dto.ExperienceDTO;
import com.interviewplannerapp.dto.ExperienceSearchDTO;
import com.interviewplannerapp.dto.ExperiencePageDTO;
import com.interviewplannerapp.dto.ExperienceConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.ExperienceService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class ExperienceServiceImpl extends GenericServiceImpl<Experience, Integer> implements ExperienceService {

    private final static Logger logger = LoggerFactory.getLogger(ExperienceServiceImpl.class);

	@Autowired
	ExperienceDAO experienceDao;

	


	@Override
	public GenericDAO<Experience, Integer> getDAO() {
		return (GenericDAO<Experience, Integer>) experienceDao;
	}
	
	public List<Experience> findAll () {
		List<Experience> experiences = experienceDao.findAll();
		
		return experiences;	
		
	}

	public ResultDTO addExperience(ExperienceDTO experienceDTO, RequestDTO requestDTO) {

		Experience experience = new Experience();

		experience.setExperienceId(experienceDTO.getExperienceId());


		experience.setCompanyName(experienceDTO.getCompanyName());


		experience.setRoleTitle(experienceDTO.getRoleTitle());


		experience.setYears(experienceDTO.getYears());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		experience = experienceDao.save(experience);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Experience> getAllExperiences(Pageable pageable) {
		return experienceDao.findAll(pageable);
	}

	public Page<Experience> getAllExperiences(Specification<Experience> spec, Pageable pageable) {
		return experienceDao.findAll(spec, pageable);
	}

	public ResponseEntity<ExperiencePageDTO> getExperiences(ExperienceSearchDTO experienceSearchDTO) {
	
			Integer experienceId = experienceSearchDTO.getExperienceId(); 
 			String companyName = experienceSearchDTO.getCompanyName(); 
 			String roleTitle = experienceSearchDTO.getRoleTitle(); 
  			String sortBy = experienceSearchDTO.getSortBy();
			String sortOrder = experienceSearchDTO.getSortOrder();
			String searchQuery = experienceSearchDTO.getSearchQuery();
			Integer page = experienceSearchDTO.getPage();
			Integer size = experienceSearchDTO.getSize();

	        Specification<Experience> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, experienceId, "experienceId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, companyName, "companyName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, roleTitle, "roleTitle"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("companyName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("roleTitle")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Experience> experiences = this.getAllExperiences(spec, pageable);
		
		//System.out.println(String.valueOf(experiences.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(experiences.getTotalPages()));
		
		List<Experience> experiencesList = experiences.getContent();
		
		ExperienceConvertCriteriaDTO convertCriteria = new ExperienceConvertCriteriaDTO();
		List<ExperienceDTO> experienceDTOs = this.convertExperiencesToExperienceDTOs(experiencesList,convertCriteria);
		
		ExperiencePageDTO experiencePageDTO = new ExperiencePageDTO();
		experiencePageDTO.setExperiences(experienceDTOs);
		experiencePageDTO.setTotalElements(experiences.getTotalElements());
		return ResponseEntity.ok(experiencePageDTO);
	}

	public List<ExperienceDTO> convertExperiencesToExperienceDTOs(List<Experience> experiences, ExperienceConvertCriteriaDTO convertCriteria) {
		
		List<ExperienceDTO> experienceDTOs = new ArrayList<ExperienceDTO>();
		
		for (Experience experience : experiences) {
			experienceDTOs.add(convertExperienceToExperienceDTO(experience,convertCriteria));
		}
		
		return experienceDTOs;

	}
	
	public ExperienceDTO convertExperienceToExperienceDTO(Experience experience, ExperienceConvertCriteriaDTO convertCriteria) {
		
		ExperienceDTO experienceDTO = new ExperienceDTO();
		
		experienceDTO.setExperienceId(experience.getExperienceId());

	
		experienceDTO.setCompanyName(experience.getCompanyName());

	
		experienceDTO.setRoleTitle(experience.getRoleTitle());

	
		experienceDTO.setYears(experience.getYears());

	

		
		return experienceDTO;
	}

	public ResultDTO updateExperience(ExperienceDTO experienceDTO, RequestDTO requestDTO) {
		
		Experience experience = experienceDao.getById(experienceDTO.getExperienceId());

		experience.setExperienceId(ControllerUtils.setValue(experience.getExperienceId(), experienceDTO.getExperienceId()));

		experience.setCompanyName(ControllerUtils.setValue(experience.getCompanyName(), experienceDTO.getCompanyName()));

		experience.setRoleTitle(ControllerUtils.setValue(experience.getRoleTitle(), experienceDTO.getRoleTitle()));

		experience.setYears(ControllerUtils.setValue(experience.getYears(), experienceDTO.getYears()));



        experience = experienceDao.save(experience);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ExperienceDTO getExperienceDTOById(Integer experienceId) {
	
		Experience experience = experienceDao.getById(experienceId);
			
		
		ExperienceConvertCriteriaDTO convertCriteria = new ExperienceConvertCriteriaDTO();
		return(this.convertExperienceToExperienceDTO(experience,convertCriteria));
	}







}
