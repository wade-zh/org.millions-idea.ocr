CREATE TABLE `wallet` (
  `wid` int(11) NOT NULL AUTO_INCREMENT COMMENT '钱包主键',
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `balance` int(11) NOT NULL COMMENT '钱包余额',
  PRIMARY KEY (`wid`),
  UNIQUE KEY `uq_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='钱包表';


INSERT INTO `wallet`(uid,balance) VALUES(1, 1000000);




#扣减余额
UPDATE wallet SET balance = balance - (SELECT reduce FROM backcategorys WHERE `code` = 'T0003604') 
WHERE uid = 1 AND balance >= (SELECT reduce FROM backcategorys WHERE `code` = 'T0003604');
