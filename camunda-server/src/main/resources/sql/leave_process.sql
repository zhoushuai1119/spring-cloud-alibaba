CREATE TABLE `leave_process` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键自增ID',
  `apply_user_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '申请人ID',
  `start_time` date NOT NULL COMMENT '请假开始时间',
  `end_time` date NOT NULL COMMENT '请假结束时间',
  `leave_days` int NOT NULL COMMENT '请假天数',
  `reason` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '请假理由',
  `type` int NOT NULL COMMENT '请假类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='请求流程';