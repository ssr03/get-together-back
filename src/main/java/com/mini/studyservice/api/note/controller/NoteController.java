package com.mini.studyservice.api.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.studyservice.api.note.dto.Note;
import com.mini.studyservice.api.note.service.NoteService;
import com.mini.studyservice.core.config.DefaultConfig;

@RestController
@RequestMapping(DefaultConfig.APP_NAME)
public class NoteController {
	@Autowired
	private NoteService service;
	
	@GetMapping("/note/receiver")
	public ResponseEntity<Object> getAllReceivedNotes(){
		return service.getAllReceivedNotes();
	}
	
	@GetMapping("/note/sender")
	public ResponseEntity<Object> getAllSentNotes(){
		return service.getAllSentNotes();
	}
	
	@GetMapping("/note/receiver/count")
	public ResponseEntity<Object> getUncheckedNoteCount(){
		return service.getUnCheckedNoteCount();
	}
	
	@PostMapping("/note")
	public ResponseEntity<Object> writeNote(@RequestBody Note note){
		return service.writeNote(note);
	}
	
	@GetMapping("/note/{note_id}")
	public ResponseEntity<Object> getNote(@PathVariable int note_id){
		return service.getNote(note_id);
	}
	
	@PutMapping("/note/{note_id}/read")
	public ResponseEntity<Object> readNote(@PathVariable int note_id){
		return service.readNote(note_id);
	}
}
