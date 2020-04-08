package com.mini.studyservice.api.recruit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.studyservice.api.recruit.dao.CommentDao;
import com.mini.studyservice.api.recruit.dto.BoardCmt;
import com.mini.studyservice.api.recruit.dto.BoardCmtDto;
import com.mini.studyservice.core.security.account.dao.UserDao;
import com.mini.studyservice.core.util.error.Error;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	@Autowired
	private final CommentDao commentDao;
	@Autowired
	private final UserDao userDao;
	
	@Transactional
	public ResponseEntity<Object> addComment(BoardCmt boardCmt, int board_id){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
		
		String loginid = userDetail.getUsername();
		int writer_id = userDao.findByLoginid(loginid).getUser_id();
		
		boardCmt.setWriter_id(writer_id);
		boardCmt.setBoard_id(board_id);
		
		commentDao.insertBoardComment(boardCmt);
		int board_cmt_id = boardCmt.getBoard_cmt_id();
		commentDao.updateParentId(board_cmt_id);
		
		return new ResponseEntity<Object>(boardCmt, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> getAllBoardCommentsByBoardId(int board_id){
		List<BoardCmtDto> boardCmtDtoList = commentDao.selectAllBoardCommentsByBoard(board_id);
		if(boardCmtDtoList.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND, "댓글이 없습니다"), HttpStatus.NOT_FOUND);
		
		List<BoardCmtDto> list = new ArrayList<BoardCmtDto>();
		//댓글-답글 구조
		int index = 0;
		List<BoardCmtDto> reply_list = null;
	    BoardCmtDto  reply = null;
		for(BoardCmtDto boardCmtDto:boardCmtDtoList) {
			int depth = boardCmtDto.getDepth();
			if(depth==0) {
				list.add(boardCmtDto);
				index++;
				reply_list = new ArrayList<BoardCmtDto>();
			}else if(depth==1) {	
				reply = list.get(index-1);
				reply_list.add(boardCmtDto);
				reply.setReply(reply_list);	
			}
		}
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> addReplyComment(BoardCmt boardCmt, int board_id, int comment_id){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
		
		String loginid = userDetail.getUsername();
		int writer_id = userDao.findByLoginid(loginid).getUser_id();
		
		boardCmt.setWriter_id(writer_id);
		boardCmt.setBoard_id(board_id);
		
		int parent_id = comment_id;
		int depth = boardCmt.getDepth()+1;
		boardCmt.setParent_id(parent_id);
		boardCmt.setDepth(depth);
		
		commentDao.insertReplyComment(boardCmt);
		return new ResponseEntity<Object>(boardCmt, HttpStatus.CREATED);
	}
}
