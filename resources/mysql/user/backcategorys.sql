#后台分类表#

CREATE TABLE `backcategorys` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '通道代码',
  `cname` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '分类名',
  `reduce` decimal(10,4) NOT NULL COMMENT '扣减基数',
  UNIQUE KEY `uq_code` (`cid`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台分类表';


INSERT INTO backcategorys(code,cname,reduce) VALUES('T0003604', '4位英数混合类型',8)