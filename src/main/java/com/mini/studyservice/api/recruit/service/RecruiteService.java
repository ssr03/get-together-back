package com.mini.studyservice.api.recruit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.studyservice.api.note.service.AutoNoteService;
import com.mini.studyservice.api.recruit.dao.RecruitDao;
import com.mini.studyservice.api.recruit.dto.StudyBoard;
import com.mini.studyservice.api.recruit.dto.StudyBoardDto;
import com.mini.studyservice.api.recruit.dto.StudyMember;
import com.mini.studyservice.api.recruit.dto.StudyMemberDto;
import com.mini.studyservice.api.recruit.dto.StudyMemberUser;
import com.mini.studyservice.api.recruit.dto.StudyTimeDto;
import com.mini.studyservice.api.tag.dao.TagDao;
import com.mini.studyservice.api.tag.dto.Tag;
import com.mini.studyservice.api.tag.dto.TagDto;
import com.mini.studyservice.api.tag.dto.TagMapping;
import com.mini.studyservice.core.config.DefaultConfig;
import com.mini.studyservice.core.security.account.dao.UserDao;
import com.mini.studyservice.core.security.account.dto.User;
import com.mini.studyservice.core.util.error.Error;

@Service
public class RecruiteService {
	@Autowired
	private AutoNoteService noteService;
	
	@Autowired
	private final RecruitDao recruitDao;
	@Autowired
	private final TagDao tagDao;
	@Autowired
	private final UserDao userDao;
	
	public RecruiteService(RecruitDao recruitDao, TagDao tagDao, UserDao userDao) {
		this.recruitDao = recruitDao;
		this.tagDao = tagDao;
		this.userDao = userDao;
	}
	
	public ResponseEntity<Object> selectAllStudyBoards(int start) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("length", DefaultConfig.BOARD_PAGE_LENGTH);
		map.put("start",start);
		
