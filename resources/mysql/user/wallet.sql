CREATE TABLE `wallet` (
  `wid` int(11) NOT NULL AUTO_INCREMENT COMMENT '钱包主键',
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `balance` decimal(10,4) NOT NULL COMMENT '钱包余额',
  PRIMARY KEY (`wid`),
  UNIQUE KEY `uq_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='钱包表';


INSERT INTO `wallet`(uid,balance) VALUES(1, 999.9999);