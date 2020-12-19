/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 19/12/2020 17:13:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容描述',
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `post_count` int(11) UNSIGNED DEFAULT 0 COMMENT '该分类的内容数量',
  `order_num` int(11) DEFAULT NULL COMMENT '排序编码',
  `parent_id` bigint(32) UNSIGNED DEFAULT NULL COMMENT '父级分类的ID',
  `meta_keywords` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO关键字',
  `meta_description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO描述内容',
  `created` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `modified` datetime(0) DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '提问', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);
INSERT INTO `category` VALUES (2, '分享', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);
INSERT INTO `category` VALUES (3, '讨论', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);
INSERT INTO `category` VALUES (4, '建议', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论的内容',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '回复的评论ID',
  `post_id` bigint(32) NOT NULL COMMENT '评论的内容ID',
  `user_id` bigint(32) NOT NULL COMMENT '评论的用户ID',
  `vote_up` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '“顶”的数量',
  `vote_down` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '“踩”的数量',
  `level` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '置顶等级',
  `status` tinyint(2) DEFAULT NULL COMMENT '评论的状态',
  `created` datetime(0) NOT NULL COMMENT '评论的时间',
  `modified` datetime(0) DEFAULT NULL COMMENT '评论的更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '第一个标题的评论', NULL, 1, 2, 0, 0, 0, NULL, '2020-12-16 11:03:07', '2020-12-16 11:03:10');
INSERT INTO `comment` VALUES (2, '第二个标题的评论', NULL, 2, 2, 0, 0, 0, NULL, '2020-12-16 11:07:39', '2020-12-16 11:07:42');
INSERT INTO `comment` VALUES (3, '1111111111的评论', NULL, 3, 3, 0, 0, 0, NULL, '2020-12-10 11:07:51', '2020-12-10 11:07:55');
INSERT INTO `comment` VALUES (4, '哈哈哈哈的评论', NULL, 4, 1, 0, 0, 0, NULL, '2020-12-17 11:08:11', '2020-12-17 11:08:14');
INSERT INTO `comment` VALUES (5, 'xixixii的评论', NULL, 5, 4, 0, 0, 0, NULL, '2020-12-16 11:08:45', '2020-12-16 11:08:48');
INSERT INTO `comment` VALUES (6, '限制。。。的评论', NULL, 6, 1, 0, 0, 0, NULL, '2020-12-16 15:15:44', '2020-12-16 15:15:47');
INSERT INTO `comment` VALUES (7, '限制。。。的评论1', NULL, 6, 2, 0, 0, 0, NULL, '2020-12-16 15:28:32', '2020-12-16 15:28:36');
INSERT INTO `comment` VALUES (8, '限制的评论2', NULL, 6, 3, 0, 0, 0, NULL, '2020-12-16 15:28:52', '2020-12-16 15:28:55');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `edit_mode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '编辑模式：html可视化，markdown ..',
  `category_id` bigint(32) DEFAULT NULL,
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户ID',
  `vote_up` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '支持人数',
  `vote_down` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '反对人数',
  `view_count` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '访问量',
  `comment_count` int(11) NOT NULL DEFAULT 0 COMMENT '评论数量',
  `recommend` tinyint(1) DEFAULT NULL COMMENT '是否为精华',
  `level` tinyint(2) NOT NULL DEFAULT 0 COMMENT '置顶等级',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态',
  `created` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `modified` datetime(0) DEFAULT NULL COMMENT '最后更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, '第一个标题', ' 内容一', '0', 1, 5, 0, 0, 5, 0, 0, 1, NULL, '2020-04-28 14:41:41', '2020-04-28 14:41:41');
