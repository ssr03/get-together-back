-- study_member
ALTER TABLE `STUDY_DB`.`study_member`
	DROP FOREIGN KEY `FK_study_board_TO_study_member`; -- study_board -> study_member

-- study_member
ALTER TABLE `STUDY_DB`.`study_member`
	DROP FOREIGN KEY `FK_user_TO_study_member`; -- user -> study_member

-- tag_mapping
ALTER TABLE `STUDY_DB`.`tag_mapping`
	DROP FOREIGN KEY `FK_tag_TO_tag_mapping`; -- tag -> tag_mapping

-- tag_mapping
ALTER TABLE `STUDY_DB`.`tag_mapping`
	DROP FOREIGN KEY `FK_study_board_TO_tag_mapping`; -- study_board -> tag_mapping

-- user
ALTER TABLE `STUDY_DB`.`user`
	DROP PRIMARY KEY; -- user 기본키

-- study_board
ALTER TABLE `STUDY_DB`.`study_board`
	DROP PRIMARY KEY; -- study_board 기본키

-- tag
ALTER TABLE `STUDY_DB`.`tag`
	DROP PRIMARY KEY; -- tag 기본키

-- board_cmt
ALTER TABLE `STUDY_DB`.`board_cmt`
	DROP PRIMARY KEY; -- board_cmt 기본키

-- study_member
ALTER TABLE `STUDY_DB`.`study_member`
	DROP PRIMARY KEY; -- study_member 기본키

-- mng_data
ALTER TABLE `STUDY_DB`.`mng_data`
	DROP PRIMARY KEY; -- mng_data 기본키

-- mng_board
ALTER TABLE `STUDY_DB`.`mng_board`
	DROP PRIMARY KEY; -- mng_board 기본키

-- tag_mapping
ALTER TABLE `STUDY_DB`.`tag_mapping`
	DROP PRIMARY KEY; -- tag_mapping 기본키

-- mng_board_cmt
ALTER TABLE `STUDY_DB`.`mng_board_cmt`
	DROP PRIMARY KEY; -- mng_board_cmt 기본키

-- study_time
ALTER TABLE `STUDY_DB`.`study_time`
	DROP PRIMARY KEY; -- study_time 기본키

-- user
DROP TABLE IF EXISTS `STUDY_DB`.`user` RESTRICT;

-- study_board
DROP TABLE IF EXISTS `STUDY_DB`.`study_board` RESTRICT;

-- study_mng
DROP TABLE IF EXISTS `STUDY_DB`.`TABLE3` RESTRICT;

-- tag
DROP TABLE IF EXISTS `STUDY_DB`.`tag` RESTRICT;

-- board_cmt
DROP TABLE IF EXISTS `STUDY_DB`.`board_cmt` RESTRICT;

-- study_member
DROP TABLE IF EXISTS `STUDY_DB`.`study_member` RESTRICT;

-- mng_notice
DROP TABLE IF EXISTS `STUDY_DB`.`TABLE7` RESTRICT;

-- mng_data
DROP TABLE IF EXISTS `STUDY_DB`.`mng_data` RESTRICT;

-- mng_board
DROP TABLE IF EXISTS `STUDY_DB`.`mng_board` RESTRICT;

-- tag_mapping
DROP TABLE IF EXISTS `STUDY_DB`.`tag_mapping` RESTRICT;

-- mng_board_cmt
DROP TABLE IF EXISTS `STUDY_DB`.`mng_board_cmt` RESTRICT;

-- study_time
DROP TABLE IF EXISTS `STUDY_DB`.`study_time` RESTRICT;

-- 새 테이블
DROP TABLE IF EXISTS `STUDY_DB`.`TABLE` RESTRICT;

-- 내 스키마
DROP SCHEMA IF EXISTS `STUDY_DB`;

-- 내 스키마
CREATE SCHEMA `STUDY_DB`;


