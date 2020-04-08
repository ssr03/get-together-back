package com.mini.studyservice.api.recruit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import lombok.Data;

@Data
public class StudyBoard {
	private int board_id;
	private String title;
	private String img_url;
	private int writer_id;
	private String writer;
	private int status;
	private int target_num;
	private int member_num;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso=ISO.DATE)
	private LocalDate deadline;
	private String content;
	private int cost;
	private String location;
	private double location_lat;
	private double location_lng;
	@JsonProperty
	private boolean is_public;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	private LocalDateTime created_time;
}
