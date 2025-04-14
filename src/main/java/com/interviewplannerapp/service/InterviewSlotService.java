package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.InterviewSlot;
import com.interviewplannerapp.dto.InterviewSlotDTO;
import com.interviewplannerapp.dto.InterviewSlotSearchDTO;
import com.interviewplannerapp.dto.InterviewSlotPageDTO;
import com.interviewplannerapp.dto.InterviewSlotConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InterviewSlotService extends GenericService<InterviewSlot, Integer> {

	List<InterviewSlot> findAll();

	ResultDTO addInterviewSlot(InterviewSlotDTO interviewSlotDTO, RequestDTO requestDTO);

	ResultDTO updateInterviewSlot(InterviewSlotDTO interviewSlotDTO, RequestDTO requestDTO);

    Page<InterviewSlot> getAllInterviewSlots(Pageable pageable);

    Page<InterviewSlot> getAllInterviewSlots(Specification<InterviewSlot> spec, Pageable pageable);

	ResponseEntity<InterviewSlotPageDTO> getInterviewSlots(InterviewSlotSearchDTO interviewSlotSearchDTO);
	
	List<InterviewSlotDTO> convertInterviewSlotsToInterviewSlotDTOs(List<InterviewSlot> interviewSlots, InterviewSlotConvertCriteriaDTO convertCriteria);

	InterviewSlotDTO getInterviewSlotDTOById(Integer interviewSlotId);







}





