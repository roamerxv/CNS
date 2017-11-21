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
  `id`                 VARCHAR(40)
                       CHARACTER SET utf8      NOT NULL,
  `operator`           VARCHAR(255)
                       COLLATE utf8_bin                  DEFAULT NULL,
  `clazz`              VARCHAR(255)
                       CHARACTER SET utf8      NOT NULL,
  `method`             VARCHAR(255)
                       CHARACTER SET utf8      NOT NULL,
  `method_description` VARCHAR(255)
                       CHARACTER SET utf8      NOT NULL,
  `success`            TINYINT(4)              NOT NULL
  COMMENT '方法是否成功运行',
  `exception_string`   TEXT COLLATE utf8_bin COMMENT '方法运行出错，抛出的exception堆栈转换成的string',
  `args`               TEXT CHARACTER SET utf8 NOT NULL,
  `time_consuming`     BIGINT                  NOT NULL  DEFAULT 0
  COMMENT '方法调用耗时（毫秒）',
  `remote_ip`          VARCHAR(32)
                       COLLATE utf8_bin                  DEFAULT NULL,
  `client_os`          VARCHAR(255)
                       COLLATE utf8_bin                  DEFAULT NULL,
  `client_browser`     VARCHAR(255)
                       COLLATE utf8_bin                  DEFAULT NULL,
  `browser_version`    VARCHAR(255)
                       COLLATE utf8_bin                  DEFAULT NULL,
  `client_device_type` VARCHAR(255)
                       COLLATE utf8_bin                  DEFAULT NULL,
  `created_at`         TIMESTAMP               NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC
  COMMENT ='业务方法调用日志';


