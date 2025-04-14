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
import com.interviewplannerapp.dao.QuestionDAO;
import com.interviewplannerapp.domain.Question;
import com.interviewplannerapp.dto.QuestionDTO;
import com.interviewplannerapp.dto.QuestionSearchDTO;
import com.interviewplannerapp.dto.QuestionPageDTO;
import com.interviewplannerapp.dto.QuestionConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.QuestionService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class QuestionServiceImpl extends GenericServiceImpl<Question, Integer> implements QuestionService {

    private final static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

	@Autowired
	QuestionDAO questionDao;

	


	@Override
	public GenericDAO<Question, Integer> getDAO() {
		return (GenericDAO<Question, Integer>) questionDao;
	}
	
	public List<Question> findAll () {
		List<Question> questions = questionDao.findAll();
		
		return questions;	
		
	}

	public ResultDTO addQuestion(QuestionDTO questionDTO, RequestDTO requestDTO) {

		Question question = new Question();

		question.setQuestionId(questionDTO.getQuestionId());


		question.setContent(questionDTO.getContent());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		question = questionDao.save(question);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Question> getAllQuestions(Pageable pageable) {
		return questionDao.findAll(pageable);
	}

	public Page<Question> getAllQuestions(Specification<Question> spec, Pageable pageable) {
		return questionDao.findAll(spec, pageable);
	}

	public ResponseEntity<QuestionPageDTO> getQuestions(QuestionSearchDTO questionSearchDTO) {
	
			Integer questionId = questionSearchDTO.getQuestionId(); 
 			String content = questionSearchDTO.getContent(); 
 			String sortBy = questionSearchDTO.getSortBy();
			String sortOrder = questionSearchDTO.getSortOrder();
			String searchQuery = questionSearchDTO.getSearchQuery();
			Integer page = questionSearchDTO.getPage();
			Integer size = questionSearchDTO.getSize();

	        Specification<Question> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, questionId, "questionId"); 
			
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

		Page<Question> questions = this.getAllQuestions(spec, pageable);
		
		//System.out.println(String.valueOf(questions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(questions.getTotalPages()));
		
		List<Question> questionsList = questions.getContent();
		
		QuestionConvertCriteriaDTO convertCriteria = new QuestionConvertCriteriaDTO();
		List<QuestionDTO> questionDTOs = this.convertQuestionsToQuestionDTOs(questionsList,convertCriteria);
		
		QuestionPageDTO questionPageDTO = new QuestionPageDTO();
		questionPageDTO.setQuestions(questionDTOs);
		questionPageDTO.setTotalElements(questions.getTotalElements());
		return ResponseEntity.ok(questionPageDTO);
	}

	public List<QuestionDTO> convertQuestionsToQuestionDTOs(List<Question> questions, QuestionConvertCriteriaDTO convertCriteria) {
		
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		
		for (Question question : questions) {
			questionDTOs.add(convertQuestionToQuestionDTO(question,convertCriteria));
		}
		
		return questionDTOs;

	}
	
	public QuestionDTO convertQuestionToQuestionDTO(Question question, QuestionConvertCriteriaDTO convertCriteria) {
		
		QuestionDTO questionDTO = new QuestionDTO();
		
		questionDTO.setQuestionId(question.getQuestionId());

	
		questionDTO.setContent(question.getContent());

	

		
		return questionDTO;
	}

	public ResultDTO updateQuestion(QuestionDTO questionDTO, RequestDTO requestDTO) {
		
		Question question = questionDao.getById(questionDTO.getQuestionId());

		question.setQuestionId(ControllerUtils.setValue(question.getQuestionId(), questionDTO.getQuestionId()));

		question.setContent(ControllerUtils.setValue(question.getContent(), questionDTO.getContent()));



        question = questionDao.save(question);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public QuestionDTO getQuestionDTOById(Integer questionId) {
	
		Question question = questionDao.getById(questionId);
			
		
		QuestionConvertCriteriaDTO convertCriteria = new QuestionConvertCriteriaDTO();
		return(this.convertQuestionToQuestionDTO(question,convertCriteria));
	}







}
