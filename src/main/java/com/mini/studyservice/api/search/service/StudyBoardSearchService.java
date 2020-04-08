package com.mini.studyservice.api.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mini.studyservice.api.recruit.dto.StudyBoardDto;
import com.mini.studyservice.api.search.dao.StudyBoardSearchDao;
import com.mini.studyservice.api.tag.dao.TagDao;
import com.mini.studyservice.api.tag.dto.Tag;
import com.mini.studyservice.api.tag.dto.TagDto;
import com.mini.studyservice.core.util.error.Error;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudyBoardSearchService {
	@Autowired
	private final StudyBoardSearchDao searchDao;
	@Autowired
	private final TagDao tagDao;
	
	public ResponseEntity<Object> searchStudyBoardByLocation(String search){
		
		List<StudyBoardDto> studyBoardList = searchDao.selectStudyBoardByLocation(search);
		if(studyBoardList.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"검색 결과가 없습니다"), HttpStatus.NOT_FOUND);
		
		List<Integer> board_ids =new ArrayList<Integer>();
		for(StudyBoardDto studyBoard: studyBoardList) {
			int board_id = studyBoard.getBoard_id();
			board_ids.add(board_id);
		}
		List<TagDto> tagDtos= tagDao.getTagsWithBoardId(board_ids);
		Map<Integer,List<Tag>> tagList = new HashMap<Integer,List<Tag>>();
		List<Tag> tags=null;
		for(TagDto tagDto: tagDtos) {
			int board_id = tagDto.getBoard_id();
			if(tagList.containsKey(board_id)) {
				tags=tagList.get(board_id);
				tags.add(new Tag(tagDto.getTag_id(),tagDto.getTag_name()));
			}else {
			    tags=new ArrayList<Tag>();
				tags.add(new Tag(tagDto.getTag_id(),tagDto.getTag_name()));
				tagList.put(board_id,tags);
			}
		}
		for(StudyBoardDto studyBoardDto: studyBoardList) {
			int board_id = studyBoardDto.getBoard_id();
			studyBoardDto.setTags(tagList.get(board_id));
		}
		
		return new ResponseEntity<Object>(studyBoardList, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> searchStudyBoard(String search){
		if(search==null)return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST,"잘못된 요청입니다"), HttpStatus.BAD_REQUEST);
			
		List<StudyBoardDto> studyBoardList = searchDao.selectStudyBoard(search);
		if(studyBoardList.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"검색 결과가 없습니다"), HttpStatus.NOT_FOUND);
		
		List<Integer> board_ids =new ArrayList<Integer>();
		for(StudyBoardDto studyBoard: studyBoardList) {
			int board_id = studyBoard.getBoard_id();
			board_ids.add(board_id);
		}
		List<TagDto> tagDtos= tagDao.getTagsWithBoardId(board_ids);
		Map<Integer,List<Tag>> tagList = new HashMap<Integer,List<Tag>>();
		List<Tag> tags=null;
		for(TagDto tagDto: tagDtos) {
			int board_id = tagDto.getBoard_id();
			if(tagList.containsKey(board_id)) {
				tags=tagList.get(board_id);
				tags.add(new Tag(tagDto.getTag_id(),tagDto.getTag_name()));
			}else {
			    tags=new ArrayList<Tag>();
				tags.add(new Tag(tagDto.getTag_id(),tagDto.getTag_name()));
				tagList.put(board_id,tags);
			}
		}
		for(StudyBoardDto studyBoardDto: studyBoardList) {
			int board_id = studyBoardDto.getBoard_id();
			studyBoardDto.setTags(tagList.get(board_id));
		}
		
		return new ResponseEntity<Object>(studyBoardList, HttpStatus.OK);
	}
}
