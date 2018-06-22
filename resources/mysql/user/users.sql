CREATE TABLE `users` (
  `uid` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '用户密码-加密字符串',
  `issue_id` int(11) NOT NULL COMMENT '密保问题主键',
  `issue_result` varchar(128) NOT NULL COMMENT '密保答案',
  `qq` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '用户QQ',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


INSERT INTO `users`(user_name,password,issue_id,issue_result,qq) VALUES('admin', MD5('admin123456'), 1, '哥谭市', '2175656094');