-- user
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'user_id',
  `loginid` varchar(30) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(30) NOT NULL COMMENT 'email',
  `registered_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'registered_time',
  `enable_flag` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `loginid` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='사용자'

-- user_role
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT 'USER.USER_ID',
  `role` varchar(30) NOT NULL COMMENT '권한',
  `created_by` int(11) DEFAULT NULL COMMENT '생성자',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_by` int(11) DEFAULT NULL COMMENT '수정자',
  `modified_date` datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`id`),
  KEY `USER_ROLE_FK` (`user_id`),
  CONSTRAINT `USER_ROLE_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='사용자_권한'

--spring_session
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

-- spring_session_attributes
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` mediumblob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  KEY `SPRING_SESSION_ATTRIBUTES_IX1` (`SESSION_PRIMARY_ID`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8

-- study_board
CREATE TABLE `study_board` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'board_id',
  `title` varchar(50) NOT NULL COMMENT 'title',
  `img_url` varchar(100) DEFAULT NULL COMMENT 'img_url',
  `writer_id` int(11) NOT NULL COMMENT 'writer_id',
  `status` int(10) unsigned NOT NULL COMMENT 'status',
  `target_num` int(10) unsigned NOT NULL COMMENT 'target_num',
  `member_num` int(10) unsigned DEFAULT 1 COMMENT 'member_num',
  `deadline` datetime NOT NULL COMMENT 'deadline',
  `content` text DEFAULT NULL COMMENT 'content',
  `cost` int(10) unsigned DEFAULT NULL COMMENT 'cost',
  `start_date` date DEFAULT NULL COMMENT 'start_date',
  `end_date` date DEFAULT NULL COMMENT 'end_date',
  `location` varchar(50) NOT NULL COMMENT 'location',
  `location_lat` float DEFAULT NULL COMMENT 'location_lat',
  `location_lng` float DEFAULT NULL COMMENT 'location_lng',
  `board_cnt` int(11) DEFAULT 0 COMMENT 'board_cnt',
  `chat_room_id` int(11) DEFAULT NULL COMMENT 'chat_room_id',
  `is_public` tinyint(1) NOT NULL DEFAULT 1 COMMENT 'is_public',
  `created_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'created_time',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='스터디원 모집글'

ALTER TABLE `STUDY_DB`.`study_board`
	AUTO_INCREMENT = 1;

-- tag
CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tag_id',
  `tag_name` varchar(20) DEFAULT NULL,
  `type` int(10) unsigned NOT NULL DEFAULT 1 COMMENT 'type',
  `city_state` int(11) DEFAULT NULL COMMENT 'state\n	1. 국가\n	2.광역자치단체(시/도)\n	3.기초자치단체(구/시)\n	4. 읍/면\n	5. 동/리\n	6. 건물번호\n	10. 기타',
  `created_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'created_time',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag_name` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 COMMENT='태그'

ALTER TABLE `STUDY_DB`.`tag`
	AUTO_INCREMENT = 1;

-- tag_mapping
CREATE TABLE `tag_mapping` (
  `tag_mapping_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tag_mapping_id',
  `tag_id` int(11) NOT NULL COMMENT 'tag_id',
  `board_id` int(11) NOT NULL COMMENT 'board_id',
  PRIMARY KEY (`tag_mapping_id`,`tag_id`,`board_id`),
  KEY `FK_tag_TO_tag_mapping` (`tag_id`),
  KEY `FK_study_board_TO_tag_mapping` (`board_id`),
  CONSTRAINT `FK_study_board_TO_tag_mapping` FOREIGN KEY (`board_id`) REFERENCES `study_board` (`board_id`),
  CONSTRAINT `FK_tag_TO_tag_mapping` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8 COMMENT='모집글과 태그 매핑'

ALTER TABLE `STUDY_DB`.`tag_mapping`
	AUTO_INCREMENT = 1;
	
-- board_cmt
CREATE TABLE `board_cmt` (
  `board_cmt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'board_cmt_id',
  `board_id` int(11) NOT NULL COMMENT 'board_id',
  `writer_id` int(11) NOT NULL COMMENT 'writer_id',
  `comment` varchar(100) NOT NULL COMMENT 'comment',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT 'ref_id',
  `depth` int(11) NOT NULL DEFAULT 0 COMMENT 'ref_step',
  `is_public` tinyint(1) NOT NULL DEFAULT 1 COMMENT 'is_public',
  `created_time` timestamp NULL DEFAULT current_timestamp() COMMENT 'created_time',
  PRIMARY KEY (`board_cmt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='스터디원 모집글 댓글'

ALTER TABLE `STUDY_DB`.`board_cmt`
	AUTO_INCREMENT = 1;

-- study_member
CREATE TABLE `study_member` (
  `user_id` int(11) NOT NULL COMMENT 'user_id',
  `board_id` int(11) NOT NULL COMMENT 'board_id',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '대기중\r\n	2: 확정',
  `created_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'created_time',
  PRIMARY KEY (`id`),
  KEY `FK_study_board_TO_study_member` (`board_id`),
  CONSTRAINT `FK_study_board_TO_study_member` FOREIGN KEY (`board_id`) REFERENCES `study_board` (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='사용자와 모집글 및 그룹 매핑'

-- study_member
ALTER TABLE `STUDY_DB`.`study_member`
	ADD CONSTRAINT `FK_user_TO_study_member` -- user -> study_member
		FOREIGN KEY (
			`user_id` -- user_id
		)
		REFERENCES `STUDY_DB`.`user` ( -- user
			`user_id` -- user_id
		);

CREATE TABLE `mng_board` (
  `mng_board_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mng_board_id',
  `board_id` int(11) NOT NULL COMMENT 'board_id',
  `writer_id` int(11) NOT NULL COMMENT 'writer_id',
  `title` varchar(50) NOT NULL COMMENT 'title',
  `content` varchar(200) DEFAULT NULL COMMENT 'content',
  `is_notice` tinyint(1) DEFAULT 0 COMMENT 'is_notice',
  `study_date` date DEFAULT NULL COMMENT 'study_date',
  `study_day` int(11) DEFAULT 1 COMMENT 'study_day',
  `created_time` timestamp NULL DEFAULT NULL COMMENT 'created_time',
  PRIMARY KEY (`mng_board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='스터디 관리 게시글'

ALTER TABLE `STUDY_DB`.`mng_board`
	AUTO_INCREMENT = 1;

-- mng_data
CREATE TABLE `mng_data` (
  `mng_data_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mng_data_id',
  `board_id` int(11) NOT NULL COMMENT 'board_id',
  `mng_board_id` int(11) DEFAULT NULL COMMENT 'mng_board_id',
  `study_date` date NOT NULL COMMENT 'study_date',
  `file_name` varchar(30) NOT NULL COMMENT 'file_name',
  `url` varchar(100) NOT NULL COMMENT 'url',
  `created_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'created_time',
  PRIMARY KEY (`mng_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='스터디 데이터'

ALTER TABLE `STUDY_DB`.`mng_data`
	AUTO_INCREMENT = 1;

-- mng_board_cmt
CREATE TABLE `mng_board_cmt` (
  `mng_board_cmt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'mng_board_cmt_id',
  `mng_board_id` int(11) NOT NULL COMMENT 'mng_board_id',
  `writer_id` int(11) NOT NULL COMMENT 'writer_id',
  `comment` varchar(100) NOT NULL COMMENT 'comment',
  `created_time` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'created_time',
  PRIMARY KEY (`mng_board_cmt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='스터디 관리 게시글 댓글'

ALTER TABLE `STUDY_DB`.`mng_board_cmt`
	AUTO_INCREMENT = 1;

-- study_time
CREATE TABLE `study_time` (
  `study_time_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'study_time_id',
  `board_id` int(11) NOT NULL COMMENT 'board_id',
  `study_day` char(10) NOT NULL,
  `study_start_time` time DEFAULT NULL COMMENT 'study_start_time',
  `study_end_time` time DEFAULT NULL COMMENT 'study_end_time',
  PRIMARY KEY (`study_time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='스터디 시간'

ALTER TABLE `STUDY_DB`.`study_time`
	AUTO_INCREMENT = 1;

-- note
CREATE TABLE `note` (
  `note_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'note_id',
  `receiver_id` int(11) NOT NULL COMMENT 'receiver_id',
  `sender_id` int(11) NOT NULL COMMENT 'sender_id',
  `title` varchar(100) NOT NULL COMMENT 'title',
   `note` text NOT NULL COMMENT 'note',
  `sent_date` datetime NOT NULL COMMENT 'sent_date',
  `read_date` datetime NOT NULL COMMENT 'read_date',
  `is_checked` tinyint(1) DEFAULT 0 COMMENT 'is_checked',
  PRIMARY KEY (`note_id`),
  KEY `FK_user_TO_note` (`receiver_id`),
  KEY `FK_user_TO_note2` (`sender_id`),
  CONSTRAINT `FK_user_TO_note` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_user_TO_note2` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='쪽지함'
