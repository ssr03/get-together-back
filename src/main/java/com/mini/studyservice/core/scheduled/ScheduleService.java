package com.mini.studyservice.core.scheduled;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini.studyservice.api.note.service.AutoNoteService;
import com.mini.studyservice.api.recruit.dao.RecruitDao;
import com.mini.studyservice.api.recruit.dto.StudyBoard;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {
	@Autowired
	private AutoNoteService noteService;
	
	@Autowired
	final private RecruitDao recruitDao;
	
	@Transactional
	public void runUpdateAfterDeadline() {
		List<StudyBoard> studyBoards = recruitDao.selectDeadlineStudyBoard();
		List<Integer> board_ids = new ArrayList<Integer>();
		List<Integer> writer_ids = new ArrayList<Integer>();
		
		for(StudyBoard studyBoard: studyBoards) {
			board_ids.add(studyBoard.getBoard_id());
			writer_ids.add(studyBoard.getWriter_id());
			noteService.sendDeadlineNotes(studyBoard.getWriter_id(), studyBoard, studyBoard.getBoard_id());
		}

		if(!board_ids.isEmpty()) {
			int result = recruitDao.autoUpdateStatusTo2(board_ids);
		}else {
			System.out.println("마감기한 지난 모집글 없음");
		}

	}
}
