package com.mini.studyservice.core.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MailService {
	private final JavaMailSender emailSender;
	public void sendSimpleMessage(String from, String to, String subject, String text) {
		
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,false, "UTF-8");
			
			if(from == null) {
				from = "adm.studyapp@gmail.com";
			}
			mimeMessage.setContent(text, "text/html;charset=utf-8");
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);

			//내부 망에서 메일 안보내지는 듯한...
			//emailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendSimpleMessage(MailDto mailDto) {
		sendSimpleMessage(mailDto.from, mailDto.to, mailDto.subject, mailDto.text);
	}
}
