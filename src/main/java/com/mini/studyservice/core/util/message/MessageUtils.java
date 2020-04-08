package com.mini.studyservice.core.util.message;

import com.mini.studyservice.api.note.dto.Note;
import com.mini.studyservice.api.recruit.dto.StudyBoard;
import com.mini.studyservice.core.security.account.dto.User;


public class MessageUtils {
	
	public static void setCloseAlramMessage(Note note, StudyBoard studyBoard) {
		String title = "[스터디 모집 종료] 모집이 마감되었습니다.";
		String msg = "<p><a href='/study/"+studyBoard.getBoard_id()+"'>["+ studyBoard.getTitle()+"]</a>";
		msg += "해당 게시글의 모집이 마감되었습니다.</p>";
		
		note.setTitle(title);
		note.setNote(msg);
	}
	
	public static void setJoinAlaramMessage(Note note, StudyBoard studyBoard, User sender) {
		String senderId = sender.getLoginid();
		String title = "[스터디 참여] "+senderId+"님이 스터디에 참여했습니다";

		note.setTitle(title);
		
		StringBuilder msg = new StringBuilder(); 
		msg.append("<h6>[스터디 참여]"+ senderId+"님이 ["+ studyBoard.getTitle()+"] 스터디에 참여했습니다</h6>");
		msg.append("<br><p>");
		msg.append("<a href='/study/"+studyBoard.getBoard_id()+"'>["+ studyBoard.getTitle()+"] 바로가기</a>");
		msg.append("</p><br>");
		note.setNote(msg.toString());
	}
}
