package com.mini.studyservice.api.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mini.studyservice.api.recruit.dto.StudyBoardDto;

@Mapper
public interface StudyBoardSearchDao {
	public List<StudyBoardDto> selectStudyBoardByLocation(String search);
	public List<StudyBoardDto> selectStudyBoard(String search);
}
