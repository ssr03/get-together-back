package com.mini.studyservice.api.recruit.dto;

import lombok.Data;

@Data
public class StudyTime {
	private int study_time_id;
	private int board_id;
	private String study_day;
	private String study_start_time;
	private String study_end_time;
}
