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
import com.interviewplannerapp.dao.FocusAreaDAO;
import com.interviewplannerapp.domain.FocusArea;
import com.interviewplannerapp.dto.FocusAreaDTO;
import com.interviewplannerapp.dto.FocusAreaSearchDTO;
import com.interviewplannerapp.dto.FocusAreaPageDTO;
import com.interviewplannerapp.dto.FocusAreaConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.FocusAreaService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class FocusAreaServiceImpl extends GenericServiceImpl<FocusArea, Integer> implements FocusAreaService {

    private final static Logger logger = LoggerFactory.getLogger(FocusAreaServiceImpl.class);

	@Autowired
	FocusAreaDAO focusAreaDao;

	


	@Override
	public GenericDAO<FocusArea, Integer> getDAO() {
		return (GenericDAO<FocusArea, Integer>) focusAreaDao;
	}
	
	public List<FocusArea> findAll () {
		List<FocusArea> focusAreas = focusAreaDao.findAll();
		
		return focusAreas;	
		
	}

	public ResultDTO addFocusArea(FocusAreaDTO focusAreaDTO, RequestDTO requestDTO) {

		FocusArea focusArea = new FocusArea();

		focusArea.setFocusAreaId(focusAreaDTO.getFocusAreaId());


		focusArea.setName(focusAreaDTO.getName());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		focusArea = focusAreaDao.save(focusArea);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<FocusArea> getAllFocusAreas(Pageable pageable) {
		return focusAreaDao.findAll(pageable);
	}

	public Page<FocusArea> getAllFocusAreas(Specification<FocusArea> spec, Pageable pageable) {
		return focusAreaDao.findAll(spec, pageable);
	}

	public ResponseEntity<FocusAreaPageDTO> getFocusAreas(FocusAreaSearchDTO focusAreaSearchDTO) {
	
			Integer focusAreaId = focusAreaSearchDTO.getFocusAreaId(); 
 			String name = focusAreaSearchDTO.getName(); 
 			String sortBy = focusAreaSearchDTO.getSortBy();
			String sortOrder = focusAreaSearchDTO.getSortOrder();
			String searchQuery = focusAreaSearchDTO.getSearchQuery();
			Integer page = focusAreaSearchDTO.getPage();
			Integer size = focusAreaSearchDTO.getSize();

	        Specification<FocusArea> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, focusAreaId, "focusAreaId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<FocusArea> focusAreas = this.getAllFocusAreas(spec, pageable);
		
		//System.out.println(String.valueOf(focusAreas.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(focusAreas.getTotalPages()));
		
		List<FocusArea> focusAreasList = focusAreas.getContent();
		
		FocusAreaConvertCriteriaDTO convertCriteria = new FocusAreaConvertCriteriaDTO();
		List<FocusAreaDTO> focusAreaDTOs = this.convertFocusAreasToFocusAreaDTOs(focusAreasList,convertCriteria);
		
		FocusAreaPageDTO focusAreaPageDTO = new FocusAreaPageDTO();
		focusAreaPageDTO.setFocusAreas(focusAreaDTOs);
		focusAreaPageDTO.setTotalElements(focusAreas.getTotalElements());
		return ResponseEntity.ok(focusAreaPageDTO);
	}

	public List<FocusAreaDTO> convertFocusAreasToFocusAreaDTOs(List<FocusArea> focusAreas, FocusAreaConvertCriteriaDTO convertCriteria) {
		
		List<FocusAreaDTO> focusAreaDTOs = new ArrayList<FocusAreaDTO>();
		
		for (FocusArea focusArea : focusAreas) {
			focusAreaDTOs.add(convertFocusAreaToFocusAreaDTO(focusArea,convertCriteria));
		}
		
		return focusAreaDTOs;

	}
	
	public FocusAreaDTO convertFocusAreaToFocusAreaDTO(FocusArea focusArea, FocusAreaConvertCriteriaDTO convertCriteria) {
		
		FocusAreaDTO focusAreaDTO = new FocusAreaDTO();
		
		focusAreaDTO.setFocusAreaId(focusArea.getFocusAreaId());

	
		focusAreaDTO.setName(focusArea.getName());

	

		
		return focusAreaDTO;
	}

	public ResultDTO updateFocusArea(FocusAreaDTO focusAreaDTO, RequestDTO requestDTO) {
		
		FocusArea focusArea = focusAreaDao.getById(focusAreaDTO.getFocusAreaId());

		focusArea.setFocusAreaId(ControllerUtils.setValue(focusArea.getFocusAreaId(), focusAreaDTO.getFocusAreaId()));

		focusArea.setName(ControllerUtils.setValue(focusArea.getName(), focusAreaDTO.getName()));



        focusArea = focusAreaDao.save(focusArea);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public FocusAreaDTO getFocusAreaDTOById(Integer focusAreaId) {
	
		FocusArea focusArea = focusAreaDao.getById(focusAreaId);
			
		
		FocusAreaConvertCriteriaDTO convertCriteria = new FocusAreaConvertCriteriaDTO();
		return(this.convertFocusAreaToFocusAreaDTO(focusArea,convertCriteria));
	}







}
