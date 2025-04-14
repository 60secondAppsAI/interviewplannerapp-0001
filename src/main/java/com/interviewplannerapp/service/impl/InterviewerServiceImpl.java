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
import com.interviewplannerapp.dao.InterviewerDAO;
import com.interviewplannerapp.domain.Interviewer;
import com.interviewplannerapp.dto.InterviewerDTO;
import com.interviewplannerapp.dto.InterviewerSearchDTO;
import com.interviewplannerapp.dto.InterviewerPageDTO;
import com.interviewplannerapp.dto.InterviewerConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.InterviewerService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class InterviewerServiceImpl extends GenericServiceImpl<Interviewer, Integer> implements InterviewerService {

    private final static Logger logger = LoggerFactory.getLogger(InterviewerServiceImpl.class);

	@Autowired
	InterviewerDAO interviewerDao;

	


	@Override
	public GenericDAO<Interviewer, Integer> getDAO() {
		return (GenericDAO<Interviewer, Integer>) interviewerDao;
	}
	
	public List<Interviewer> findAll () {
		List<Interviewer> interviewers = interviewerDao.findAll();
		
		return interviewers;	
		
	}

	public ResultDTO addInterviewer(InterviewerDTO interviewerDTO, RequestDTO requestDTO) {

		Interviewer interviewer = new Interviewer();

		interviewer.setInterviewerId(interviewerDTO.getInterviewerId());


		interviewer.setName(interviewerDTO.getName());


		interviewer.setExpertise(interviewerDTO.getExpertise());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		interviewer = interviewerDao.save(interviewer);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Interviewer> getAllInterviewers(Pageable pageable) {
		return interviewerDao.findAll(pageable);
	}

	public Page<Interviewer> getAllInterviewers(Specification<Interviewer> spec, Pageable pageable) {
		return interviewerDao.findAll(spec, pageable);
	}

	public ResponseEntity<InterviewerPageDTO> getInterviewers(InterviewerSearchDTO interviewerSearchDTO) {
	
			Integer interviewerId = interviewerSearchDTO.getInterviewerId(); 
 			String name = interviewerSearchDTO.getName(); 
 			String expertise = interviewerSearchDTO.getExpertise(); 
 			String sortBy = interviewerSearchDTO.getSortBy();
			String sortOrder = interviewerSearchDTO.getSortOrder();
			String searchQuery = interviewerSearchDTO.getSearchQuery();
			Integer page = interviewerSearchDTO.getPage();
			Integer size = interviewerSearchDTO.getSize();

	        Specification<Interviewer> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, interviewerId, "interviewerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, expertise, "expertise"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("expertise")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Interviewer> interviewers = this.getAllInterviewers(spec, pageable);
		
		//System.out.println(String.valueOf(interviewers.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(interviewers.getTotalPages()));
		
		List<Interviewer> interviewersList = interviewers.getContent();
		
		InterviewerConvertCriteriaDTO convertCriteria = new InterviewerConvertCriteriaDTO();
		List<InterviewerDTO> interviewerDTOs = this.convertInterviewersToInterviewerDTOs(interviewersList,convertCriteria);
		
		InterviewerPageDTO interviewerPageDTO = new InterviewerPageDTO();
		interviewerPageDTO.setInterviewers(interviewerDTOs);
		interviewerPageDTO.setTotalElements(interviewers.getTotalElements());
		return ResponseEntity.ok(interviewerPageDTO);
	}

	public List<InterviewerDTO> convertInterviewersToInterviewerDTOs(List<Interviewer> interviewers, InterviewerConvertCriteriaDTO convertCriteria) {
		
		List<InterviewerDTO> interviewerDTOs = new ArrayList<InterviewerDTO>();
		
		for (Interviewer interviewer : interviewers) {
			interviewerDTOs.add(convertInterviewerToInterviewerDTO(interviewer,convertCriteria));
		}
		
		return interviewerDTOs;

	}
	
	public InterviewerDTO convertInterviewerToInterviewerDTO(Interviewer interviewer, InterviewerConvertCriteriaDTO convertCriteria) {
		
		InterviewerDTO interviewerDTO = new InterviewerDTO();
		
		interviewerDTO.setInterviewerId(interviewer.getInterviewerId());

	
		interviewerDTO.setName(interviewer.getName());

	
		interviewerDTO.setExpertise(interviewer.getExpertise());

	

		
		return interviewerDTO;
	}

	public ResultDTO updateInterviewer(InterviewerDTO interviewerDTO, RequestDTO requestDTO) {
		
		Interviewer interviewer = interviewerDao.getById(interviewerDTO.getInterviewerId());

		interviewer.setInterviewerId(ControllerUtils.setValue(interviewer.getInterviewerId(), interviewerDTO.getInterviewerId()));

		interviewer.setName(ControllerUtils.setValue(interviewer.getName(), interviewerDTO.getName()));

		interviewer.setExpertise(ControllerUtils.setValue(interviewer.getExpertise(), interviewerDTO.getExpertise()));



        interviewer = interviewerDao.save(interviewer);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InterviewerDTO getInterviewerDTOById(Integer interviewerId) {
	
		Interviewer interviewer = interviewerDao.getById(interviewerId);
			
		
		InterviewerConvertCriteriaDTO convertCriteria = new InterviewerConvertCriteriaDTO();
		return(this.convertInterviewerToInterviewerDTO(interviewer,convertCriteria));
	}







}
