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
@Table(name="focus_areas")
@Getter @Setter @NoArgsConstructor
public class FocusArea {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="focus_area_id")
	private Integer focusAreaId;
    
  	@Column(name="name")
	private String name;
    
	




}
