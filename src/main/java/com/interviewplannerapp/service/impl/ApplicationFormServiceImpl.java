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
import com.interviewplannerapp.dao.ApplicationFormDAO;
import com.interviewplannerapp.domain.ApplicationForm;
import com.interviewplannerapp.dto.ApplicationFormDTO;
import com.interviewplannerapp.dto.ApplicationFormSearchDTO;
import com.interviewplannerapp.dto.ApplicationFormPageDTO;
import com.interviewplannerapp.dto.ApplicationFormConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.ApplicationFormService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class ApplicationFormServiceImpl extends GenericServiceImpl<ApplicationForm, Integer> implements ApplicationFormService {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationFormServiceImpl.class);

	@Autowired
	ApplicationFormDAO applicationFormDao;

	


	@Override
	public GenericDAO<ApplicationForm, Integer> getDAO() {
		return (GenericDAO<ApplicationForm, Integer>) applicationFormDao;
	}
	
	public List<ApplicationForm> findAll () {
		List<ApplicationForm> applicationForms = applicationFormDao.findAll();
		
		return applicationForms;	
		
	}

	public ResultDTO addApplicationForm(ApplicationFormDTO applicationFormDTO, RequestDTO requestDTO) {

		ApplicationForm applicationForm = new ApplicationForm();

		applicationForm.setApplicationFormId(applicationFormDTO.getApplicationFormId());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		applicationForm = applicationFormDao.save(applicationForm);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ApplicationForm> getAllApplicationForms(Pageable pageable) {
		return applicationFormDao.findAll(pageable);
	}

	public Page<ApplicationForm> getAllApplicationForms(Specification<ApplicationForm> spec, Pageable pageable) {
		return applicationFormDao.findAll(spec, pageable);
	}

	public ResponseEntity<ApplicationFormPageDTO> getApplicationForms(ApplicationFormSearchDTO applicationFormSearchDTO) {
	
			Integer applicationFormId = applicationFormSearchDTO.getApplicationFormId(); 
 			String sortBy = applicationFormSearchDTO.getSortBy();
			String sortOrder = applicationFormSearchDTO.getSortOrder();
			String searchQuery = applicationFormSearchDTO.getSearchQuery();
			Integer page = applicationFormSearchDTO.getPage();
			Integer size = applicationFormSearchDTO.getSize();

	        Specification<ApplicationForm> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, applicationFormId, "applicationFormId"); 
			

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

		Page<ApplicationForm> applicationForms = this.getAllApplicationForms(spec, pageable);
		
		//System.out.println(String.valueOf(applicationForms.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(applicationForms.getTotalPages()));
		
		List<ApplicationForm> applicationFormsList = applicationForms.getContent();
		
		ApplicationFormConvertCriteriaDTO convertCriteria = new ApplicationFormConvertCriteriaDTO();
		List<ApplicationFormDTO> applicationFormDTOs = this.convertApplicationFormsToApplicationFormDTOs(applicationFormsList,convertCriteria);
		
		ApplicationFormPageDTO applicationFormPageDTO = new ApplicationFormPageDTO();
		applicationFormPageDTO.setApplicationForms(applicationFormDTOs);
		applicationFormPageDTO.setTotalElements(applicationForms.getTotalElements());
		return ResponseEntity.ok(applicationFormPageDTO);
	}

	public List<ApplicationFormDTO> convertApplicationFormsToApplicationFormDTOs(List<ApplicationForm> applicationForms, ApplicationFormConvertCriteriaDTO convertCriteria) {
		
		List<ApplicationFormDTO> applicationFormDTOs = new ArrayList<ApplicationFormDTO>();
		
		for (ApplicationForm applicationForm : applicationForms) {
			applicationFormDTOs.add(convertApplicationFormToApplicationFormDTO(applicationForm,convertCriteria));
		}
		
		return applicationFormDTOs;

	}
	
	public ApplicationFormDTO convertApplicationFormToApplicationFormDTO(ApplicationForm applicationForm, ApplicationFormConvertCriteriaDTO convertCriteria) {
		
		ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO();
		
		applicationFormDTO.setApplicationFormId(applicationForm.getApplicationFormId());

	

		
		return applicationFormDTO;
	}

	public ResultDTO updateApplicationForm(ApplicationFormDTO applicationFormDTO, RequestDTO requestDTO) {
		
		ApplicationForm applicationForm = applicationFormDao.getById(applicationFormDTO.getApplicationFormId());

		applicationForm.setApplicationFormId(ControllerUtils.setValue(applicationForm.getApplicationFormId(), applicationFormDTO.getApplicationFormId()));



        applicationForm = applicationFormDao.save(applicationForm);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ApplicationFormDTO getApplicationFormDTOById(Integer applicationFormId) {
	
		ApplicationForm applicationForm = applicationFormDao.getById(applicationFormId);
			
		
		ApplicationFormConvertCriteriaDTO convertCriteria = new ApplicationFormConvertCriteriaDTO();
		return(this.convertApplicationFormToApplicationFormDTO(applicationForm,convertCriteria));
	}







}
