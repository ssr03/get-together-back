package com.mini.studyservice.api.note.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class NoteDto {
	private int note_id;
	private String receiver;
	private String sender;
	private int sender_id;
	private String title;
	private String note;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	private LocalDateTime sent_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
	private LocalDateTime read_date;
	private boolean is_checked;
}
