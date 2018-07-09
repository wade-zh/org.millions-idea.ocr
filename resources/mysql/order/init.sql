#先修改mysql的时区 https://blog.csdn.net/qq_28018283/article/details/80109290


DROP DATABASE IF EXISTS `mi-ocr-order`;

CREATE DATABASE `mi-ocr-order`;

USE `mi-ocr-order`;

#资金变化表
CREATE TABLE `money_change_logs`(
	`auto_id` int(11) NOT NULL AUTO_INCREMENT,
	`log_id` varchar(32) NOT NULL COMMENT 'uuid',
	`record_id` varchar(32) NOT NULL COMMENT '系统交易流水号',
	`from_uid` int(11) NOT NULL COMMENT '用户id',
	`trade_type` int(11) NOT NULL COMMENT '1=增 2=减',
	`trade_amount` decimal(16,4) NOT NULL COMMENT '交易额',
	`account_balance` decimal(16,4) NOT NULL COMMENT '余额',
	`remark` varchar(32) NOT NULL COMMENT '摘要',
	`add_date` datetime NOT NULL DEFAULT NOW() COMMENT '交易日',
	PRIMARY KEY (`auto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资金变化表';


#交易流水表
CREATE TABLE `transaction_records`(
	`auto_id` int(11) NOT NULL AUTO_INCREMENT,
	`record_id` varchar(32) NOT NULL COMMENT 'uuid',
	`record_no` varchar(52) NOT NULL DEFAULT 'A873796782' COMMENT '交易号(默认生成规则为app名称对应的ascii码)',
	`captcha_id` varchar(32) DEFAULT NULL COMMENT '验证码票据',
	`from_uid` int(11) NOT NULL COMMENT '发起方账户',
	`to_uid` int(11) NOT NULL COMMENT '对方账户',
	`trade_date` datetime NOT NULL DEFAULT NOW() COMMENT '交易日',
	`trade_type` int(11) NOT NULL COMMENT '交易类别(1=充值,2=消费,3=报错)',
	`trade_amount` decimal(16,4) NOT NULL COMMENT '交易额',
	`remark` varchar(100) NOT NULL COMMENT '摘要',
	`ack` int(11) DEFAULT 0 COMMENT '确认状态(0=未确认,1=已确认)',
	PRIMARY KEY(auto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水表';


#钱包表
CREATE TABLE `wallet`(
	`auto_id` int(11) NOT NULL AUTO_INCREMENT,
	`uid` int(11) NOT NULL COMMENT '用户id',
	`balance` decimal(16,4) NOT NULL COMMENT '余额',
	`edit_date` datetime NOT NULL DEFAULT NOW() COMMENT '编辑时间',
	`state` int(11) NOT NULL DEFAULT 0 COMMENT '可用状态(0=可用,1=禁用)',
	`version` int(11) DEFAULT 0,
	PRIMARY KEY(auto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='钱包表';

		
-- in captchaId varchar(32),in fromUid int, in toUid int, in unitAmount decimal(16,4), in type int, out status nvarchar(32)
-- buy 存储过程
CREATE PROCEDURE `buy`(in fromUid int, in toUid int, in unitAmount decimal(16,4), in type int, out status nvarchar(32))
BEGIN
	DECLARE errCode INTEGER DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET errCode=1;-- 异常时设置为1
	SET AUTOCOMMIT = 0;
	START TRANSACTION;

	#1.插入流水
	SET @record_id = REPLACE(UUID(),'-','');
	SET @ack = 0;
	IF type = 3 THEN 
		SET @ack = 1;
	END IF;
	INSERT INTO transaction_records(record_id, captchaId, from_uid,to_uid,trade_type,trade_amount,remark,ack,)
	VALUES(@record_id,captchaId, fromUid, toUid, `type`, unitAmount, '转账',@ack);
	IF ROW_COUNT() = 0 THEN
		SET errCode = 1;
		SET status = 'ORDER_CREATE_TRANSACTION_RECORD';
	ELSE
		#2.修改余额
		SET @balance = (SELECT balance FROM wallet WHERE `uid` = fromUid);
		IF @balance <= 0 OR @balance <= unitAmount THEN
			SET errCode = 1;
			SET status = 'ORDER_WALLET_BALANCE_ZERO';
		ELSE
			SET @version = (SELECT version FROM wallet WHERE `uid` = fromUid);
			UPDATE wallet SET balance = balance - unitAmount,edit_date = NOW(), version = version + 1 WHERE `uid` = fromUid AND `state` = 0 AND balance >= unitAmount AND version = @version;
			IF ROW_COUNT() = 0 THEN
				SET errCode = 1;
				SET status = 'ORDER_REDUCE_WALLET_BALANCE';
			ELSE
				SET @version = (SELECT version FROM wallet WHERE `uid` = toUid);
				UPDATE wallet SET balance = balance + unitAmount,edit_date = NOW(), version = version + 1 WHERE `uid` = toUid AND `state` = 0 AND version = @version;
				IF ROW_COUNT() = 0 THEN
					SET errCode = 1;
					SET status = 'ORDER_ADD_WALLET_BALANCE';
				ELSE 
					#3.资金变动
					INSERT INTO money_change_logs(log_id,`record_id`,from_uid,trade_type,trade_amount,`account_balance`,`remark`,`add_date`) VALUES(REPLACE(UUID(),'-',''), @record_id, fromUid, 2, unitAmount, (SELECT balance FROM wallet WHERE uid = fromUid), '支出', NOW());
					IF ROW_COUNT() = 0 THEN
						SET errCode = 1;
						SET status = 'ORDER_CREATE_MONEY_CHANGE_MINUS_LOG';
					ELSE
						INSERT INTO money_change_logs(log_id,`record_id`,from_uid,trade_type,trade_amount,`account_balance`,`remark`,`add_date`) VALUES(REPLACE(UUID(),'-',''), @record_id, toUid, 1, unitAmount, (SELECT balance FROM wallet WHERE uid = toUid), '收入', NOW());
						IF ROW_COUNT() = 0 THEN
							SET errCode = 1;
							SET status = 'ORDER_CREATE_MONEY_CHANGE_PLUS_LOG';
						ELSE 
							SET errCode = 0;
							SET status = '';
						END IF;-- 收入账单end
					END IF;-- 支出账单end
				END IF;-- 增加余额end
			END IF;-- 减少余额end
		END IF;-- 查询余额end
	END IF;-- 插入流水end

	IF errCode = 1 THEN
		IF len(status) = 0 THEN
			SET status = 'ORDER_BUY_ERROR';
		END IF;
		ROLLBACK;
	ELSE
		SET status = 'SUCCESS';
		COMMIT;
	END IF;
END;








-- in recordId varchar(36), in fromUid int, in toUid int, in unitAmount decimal(16,4), in type int, out status nvarchar(32)
-- delayedBuy 存储过程
CREATE PROCEDURE `delayedBuy`(in recordId varchar(36), in fromUid int, in toUid int, in unitAmount decimal(16,4), in type int, out status nvarchar(32))
BEGIN
	DECLARE errCode INTEGER DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET errCode=1;-- 异常时设置为1
	SET AUTOCOMMIT = 0;
	START TRANSACTION;


	SET errCode = 1;
	SET status = 'NULL';

	#1.插入流水
	SET @record_id = recordId;
	UPDATE transaction_records SET ack = 1 WHERE `from_uid` = fromUid AND record_id = recordId AND ack = 0;
	IF ROW_COUNT() = 0 THEN
		SET errCode = 1;
		SET status = 'ORDER_ACK';
	ELSE
		#2.修改余额
		SET @balance = (SELECT balance FROM wallet WHERE `uid` = fromUid);
		IF @balance <= 0 OR @balance <= unitAmount THEN
			SET errCode = 1;
			SET status = 'ORDER_WALLET_BALANCE_ZERO';
		ELSE
			SET @version = (SELECT version FROM wallet WHERE `uid` = fromUid);
			UPDATE wallet SET balance = balance - unitAmount,edit_date = NOW(), version = version + 1 WHERE `uid` = fromUid AND `state` = 0 AND balance >= unitAmount AND version = @version;
			IF ROW_COUNT() = 0 THEN
				SET errCode = 1;
				SET status = 'ORDER_REDUCE_WALLET_BALANCE';
			ELSE
				SET @version = (SELECT version FROM wallet WHERE `uid` = toUid);
				UPDATE wallet SET balance = balance + unitAmount,edit_date = NOW(), version = version + 1 WHERE `uid` = toUid AND `state` = 0 AND version = @version;
				IF ROW_COUNT() = 0 THEN
					SET errCode = 1;
					SET status = 'ORDER_ADD_WALLET_BALANCE';
				ELSE 
					#3.资金变动
					INSERT INTO money_change_logs(log_id,`record_id`,from_uid,trade_type,trade_amount,`account_balance`,`remark`,`add_date`) VALUES(REPLACE(UUID(),'-',''), @record_id, fromUid, 2, unitAmount, (SELECT balance FROM wallet WHERE uid = fromUid), '支出', NOW());
					IF ROW_COUNT() = 0 THEN
						SET errCode = 1;
						SET status = 'ORDER_CREATE_MONEY_CHANGE_MINUS_LOG';
					ELSE
						INSERT INTO money_change_logs(log_id,`record_id`,from_uid,trade_type,trade_amount,`account_balance`,`remark`,`add_date`) VALUES(REPLACE(UUID(),'-',''), @record_id, toUid, 1, unitAmount, (SELECT balance FROM wallet WHERE uid = toUid), '收入', NOW());
						IF ROW_COUNT() = 0 THEN
							SET errCode = 1;
							SET status = 'ORDER_CREATE_MONEY_CHANGE_PLUS_LOG';
						ELSE 
							SET errCode = 0;
							SET status = '';
						END IF;-- 收入账单end
					END IF;-- 支出账单end
				END IF;-- 增加余额end
			END IF;-- 减少余额end
		END IF;-- 查询余额end
	END IF;-- 插入流水end
	IF errCode = 1 THEN
		IF len(status) = 0 THEN
			SET status = 'ORDER_BUY_ERROR';
		END IF;
		ROLLBACK;
	ELSE
		SET status = 'SUCCESS';
		COMMIT;
	END IF;
END;










 