INSERT INTO `post` VALUES (2, '标题二', '内容2', '0', 2, 4, 0, 0, 3, 0, 1, 1, NULL, '2020-04-28 14:55:16', '2020-04-28 14:55:16');
INSERT INTO `post` VALUES (3, '111111111111', '1222222222222222', '0', 3, 3, 0, 0, 1, 0, 0, 0, NULL, '2020-04-28 14:55:48', '2020-04-28 14:55:48');
INSERT INTO `post` VALUES (4, '哈哈哈哈哈哈', 'i嘻嘻嘻嘻嘻', '0', 4, 2, 0, 0, 6, 1, 1, 0, NULL, '2020-12-15 10:59:00', '2020-12-15 10:59:05');
INSERT INTO `post` VALUES (5, 'xixixixix', '嘿嘿嘿额hiface[微笑] ', '0', 1, 1, 0, 0, 6, 2, 1, 0, NULL, '2020-12-16 10:38:09', '2020-12-16 10:38:11');
INSERT INTO `post` VALUES (6, '限制9岁孤女高消费，河南一法院道歉：我们错了标题', '近日，金水法院执行人员对一名九岁儿童采取限制高消费的执行措施，引发广大网友高度关注。12月16日凌晨，@金水法院 官微发布《致歉声明》，郑州市金水区人民法院表示，立即对案件进行了复查，郑重地对大家说一声：“我们错了！”金水法院指出，对未成年人发出限制消费令不符合相关立法精神和善意文明执行理念，是错误的。该院已依法解除了限制消费令。以下为致歉声明原文：\r\n\r\n近日，我院执行人员对一名九岁儿童采取限制高消费的执行措施，引发广大网友高度关注。我们立即对案件进行了复查，现在，我们郑重地对大家说一声：我们错了！对未成年人发出限制消费令不符合相关立法精神和善意文明执行理念，是错误的。我院已依法解除了限制消费令。我院就此错误向当事人和网友诚恳道歉！\r\n\r\n儿童健康成长高于一切！我们个别执行人员机械司法，造成了很不好的社会影响，在今后工作中，我们将认真汲取教训，正确理解立法和司法解释精神，牢固树立审慎、善意、文明的执行理念，把保障未成年人健康成长放在最优先的位置，追求情、理、法相统一的司法目标，公平保护申请执行人和被执行人的合法权益。\r\n\r\n由衷感谢并诚恳欢迎广大网友的监督和关心。\r\n\r\n郑州市金水区人民法院\r\n\r\n\r\n新闻多看点\r\n\r\n2012年，一名九岁儿童的父亲陈东杀害了这名儿童的母亲和外婆，随后将夫妻共同拥有的房子以68万的价格转让给王某。王某付给陈东55万的首付款后入住。但是，没有等到房子过户，陈东就被抓获并判处死刑。2019年，法院判决认定房屋转让合同无效，王某应该返还房屋，这名儿童归还陈东的卖房所得55万元。王某后将房产继承人告上法庭，要求判令合同无效，归还卖房55万元，获法院支持。因这名儿童未归还，法院向其发布限制消费令。\r\n\r\n（来源：津云）', '0', 1, 1, 0, 0, 5, 3, 1, 1, NULL, '2020-12-16 10:39:38', '2020-12-16 10:39:41');
INSERT INTO `post` VALUES (8, '知情人士透露美国将在周五将数十家中企列入黑名单，汪文斌回应', '【环球时报-环球网报道 记者李司坤】路透社当地时间周四报道，有知情人士透露，美国将在周五将数十家中国企业列入贸易黑名单，其中包括中国最大的芯片制造商中芯国际(SMIC)。路透社指出，这一此前从未被报道过的举动，被视为美国总统特朗普巩固其对华强硬政策遗产的最新努力。汪文斌（资料图）\r\n\r\n在18日举行的外交部例行记者会上，有来自路透社的记者就此事提问，对此，发言人汪文斌表示，如果你说的消息属实，这将是美国动用国家力量打压中国企业的又一力证，中方对此坚决反对。\r\n\r\n汪文斌指出，美方将经贸问题政治化，违背其一贯标榜的市场经济和公平竞争原则，违反国际贸易规则，不仅损害中国企业的合法权益，也不符合美国企业的利益，将严重干扰两国乃至全球正常的科技交流和贸易往来，对全球产业链、供应链、价值链造成破坏。“我们敦促美方停止无理打压外国企业的错误行为，中方将继续采取必要措施，维护中国企业的正当权益。”汪文斌表示。', '0', 1, 1, 0, 0, 2, 2, 1, 0, NULL, '2020-12-18 16:58:27', '2020-12-18 16:58:30');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮件',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机电话',
  `point` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '积分',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个性签名',
  `gender` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `wechat` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信号',
  `vip_level` int(32) DEFAULT NULL COMMENT 'vip等级',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
  `post_count` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '内容数量',
  `comment_count` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论数量',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '状态',
  `lasted` datetime(0) DEFAULT NULL COMMENT '最后的登陆时间',
  `created` datetime(0) NOT NULL COMMENT '创建日期',
  `modified` datetime(0) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test00465', '96e79218965eb72c92a549dd5a330112', '11111@qq.com', NULL, 0, 'hahahh', '0', NULL, 0, NULL, '/upload/avatar/avatar_1.jpg', 0, 0, 0, '2020-12-19 09:08:32', '2020-04-28 14:37:24', NULL);
