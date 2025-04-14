package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Question;
import com.interviewplannerapp.dto.QuestionDTO;
import com.interviewplannerapp.dto.QuestionSearchDTO;
import com.interviewplannerapp.dto.QuestionPageDTO;
import com.interviewplannerapp.dto.QuestionConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface QuestionService extends GenericService<Question, Integer> {

	List<Question> findAll();

	ResultDTO addQuestion(QuestionDTO questionDTO, RequestDTO requestDTO);

	ResultDTO updateQuestion(QuestionDTO questionDTO, RequestDTO requestDTO);

    Page<Question> getAllQuestions(Pageable pageable);

    Page<Question> getAllQuestions(Specification<Question> spec, Pageable pageable);

	ResponseEntity<QuestionPageDTO> getQuestions(QuestionSearchDTO questionSearchDTO);
	
	List<QuestionDTO> convertQuestionsToQuestionDTOs(List<Question> questions, QuestionConvertCriteriaDTO convertCriteria);

	QuestionDTO getQuestionDTOById(Integer questionId);







}





