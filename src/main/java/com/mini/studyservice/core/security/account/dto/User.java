package com.mini.studyservice.core.security.account.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class User {
	private int user_id;
	private String loginid;
	private String password;
	private String username;
	private String email;
	private LocalDateTime registered_time;
	private boolean enable_flag;
	private String session_primary_id;
}
