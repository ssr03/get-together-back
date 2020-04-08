package com.mini.studyservice.api.recruit.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.studyservice.api.recruit.dto.StudyBoardDto;
import com.mini.studyservice.api.recruit.service.RecruiteService;
import com.mini.studyservice.core.config.DefaultConfig;

@RestController
@RequestMapping(DefaultConfig.APP_NAME)
public class RecruitController {
	@Autowired
	private RecruiteService service;
	
//	@CrossOrigin(origins=DefaultConfig.CROS_ORIGIN)
	@GetMapping({"/recruit/start/{start}","/recruit/start"})
	public ResponseEntity<Object> selectAllStudyBoards(@PathVariable(required = false) Optional<Integer> start) throws Exception {
		int startPage = 0;
		if(start.isPresent())startPage = start.get();
		
		return service.selectAllStudyBoards(startPage);
	}
	
	@GetMapping({"/recruit/status/{status}/start/{start}","/recruit/status/{status}/start"})
	public ResponseEntity<Object> getStudyBoardsByStatus(@PathVariable int status, @PathVariable(required = false) Optional<Integer> start) throws Exception {
		int startPage = 0;
		if(start.isPresent())startPage = start.get();
		
		return service.getStudyBoardsByStatus(startPage, status);
	}
	
	@PostMapping("/recruit")
	public ResponseEntity<Object> addStudyBoard(@RequestBody StudyBoardDto studyBoardDto){
		return service.addStudyBoard(studyBoardDto);
	}
	
	@GetMapping("/recruit/{board_id}")
	public ResponseEntity<Object> getStudyBoard(@PathVariable int board_id) {
		return service.getStudyBoard(board_id);
	}
	
	@PostMapping("/recruit/{board_id}/join")
	public ResponseEntity<Object> joinStudyGroup(@PathVariable int board_id){
		return service.joinStudyGroup(board_id);
	}
	
	@GetMapping("/recruit/tag/{tag_id}")
	public ResponseEntity<Object> getStudyBoardsByTag(@PathVariable int tag_id){
		return service.selectStudyBoardsByTagId(tag_id);
	}
	
	/*
	 * 로그인한 유저가 만든 스터디
	 */
	@GetMapping("/mystudy/owner")
	public ResponseEntity<Object> getStudyBoardsByWriter(){
		return service.getStudyBoardsByWriter();
	}
	
	/*
	 * 로그인한 유저가 참여중인 스터디(스터디장 포함)
	 */
	@GetMapping("/mystudy")
	public ResponseEntity<Object> getStudyBoardsByMember(){
		return service.getStudyBoardsByMember();
	}
	
	@GetMapping("/recruit/{board_id}/member")
	public ResponseEntity<Object> getStudyMemberByBoard(@PathVariable int board_id){
		return service.getStudyMemberByBoard(board_id);
	}
}
