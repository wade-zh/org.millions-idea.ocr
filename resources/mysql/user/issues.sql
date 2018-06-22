CREATE TABLE `issues` (
  `eid` int(11) NOT NULL AUTO_INCREMENT COMMENT '密保问题表',
  `issue` varchar(64) NOT NULL COMMENT '密保问题',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='密保问题表';


INSERT INTO `issues`(issue) VALUES('您目前居住在哪个城市?');