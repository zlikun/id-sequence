-- 针对用户表的主键生成器，自增起始点为：1,000,000，自增步长为：2 (有两台机器)
-- 两台机器，一台从1,000,000开始自增、一台从1,000,001开始自增，相互错开
DROP TABLE IF EXISTS test.ID_USER;
CREATE TABLE test.ID_USER (
  id INT PRIMARY KEY AUTO_INCREMENT,
  stub CHAR(1) NOT NULL UNIQUE
) ENGINE=MyISAM ;

-- 分别设置初始值
INSERT INTO test.`ID_USER` (id, stub) VALUES (1000000, 'x');
INSERT INTO test.`ID_USER` (id, stub) VALUES (1000001, 'x');

-- 参考：https://www.cnblogs.com/olinux/p/6518766.html
-- 设置自增偏移量及步长(可以在建表时指定，这里不设置)
SET @@auto_increment_increment=2;
-- 一个表设置为1、一个表设置为2
SET @@auto_increment_offset=2;

-- 查看自增步长
SHOW VARIABLES LIKE 'auto_inc%';

-- 在一个事务里执行如下语句，得到自增ID (REPLACE需要记录事先存在，所以可以手动先插入一条)
REPLACE INTO test.ID_USER (stub) VALUES ('x');
SELECT LAST_INSERT_ID();