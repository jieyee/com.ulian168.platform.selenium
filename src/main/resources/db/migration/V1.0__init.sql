--编排表
DROP TABLE IF EXISTS `arrangeinfo`; 
CREATE TABLE `arrangeinfo`(
  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '',
  `jobid` varchar(50) DEFAULT NULL COMMENT '任务id',
  `serverip` varchar(50) DEFAULT NULL COMMENT '执行服务器ip',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `path` varchar(200) DEFAULT NULL COMMENT '地址',
  `type` varchar(15) DEFAULT NULL COMMENT '类型',
  `state` varchar(100) DEFAULT NULL COMMENT '状态 0执行成功 1执行中 2执行失败',
  `reportpath` varchar(100) DEFAULT NULL COMMENT '报告地址',
  `create_time` datetime default CURRENT_TIMESTAMP COMMENT '创建时间',         
  `last_update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
) ENGINE=INNODB DEFAULT CHARSET=utf8;

