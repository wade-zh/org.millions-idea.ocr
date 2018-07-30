DROP DATABASE IF EXISTS `mi-ocr-user`;

CREATE DATABASE `mi-ocr-user`;

USE `mi-ocr-user`;

#用户表
CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `regist_time` datetime NOT NULL COMMENT '注册时间',
  `last_active_time` datetime DEFAULT NULL COMMENT '最后活动时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_area` varchar(32) DEFAULT NULL COMMENT '最后登录区域',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


#频道表
CREATE TABLE `channels` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NULL COMMENT '编码',
  `unit_amount` decimal(10,4) NOT NULL COMMENT '扣点',
  `desc` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '描述',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `uq_code` (`cid`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='频道表';


#用户信息表
CREATE TABLE `informations`(
	`info_id` int(11) NOT NULL AUTO_INCREMENT,
	`uid` int(11) NOT NULL COMMENT '用户id',
	`balance` decimal(10,4) DEFAULT 0.0000 COMMENT '余额',
	PRIMARY KEY (`info_id`),
	UNIQUE KEY `uq_uid` (`info_id`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';