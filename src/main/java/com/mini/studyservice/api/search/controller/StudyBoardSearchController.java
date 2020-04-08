package com.mini.studyservice.api.search.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini.studyservice.api.search.service.StudyBoardSearchService;
import com.mini.studyservice.core.config.DefaultConfig;

@RestController
@RequestMapping(DefaultConfig.APP_NAME)
public class StudyBoardSearchController {
	
	@Autowired
	private StudyBoardSearchService service;
	
	@GetMapping("/search/location")
	public ResponseEntity<Object> searchStudyBoardByLocation(@PathParam(value="search") String search){
		return service.searchStudyBoardByLocation(search);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> searchStudyBoard(@PathParam(value="search") String search){
		return service.searchStudyBoard(search);
	}
}
