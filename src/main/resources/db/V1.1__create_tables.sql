/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云-youyou 数据库
 Source Server Type    : MySQL
 Source Server Version : 50634
 Source Host           : rds8a08c30s41zzv710xo.mysql.rds.aliyuncs.com:3306
 Source Schema         : youyou

 Target Server Type    : MySQL
 Target Server Version : 50634
 File Encoding         : 65001

 Date: 18/08/2017 13:00:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for business_log
-- ----------------------------
CREATE TABLE `business_log` (
  `id` varchar(40) CHARACTER SET utf8 NOT NULL,
  `operator` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `clazz` varchar(255) CHARACTER SET utf8 NOT NULL,
  `method` varchar(255) CHARACTER SET utf8 NOT NULL,
  `method_description` varchar(255) CHARACTER SET utf8 NOT NULL,
  `success` tinyint(4) NOT NULL COMMENT '方法是否成功运行',
  `exception_string` text COLLATE utf8_bin COMMENT '方法运行出错，抛出的exception堆栈转换成的string',
  `args` text CHARACTER SET utf8 NOT NULL,
  `time_consuming` bigint NOT NULL  DEFAULT 0 COMMENT '方法调用耗时（毫秒）',
  `remote_ip` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `client_os` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `client_browser` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `browser_version` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `client_device_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='业务方法调用日志';



CREATE TABLE `user` (
  `name` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `passwd` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '密码',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `user` VALUES ('admin@me.com', '1');


CREATE TABLE `cns`.`event` (
  `id` varchar(36) NOT NULL COMMENT 'id',
  `name` varchar(256) NOT NULL COMMENT '事件名字',
  `memo` text NULL COMMENT '事件描述',
  `notice_content` text NOT NULL COMMENT '提醒内容',
  `act_date` date NOT NULL COMMENT '提醒日',
  `repeat_type` integer(2) NOT NULL COMMENT '重复提醒类型。0 到期提醒一次  1每月提醒 2每周提醒',
  `notice` tinyint(1) NOT NULL COMMENT '是否需要提醒 0 不需要 1 需要',
  `notice_mail` text NOT NULL COMMENT '提醒对象的 mail 地址 , 以 ，分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT = '提醒事件表';


# 系统参数表
CREATE TABLE `system_configure` (
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '参数名字',
  `value` text COLLATE utf8_bin NOT NULL COMMENT '参数值',
  `description` text COLLATE utf8_bin NOT NULL COMMENT '描述',
  `sort_no` INT NOT NULL  COMMENT '序号',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数表';

-- ----------------------------
-- Records of system_configure
-- ----------------------------
BEGIN;
INSERT INTO `system_configure` VALUES ('cns_subject', 'CNS-对[%s]进行的提醒', '提醒邮件的标题',1);
INSERT INTO `system_configure` VALUES ('banner_message', '多谢你如此精彩耀眼，做我平淡岁月里星辰', '用户的 banner 信息',2);

COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

# 客户基本信息
CREATE TABLE `customer` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '客户名称',
  `contacts` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '联系手机号',
  `tel` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '联系座机',
  `address` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '公司地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='客户信息表';

SET FOREIGN_KEY_CHECKS = 1;

#合同信息表
CREATE TABLE `cns`.`contract`  (
  `id` varchar(36) NOT NULL COMMENT '合同 ID',
  `name` varchar(256) NOT NULL COMMENT '合同名字',
  `description` text NULL COMMENT '合同描述',
  `customer_id` varchar(36) NOT NULL COMMENT '所属客户 ID',
  `amount` float(10, 2)  COMMENT  '合同金额',
  `begin_date` DATE NOT NULL  COMMENT '合同开始日期',
  `end_date` DATE NOT NULL  COMMENT '合同结束日期',
  PRIMARY KEY (`id`)
) CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT='合同信息表';

# 收款信息表
CREATE TABLE `cns`.`gather_info`  (
  `id` varchar(36) NOT NULL COMMENT 'id',
  `contract_id` varchar(36) NOT NULL COMMENT '对应的合同ID',
  `name` varchar(256) NOT NULL COMMENT '收款信息的名字',
  `description` text NULL COMMENT '描述',
  `amount` float(10, 2) NOT NULL COMMENT '收款金额',
  `gather_date` date NOT NULL COMMENT '收款日期',
  `notice_date` date NULL COMMENT '提醒日期',
  `notice_content` TEXT COMMENT '提醒内容',
  `notice_to` TEXT COMMENT '提醒对象',
  `notice` tinyint(0) NOT NULL COMMENT '是否需要提醒 0 不需要 1 需要',
  `gathered` tinyint(0) NOT NULL COMMENT '是否已经收款完成 ！未收到 是 0， 已经收讫是 1 ',
  `gathered_date` date NULL COMMENT '收讫的日期',
  PRIMARY KEY (`id`)
) CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT '收款信息表';
