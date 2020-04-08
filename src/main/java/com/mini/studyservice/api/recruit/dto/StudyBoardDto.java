package com.mini.studyservice.api.recruit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mini.studyservice.api.tag.dto.Tag;

import lombok.Data;

@Data
public class StudyBoardDto {
	private int board_id;
	private String title;
	private String img_url;
	private int writer_id;
	private String writer;
	private int status;
	private int member_num;
	private int target_num;   
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso=ISO.DATE)
	private LocalDate deadline;
	private String content;
	private int cost;
	private String location;
	private double location_lat;
	private double location_lng;
	private List<Tag> tags;
	private List<StudyTimeDto> schedules;
	@JsonProperty
	private boolean is_public;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	private LocalDateTime created_time;
}
