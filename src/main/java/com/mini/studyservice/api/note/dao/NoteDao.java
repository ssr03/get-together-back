package com.mini.studyservice.api.note.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mini.studyservice.api.note.dto.Note;
import com.mini.studyservice.api.note.dto.NoteDto;

@Mapper
public interface NoteDao {
	public List<NoteDto> selectAllNotesByReceiver(String loginid);
	public List<NoteDto> selectAllNotesBySender(String loginid);
	public int insertNote(Note note);
	public int insertNotes(List<Note> notes);
	public int getUnCheckedNoteCount(String loginid);
	public NoteDto selectNote(Map<String, Integer> map);
	public int updateIsCheckByReceiver(Map<String, Integer> map);
	public int updateIsCheck(int note_id);
}
