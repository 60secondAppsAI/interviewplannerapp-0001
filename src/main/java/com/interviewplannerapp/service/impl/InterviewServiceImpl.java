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
import com.interviewplannerapp.dao.InterviewDAO;
import com.interviewplannerapp.domain.Interview;
import com.interviewplannerapp.dto.InterviewDTO;
import com.interviewplannerapp.dto.InterviewSearchDTO;
import com.interviewplannerapp.dto.InterviewPageDTO;
import com.interviewplannerapp.dto.InterviewConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.InterviewService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class InterviewServiceImpl extends GenericServiceImpl<Interview, Integer> implements InterviewService {

    private final static Logger logger = LoggerFactory.getLogger(InterviewServiceImpl.class);

	@Autowired
	InterviewDAO interviewDao;

	


	@Override
	public GenericDAO<Interview, Integer> getDAO() {
		return (GenericDAO<Interview, Integer>) interviewDao;
	}
	
	public List<Interview> findAll () {
		List<Interview> interviews = interviewDao.findAll();
		
		return interviews;	
		
	}

	public ResultDTO addInterview(InterviewDTO interviewDTO, RequestDTO requestDTO) {

		Interview interview = new Interview();

		interview.setInterviewId(interviewDTO.getInterviewId());


		interview.setDate(interviewDTO.getDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		interview = interviewDao.save(interview);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Interview> getAllInterviews(Pageable pageable) {
		return interviewDao.findAll(pageable);
	}

	public Page<Interview> getAllInterviews(Specification<Interview> spec, Pageable pageable) {
		return interviewDao.findAll(spec, pageable);
	}

	public ResponseEntity<InterviewPageDTO> getInterviews(InterviewSearchDTO interviewSearchDTO) {
	
			Integer interviewId = interviewSearchDTO.getInterviewId(); 
   			String sortBy = interviewSearchDTO.getSortBy();
			String sortOrder = interviewSearchDTO.getSortOrder();
			String searchQuery = interviewSearchDTO.getSearchQuery();
			Integer page = interviewSearchDTO.getPage();
			Integer size = interviewSearchDTO.getSize();

	        Specification<Interview> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, interviewId, "interviewId"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<Interview> interviews = this.getAllInterviews(spec, pageable);
		
		//System.out.println(String.valueOf(interviews.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(interviews.getTotalPages()));
		
		List<Interview> interviewsList = interviews.getContent();
		
		InterviewConvertCriteriaDTO convertCriteria = new InterviewConvertCriteriaDTO();
		List<InterviewDTO> interviewDTOs = this.convertInterviewsToInterviewDTOs(interviewsList,convertCriteria);
		
		InterviewPageDTO interviewPageDTO = new InterviewPageDTO();
		interviewPageDTO.setInterviews(interviewDTOs);
		interviewPageDTO.setTotalElements(interviews.getTotalElements());
		return ResponseEntity.ok(interviewPageDTO);
	}

	public List<InterviewDTO> convertInterviewsToInterviewDTOs(List<Interview> interviews, InterviewConvertCriteriaDTO convertCriteria) {
		
		List<InterviewDTO> interviewDTOs = new ArrayList<InterviewDTO>();
		
		for (Interview interview : interviews) {
			interviewDTOs.add(convertInterviewToInterviewDTO(interview,convertCriteria));
		}
		
		return interviewDTOs;

	}
	
	public InterviewDTO convertInterviewToInterviewDTO(Interview interview, InterviewConvertCriteriaDTO convertCriteria) {
		
		InterviewDTO interviewDTO = new InterviewDTO();
		
		interviewDTO.setInterviewId(interview.getInterviewId());

	
		interviewDTO.setDate(interview.getDate());

	

		
		return interviewDTO;
	}

	public ResultDTO updateInterview(InterviewDTO interviewDTO, RequestDTO requestDTO) {
		
		Interview interview = interviewDao.getById(interviewDTO.getInterviewId());

		interview.setInterviewId(ControllerUtils.setValue(interview.getInterviewId(), interviewDTO.getInterviewId()));

		interview.setDate(ControllerUtils.setValue(interview.getDate(), interviewDTO.getDate()));



        interview = interviewDao.save(interview);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InterviewDTO getInterviewDTOById(Integer interviewId) {
	
		Interview interview = interviewDao.getById(interviewId);
			
		
		InterviewConvertCriteriaDTO convertCriteria = new InterviewConvertCriteriaDTO();
		return(this.convertInterviewToInterviewDTO(interview,convertCriteria));
	}







}
