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
import com.interviewplannerapp.dao.FeedbackDAO;
import com.interviewplannerapp.domain.Feedback;
import com.interviewplannerapp.dto.FeedbackDTO;
import com.interviewplannerapp.dto.FeedbackSearchDTO;
import com.interviewplannerapp.dto.FeedbackPageDTO;
import com.interviewplannerapp.dto.FeedbackConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.FeedbackService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class FeedbackServiceImpl extends GenericServiceImpl<Feedback, Integer> implements FeedbackService {

    private final static Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	@Autowired
	FeedbackDAO feedbackDao;

	


	@Override
	public GenericDAO<Feedback, Integer> getDAO() {
		return (GenericDAO<Feedback, Integer>) feedbackDao;
	}
	
	public List<Feedback> findAll () {
		List<Feedback> feedbacks = feedbackDao.findAll();
		
		return feedbacks;	
		
	}

	public ResultDTO addFeedback(FeedbackDTO feedbackDTO, RequestDTO requestDTO) {

		Feedback feedback = new Feedback();

		feedback.setFeedbackId(feedbackDTO.getFeedbackId());


		feedback.setContent(feedbackDTO.getContent());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		feedback = feedbackDao.save(feedback);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Feedback> getAllFeedbacks(Pageable pageable) {
		return feedbackDao.findAll(pageable);
	}

	public Page<Feedback> getAllFeedbacks(Specification<Feedback> spec, Pageable pageable) {
		return feedbackDao.findAll(spec, pageable);
	}

	public ResponseEntity<FeedbackPageDTO> getFeedbacks(FeedbackSearchDTO feedbackSearchDTO) {
	
			Integer feedbackId = feedbackSearchDTO.getFeedbackId(); 
 			String content = feedbackSearchDTO.getContent(); 
 			String sortBy = feedbackSearchDTO.getSortBy();
			String sortOrder = feedbackSearchDTO.getSortOrder();
			String searchQuery = feedbackSearchDTO.getSearchQuery();
			Integer page = feedbackSearchDTO.getPage();
			Integer size = feedbackSearchDTO.getSize();

	        Specification<Feedback> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, feedbackId, "feedbackId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, content, "content"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("content")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Feedback> feedbacks = this.getAllFeedbacks(spec, pageable);
		
		//System.out.println(String.valueOf(feedbacks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(feedbacks.getTotalPages()));
		
		List<Feedback> feedbacksList = feedbacks.getContent();
		
		FeedbackConvertCriteriaDTO convertCriteria = new FeedbackConvertCriteriaDTO();
		List<FeedbackDTO> feedbackDTOs = this.convertFeedbacksToFeedbackDTOs(feedbacksList,convertCriteria);
		
		FeedbackPageDTO feedbackPageDTO = new FeedbackPageDTO();
		feedbackPageDTO.setFeedbacks(feedbackDTOs);
		feedbackPageDTO.setTotalElements(feedbacks.getTotalElements());
		return ResponseEntity.ok(feedbackPageDTO);
	}

	public List<FeedbackDTO> convertFeedbacksToFeedbackDTOs(List<Feedback> feedbacks, FeedbackConvertCriteriaDTO convertCriteria) {
		
		List<FeedbackDTO> feedbackDTOs = new ArrayList<FeedbackDTO>();
		
		for (Feedback feedback : feedbacks) {
			feedbackDTOs.add(convertFeedbackToFeedbackDTO(feedback,convertCriteria));
		}
		
		return feedbackDTOs;

	}
	
	public FeedbackDTO convertFeedbackToFeedbackDTO(Feedback feedback, FeedbackConvertCriteriaDTO convertCriteria) {
		
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		
		feedbackDTO.setFeedbackId(feedback.getFeedbackId());

	
		feedbackDTO.setContent(feedback.getContent());

	

		
		return feedbackDTO;
	}

	public ResultDTO updateFeedback(FeedbackDTO feedbackDTO, RequestDTO requestDTO) {
		
		Feedback feedback = feedbackDao.getById(feedbackDTO.getFeedbackId());

		feedback.setFeedbackId(ControllerUtils.setValue(feedback.getFeedbackId(), feedbackDTO.getFeedbackId()));

		feedback.setContent(ControllerUtils.setValue(feedback.getContent(), feedbackDTO.getContent()));



        feedback = feedbackDao.save(feedback);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public FeedbackDTO getFeedbackDTOById(Integer feedbackId) {
	
		Feedback feedback = feedbackDao.getById(feedbackId);
			
		
		FeedbackConvertCriteriaDTO convertCriteria = new FeedbackConvertCriteriaDTO();
		return(this.convertFeedbackToFeedbackDTO(feedback,convertCriteria));
	}







}
