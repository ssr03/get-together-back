package com.mini.studyservice.core.security.account.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	private static final long serialVersionUID = -1255150713846954145L;
	int user_id;
	String loginid;
	String password;
	String username;
	boolean enable_flag;	
    List<String> roles;
    String role;
	
	public UserDto() {}
		
	public UserDto(String loginid, String password) {
		this.loginid = loginid;
		this.password = password;
		//this.enable_flag = enable_flag;
	}
	
   /* UserService.getAuthToken */
   public UserDto(String loginid, String username, boolean enable_flag) {
        this.loginid = loginid;
        this.username = username;
        this.enable_flag = enable_flag;
    }
   
   public UserDto(int user_id,
           String loginid,
           String password,
           String username,
           boolean enable_flag,
           String role,
           List<String> roles) {
		this.user_id = user_id;
		this.loginid = loginid;
		this.password = password;
		this.username = username;
		this.enable_flag = enable_flag;
		this.roles = roles;
		this.role = role;
   }
}


