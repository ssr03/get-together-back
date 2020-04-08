package com.mini.studyservice.api.recruit.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StudyMemberUser {
	private int user_id;
	private String loginid;
	private String username;
	private String email;
	private int board_id;
	private int writer_id;
	private boolean is_owner;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	private LocalDateTime joined_date;
}
