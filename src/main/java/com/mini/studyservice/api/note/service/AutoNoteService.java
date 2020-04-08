package com.mini.studyservice.api.note.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.studyservice.api.note.dao.NoteDao;
import com.mini.studyservice.api.note.dto.Note;
import com.mini.studyservice.api.recruit.dao.RecruitDao;
import com.mini.studyservice.api.recruit.dto.StudyBoard;
import com.mini.studyservice.api.recruit.dto.StudyMemberDto;
import com.mini.studyservice.core.security.account.dto.User;
import com.mini.studyservice.core.util.message.MessageUtils;

@Service
public class AutoNoteService {
	
	@Autowired
	private NoteDao noteDao;
	@Autowired
	private RecruitDao recruitDao;
	
	/* 스터디 참여시 스터디장에게 알림
	 * */
	@Async
	public void sendJoinAlramNote(User sender, StudyBoard studyBoard, int receiver_id) {
		Note note = new Note();

		note.setSender_id(sender.getUser_id());
		note.setReceiver_id(receiver_id);
		MessageUtils.setJoinAlaramMessage(note, studyBoard, sender);
		
		noteDao.insertNote(note);	
	}
	
	/* 스터디 참여인원 모두에게 모집 마감 알림
	 * 스터디 마감 기한 지난 경우 or 모집인원이 다 찬 경우
	 * */
	@Transactional
	@Async
	public void sendDeadlineNotes(int sender_id, StudyBoard studyBoard, int board_id) {
		
		List<StudyMemberDto> studyMembers = recruitDao.selectStudyMember(board_id);
		
		List<Note> notes = new ArrayList<Note>();
		for(StudyMemberDto studyMember:studyMembers) {
			Note note = new Note();
			note.setSender_id(sender_id);
			note.setReceiver_id(studyMember.getUser_id());
			MessageUtils.setCloseAlramMessage(note, studyBoard);
			notes.add(note);
		}
		noteDao.insertNotes(notes);		
	}
}
