package com.mini.studyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.mini.studyservice.core.config.FileStorageConfig;

@EnableConfigurationProperties(FileStorageConfig.class)
@SpringBootApplication
@EnableScheduling
public class StudyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyServiceApplication.class, args);
	}
}
