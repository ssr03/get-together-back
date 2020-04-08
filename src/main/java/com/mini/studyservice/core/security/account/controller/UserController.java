package com.mini.studyservice.core.security.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.studyservice.core.config.DefaultConfig;
import com.mini.studyservice.core.security.account.dto.User;
import com.mini.studyservice.core.security.account.service.UserService;

@RequestMapping(DefaultConfig.APP_NAME)
@RestController
public class UserController {
		
	@Autowired
	private UserService service;
	

	@GetMapping("/user/id/{user_id}")
	public User selectMyInfo(@PathVariable String user_id) throws Exception {
		return service.selectMyInfo(user_id);
	}
//	@GetMapping("/user/login-id/{loginid}")
//	public List<User> getSearchUser(@PathVariable String loginid){
//		return UserService.
//	}
	//사용자 추가
	@PostMapping("/user")
	public ResponseEntity<Object> addUser(@RequestBody User user){
		return service.addUser(user);
	}
	
}

