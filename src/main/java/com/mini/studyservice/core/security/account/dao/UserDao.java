package com.mini.studyservice.core.security.account.dao;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;

import com.mini.studyservice.core.security.AuthToken;
import com.mini.studyservice.core.security.account.dto.User;
import com.mini.studyservice.core.security.account.dto.UserDto;


@Mapper
public interface UserDao {
	public User selectMyInfo(String loingid) throws Exception;
	public int addUser(User user);
	public ResponseEntity<AuthToken> login(UserDto userDto, HttpSession session, HttpServletRequest request);
	public User findByLoginid(String loginid);
	public int insertNewUserRole(Map<String, Integer> map);
}
