package com.mini.studyservice.core.security.account.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.studyservice.core.security.AuthToken;
import com.mini.studyservice.core.security.account.dao.UserDao;
import com.mini.studyservice.core.security.account.dto.User;
import com.mini.studyservice.core.security.account.dto.UserDto;
import com.mini.studyservice.core.util.error.Error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
	@Autowired
	private final UserDao userDao;
	
	@Autowired
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
		
	public User selectMyInfo(String user_id) throws Exception {
		return userDao.selectMyInfo(user_id);
	}
	
	@Transactional
	public ResponseEntity<Object> addUser(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User userInfo=userDao.findByLoginid(user.getLoginid());
		if(userInfo!=null)return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다"), HttpStatus.BAD_REQUEST);
		int result = userDao.addUser(user);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("user_id", user.getUser_id());
		userDao.insertNewUserRole(map);
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	public ResponseEntity<AuthToken> login(UserDto userDto, HttpSession session, HttpServletRequest request){
		
		try {
			
			String loginid = userDto.getLoginid();
			String password = userDto.getPassword().trim();

			Optional<User> user = Optional.ofNullable(findByLoginId(loginid)); 
		
			Optional<AuthToken> result = 
					user.map(obj ->{
						// 1. username, password를 조합하여 UsernamePasswordAuthenticationToken 생성
						UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginid, password);

						// 2. 검증을 위해 UsernamePasswordAuthenticationToken 을 authenticationManager 의 인스턴스로 전달
						Authentication authentication = authenticationManager.authenticate(token); // 3. 인증에 성공하면 Authentication 인스턴스 리턴
						
						return getAuthToken(session, loginid, obj, token, authentication);
					});
			return result.map(authToken -> new ResponseEntity<>(authToken, HttpStatus.OK))
					.orElseGet(()->new ResponseEntity<>(new AuthToken(), HttpStatus.UNAUTHORIZED));
		
		}catch(AuthenticationException e) {
			return new ResponseEntity<AuthToken>(new AuthToken(),HttpStatus.UNAUTHORIZED);
		}
					
	}
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	public User findByLoginId(String loginid) {
		return userDao.findByLoginid(loginid);
    }

	@NotNull
	public AuthToken getAuthToken(final HttpSession session, final String loginid, final User obj,final UsernamePasswordAuthenticationToken token, final Authentication authentication) {
		// 4. Authentication 인스턴스를 SecurityContextHolder의 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(token);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        //authToken생성
        return new AuthToken(
                loginid,
                obj.getUsername(),
                session.getId());
	}
	
}