INSERT INTO `user` VALUES (2, 'test0000', '96e79218965eb72c92a549dd5a330112', '1111@qq.com', NULL, 0, 'hahahah', '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, 0, '2020-12-19 02:31:09', '2020-04-28 14:45:07', NULL);
INSERT INTO `user` VALUES (3, 'test004', '96e79218965eb72c92a549dd5a330112', '144d11@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, 0, NULL, '2020-04-28 14:48:26', NULL);
INSERT INTO `user` VALUES (4, 'test005', '96e79218965eb72c92a549dd5a330112', '144d15@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, 0, NULL, '2020-04-28 14:48:26', NULL);
INSERT INTO `user` VALUES (5, 'test00756', '96e79218965eb72c92a549dd5a330112', '45454541@qq.com', NULL, 0, NULL, '0', NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, 0, NULL, '2020-04-28 14:53:49', NULL);
INSERT INTO `user` VALUES (6, 'test00789', 'e10adc3949ba59abbe56e057f20f883e', '11111232@qq.com', NULL, 0, NULL, NULL, NULL, 0, NULL, '/res/images/avatar/default.png', 0, 0, 0, NULL, '2020-12-17 11:57:17', NULL);

-- ----------------------------
-- Table structure for user_action
-- ----------------------------
DROP TABLE IF EXISTS `user_action`;
CREATE TABLE `user_action`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
  `action` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '动作类型',
  `point` int(11) DEFAULT NULL COMMENT '得分',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的帖子ID',
  `comment_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的评论ID',
  `created` datetime(0) DEFAULT NULL,
  `modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  `post_user_id` bigint(20) NOT NULL,
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collection
-- ----------------------------
INSERT INTO `user_collection` VALUES (1, 1, 1, 5, '2020-12-18 17:06:55', '2020-12-18 17:07:01');
INSERT INTO `user_collection` VALUES (2, 1, 2, 4, '2020-12-18 17:07:20', '2020-12-18 17:07:22');
INSERT INTO `user_collection` VALUES (3, 1, 3, 3, '2020-12-18 17:09:07', '2020-12-18 17:09:10');
INSERT INTO `user_collection` VALUES (4, 1, 4, 2, '2020-12-18 17:09:26', '2020-12-18 17:09:28');

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(20) NOT NULL COMMENT '发送消息的用户ID',
  `to_user_id` bigint(20) NOT NULL COMMENT '接收消息的用户ID',
  `post_id` bigint(20) DEFAULT NULL COMMENT '消息可能关联的帖子',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '消息可能关联的评论',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `type` tinyint(2) DEFAULT NULL COMMENT '消息类型',
  `created` datetime(0) NOT NULL,
  `modified` datetime(0) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_message
-- ----------------------------
INSERT INTO `user_message` VALUES (1, 1, 1, 1, 1, 'hhhh ', 1, '2020-12-18 17:33:50', '2020-12-18 17:33:53', 1);
INSERT INTO `user_message` VALUES (2, 1, 1, NULL, NULL, '系统消息', 0, '2020-12-18 17:34:43', '2020-12-18 17:34:46', 0);
INSERT INTO `user_message` VALUES (3, 3, 1, 3, 3, '你好AA？？！', 1, '2020-12-18 18:03:23', '2020-12-18 18:03:25', NULL);
INSERT INTO `user_message` VALUES (4, 4, 1, 4, 4, '芜湖！@', 2, '2020-12-18 18:05:26', '2020-12-18 18:05:29', 0);

SET FOREIGN_KEY_CHECKS = 1;
