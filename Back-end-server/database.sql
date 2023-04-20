/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 5.7.24 : Database - fuchuang
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fuchuang` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `HuiJiZhang`;

/*Table structure for table `fuchuang_user` */

DROP TABLE IF EXISTS `fuchuang_user`;

CREATE TABLE `fuchuang_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `phone_number` varchar(11) DEFAULT NULL COMMENT '电话',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `visible` bit(1) DEFAULT b'1' COMMENT '逻辑删除标志 1存在 0删除',
  `avatar_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Table structure for table `money_record` */

DROP TABLE IF EXISTS `money_record`;

CREATE TABLE `money_record` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(256) CHARACTER SET utf8 NOT NULL COMMENT '用户id',
  `type_id` bigint(20) NOT NULL COMMENT '记录类型id',
  `money` decimal(10,2) DEFAULT NULL COMMENT '记录金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `seller` varchar(20) DEFAULT NULL COMMENT '商家',
  `visible` bit(1) DEFAULT b'1' COMMENT '1存在 0删除',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '类型的父类id',
  `info` varchar(256) DEFAULT NULL COMMENT '拓展信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户记账表';

/*Table structure for table `money_record_file` */

DROP TABLE IF EXISTS `money_record_file`;

CREATE TABLE `money_record_file` (
  `id` bigint(20) NOT NULL,
  `record_id` bigint(20) NOT NULL,
  `url` varchar(256) NOT NULL,
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='记录附件表';

/*Table structure for table `money_type` */

DROP TABLE IF EXISTS `money_type`;

CREATE TABLE `money_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(20) NOT NULL,
  `contents_type` char(1) DEFAULT '1' COMMENT '0为目录 1为类型',
  `visible` bit(1) DEFAULT b'1' COMMENT '0为删除 1存在',
  `record_type` char(1) DEFAULT '1' COMMENT '0为收入 1为支出',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父类别 0代表没有',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='金额类型表';

/*Table structure for table `pay_budget` */

DROP TABLE IF EXISTS `pay_budget`;

CREATE TABLE `pay_budget` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `type_budget` decimal(10,2) DEFAULT NULL COMMENT '该类型预算',
  `type_id` bigint(20) DEFAULT NULL COMMENT '类型id',
  `all_budget` decimal(10,2) DEFAULT NULL COMMENT '所有类型预算之和',
  `month_year` bit(1) DEFAULT b'1' COMMENT '月预算还是年 默认1为月',
  `visible` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pay_budget_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `relationship_user_type` */

DROP TABLE IF EXISTS `relationship_user_type`;

CREATE TABLE `relationship_user_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `type_name` varchar(20) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL COMMENT '目录类型id 0代表没有',
  `flag` bit(1) DEFAULT b'1' COMMENT 'true为新增类型 否则删除类型',
  `visible` bit(1) DEFAULT b'1' COMMENT '可视标志',
  `record_type` char(1) DEFAULT '' COMMENT '收入或支出 默认1为支出',
  `contents_type` char(1) DEFAULT '1' COMMENT '0为目录 1为子类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` varchar(256) NOT NULL,
  `username` varchar(256) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user_account` */

DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `userId` bigint(20) NOT NULL,
  `accountId` bigint(20) NOT NULL,
  `accountName` varchar(20) DEFAULT NULL,
  `info` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user_budget` */

DROP TABLE IF EXISTS `user_budget`;

CREATE TABLE `user_budget` (
  `user_id` bigint(20) NOT NULL,
  `month_budget` decimal(10,2) NOT NULL,
  `year_budget` decimal(10,2) NOT NULL,
  UNIQUE KEY `user_budget_user_id_uindex` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user_files` */

DROP TABLE IF EXISTS `user_files`;

CREATE TABLE `user_files` (
  `id` varchar(256) CHARACTER SET latin1 NOT NULL,
  `url` varchar(256) CHARACTER SET latin1 NOT NULL COMMENT '文件路径',
  `user_id` varchar(256) CHARACTER SET latin1 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `visble` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1为软删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `userid_wxopenid` */

DROP TABLE IF EXISTS `userid_wxopenid`;

CREATE TABLE `userid_wxopenid` (
  `user_id` bigint(20) NOT NULL,
  `open_id` varchar(200) NOT NULL COMMENT '微信openId',
  `visible` bit(1) DEFAULT b'1',
  UNIQUE KEY `userId_wxOpenId_open_id_uindex` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
