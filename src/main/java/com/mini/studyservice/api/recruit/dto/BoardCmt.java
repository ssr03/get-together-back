package com.mini.studyservice.api.recruit.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BoardCmt {
	private int board_cmt_id;
	private int board_id;
	private int writer_id;
	private String comment;
	private int parent_id;
	private int depth;
	@JsonProperty
	private boolean is_public;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	private LocalDateTime created_time;
	
}
