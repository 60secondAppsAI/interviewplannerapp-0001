package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.MeetingNote;
import com.interviewplannerapp.dto.MeetingNoteDTO;
import com.interviewplannerapp.dto.MeetingNoteSearchDTO;
import com.interviewplannerapp.dto.MeetingNotePageDTO;
import com.interviewplannerapp.dto.MeetingNoteConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MeetingNoteService extends GenericService<MeetingNote, Integer> {

	List<MeetingNote> findAll();

	ResultDTO addMeetingNote(MeetingNoteDTO meetingNoteDTO, RequestDTO requestDTO);

	ResultDTO updateMeetingNote(MeetingNoteDTO meetingNoteDTO, RequestDTO requestDTO);

    Page<MeetingNote> getAllMeetingNotes(Pageable pageable);

    Page<MeetingNote> getAllMeetingNotes(Specification<MeetingNote> spec, Pageable pageable);

	ResponseEntity<MeetingNotePageDTO> getMeetingNotes(MeetingNoteSearchDTO meetingNoteSearchDTO);
	
	List<MeetingNoteDTO> convertMeetingNotesToMeetingNoteDTOs(List<MeetingNote> meetingNotes, MeetingNoteConvertCriteriaDTO convertCriteria);

	MeetingNoteDTO getMeetingNoteDTOById(Integer meetingNoteId);







}





