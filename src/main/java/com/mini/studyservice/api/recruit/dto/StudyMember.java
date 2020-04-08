package com.mini.studyservice.api.recruit.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StudyMember {
	private int user_id;
	private int board_id;
	private int status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	LocalDateTime created_time;
}
