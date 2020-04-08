package com.mini.studyservice.api.tag.dto;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class Tag {
	private int tag_id;
	private String tag_name;
	private int type;
	private int city_state;
	LocalDateTime created_time;
	
	public Tag(int tag_id, String tag_name) {
		this.tag_id = tag_id;
		this.tag_name = tag_name;
	}
}
