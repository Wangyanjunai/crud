-- 创建用户
create user 'mp'@'%' identified by '123456';
-- 创建数据库
create database if not exists mp default character set utf8 default collate utf8_general_ci;
-- 授权mysql5.7
grant all on mp.* to 'mp'@'%' identified by '123456';
-- 授权mysql8.1
grant all privileges on mp.* to 'mp'@'%' with grant option;
alter user 'mp'@'%' identified with mysql_native_password by '123456'; #修改加密规则
alter user 'mp'@'%' identified by '123456' password expire never; #更新一下用户的密码且不过期
-- 刷新权限
flush privileges;