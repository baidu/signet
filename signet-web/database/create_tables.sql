CREATE DATABASE /*!32312 IF NOT EXISTS*/`signet_online` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `signet_online`;

/*Table structure for table `tb_auto` */

DROP TABLE IF EXISTS `tb_auto`;

CREATE TABLE `tb_auto` (
  `auto_id` int(11) DEFAULT NULL COMMENT '自动化id',
  `auto_name` varchar(30) DEFAULT NULL COMMENT '自动化name'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_card` */

DROP TABLE IF EXISTS `tb_card`;

CREATE TABLE `tb_card` (
  `card_id` int(11) NOT NULL COMMENT '卡片编号',
  `title` varchar(50) DEFAULT NULL,
  `text` text,
  `iteration` int(11) DEFAULT NULL COMMENT '迭代数',
  `project_id` int(11) NOT NULL COMMENT '空间id',
  `is_edit` tinyint(1) DEFAULT NULL COMMENT '是否正在被编，1:正在编辑，0:未被编辑',
  `start_edit_time` datetime DEFAULT NULL COMMENT '开始编辑的时间',
  `edit_user_name` varchar(50) DEFAULT 'temp' COMMENT '正在编辑卡片的用户名',
  `appraise_flag` tinyint(2) DEFAULT '0',
  `is_update_today` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`card_id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_card_status_conf` */

DROP TABLE IF EXISTS `tb_card_status_conf`;

CREATE TABLE `tb_card_status_conf` (
  `id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_log` */

DROP TABLE IF EXISTS `tb_log`;

CREATE TABLE `tb_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(100) DEFAULT NULL,
  `access_date` varchar(25) NOT NULL,
  `action` varchar(1024) DEFAULT NULL,
  `space_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1655925 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_log_copy` */

DROP TABLE IF EXISTS `tb_log_copy`;

CREATE TABLE `tb_log_copy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(100) DEFAULT NULL,
  `access_date` varchar(25) NOT NULL,
  `action` varchar(1024) DEFAULT NULL,
  `space_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=663319 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_mult_role` */

DROP TABLE IF EXISTS `tb_mult_role`;

CREATE TABLE `tb_mult_role` (
  `id` int(10) unsigned NOT NULL COMMENT 'spaceid，存在表示多角色签章，不存在表示单选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_node` */

DROP TABLE IF EXISTS `tb_node`;

CREATE TABLE `tb_node` (
  `node_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '节点id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父节点id',
  `auto_sign_type` int(11) DEFAULT NULL COMMENT '自动化签章类型：0、未签章，1、单元测试，2、集成测试，4、系统测试，3、单元+集成，6、集成+系统，5、单元+系统，7、单元+集成+系统',
  `role_sign_id` varchar(256) DEFAULT NULL COMMENT '角色签章id，0表示未签章',
  `node_text` text COMMENT '节点内容',
  `test_case_name` text COMMENT '自动化case名',
  `risk` int(11) DEFAULT NULL COMMENT '风险内容',
  `is_care` tinyint(1) DEFAULT NULL COMMENT '是否需要测试时特别关注',
  `story_id` int(11) DEFAULT NULL COMMENT 'story id',
  `project_id` int(11) DEFAULT NULL COMMENT '项目id',
  `seq` int(10) DEFAULT '-1' COMMENT '节点顺序',
  `create_person` varchar(256) DEFAULT NULL,
  `remark_flag` tinyint(2) DEFAULT '0',
  `is_retest` tinyint(2) DEFAULT '0' COMMENT '0未回归 1已回归',
  `appraise_flag` tinyint(2) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`node_id`),
  KEY `search` (`project_id`,`story_id`,`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=241107 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_node_appraise` */

DROP TABLE IF EXISTS `tb_node_appraise`;

CREATE TABLE `tb_node_appraise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `space_id` int(11) NOT NULL,
  `story_id` int(11) NOT NULL,
  `node_id` int(11) NOT NULL,
  `can_not_understand` tinyint(1) DEFAULT '0' COMMENT '看不明白',
  `is_mistake` tinyint(1) DEFAULT '0' COMMENT '有错误',
  `has_ambiguity` tinyint(1) DEFAULT '0' COMMENT '有二义性',
  `not_concise` tinyint(1) DEFAULT '0' COMMENT '不简洁',
  `good_case` tinyint(1) DEFAULT NULL COMMENT '好case',
  `bug_info` varchar(50) DEFAULT NULL,
  `temp1` tinyint(1) DEFAULT NULL,
  `temp2` tinyint(1) DEFAULT NULL,
  `temp3` tinyint(1) DEFAULT NULL,
  `temp4` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`space_id`,`story_id`,`node_id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `tb_node_remark` */

DROP TABLE IF EXISTS `tb_node_remark`;

CREATE TABLE `tb_node_remark` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `node_id` int(10) unsigned NOT NULL COMMENT 'node_id',
  `user` varchar(32) NOT NULL COMMENT '添加人',
  `content` text COMMENT '内容',
  `add_date` varchar(32) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1339 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_project` */

DROP TABLE IF EXISTS `tb_project`;

CREATE TABLE `tb_project` (
  `project_id` int(11) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `project_desc` varchar(100) DEFAULT NULL,
  `open_date` varchar(100) DEFAULT NULL,
  `svn` varchar(512) DEFAULT NULL,
  `is_crm` tinyint(1) DEFAULT NULL COMMENT '1:crm,2:fc,0未知'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_project_property` */

DROP TABLE IF EXISTS `tb_project_property`;

CREATE TABLE `tb_project_property` (
  `id` int(10) unsigned NOT NULL,
  `property` varchar(32) NOT NULL DEFAULT '',
  `value` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`,`property`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_sign_type` int(11) DEFAULT NULL COMMENT '角色签章类型：1、PM，2、RD，3、QA',
  `name` varchar(32) DEFAULT NULL COMMENT '人名',
  `project_id` int(11) DEFAULT NULL COMMENT '项目id',
  `role_tag` varchar(50) NOT NULL COMMENT '角色唯一标识（文件名）',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1826 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_signet_date` */

DROP TABLE IF EXISTS `tb_signet_date`;

CREATE TABLE `tb_signet_date` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `space_id` int(10) unsigned NOT NULL COMMENT '空间id',
  `story_id` int(10) unsigned NOT NULL COMMENT 'story id',
  `node_id` int(10) unsigned NOT NULL COMMENT 'node_id',
  `pre_role_id` varchar(256) DEFAULT NULL COMMENT '改变前role_id',
  `after_role_id` varchar(256) DEFAULT NULL COMMENT '改变后role_id',
  `signet_date` varchar(20) NOT NULL COMMENT '签章时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76984 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_story_appraise` */

DROP TABLE IF EXISTS `tb_story_appraise`;

CREATE TABLE `tb_story_appraise` (
  `space_id` int(11) NOT NULL,
  `story_id` int(11) NOT NULL,
  `structure_is_not_clear` tinyint(1) DEFAULT '0' COMMENT '结构不清晰',
  `too_subtle` tinyint(1) DEFAULT '0' COMMENT '粒度太细',
  `unsubtle` tinyint(1) DEFAULT '0' COMMENT '粒度太粗',
  `not_concise` tinyint(1) DEFAULT '0' COMMENT '不简洁',
  `unusual_branch_consider_not_enough` tinyint(1) DEFAULT '0' COMMENT '异常分支考虑不足',
  `dependence_module_consider_not_enough` tinyint(1) DEFAULT '0' COMMENT '与相关模块的关联考虑不足',
  `good_case` tinyint(1) DEFAULT NULL COMMENT '清洗易懂',
  `rd_find_problem_num` int(11) DEFAULT NULL COMMENT 'RD自测发现问题数',
  `temp2` tinyint(1) DEFAULT NULL,
  `temp3` tinyint(1) DEFAULT NULL,
  `temp4` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`space_id`,`story_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `tb_story_socre` */

DROP TABLE IF EXISTS `tb_story_socre`;

CREATE TABLE `tb_story_socre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `space_id` int(11) NOT NULL,
  `story_id` int(11) NOT NULL,
  `structure_is_not_clear` tinyint(1) DEFAULT '0' COMMENT '结构不清晰',
  `too_subtle` tinyint(1) DEFAULT '0' COMMENT '粒度太细',
  `unsubtle` tinyint(1) DEFAULT '0' COMMENT '粒度太粗',
  `not_concise` tinyint(1) DEFAULT '0' COMMENT '不简洁',
  `unusual_branch_consider_not_enough` tinyint(1) DEFAULT '0' COMMENT '异常分支考虑不足',
  `dependence_module_consider_not_enough` tinyint(1) DEFAULT '0' COMMENT '与相关模块的关联考虑不足',
  `good_case` tinyint(1) DEFAULT NULL COMMENT '清晰易懂',
  `can_not_understand_num` int(11) DEFAULT NULL COMMENT '看不明白的节点个数',
  `is_mistake_num` int(11) DEFAULT NULL COMMENT '有错误的节点个数',
  `has_ambiguity_num` int(11) DEFAULT NULL COMMENT '有二义性的节点个数',
  `not_concise_node_num` int(11) DEFAULT NULL COMMENT '不简洁的节点个数',
  `bug_num` int(11) DEFAULT NULL COMMENT 'bug数',
  `long_node_num` int(11) DEFAULT NULL COMMENT '长节点个数',
  `match_percent` int(11) DEFAULT NULL COMMENT '覆盖需求比例',
  `good_case_num` int(11) DEFAULT NULL COMMENT 'good case 个数',
  `score` int(4) DEFAULT NULL COMMENT '评分',
  `date` date NOT NULL COMMENT '时间',
  `rd_find_problem_num` int(11) DEFAULT NULL COMMENT 'RD自测发现问题数',
  `temp2` tinyint(1) DEFAULT NULL,
  `temp3` tinyint(1) DEFAULT NULL,
  `temp4` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_idspacestorydate` (`id`,`space_id`,`story_id`,`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22267 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `tb_submit` */

DROP TABLE IF EXISTS `tb_submit`;

CREATE TABLE `tb_submit` (
  `project_id` int(10) NOT NULL,
  `story_id` int(10) NOT NULL,
  PRIMARY KEY (`project_id`,`story_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_temp` */

DROP TABLE IF EXISTS `tb_temp`;

CREATE TABLE `tb_temp` (
  `id` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `access_date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tb_user_sprint_config` */

DROP TABLE IF EXISTS `tb_user_sprint_config`;

CREATE TABLE `tb_user_sprint_config` (
  `user` varchar(32) NOT NULL,
  `space_id` int(10) unsigned NOT NULL,
  `sprint` varchar(256) NOT NULL,
  PRIMARY KEY (`user`,`space_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tr_user_project` */

DROP TABLE IF EXISTS `tr_user_project`;

CREATE TABLE `tr_user_project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(20) DEFAULT NULL,
  `project_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
