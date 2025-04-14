package com.interviewplannerapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;



@Entity
@Table(name="job_descriptions")
@Getter @Setter @NoArgsConstructor
public class JobDescription {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="job_description_id")
	private Integer jobDescriptionId;
    
  	@Column(name="role_title")
	private String roleTitle;
    
  	@Column(name="department")
	private String department;
    
  	@Column(name="file_path")
	private String filePath;
    
	




}
