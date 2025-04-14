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
import com.interviewplannerapp.dao.ResumeDAO;
import com.interviewplannerapp.domain.Resume;
import com.interviewplannerapp.dto.ResumeDTO;
import com.interviewplannerapp.dto.ResumeSearchDTO;
import com.interviewplannerapp.dto.ResumePageDTO;
import com.interviewplannerapp.dto.ResumeConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.ResumeService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class ResumeServiceImpl extends GenericServiceImpl<Resume, Integer> implements ResumeService {

    private final static Logger logger = LoggerFactory.getLogger(ResumeServiceImpl.class);

	@Autowired
	ResumeDAO resumeDao;

	


	@Override
	public GenericDAO<Resume, Integer> getDAO() {
		return (GenericDAO<Resume, Integer>) resumeDao;
	}
	
	public List<Resume> findAll () {
		List<Resume> resumes = resumeDao.findAll();
		
		return resumes;	
		
	}

	public ResultDTO addResume(ResumeDTO resumeDTO, RequestDTO requestDTO) {

		Resume resume = new Resume();

		resume.setResumeId(resumeDTO.getResumeId());


		resume.setCandidateName(resumeDTO.getCandidateName());


		resume.setCandidateEmail(resumeDTO.getCandidateEmail());


		resume.setFilePath(resumeDTO.getFilePath());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		resume = resumeDao.save(resume);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Resume> getAllResumes(Pageable pageable) {
		return resumeDao.findAll(pageable);
	}

	public Page<Resume> getAllResumes(Specification<Resume> spec, Pageable pageable) {
		return resumeDao.findAll(spec, pageable);
	}

	public ResponseEntity<ResumePageDTO> getResumes(ResumeSearchDTO resumeSearchDTO) {
	
			Integer resumeId = resumeSearchDTO.getResumeId(); 
 			String candidateName = resumeSearchDTO.getCandidateName(); 
 			String candidateEmail = resumeSearchDTO.getCandidateEmail(); 
 			String filePath = resumeSearchDTO.getFilePath(); 
 			String sortBy = resumeSearchDTO.getSortBy();
			String sortOrder = resumeSearchDTO.getSortOrder();
			String searchQuery = resumeSearchDTO.getSearchQuery();
			Integer page = resumeSearchDTO.getPage();
			Integer size = resumeSearchDTO.getSize();

	        Specification<Resume> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, resumeId, "resumeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, candidateName, "candidateName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, candidateEmail, "candidateEmail"); 
			
			spec = ControllerUtils.andIfNecessary(spec, filePath, "filePath"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("candidateEmail")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("filePath")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("candidateName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Resume> resumes = this.getAllResumes(spec, pageable);
		
		//System.out.println(String.valueOf(resumes.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(resumes.getTotalPages()));
		
		List<Resume> resumesList = resumes.getContent();
		
		ResumeConvertCriteriaDTO convertCriteria = new ResumeConvertCriteriaDTO();
		List<ResumeDTO> resumeDTOs = this.convertResumesToResumeDTOs(resumesList,convertCriteria);
		
		ResumePageDTO resumePageDTO = new ResumePageDTO();
		resumePageDTO.setResumes(resumeDTOs);
		resumePageDTO.setTotalElements(resumes.getTotalElements());
		return ResponseEntity.ok(resumePageDTO);
	}

	public List<ResumeDTO> convertResumesToResumeDTOs(List<Resume> resumes, ResumeConvertCriteriaDTO convertCriteria) {
		
		List<ResumeDTO> resumeDTOs = new ArrayList<ResumeDTO>();
		
		for (Resume resume : resumes) {
			resumeDTOs.add(convertResumeToResumeDTO(resume,convertCriteria));
		}
		
		return resumeDTOs;

	}
	
	public ResumeDTO convertResumeToResumeDTO(Resume resume, ResumeConvertCriteriaDTO convertCriteria) {
		
		ResumeDTO resumeDTO = new ResumeDTO();
		
		resumeDTO.setResumeId(resume.getResumeId());

	
		resumeDTO.setCandidateName(resume.getCandidateName());

	
		resumeDTO.setCandidateEmail(resume.getCandidateEmail());

	
		resumeDTO.setFilePath(resume.getFilePath());

	

		
		return resumeDTO;
	}

	public ResultDTO updateResume(ResumeDTO resumeDTO, RequestDTO requestDTO) {
		
		Resume resume = resumeDao.getById(resumeDTO.getResumeId());

		resume.setResumeId(ControllerUtils.setValue(resume.getResumeId(), resumeDTO.getResumeId()));

		resume.setCandidateName(ControllerUtils.setValue(resume.getCandidateName(), resumeDTO.getCandidateName()));

		resume.setCandidateEmail(ControllerUtils.setValue(resume.getCandidateEmail(), resumeDTO.getCandidateEmail()));

		resume.setFilePath(ControllerUtils.setValue(resume.getFilePath(), resumeDTO.getFilePath()));



        resume = resumeDao.save(resume);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ResumeDTO getResumeDTOById(Integer resumeId) {
	
		Resume resume = resumeDao.getById(resumeId);
			
		
		ResumeConvertCriteriaDTO convertCriteria = new ResumeConvertCriteriaDTO();
		return(this.convertResumeToResumeDTO(resume,convertCriteria));
	}







}
