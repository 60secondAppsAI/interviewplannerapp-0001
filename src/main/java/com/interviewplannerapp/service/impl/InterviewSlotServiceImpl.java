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
import com.interviewplannerapp.dao.InterviewSlotDAO;
import com.interviewplannerapp.domain.InterviewSlot;
import com.interviewplannerapp.dto.InterviewSlotDTO;
import com.interviewplannerapp.dto.InterviewSlotSearchDTO;
import com.interviewplannerapp.dto.InterviewSlotPageDTO;
import com.interviewplannerapp.dto.InterviewSlotConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.InterviewSlotService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class InterviewSlotServiceImpl extends GenericServiceImpl<InterviewSlot, Integer> implements InterviewSlotService {

    private final static Logger logger = LoggerFactory.getLogger(InterviewSlotServiceImpl.class);

	@Autowired
	InterviewSlotDAO interviewSlotDao;

	


	@Override
	public GenericDAO<InterviewSlot, Integer> getDAO() {
		return (GenericDAO<InterviewSlot, Integer>) interviewSlotDao;
	}
	
	public List<InterviewSlot> findAll () {
		List<InterviewSlot> interviewSlots = interviewSlotDao.findAll();
		
		return interviewSlots;	
		
	}

	public ResultDTO addInterviewSlot(InterviewSlotDTO interviewSlotDTO, RequestDTO requestDTO) {

		InterviewSlot interviewSlot = new InterviewSlot();

		interviewSlot.setInterviewSlotId(interviewSlotDTO.getInterviewSlotId());


		interviewSlot.setStartTime(interviewSlotDTO.getStartTime());


		interviewSlot.setEndTime(interviewSlotDTO.getEndTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		interviewSlot = interviewSlotDao.save(interviewSlot);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<InterviewSlot> getAllInterviewSlots(Pageable pageable) {
		return interviewSlotDao.findAll(pageable);
	}

	public Page<InterviewSlot> getAllInterviewSlots(Specification<InterviewSlot> spec, Pageable pageable) {
		return interviewSlotDao.findAll(spec, pageable);
	}

	public ResponseEntity<InterviewSlotPageDTO> getInterviewSlots(InterviewSlotSearchDTO interviewSlotSearchDTO) {
	
			Integer interviewSlotId = interviewSlotSearchDTO.getInterviewSlotId(); 
     			String sortBy = interviewSlotSearchDTO.getSortBy();
			String sortOrder = interviewSlotSearchDTO.getSortOrder();
			String searchQuery = interviewSlotSearchDTO.getSearchQuery();
			Integer page = interviewSlotSearchDTO.getPage();
			Integer size = interviewSlotSearchDTO.getSize();

	        Specification<InterviewSlot> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, interviewSlotId, "interviewSlotId"); 
			
 			
 			

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

		Page<InterviewSlot> interviewSlots = this.getAllInterviewSlots(spec, pageable);
		
		//System.out.println(String.valueOf(interviewSlots.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(interviewSlots.getTotalPages()));
		
		List<InterviewSlot> interviewSlotsList = interviewSlots.getContent();
		
		InterviewSlotConvertCriteriaDTO convertCriteria = new InterviewSlotConvertCriteriaDTO();
		List<InterviewSlotDTO> interviewSlotDTOs = this.convertInterviewSlotsToInterviewSlotDTOs(interviewSlotsList,convertCriteria);
		
		InterviewSlotPageDTO interviewSlotPageDTO = new InterviewSlotPageDTO();
		interviewSlotPageDTO.setInterviewSlots(interviewSlotDTOs);
		interviewSlotPageDTO.setTotalElements(interviewSlots.getTotalElements());
		return ResponseEntity.ok(interviewSlotPageDTO);
	}

	public List<InterviewSlotDTO> convertInterviewSlotsToInterviewSlotDTOs(List<InterviewSlot> interviewSlots, InterviewSlotConvertCriteriaDTO convertCriteria) {
		
		List<InterviewSlotDTO> interviewSlotDTOs = new ArrayList<InterviewSlotDTO>();
		
		for (InterviewSlot interviewSlot : interviewSlots) {
			interviewSlotDTOs.add(convertInterviewSlotToInterviewSlotDTO(interviewSlot,convertCriteria));
		}
		
		return interviewSlotDTOs;

	}
	
	public InterviewSlotDTO convertInterviewSlotToInterviewSlotDTO(InterviewSlot interviewSlot, InterviewSlotConvertCriteriaDTO convertCriteria) {
		
		InterviewSlotDTO interviewSlotDTO = new InterviewSlotDTO();
		
		interviewSlotDTO.setInterviewSlotId(interviewSlot.getInterviewSlotId());

	
		interviewSlotDTO.setStartTime(interviewSlot.getStartTime());

	
		interviewSlotDTO.setEndTime(interviewSlot.getEndTime());

	

		
		return interviewSlotDTO;
	}

	public ResultDTO updateInterviewSlot(InterviewSlotDTO interviewSlotDTO, RequestDTO requestDTO) {
		
		InterviewSlot interviewSlot = interviewSlotDao.getById(interviewSlotDTO.getInterviewSlotId());

		interviewSlot.setInterviewSlotId(ControllerUtils.setValue(interviewSlot.getInterviewSlotId(), interviewSlotDTO.getInterviewSlotId()));

		interviewSlot.setStartTime(ControllerUtils.setValue(interviewSlot.getStartTime(), interviewSlotDTO.getStartTime()));

		interviewSlot.setEndTime(ControllerUtils.setValue(interviewSlot.getEndTime(), interviewSlotDTO.getEndTime()));



        interviewSlot = interviewSlotDao.save(interviewSlot);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InterviewSlotDTO getInterviewSlotDTOById(Integer interviewSlotId) {
	
		InterviewSlot interviewSlot = interviewSlotDao.getById(interviewSlotId);
			
		
		InterviewSlotConvertCriteriaDTO convertCriteria = new InterviewSlotConvertCriteriaDTO();
		return(this.convertInterviewSlotToInterviewSlotDTO(interviewSlot,convertCriteria));
	}







}
