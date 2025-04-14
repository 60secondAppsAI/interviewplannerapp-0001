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
import com.interviewplannerapp.dao.DepartmentDAO;
import com.interviewplannerapp.domain.Department;
import com.interviewplannerapp.dto.DepartmentDTO;
import com.interviewplannerapp.dto.DepartmentSearchDTO;
import com.interviewplannerapp.dto.DepartmentPageDTO;
import com.interviewplannerapp.dto.DepartmentConvertCriteriaDTO;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import com.interviewplannerapp.service.DepartmentService;
import com.interviewplannerapp.util.ControllerUtils;





@Service
public class DepartmentServiceImpl extends GenericServiceImpl<Department, Integer> implements DepartmentService {

    private final static Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	@Autowired
	DepartmentDAO departmentDao;

	


	@Override
	public GenericDAO<Department, Integer> getDAO() {
		return (GenericDAO<Department, Integer>) departmentDao;
	}
	
	public List<Department> findAll () {
		List<Department> departments = departmentDao.findAll();
		
		return departments;	
		
	}

	public ResultDTO addDepartment(DepartmentDTO departmentDTO, RequestDTO requestDTO) {

		Department department = new Department();

		department.setDepartmentId(departmentDTO.getDepartmentId());


		department.setName(departmentDTO.getName());


		department.setHead(departmentDTO.getHead());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		department = departmentDao.save(department);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Department> getAllDepartments(Pageable pageable) {
		return departmentDao.findAll(pageable);
	}

	public Page<Department> getAllDepartments(Specification<Department> spec, Pageable pageable) {
		return departmentDao.findAll(spec, pageable);
	}

	public ResponseEntity<DepartmentPageDTO> getDepartments(DepartmentSearchDTO departmentSearchDTO) {
	
			Integer departmentId = departmentSearchDTO.getDepartmentId(); 
 			String name = departmentSearchDTO.getName(); 
 			String head = departmentSearchDTO.getHead(); 
 			String sortBy = departmentSearchDTO.getSortBy();
			String sortOrder = departmentSearchDTO.getSortOrder();
			String searchQuery = departmentSearchDTO.getSearchQuery();
			Integer page = departmentSearchDTO.getPage();
			Integer size = departmentSearchDTO.getSize();

	        Specification<Department> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, departmentId, "departmentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, head, "head"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("head")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Department> departments = this.getAllDepartments(spec, pageable);
		
		//System.out.println(String.valueOf(departments.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(departments.getTotalPages()));
		
		List<Department> departmentsList = departments.getContent();
		
		DepartmentConvertCriteriaDTO convertCriteria = new DepartmentConvertCriteriaDTO();
		List<DepartmentDTO> departmentDTOs = this.convertDepartmentsToDepartmentDTOs(departmentsList,convertCriteria);
		
		DepartmentPageDTO departmentPageDTO = new DepartmentPageDTO();
		departmentPageDTO.setDepartments(departmentDTOs);
		departmentPageDTO.setTotalElements(departments.getTotalElements());
		return ResponseEntity.ok(departmentPageDTO);
	}

	public List<DepartmentDTO> convertDepartmentsToDepartmentDTOs(List<Department> departments, DepartmentConvertCriteriaDTO convertCriteria) {
		
		List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();
		
		for (Department department : departments) {
			departmentDTOs.add(convertDepartmentToDepartmentDTO(department,convertCriteria));
		}
		
		return departmentDTOs;

	}
	
	public DepartmentDTO convertDepartmentToDepartmentDTO(Department department, DepartmentConvertCriteriaDTO convertCriteria) {
		
		DepartmentDTO departmentDTO = new DepartmentDTO();
		
		departmentDTO.setDepartmentId(department.getDepartmentId());

	
		departmentDTO.setName(department.getName());

	
		departmentDTO.setHead(department.getHead());

	

		
		return departmentDTO;
	}

	public ResultDTO updateDepartment(DepartmentDTO departmentDTO, RequestDTO requestDTO) {
		
		Department department = departmentDao.getById(departmentDTO.getDepartmentId());

		department.setDepartmentId(ControllerUtils.setValue(department.getDepartmentId(), departmentDTO.getDepartmentId()));

		department.setName(ControllerUtils.setValue(department.getName(), departmentDTO.getName()));

		department.setHead(ControllerUtils.setValue(department.getHead(), departmentDTO.getHead()));



        department = departmentDao.save(department);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DepartmentDTO getDepartmentDTOById(Integer departmentId) {
	
		Department department = departmentDao.getById(departmentId);
			
		
		DepartmentConvertCriteriaDTO convertCriteria = new DepartmentConvertCriteriaDTO();
		return(this.convertDepartmentToDepartmentDTO(department,convertCriteria));
	}







}
