INSERT INTO wallet(`uid`,balance,edit_date,`state`) VALUES(1,10000000,NOW(),0);
INSERT INTO wallet(`uid`,balance,edit_date,`state`) VALUES(2,1,NOW(),0);



INSERT INTO `transaction_records` (`auto_id`, `record_id`, `record_no`, `captcha_id`, `from_uid`, `to_uid`, `trade_date`, `trade_type`, `trade_amount`, `remark`, `ack`, `isAvailable`) VALUES ('1', 'fb1156e086154fc3af1fdc9dfe7d466a', 'A873796782', 'fb1156e086154fc3af1fdc9df555466a', '2', '1', '2018-07-10 16:33:35', '1', '100.0000', '充值', '0', '1');


INSERT INTO transaction_records(record_id, from_uid,to_uid,trade_type,trade_amount,remark)
VALUES(REPLACE(UUID(),'-',''), 1, 2, 1, 100.0000, '充值');


UPDATE wallet SET balance = balance - 100 WHERE uid = 1 AND `state` = 0 AND balance > 0;
UPDATE wallet SET balance = balance + 100 WHERE uid = 2 AND `state` = 0;


INSERT INTO money_change_logs VALUES(REPLACE(UUID(),'-',''), 'f3ed08517ce111e8bea408002789a6ed', 1, 2, 100, (SELECT balance FROM wallet WHERE uid = 1), '支出', NOW());
INSERT INTO money_change_logs VALUES(REPLACE(UUID(),'-',''), 'f3ed08517ce111e8bea408002789a6ed', 2, 1, 100, (SELECT balance FROM wallet WHERE uid = 2), '汇入', NOW());

#14918070212183100027


INSERT INTO money_change_logs(log_id,`record_id`,from_uid,trade_type,trade_amount,`account_balance`,`remark`,`add_date`)
        VALUES(REPLACE(UUID(),'-',''), @record_id, fromUid, 2, unitAmount, (SELECT balance FROM wallet WHERE uid = fromUid), '支出', NOW());
		
		
CALL delayedBuy('fb1156e086154fc3af1fdc9dfe7d466a',2,1,0.0008,2,@result);
SELECT @result;	


CALL delayedBuy('fb1156e086154fc3af1fdc9dfe7d466a',2,1,0.0008,2,@result);
SELECT @result;	

CALL buy('fb1156e086154fc3af1fdc9df555466a',1,2,0.0008,2,@result);
SELECT @result;	  
