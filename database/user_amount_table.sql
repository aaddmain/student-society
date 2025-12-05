-- 创建 user_amount 表，如果不存在
CREATE TABLE IF NOT EXISTS `user_amount` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `amount` BIGINT DEFAULT 0 COMMENT '交易金额（以分为单位）',
  `balance` BIGINT DEFAULT 0 COMMENT '账户余额（以分为单位）',
  `state` INT DEFAULT 1 COMMENT '账户状态：0-禁用，1-启用，2-冻结',
  `is_deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `create_by` BIGINT DEFAULT 1 COMMENT '创建者ID',
  `update_by` BIGINT DEFAULT 1 COMMENT '更新者ID',
  `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户信息表';

-- 如果表已存在，确保字段一致性
ALTER TABLE `user_amount` 
  MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  MODIFY COLUMN `user_id` BIGINT NOT NULL COMMENT '用户ID',
  MODIFY COLUMN `amount` BIGINT DEFAULT 0 COMMENT '交易金额（以分为单位）',
  MODIFY COLUMN `balance` BIGINT DEFAULT 0 COMMENT '账户余额（以分为单位）',
  MODIFY COLUMN `state` INT DEFAULT 1 COMMENT '账户状态：0-禁用，1-启用，2-冻结',
  MODIFY COLUMN `is_deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  MODIFY COLUMN `create_by` BIGINT DEFAULT 1 COMMENT '创建者ID',
  MODIFY COLUMN `update_by` BIGINT DEFAULT 1 COMMENT '更新者ID',
  MODIFY COLUMN `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  MODIFY COLUMN `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

-- 确保必要的索引存在
ALTER TABLE `user_amount` 
  ADD UNIQUE KEY IF NOT EXISTS `uk_user_id` (`user_id`),
  ADD KEY IF NOT EXISTS `idx_create_time` (`create_time`);