		//없는 경우 not found 
		List<StudyBoardDto> studyBoardDtos = recruitDao.selectAllStudyBoards(map);
		if(studyBoardDtos.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"해당 게시물이 없습니다"), HttpStatus.NOT_FOUND);
	
		List<Integer> board_ids =new ArrayList<Integer>();
		for(StudyBoardDto studyBoardDto: studyBoardDtos) {
			int board_id = studyBoardDto.getBoard_id();
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
		for(StudyBoardDto studyBoardDto: studyBoardDtos) {
			int board_id = studyBoardDto.getBoard_id();
			studyBoardDto.setTags(tagList.get(board_id));
		}

		return new ResponseEntity<Object>(studyBoardDtos, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getStudyBoardsByStatus(int start, int status){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("length", DefaultConfig.BOARD_PAGE_LENGTH);
		map.put("start",start);
		map.put("status", status);
		
		//없는 경우 not found 
		List<StudyBoardDto> studyBoardDtos = recruitDao.selectStudyBoardByStatus(map);
		if(studyBoardDtos.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"해당 게시물이 없습니다"), HttpStatus.NOT_FOUND);
	
		List<Integer> board_ids =new ArrayList<Integer>();
		for(StudyBoardDto studyBoardDto: studyBoardDtos) {
			int board_id = studyBoardDto.getBoard_id();
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
		for(StudyBoardDto studyBoardDto: studyBoardDtos) {
			int board_id = studyBoardDto.getBoard_id();
			studyBoardDto.setTags(tagList.get(board_id));
		}

		return new ResponseEntity<Object>(studyBoardDtos, HttpStatus.OK);
	}
	public ResponseEntity<Object> getStudyBoard(int board_id) {
			StudyBoardDto studyBoardDto = recruitDao.getStudyBoard(board_id);
			if(studyBoardDto!=null) {
				// 없는 경우 not found 
				List<Tag> tags=tagDao.getTags(board_id);
				studyBoardDto.setTags(tags);
				
				List<StudyTimeDto> schedules= recruitDao.selectStudyTimeByStudyBoard(board_id);
				studyBoardDto.setSchedules(schedules);
				
				return new ResponseEntity<Object>(studyBoardDto, HttpStatus.OK);
			}else {
				return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND, "해당 게시물이 없습니다"), HttpStatus.NOT_FOUND);
			}
	}
	
	@Transactional
	public ResponseEntity<Object> addStudyBoard(StudyBoardDto studyBoardDto){
		
		StudyBoard studyBoard = new StudyBoard();
		studyBoard.setImg_url(studyBoardDto.getImg_url());//null no
		studyBoard.setTitle(studyBoardDto.getTitle());//null no
		//user id가져오는 부분
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername();
		User user = userDao.findByLoginid(loginid);
		int writer_id = user.getUser_id();
		
		studyBoard.setWriter_id(writer_id);//null no
		studyBoard.setTarget_num(studyBoardDto.getTarget_num());//null no
		studyBoard.setDeadline(studyBoardDto.getDeadline());//null no
		studyBoard.setContent(studyBoardDto.getContent());
		studyBoard.setCost(studyBoardDto.getCost());
		studyBoard.setLocation(studyBoardDto.getLocation());//null no
		studyBoard.setLocation_lat(studyBoardDto.getLocation_lat());
		studyBoard.setLocation_lng(studyBoardDto.getLocation_lng());
		studyBoard.set_public(studyBoardDto.is_public());
		List<Tag>tags = studyBoardDto.getTags();
				
		//이미지 처리//썸네일 처리
		//studyboard insert
		recruitDao.insertStudyBoard(studyBoard);
		int board_id = studyBoard.getBoard_id();
		studyBoardDto.setBoard_id(board_id);
		
		//study_time insert 
		List<StudyTimeDto> studyTimeList = studyBoardDto.getSchedules();
		if(!studyTimeList.isEmpty()) {
			for(StudyTimeDto studyTime: studyTimeList) {
				if(studyTime.getStudy_day()!=null&&studyTime.getStudy_start_time()!=null&&studyTime.getStudy_end_time()!=null) {
					studyTime.setBoard_id(board_id);
				}else {
					//study_time format에러-400에러 만들기
				}
			}
			recruitDao.insertStudyTime(studyTimeList);
		}
		//tag ignore insert
		tagDao.insertTages(tags);
		List<Integer>tag_ids = tagDao.getTagIds(tags);
		List<TagMapping> tagMappingList = new ArrayList<TagMapping>();
		for(int tag_id: tag_ids) {
			tagMappingList.add(new TagMapping(tag_id,board_id));
		}
		//tag mapping insert-tag_id, board_id필요
		tagDao.insertTagMappings(tagMappingList);
		//study member에 board_id& writer_id insert
		StudyMember studyMember = new StudyMember();
		studyMember.setBoard_id(board_id);
		studyMember.setUser_id(writer_id);
		recruitDao.insertStudyMember(studyMember);
		return new ResponseEntity<Object>(studyBoardDto, HttpStatus.CREATED);
	}
	@Transactional
	public ResponseEntity<Object> joinStudyGroup(int board_id){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername();
		User user = userDao.findByLoginid(loginid);
		int user_id = user.getUser_id();
		 
		List<StudyMemberDto> list = recruitDao.selectStudyMember(board_id);
		StudyMemberDto studyMemberDto = list.get(0);
		int result = 0;
		if(studyMemberDto.getBoard_status()!=1) 
			return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST,"모집이 종료된 스터디 입니다"), HttpStatus.BAD_REQUEST);
		for(StudyMemberDto study: list) {
			if(user_id==study.getUser_id()) {
				return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST,"이미 참여했습니다"), HttpStatus.BAD_REQUEST);
			}
		}

		StudyMember studyMember = new StudyMember();
		studyMember.setBoard_id(board_id);
		studyMember.setUser_id(user_id);
		result=recruitDao.insertStudyMember(studyMember);
		if(result==0)return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST,"실패"), HttpStatus.BAD_REQUEST);
		
		//스터디 장에게 메세지 보내기
		int receiver_id = studyMemberDto.getWriter_id();
		StudyBoard studyBoard = recruitDao.selectStudyBoard(board_id);
		noteService.sendJoinAlramNote(user, studyBoard,receiver_id);
		
		//member_num 1증가
		int member_num = studyMemberDto.getMember_num()+1;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("member_num", member_num);
		map.put("board_id", board_id);
		result=recruitDao.updateMemberNum(map);
		if(result==0)return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST,"실패"), HttpStatus.BAD_REQUEST);
		
		//member_num과 target_num이 같으면 status 업데이트
		if(member_num==studyMemberDto.getTarget_num()) {
			result=recruitDao.updateStatusTo2(board_id);
			if(result==0)return new ResponseEntity<Object>(new Error(HttpStatus.BAD_REQUEST,"실패"), HttpStatus.BAD_REQUEST);
			noteService.sendDeadlineNotes(user.getUser_id(), studyBoard, board_id);
		}
		
		StudyBoardDto studyBoardDto = recruitDao.getStudyBoard(board_id);
		return new ResponseEntity<Object>(studyBoardDto,HttpStatus.OK);
	}
	
	public ResponseEntity<Object> selectStudyBoardsByTagId(int tag_id){
		List<StudyBoardDto> list = recruitDao.selectStudyBoardsByTagId(tag_id);
		if(list.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"해당 게시물이 없습니다"), HttpStatus.NOT_FOUND);
		
		List<Integer> board_ids =new ArrayList<Integer>();
		for(StudyBoardDto studyBoardDto: list) {
			int board_id = studyBoardDto.getBoard_id();
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
		for(StudyBoardDto studyBoardDto: list) {
			int board_id = studyBoardDto.getBoard_id();
			studyBoardDto.setTags(tagList.get(board_id));
		}
		
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}
	public ResponseEntity<Object> getStudyBoardsByWriter(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername();
		User user = userDao.findByLoginid(loginid);
		int writer_id = user.getUser_id();
		
		List<StudyBoard> studyBoard= recruitDao.selectStudyBoardsByWriter(writer_id);
		if(studyBoard.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"해당 게시물이 없습니다"), HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<Object>(studyBoard, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getStudyBoardsByMember(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername();
		User user = userDao.findByLoginid(loginid);
		int user_id = user.getUser_id();
		
		List<StudyBoard> studyBoard = recruitDao.selectStudyBoardsByMember(user_id);
		if(studyBoard.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"참여중인 스터디가 없습니다."), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Object>(studyBoard, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getStudyMemberByBoard(int board_id){		
	
		List<StudyMemberUser> studyMemberDtos = recruitDao.selectStudyMemberByBoard(board_id);
		if(studyMemberDtos.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"스터디 원이 없습니다"), HttpStatus.NOT_FOUND);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername();
		
		boolean result = false;
		for(StudyMemberUser studyMember: studyMemberDtos) {
			if(loginid.equals(studyMember.getLoginid())) {
				result=true;
			}
			if(studyMember.getWriter_id()==studyMember.getUser_id())studyMember.set_owner(true);
		}
		if(!result)return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"스터디원이 아닙니다"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<Object>(studyMemberDtos,HttpStatus.OK);
	}	
}
