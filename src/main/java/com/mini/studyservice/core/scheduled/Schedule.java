package com.mini.studyservice.core.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Schedule {
	private final ScheduleService scheduleService;
	
	/*
	 * 마감기한 종료 후 상태 변경: 모집중->모집완료(1->2)
	 * Desc 매일 저녁 12시 0분 0초 실행( 초 분 시)
	 */
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void cronJobUpdateStatus() {
		scheduleService.runUpdateAfterDeadline();
	}
}
