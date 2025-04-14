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
import com.interviewplannerapp.dao.DocumentationDAO;
import com.interviewplannerapp.domain.Documentation;
import com.interviewplannerapp.dto.DocumentationDTO;
import com.interviewplannerapp.dto.DocumentationSearchDTO;
import com.interviewplannerapp.dto.DocumentationPageDTO;
import com.interviewplannerapp.dto.DocumentationConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.DocumentationService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class DocumentationServiceImpl extends GenericServiceImpl<Documentation, Integer> implements DocumentationService {

    private final static Logger logger = LoggerFactory.getLogger(DocumentationServiceImpl.class);

	@Autowired
	DocumentationDAO documentationDao;

	


	@Override
	public GenericDAO<Documentation, Integer> getDAO() {
		return (GenericDAO<Documentation, Integer>) documentationDao;
	}
	
	public List<Documentation> findAll () {
		List<Documentation> documentations = documentationDao.findAll();
		
		return documentations;	
		
	}

	public ResultDTO addDocumentation(DocumentationDTO documentationDTO, RequestDTO requestDTO) {

		Documentation documentation = new Documentation();

		documentation.setDocumentationId(documentationDTO.getDocumentationId());


		documentation.setName(documentationDTO.getName());


		documentation.setLink(documentationDTO.getLink());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		documentation = documentationDao.save(documentation);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Documentation> getAllDocumentations(Pageable pageable) {
		return documentationDao.findAll(pageable);
	}

	public Page<Documentation> getAllDocumentations(Specification<Documentation> spec, Pageable pageable) {
		return documentationDao.findAll(spec, pageable);
	}

	public ResponseEntity<DocumentationPageDTO> getDocumentations(DocumentationSearchDTO documentationSearchDTO) {
	
			Integer documentationId = documentationSearchDTO.getDocumentationId(); 
 			String name = documentationSearchDTO.getName(); 
 			String link = documentationSearchDTO.getLink(); 
 			String sortBy = documentationSearchDTO.getSortBy();
			String sortOrder = documentationSearchDTO.getSortOrder();
			String searchQuery = documentationSearchDTO.getSearchQuery();
			Integer page = documentationSearchDTO.getPage();
			Integer size = documentationSearchDTO.getSize();

	        Specification<Documentation> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, documentationId, "documentationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, link, "link"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("link")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Documentation> documentations = this.getAllDocumentations(spec, pageable);
		
		//System.out.println(String.valueOf(documentations.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(documentations.getTotalPages()));
		
		List<Documentation> documentationsList = documentations.getContent();
		
		DocumentationConvertCriteriaDTO convertCriteria = new DocumentationConvertCriteriaDTO();
		List<DocumentationDTO> documentationDTOs = this.convertDocumentationsToDocumentationDTOs(documentationsList,convertCriteria);
		
		DocumentationPageDTO documentationPageDTO = new DocumentationPageDTO();
		documentationPageDTO.setDocumentations(documentationDTOs);
		documentationPageDTO.setTotalElements(documentations.getTotalElements());
		return ResponseEntity.ok(documentationPageDTO);
	}

	public List<DocumentationDTO> convertDocumentationsToDocumentationDTOs(List<Documentation> documentations, DocumentationConvertCriteriaDTO convertCriteria) {
		
		List<DocumentationDTO> documentationDTOs = new ArrayList<DocumentationDTO>();
		
		for (Documentation documentation : documentations) {
			documentationDTOs.add(convertDocumentationToDocumentationDTO(documentation,convertCriteria));
		}
		
		return documentationDTOs;

	}
	
	public DocumentationDTO convertDocumentationToDocumentationDTO(Documentation documentation, DocumentationConvertCriteriaDTO convertCriteria) {
		
		DocumentationDTO documentationDTO = new DocumentationDTO();
		
		documentationDTO.setDocumentationId(documentation.getDocumentationId());

	
		documentationDTO.setName(documentation.getName());

	
		documentationDTO.setLink(documentation.getLink());

	

		
		return documentationDTO;
	}

	public ResultDTO updateDocumentation(DocumentationDTO documentationDTO, RequestDTO requestDTO) {
		
		Documentation documentation = documentationDao.getById(documentationDTO.getDocumentationId());

		documentation.setDocumentationId(ControllerUtils.setValue(documentation.getDocumentationId(), documentationDTO.getDocumentationId()));

		documentation.setName(ControllerUtils.setValue(documentation.getName(), documentationDTO.getName()));

		documentation.setLink(ControllerUtils.setValue(documentation.getLink(), documentationDTO.getLink()));



        documentation = documentationDao.save(documentation);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DocumentationDTO getDocumentationDTOById(Integer documentationId) {
	
		Documentation documentation = documentationDao.getById(documentationId);
			
		
		DocumentationConvertCriteriaDTO convertCriteria = new DocumentationConvertCriteriaDTO();
		return(this.convertDocumentationToDocumentationDTO(documentation,convertCriteria));
	}







}
