package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Documentation;
import com.interviewplannerapp.dto.DocumentationDTO;
import com.interviewplannerapp.dto.DocumentationSearchDTO;
import com.interviewplannerapp.dto.DocumentationPageDTO;
import com.interviewplannerapp.dto.DocumentationConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface DocumentationService extends GenericService<Documentation, Integer> {

	List<Documentation> findAll();

	ResultDTO addDocumentation(DocumentationDTO documentationDTO, RequestDTO requestDTO);

	ResultDTO updateDocumentation(DocumentationDTO documentationDTO, RequestDTO requestDTO);

    Page<Documentation> getAllDocumentations(Pageable pageable);

    Page<Documentation> getAllDocumentations(Specification<Documentation> spec, Pageable pageable);

	ResponseEntity<DocumentationPageDTO> getDocumentations(DocumentationSearchDTO documentationSearchDTO);
	
	List<DocumentationDTO> convertDocumentationsToDocumentationDTOs(List<Documentation> documentations, DocumentationConvertCriteriaDTO convertCriteria);

	DocumentationDTO getDocumentationDTOById(Integer documentationId);







}





