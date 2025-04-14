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
import com.interviewplannerapp.dao.EducationDAO;
import com.interviewplannerapp.domain.Education;
import com.interviewplannerapp.dto.EducationDTO;
import com.interviewplannerapp.dto.EducationSearchDTO;
import com.interviewplannerapp.dto.EducationPageDTO;
import com.interviewplannerapp.dto.EducationConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.EducationService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class EducationServiceImpl extends GenericServiceImpl<Education, Integer> implements EducationService {

    private final static Logger logger = LoggerFactory.getLogger(EducationServiceImpl.class);

	@Autowired
	EducationDAO educationDao;

	


	@Override
	public GenericDAO<Education, Integer> getDAO() {
		return (GenericDAO<Education, Integer>) educationDao;
	}
	
	public List<Education> findAll () {
		List<Education> educations = educationDao.findAll();
		
		return educations;	
		
	}

	public ResultDTO addEducation(EducationDTO educationDTO, RequestDTO requestDTO) {

		Education education = new Education();

		education.setEducationId(educationDTO.getEducationId());


		education.setInstitution(educationDTO.getInstitution());


		education.setDegree(educationDTO.getDegree());


		education.setYearOfGraduation(educationDTO.getYearOfGraduation());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		education = educationDao.save(education);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Education> getAllEducations(Pageable pageable) {
		return educationDao.findAll(pageable);
	}

	public Page<Education> getAllEducations(Specification<Education> spec, Pageable pageable) {
		return educationDao.findAll(spec, pageable);
	}

	public ResponseEntity<EducationPageDTO> getEducations(EducationSearchDTO educationSearchDTO) {
	
			Integer educationId = educationSearchDTO.getEducationId(); 
 			String institution = educationSearchDTO.getInstitution(); 
 			String degree = educationSearchDTO.getDegree(); 
  			String sortBy = educationSearchDTO.getSortBy();
			String sortOrder = educationSearchDTO.getSortOrder();
			String searchQuery = educationSearchDTO.getSearchQuery();
			Integer page = educationSearchDTO.getPage();
			Integer size = educationSearchDTO.getSize();

	        Specification<Education> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, educationId, "educationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, institution, "institution"); 
			
			spec = ControllerUtils.andIfNecessary(spec, degree, "degree"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("institution")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("degree")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Education> educations = this.getAllEducations(spec, pageable);
		
		//System.out.println(String.valueOf(educations.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(educations.getTotalPages()));
		
		List<Education> educationsList = educations.getContent();
		
		EducationConvertCriteriaDTO convertCriteria = new EducationConvertCriteriaDTO();
		List<EducationDTO> educationDTOs = this.convertEducationsToEducationDTOs(educationsList,convertCriteria);
		
		EducationPageDTO educationPageDTO = new EducationPageDTO();
		educationPageDTO.setEducations(educationDTOs);
		educationPageDTO.setTotalElements(educations.getTotalElements());
		return ResponseEntity.ok(educationPageDTO);
	}

	public List<EducationDTO> convertEducationsToEducationDTOs(List<Education> educations, EducationConvertCriteriaDTO convertCriteria) {
		
		List<EducationDTO> educationDTOs = new ArrayList<EducationDTO>();
		
		for (Education education : educations) {
			educationDTOs.add(convertEducationToEducationDTO(education,convertCriteria));
		}
		
		return educationDTOs;

	}
	
	public EducationDTO convertEducationToEducationDTO(Education education, EducationConvertCriteriaDTO convertCriteria) {
		
		EducationDTO educationDTO = new EducationDTO();
		
		educationDTO.setEducationId(education.getEducationId());

	
		educationDTO.setInstitution(education.getInstitution());

	
		educationDTO.setDegree(education.getDegree());

	
		educationDTO.setYearOfGraduation(education.getYearOfGraduation());

	

		
		return educationDTO;
	}

	public ResultDTO updateEducation(EducationDTO educationDTO, RequestDTO requestDTO) {
		
		Education education = educationDao.getById(educationDTO.getEducationId());

		education.setEducationId(ControllerUtils.setValue(education.getEducationId(), educationDTO.getEducationId()));

		education.setInstitution(ControllerUtils.setValue(education.getInstitution(), educationDTO.getInstitution()));

		education.setDegree(ControllerUtils.setValue(education.getDegree(), educationDTO.getDegree()));

		education.setYearOfGraduation(ControllerUtils.setValue(education.getYearOfGraduation(), educationDTO.getYearOfGraduation()));



        education = educationDao.save(education);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public EducationDTO getEducationDTOById(Integer educationId) {
	
		Education education = educationDao.getById(educationId);
			
		
		EducationConvertCriteriaDTO convertCriteria = new EducationConvertCriteriaDTO();
		return(this.convertEducationToEducationDTO(education,convertCriteria));
	}







}
