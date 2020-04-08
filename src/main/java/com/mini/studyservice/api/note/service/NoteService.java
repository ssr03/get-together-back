package com.mini.studyservice.api.note.service;

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

import com.mini.studyservice.api.note.dao.NoteDao;
import com.mini.studyservice.api.note.dto.Note;
import com.mini.studyservice.api.note.dto.NoteDto;
import com.mini.studyservice.core.security.account.dao.UserDao;
import com.mini.studyservice.core.security.account.dto.User;
import com.mini.studyservice.core.util.error.Error;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoteService {
	@Autowired
	private final NoteDao noteDao;
	@Autowired
	private final UserDao userDao;
	
	public ResponseEntity<Object> getAllReceivedNotes(){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername(); 
	
		List<NoteDto> notes = noteDao.selectAllNotesByReceiver(loginid);
		
		if(notes.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"받은 쪽지가 없습니다"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Object>(notes, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getAllSentNotes(){
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
	
		String loginid= userDetail.getUsername(); 
		
		List<NoteDto> notes = noteDao.selectAllNotesBySender(loginid);
		
		if(notes.isEmpty())return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND,"보낸 쪽지가 없습니다"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Object>(notes, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getUnCheckedNoteCount() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
		
		String loginid = userDetail.getUsername();		
		int count = noteDao.getUnCheckedNoteCount(loginid);
		
		return new ResponseEntity<Object>(count,HttpStatus.OK);
	}
	
	@Transactional
	public void sendNote(Note note) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
		
		String loginid = userDetail.getUsername();
		User sender = userDao.findByLoginid(loginid);
		
		note.setSender_id(sender.getUser_id());
		
		noteDao.insertNote(note);
	}
	
	public ResponseEntity<Object> getNote(int note_id){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
		
		String loginid = userDetail.getUsername();
		User receiver = userDao.findByLoginid(loginid);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("note_id", note_id);
		map.put("receiver_id", receiver.getUser_id());
		NoteDto note = noteDao.selectNote(map);
		if(note==null)return new ResponseEntity<Object>(new Error(HttpStatus.NOT_FOUND, "해당 쪽지가 없습니다."),HttpStatus.NOT_FOUND);
		
		int result=noteDao.updateIsCheckByReceiver(map);
		return new ResponseEntity<Object>(note, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> readNote(int note_id){
		int result=noteDao.updateIsCheck(note_id);
		return new ResponseEntity<Object>(note_id+"의 메세지를 읽었습니다", HttpStatus.OK);
	}
	
	public ResponseEntity<Object> writeNote(Note note){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetail = (UserDetails)principal;
		
		String loginid = userDetail.getUsername();
		User sender = userDao.findByLoginid(loginid);
		note.setSender_id(sender.getUser_id());
		noteDao.insertNote(note);
		
		return new ResponseEntity<Object>(note, HttpStatus.CREATED);
		
	}
}
