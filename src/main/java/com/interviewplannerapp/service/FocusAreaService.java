package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.FocusArea;
import com.interviewplannerapp.dto.FocusAreaDTO;
import com.interviewplannerapp.dto.FocusAreaSearchDTO;
import com.interviewplannerapp.dto.FocusAreaPageDTO;
import com.interviewplannerapp.dto.FocusAreaConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface FocusAreaService extends GenericService<FocusArea, Integer> {

	List<FocusArea> findAll();

	ResultDTO addFocusArea(FocusAreaDTO focusAreaDTO, RequestDTO requestDTO);

	ResultDTO updateFocusArea(FocusAreaDTO focusAreaDTO, RequestDTO requestDTO);

    Page<FocusArea> getAllFocusAreas(Pageable pageable);

    Page<FocusArea> getAllFocusAreas(Specification<FocusArea> spec, Pageable pageable);

	ResponseEntity<FocusAreaPageDTO> getFocusAreas(FocusAreaSearchDTO focusAreaSearchDTO);
	
	List<FocusAreaDTO> convertFocusAreasToFocusAreaDTOs(List<FocusArea> focusAreas, FocusAreaConvertCriteriaDTO convertCriteria);

	FocusAreaDTO getFocusAreaDTOById(Integer focusAreaId);







}





