package com.mini.studyservice.api.recruit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mini.studyservice.api.recruit.dto.StudyBoard;
import com.mini.studyservice.api.recruit.dto.StudyBoardDto;
import com.mini.studyservice.api.recruit.dto.StudyMember;
import com.mini.studyservice.api.recruit.dto.StudyMemberDto;
import com.mini.studyservice.api.recruit.dto.StudyMemberUser;
import com.mini.studyservice.api.recruit.dto.StudyTimeDto;

@Mapper
public interface RecruitDao {
	public List<StudyBoardDto> selectAllStudyBoards(Map<String,Integer> map) throws Exception;
	public List<StudyBoardDto> selectStudyBoardByStatus(Map<String, Integer> map);
	public StudyBoardDto getStudyBoard(int board_id);
	public StudyBoard selectStudyBoard(int board_id);
	public int insertStudyBoard(StudyBoard studyBoard);
	public int insertStudyTime(List<StudyTimeDto> studyTimeList);
	public int insertStudyMember(StudyMember studyMember);
	public List<StudyMemberDto> selectStudyMember(int board_id);
	public int updateMemberNum(Map<String, Integer> map);
	public int updateStatusTo2(int board_id);
	public List<StudyBoardDto> selectStudyBoardsByTagId(int tag_id);
	public List<StudyBoard> selectStudyBoardsByWriter(int writer_id);
	public List<StudyBoard> selectStudyBoardsByMember(int user_id);
	public List<StudyMemberUser> selectStudyMemberByBoard(int board_id);
	public List<StudyBoard> selectDeadlineStudyBoard();
	public int autoUpdateStatusTo2(List<Integer> board_ids);
	public List<StudyTimeDto> selectStudyTimeByStudyBoard(int board_id);	
}