# 用户信息表
CREATE TABLE `user` (
  `name`       VARCHAR(36)
               COLLATE utf8_bin NOT NULL
  COMMENT '用户名',
  `full_name`  VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '用户全名',
  `occupation` VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '职务',
  `company`    VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '公司名字',
  `phone`      VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '电话号码',
  `address`    VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '地址',
  `city`       VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '城市',
  `state`      VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '区县',
  `postcode`   VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '邮政编码',
  `linkedin`   VARCHAR(128)
               COLLATE utf8_bin
  COMMENT 'linkedin',
  `facebook`   VARCHAR(128)
               COLLATE utf8_bin
  COMMENT 'Facebook',
  `twitter`    VARCHAR(128)
               COLLATE utf8_bin
  COMMENT 'Twitter',
  `instagram`  VARCHAR(128)
               COLLATE utf8_bin
  COMMENT 'Instagram',
  `email`      VARCHAR(36)
               COLLATE utf8_bin
  COMMENT 'mail地址',
  `avatar`     VARCHAR(128)
               COLLATE utf8_bin
  COMMENT '头像文件 ID',
  `passwd`     VARCHAR(36)
               COLLATE utf8_bin
  COMMENT '密码',
  PRIMARY KEY (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('admin1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '63857555');
INSERT INTO `user` VALUES ('admin2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '63857555');
INSERT INTO `user` VALUES ('admin3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '63857555');
INSERT INTO `user` VALUES ('masa', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '63857555');
INSERT INTO `user` VALUES ('roamer', '徐泽宇', ' CTO', 'alcor', '15800392098', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'roamerxv@163.com', '', '1');
COMMIT;



CREATE TABLE `cns`.`event` (
  `id`             VARCHAR(36)  NOT NULL
  COMMENT 'id',
  `name`           VARCHAR(256) NOT NULL
  COMMENT '事件名字',
  `memo`           TEXT         NULL
  COMMENT '事件描述',
  `notice_content` TEXT         NOT NULL
  COMMENT '提醒内容',
  `act_date`       DATE         NOT NULL
  COMMENT '提醒日',
  `repeat_type`    INTEGER(2)   NOT NULL
  COMMENT '重复提醒类型。0 到期提醒一次  1每月提醒 2每周提醒',
  `notice`         TINYINT(1)   NOT NULL
  COMMENT '是否需要提醒 0 不需要 1 需要',
  `notice_mail`    TEXT         NOT NULL
  COMMENT '提醒对象的 mail 地址 , 以 ，分隔',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT = '提醒事件表';

# 系统参数表
CREATE TABLE `system_configure` (
  `name`        VARCHAR(32)
                COLLATE utf8_bin      NOT NULL
  COMMENT '参数名字',
  `value`       TEXT COLLATE utf8_bin NOT NULL
  COMMENT '参数值',
  `description` TEXT COLLATE utf8_bin NOT NULL
  COMMENT '描述',
  `sort_no`     INT                   NOT NULL
  COMMENT '序号',
  PRIMARY KEY (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='系统参数表';

-- ----------------------------
-- Records of system_configure
-- ----------------------------
BEGIN;
INSERT INTO `system_configure` VALUES ('cns_subject', 'CNS-对[%s]进行的提醒', '提醒邮件的标题', 1);
INSERT INTO `system_configure` VALUES ('cns_content', '<h1>!收款提醒!</h1>
<h2>{0}</h2><br/>
<h2>的合同-{1}</h1><br/>
<h2>对应的收款计划-{2}<h2><br/>
<h2>将于{3}进行收款.</h2><br/>
<h2>应收金额是: <font color="red">{4}</font></h2>', '提醒邮件的内容：{0} 对应客户名字,{1}对应合同名字，{1}对应收款计划名字，{2}对应收款日期，{3}对应收款金额', 2);
INSERT INTO `system_configure` VALUES ('cns_mail_to', 'Masa@dbond.net', "提醒邮件需要发往的邮箱.多个邮箱通过，分隔", 3);
INSERT INTO `system_configure` VALUES ('notice_date_offset', '1', "自动生成收款记录时，提醒日对应收款日提前的天数", 4);
INSERT INTO `system_configure` VALUES ('banner_message', '多谢你如此精彩耀眼，做我平淡岁月里星辰', '用户的 banner 信息', 5);


COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

# 客户基本信息
CREATE TABLE `customer` (
  `id`       VARCHAR(36)
             COLLATE utf8_bin NOT NULL
  COMMENT 'id',
  `name`     VARCHAR(256)
             COLLATE utf8_bin NOT NULL
  COMMENT '客户名称',
  `contacts` VARCHAR(256)
             COLLATE utf8_bin DEFAULT NULL
  COMMENT '联系人',
  `mobile`   VARCHAR(36)
             COLLATE utf8_bin DEFAULT NULL
  COMMENT '联系手机号',
  `tel`      VARCHAR(36)
             COLLATE utf8_bin DEFAULT NULL
  COMMENT '联系座机',
  `type_id`  VARCHAR(36)
             COLLATE utf8_bin NOT NULL
  COMMENT '客户分类 ID',
  `address`  VARCHAR(256)
             COLLATE utf8_bin DEFAULT NULL
  COMMENT '公司地址',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  COMMENT ='客户信息表';

SET FOREIGN_KEY_CHECKS = 1;

#合同信息表
CREATE TABLE `cns`.`contract` (
  `id`                VARCHAR(36)  NOT NULL
  COMMENT '合同 ID',
  `name`              VARCHAR(256) NOT NULL
  COMMENT '合同名字',
  `description`       TEXT         NULL
  COMMENT '合同描述',
  `customer_id`       VARCHAR(36)  NOT NULL
  COMMENT '所属客户 ID',
  `amount`            FLOAT(10, 2) COMMENT '合同金额',
  `first_gather_date` DATE         NOT NULL
  COMMENT '首次付款日期',
  `gather_interval`   INTEGER      NOT NULL DEFAULT 0
  COMMENT '每隔多少个月产生下一个收款计划单',
  `gather_count`      INTEGER      NOT NULL DEFAULT 0
  COMMENT '一个合同分多少次收款',
  `begin_date`        DATE         NOT NULL
  COMMENT '合同开始日期',
  `end_date`          DATE         NOT NULL
  COMMENT '合同结束日期',
  PRIMARY KEY (`id`)
)
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  COMMENT ='合同信息表';

# 收款信息表
CREATE TABLE `cns`.`gather_info` (
  `id`             VARCHAR(36)  NOT NULL
  COMMENT 'id',
  `contract_id`    VARCHAR(36)  NOT NULL
  COMMENT '对应的合同ID',
  `name`           VARCHAR(256) NOT NULL
  COMMENT '收款信息的名字',
  `description`    TEXT         NULL
  COMMENT '描述',
  `amount`         FLOAT(10, 2) NOT NULL
  COMMENT '收款金额',
  `gather_date`    DATE         NOT NULL
  COMMENT '收款日期',
  `notice_date`    DATE         NULL
  COMMENT '提醒日期',
  `notice_content` TEXT COMMENT '提醒内容',
  `notice_to`      TEXT COMMENT '提醒对象',
  `notice`         TINYINT(0)   NOT NULL
  COMMENT '是否需要提醒 0 不需要 1 需要',
  `gathered`       TINYINT(0)   NOT NULL
  COMMENT '是否已经收款完成 ！未收到 是 0， 已经收讫是 1 ',
  `gathered_date`  DATE         NULL
  COMMENT '收讫的日期',
  PRIMARY KEY (`id`)
)
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  COMMENT '收款信息表';

# 客户分类信息表

CREATE TABLE `cns`.`customer_type` (
  `id`   VARCHAR(36)  NOT NULL
  COMMENT '客户分类的 ID',
  `name` VARCHAR(128) NOT NULL
  COMMENT '分类名称',
  `p_id` VARCHAR(36)  NOT NULL
  COMMENT '父分类 ID，id 为 0 的时候代表 是一级分类',
  PRIMARY KEY (`id`)
)
  CHARACTER SET = utf8
  COLLATE = utf8_bin
  COMMENT = '客户分类信息表';

INSERT INTO `customer_type` VALUES ('23737aed-6f80-46d8-9620-762acef80680', 'RS-A', '0');
INSERT INTO `customer_type`
VALUES ('3a08cb62-3db2-4701-9733-a4f01f048d66', 'A2', '23737aed-6f80-46d8-9620-762acef80680');
INSERT INTO `customer_type`
VALUES ('978d8266-762d-4b7d-a50e-d62b56d4a1d6', 'A1', '23737aed-6f80-46d8-9620-762acef80680');
INSERT INTO `customer_type` VALUES ('bb37ee03-49ec-46cc-985b-a625ecaf404e', 'RS-A', '0');
