package com.mini.studyservice.core.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDto {
	String from;
	String to;
	String subject;
	String text;
}
