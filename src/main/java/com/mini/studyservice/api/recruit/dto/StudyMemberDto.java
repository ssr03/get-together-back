package com.mini.studyservice.api.recruit.dto;

import lombok.Data;

@Data
public class StudyMemberDto {
	private int user_id;
	private int board_id;
	private int writer_id;
	private int target_num;
	private int member_num;
	private int board_status;
}
