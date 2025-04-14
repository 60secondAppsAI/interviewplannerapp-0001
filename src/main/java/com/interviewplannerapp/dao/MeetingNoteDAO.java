package com.interviewplannerapp.dao;

import java.util.List;

import com.interviewplannerapp.dao.GenericDAO;
import com.interviewplannerapp.domain.MeetingNote;





public interface MeetingNoteDAO extends GenericDAO<MeetingNote, Integer> {
  
	List<MeetingNote> findAll();
	






}


