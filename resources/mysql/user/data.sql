#用户表
INSERT INTO `users`(user_name,password,regist_time) VALUES('admin', MD5('admin123456'), CURRENT_TIMESTAMP);
INSERT INTO `users`(user_name,password,regist_time) VALUES('root', MD5('root123456'), CURRENT_TIMESTAMP);

#频道表
INSERT INTO channels(code,unit_amount,`desc`) VALUES('T0003604', 8, '英数混合');
INSERT INTO channels(code,unit_amount,`desc`) VALUES('T0003605', 8.5, '英数混合');

#用户信息表
INSERT INTO informations(uid,balance) VALUES(1, 10000);
INSERT INTO informations(uid,balance) VALUES(2, 2000);

