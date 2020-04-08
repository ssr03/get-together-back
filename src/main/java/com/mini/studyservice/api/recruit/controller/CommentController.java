package com.mini.studyservice.api.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.studyservice.api.recruit.dto.BoardCmt;
import com.mini.studyservice.api.recruit.service.CommentService;
import com.mini.studyservice.core.config.DefaultConfig;

@RestController
@RequestMapping(DefaultConfig.APP_NAME)
public class CommentController {
	@Autowired
	private CommentService service;
	@GetMapping("/recruit/{board_id}/comment")
	public ResponseEntity<Object> getAllBoardCommentsByBoardId(@PathVariable int board_id){
		return service.getAllBoardCommentsByBoardId(board_id);
	}
	@PostMapping("/recruit/{board_id}/comment")
	public ResponseEntity<Object> addComment(@RequestBody BoardCmt boardCmt, @PathVariable int board_id){
		return service.addComment(boardCmt, board_id);
	}
	@PostMapping("/recruit/{board_id}/comment/{comment_id}")
	public ResponseEntity<Object> addReplyComment(@RequestBody BoardCmt boardCmt, @PathVariable int board_id, @PathVariable int comment_id){
		return service.addReplyComment(boardCmt, board_id, comment_id);
	}
}
