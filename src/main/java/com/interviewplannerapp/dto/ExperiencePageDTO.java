package com.interviewplannerapp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExperiencePageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<ExperienceDTO> experiences;
}





