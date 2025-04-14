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
import com.interviewplannerapp.dao.MeetingNoteDAO;
import com.interviewplannerapp.domain.MeetingNote;
import com.interviewplannerapp.dto.MeetingNoteDTO;
import com.interviewplannerapp.dto.MeetingNoteSearchDTO;
import com.interviewplannerapp.dto.MeetingNotePageDTO;
import com.interviewplannerapp.dto.MeetingNoteConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.MeetingNoteService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class MeetingNoteServiceImpl extends GenericServiceImpl<MeetingNote, Integer> implements MeetingNoteService {

    private final static Logger logger = LoggerFactory.getLogger(MeetingNoteServiceImpl.class);

	@Autowired
	MeetingNoteDAO meetingNoteDao;

	


	@Override
	public GenericDAO<MeetingNote, Integer> getDAO() {
		return (GenericDAO<MeetingNote, Integer>) meetingNoteDao;
	}
	
	public List<MeetingNote> findAll () {
		List<MeetingNote> meetingNotes = meetingNoteDao.findAll();
		
		return meetingNotes;	
		
	}

	public ResultDTO addMeetingNote(MeetingNoteDTO meetingNoteDTO, RequestDTO requestDTO) {

		MeetingNote meetingNote = new MeetingNote();

		meetingNote.setMeetingNoteId(meetingNoteDTO.getMeetingNoteId());


		meetingNote.setText(meetingNoteDTO.getText());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		meetingNote = meetingNoteDao.save(meetingNote);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<MeetingNote> getAllMeetingNotes(Pageable pageable) {
		return meetingNoteDao.findAll(pageable);
	}

	public Page<MeetingNote> getAllMeetingNotes(Specification<MeetingNote> spec, Pageable pageable) {
		return meetingNoteDao.findAll(spec, pageable);
	}

	public ResponseEntity<MeetingNotePageDTO> getMeetingNotes(MeetingNoteSearchDTO meetingNoteSearchDTO) {
	
			Integer meetingNoteId = meetingNoteSearchDTO.getMeetingNoteId(); 
 			String text = meetingNoteSearchDTO.getText(); 
 			String sortBy = meetingNoteSearchDTO.getSortBy();
			String sortOrder = meetingNoteSearchDTO.getSortOrder();
			String searchQuery = meetingNoteSearchDTO.getSearchQuery();
			Integer page = meetingNoteSearchDTO.getPage();
			Integer size = meetingNoteSearchDTO.getSize();

	        Specification<MeetingNote> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, meetingNoteId, "meetingNoteId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, text, "text"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("text")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<MeetingNote> meetingNotes = this.getAllMeetingNotes(spec, pageable);
		
		//System.out.println(String.valueOf(meetingNotes.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(meetingNotes.getTotalPages()));
		
		List<MeetingNote> meetingNotesList = meetingNotes.getContent();
		
		MeetingNoteConvertCriteriaDTO convertCriteria = new MeetingNoteConvertCriteriaDTO();
		List<MeetingNoteDTO> meetingNoteDTOs = this.convertMeetingNotesToMeetingNoteDTOs(meetingNotesList,convertCriteria);
		
		MeetingNotePageDTO meetingNotePageDTO = new MeetingNotePageDTO();
		meetingNotePageDTO.setMeetingNotes(meetingNoteDTOs);
		meetingNotePageDTO.setTotalElements(meetingNotes.getTotalElements());
		return ResponseEntity.ok(meetingNotePageDTO);
	}

	public List<MeetingNoteDTO> convertMeetingNotesToMeetingNoteDTOs(List<MeetingNote> meetingNotes, MeetingNoteConvertCriteriaDTO convertCriteria) {
		
		List<MeetingNoteDTO> meetingNoteDTOs = new ArrayList<MeetingNoteDTO>();
		
		for (MeetingNote meetingNote : meetingNotes) {
			meetingNoteDTOs.add(convertMeetingNoteToMeetingNoteDTO(meetingNote,convertCriteria));
		}
		
		return meetingNoteDTOs;

	}
	
	public MeetingNoteDTO convertMeetingNoteToMeetingNoteDTO(MeetingNote meetingNote, MeetingNoteConvertCriteriaDTO convertCriteria) {
		
		MeetingNoteDTO meetingNoteDTO = new MeetingNoteDTO();
		
		meetingNoteDTO.setMeetingNoteId(meetingNote.getMeetingNoteId());

	
		meetingNoteDTO.setText(meetingNote.getText());

	

		
		return meetingNoteDTO;
	}

	public ResultDTO updateMeetingNote(MeetingNoteDTO meetingNoteDTO, RequestDTO requestDTO) {
		
		MeetingNote meetingNote = meetingNoteDao.getById(meetingNoteDTO.getMeetingNoteId());

		meetingNote.setMeetingNoteId(ControllerUtils.setValue(meetingNote.getMeetingNoteId(), meetingNoteDTO.getMeetingNoteId()));

		meetingNote.setText(ControllerUtils.setValue(meetingNote.getText(), meetingNoteDTO.getText()));



        meetingNote = meetingNoteDao.save(meetingNote);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MeetingNoteDTO getMeetingNoteDTOById(Integer meetingNoteId) {
	
		MeetingNote meetingNote = meetingNoteDao.getById(meetingNoteId);
			
		
		MeetingNoteConvertCriteriaDTO convertCriteria = new MeetingNoteConvertCriteriaDTO();
		return(this.convertMeetingNoteToMeetingNoteDTO(meetingNote,convertCriteria));
	}







}
