package com.mini.studyservice.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
@Configuration
public class FileStorageConfig {
	  @Value("${file.upload-dir}")
	  String uploadDir;
	  @Value("${file.thumbnail-dir}")
	  String thumbnailDir;
}
