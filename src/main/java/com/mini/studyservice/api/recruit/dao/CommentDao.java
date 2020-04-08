package com.mini.studyservice.api.recruit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mini.studyservice.api.recruit.dto.BoardCmt;
import com.mini.studyservice.api.recruit.dto.BoardCmtDto;

@Mapper
public interface CommentDao {
	public int insertBoardComment(BoardCmt boardCmt);
	public int insertReplyComment(BoardCmt boardCmt);
	public List<BoardCmtDto> selectAllBoardCommentsByBoard(int board_id);
	public int updateParentId(int board_cmt_id);
}
