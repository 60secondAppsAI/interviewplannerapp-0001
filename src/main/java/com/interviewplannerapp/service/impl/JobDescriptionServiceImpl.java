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
import com.interviewplannerapp.dao.JobDescriptionDAO;
import com.interviewplannerapp.domain.JobDescription;
import com.interviewplannerapp.dto.JobDescriptionDTO;
import com.interviewplannerapp.dto.JobDescriptionSearchDTO;
import com.interviewplannerapp.dto.JobDescriptionPageDTO;
import com.interviewplannerapp.dto.JobDescriptionConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.JobDescriptionService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class JobDescriptionServiceImpl extends GenericServiceImpl<JobDescription, Integer> implements JobDescriptionService {

    private final static Logger logger = LoggerFactory.getLogger(JobDescriptionServiceImpl.class);

	@Autowired
	JobDescriptionDAO jobDescriptionDao;

	


	@Override
	public GenericDAO<JobDescription, Integer> getDAO() {
		return (GenericDAO<JobDescription, Integer>) jobDescriptionDao;
	}
	
	public List<JobDescription> findAll () {
		List<JobDescription> jobDescriptions = jobDescriptionDao.findAll();
		
		return jobDescriptions;	
		
	}

	public ResultDTO addJobDescription(JobDescriptionDTO jobDescriptionDTO, RequestDTO requestDTO) {

		JobDescription jobDescription = new JobDescription();

		jobDescription.setJobDescriptionId(jobDescriptionDTO.getJobDescriptionId());


		jobDescription.setRoleTitle(jobDescriptionDTO.getRoleTitle());


		jobDescription.setDepartment(jobDescriptionDTO.getDepartment());


		jobDescription.setFilePath(jobDescriptionDTO.getFilePath());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		jobDescription = jobDescriptionDao.save(jobDescription);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<JobDescription> getAllJobDescriptions(Pageable pageable) {
		return jobDescriptionDao.findAll(pageable);
	}

	public Page<JobDescription> getAllJobDescriptions(Specification<JobDescription> spec, Pageable pageable) {
		return jobDescriptionDao.findAll(spec, pageable);
	}

	public ResponseEntity<JobDescriptionPageDTO> getJobDescriptions(JobDescriptionSearchDTO jobDescriptionSearchDTO) {
	
			Integer jobDescriptionId = jobDescriptionSearchDTO.getJobDescriptionId(); 
 			String roleTitle = jobDescriptionSearchDTO.getRoleTitle(); 
 			String department = jobDescriptionSearchDTO.getDepartment(); 
 			String filePath = jobDescriptionSearchDTO.getFilePath(); 
 			String sortBy = jobDescriptionSearchDTO.getSortBy();
			String sortOrder = jobDescriptionSearchDTO.getSortOrder();
			String searchQuery = jobDescriptionSearchDTO.getSearchQuery();
			Integer page = jobDescriptionSearchDTO.getPage();
			Integer size = jobDescriptionSearchDTO.getSize();

	        Specification<JobDescription> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, jobDescriptionId, "jobDescriptionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, roleTitle, "roleTitle"); 
			
			spec = ControllerUtils.andIfNecessary(spec, department, "department"); 
			
			spec = ControllerUtils.andIfNecessary(spec, filePath, "filePath"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("department")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("filePath")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<JobDescription> jobDescriptions = this.getAllJobDescriptions(spec, pageable);
		
		//System.out.println(String.valueOf(jobDescriptions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(jobDescriptions.getTotalPages()));
		
		List<JobDescription> jobDescriptionsList = jobDescriptions.getContent();
		
		JobDescriptionConvertCriteriaDTO convertCriteria = new JobDescriptionConvertCriteriaDTO();
		List<JobDescriptionDTO> jobDescriptionDTOs = this.convertJobDescriptionsToJobDescriptionDTOs(jobDescriptionsList,convertCriteria);
		
		JobDescriptionPageDTO jobDescriptionPageDTO = new JobDescriptionPageDTO();
		jobDescriptionPageDTO.setJobDescriptions(jobDescriptionDTOs);
		jobDescriptionPageDTO.setTotalElements(jobDescriptions.getTotalElements());
		return ResponseEntity.ok(jobDescriptionPageDTO);
	}

	public List<JobDescriptionDTO> convertJobDescriptionsToJobDescriptionDTOs(List<JobDescription> jobDescriptions, JobDescriptionConvertCriteriaDTO convertCriteria) {
		
		List<JobDescriptionDTO> jobDescriptionDTOs = new ArrayList<JobDescriptionDTO>();
		
		for (JobDescription jobDescription : jobDescriptions) {
			jobDescriptionDTOs.add(convertJobDescriptionToJobDescriptionDTO(jobDescription,convertCriteria));
		}
		
		return jobDescriptionDTOs;

	}
	
	public JobDescriptionDTO convertJobDescriptionToJobDescriptionDTO(JobDescription jobDescription, JobDescriptionConvertCriteriaDTO convertCriteria) {
		
		JobDescriptionDTO jobDescriptionDTO = new JobDescriptionDTO();
		
		jobDescriptionDTO.setJobDescriptionId(jobDescription.getJobDescriptionId());

	
		jobDescriptionDTO.setRoleTitle(jobDescription.getRoleTitle());

	
		jobDescriptionDTO.setDepartment(jobDescription.getDepartment());

	
		jobDescriptionDTO.setFilePath(jobDescription.getFilePath());

	

		
		return jobDescriptionDTO;
	}

	public ResultDTO updateJobDescription(JobDescriptionDTO jobDescriptionDTO, RequestDTO requestDTO) {
		
		JobDescription jobDescription = jobDescriptionDao.getById(jobDescriptionDTO.getJobDescriptionId());

		jobDescription.setJobDescriptionId(ControllerUtils.setValue(jobDescription.getJobDescriptionId(), jobDescriptionDTO.getJobDescriptionId()));

		jobDescription.setRoleTitle(ControllerUtils.setValue(jobDescription.getRoleTitle(), jobDescriptionDTO.getRoleTitle()));

		jobDescription.setDepartment(ControllerUtils.setValue(jobDescription.getDepartment(), jobDescriptionDTO.getDepartment()));

		jobDescription.setFilePath(ControllerUtils.setValue(jobDescription.getFilePath(), jobDescriptionDTO.getFilePath()));



        jobDescription = jobDescriptionDao.save(jobDescription);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public JobDescriptionDTO getJobDescriptionDTOById(Integer jobDescriptionId) {
	
		JobDescription jobDescription = jobDescriptionDao.getById(jobDescriptionId);
			
		
		JobDescriptionConvertCriteriaDTO convertCriteria = new JobDescriptionConvertCriteriaDTO();
		return(this.convertJobDescriptionToJobDescriptionDTO(jobDescription,convertCriteria));
	}







}
