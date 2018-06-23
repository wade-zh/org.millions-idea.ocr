CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `issue_id` int(11) NOT NULL COMMENT '密保问题',
  `issue_result` varchar(128) NOT NULL COMMENT '密保答案',
  `contact` varchar(32) NOT NULL,
  `regist_time` datetime NOT NULL COMMENT '注册时间',
  `last_active_time` datetime DEFAULT NULL COMMENT '最后活动时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


 
INSERT INTO `users`(user_name,password,issue_id,issue_result,contact,regist_time) VALUES('admin', MD5('admin123456'), 1, '哥谭市', 'qq:2175656094', CURRENT_TIMESTAMP);


#评定用户消费等级[不要录入数据库]
UPDATE users SET level_id = 
	CASE WHEN ((SELECT balance FROM wallet WHERE uid = 1) > 50) THEN 2 ELSE 1 END
WHERE uid = 1;