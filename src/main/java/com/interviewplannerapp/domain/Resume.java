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
@Table(name="resumes")
@Getter @Setter @NoArgsConstructor
public class Resume {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="resume_id")
	private Integer resumeId;
    
  	@Column(name="candidate_name")
	private String candidateName;
    
  	@Column(name="candidate_email")
	private String candidateEmail;
    
  	@Column(name="file_path")
	private String filePath;
    
	




}
