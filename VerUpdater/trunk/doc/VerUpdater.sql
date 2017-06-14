/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.205
Source Server Version : 50173
Source Host           : 192.168.2.205:3306
Source Database       : verupdater

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2016-10-27 14:59:49
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mdi_fileinfo`
-- ----------------------------
DROP TABLE IF EXISTS `mdi_fileinfo`;
CREATE TABLE `mdi_fileinfo` (
  `FILE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `FILE_NAME` char(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '文件名',
  `URL_PATH` char(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '文件访问路径',
  `STATE` char(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT '01' COMMENT '状态(已使用：01、未使用：02)',
  `RES1` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`FILE_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='文件信息表';

-- ----------------------------
-- Records of mdi_fileinfo
-- ----------------------------
INSERT INTO mdi_fileinfo VALUES ('1', 'bus.png', '/jpg/bus.png', '01', null, null, null, null, '2016-10-25 18:23:24');
INSERT INTO mdi_fileinfo VALUES ('2', 'bus1.jpg', '/jpg/bus1.jpg', '01', null, null, null, null, '2016-10-25 18:23:50');
INSERT INTO mdi_fileinfo VALUES ('3', 'bus2.jpg', '/jpg/bus2.jpg', '01', null, null, null, null, '2016-10-25 18:24:07');
INSERT INTO mdi_fileinfo VALUES ('4', 'bus3.jpg', '/jpg/bus3.jpg', '01', null, null, null, null, '2016-10-25 18:24:25');
INSERT INTO mdi_fileinfo VALUES ('5', 'bus4.jpg', '/jpg/bus4.jpg', '01', null, null, null, null, '2016-10-25 18:25:28');
INSERT INTO mdi_fileinfo VALUES ('6', 'bus5.jpg', '/jpg/bus5.jpg', '01', null, null, null, null, '2016-10-25 18:25:52');
INSERT INTO mdi_fileinfo VALUES ('7', 'duona.png', '/jpg/duona.png', '01', null, null, null, null, '2016-10-25 18:28:43');
INSERT INTO mdi_fileinfo VALUES ('8', 'duona1.jpg', '/jpg/duona1.jpg', '01', null, null, null, null, '2016-10-25 18:29:03');
INSERT INTO mdi_fileinfo VALUES ('9', 'duona2.jpg', '/jpg/duona2.jpg', '01', null, null, null, null, '2016-10-27 15:00:05');
INSERT INTO mdi_fileinfo VALUES ('10', 'duona3.jpg', '/jpg/duona3.jpg', '01', null, null, null, null, '2016-10-25 18:29:46');
INSERT INTO mdi_fileinfo VALUES ('11', 'duona4.jpg', '/jpg/duona4.jpg', '01', null, null, null, null, '2016-10-25 18:30:09');
INSERT INTO mdi_fileinfo VALUES ('12', 'duona5.jpg', '/jpg/duona5.jpg', '01', null, null, null, null, '2016-10-25 18:30:26');
INSERT INTO mdi_fileinfo VALUES ('13', 'huahua.png', '/jpg/huahua.png', '01', null, null, null, null, '2016-10-25 18:23:24');
INSERT INTO mdi_fileinfo VALUES ('14', 'huahua1.jpg', '/jpg/huahua1.png', '01', null, null, null, null, '2016-10-27 14:19:37');
INSERT INTO mdi_fileinfo VALUES ('15', 'huahua2.jpg', '/jpg/huahua2.png', '01', null, null, null, null, '2016-10-27 14:19:41');
INSERT INTO mdi_fileinfo VALUES ('16', 'huahua3.jpg', '/jpg/huahua3.png', '01', null, null, null, null, '2016-10-27 14:19:45');
INSERT INTO mdi_fileinfo VALUES ('17', 'huahua4.jpg', '/jpg/huahua4.png', '01', null, null, null, null, '2016-10-27 14:19:48');
INSERT INTO mdi_fileinfo VALUES ('18', 'huahua5.jpg', '/jpg/huahua5.png', '01', null, null, null, null, '2016-10-27 14:19:56');
INSERT INTO mdi_fileinfo VALUES ('19', 'jiaoyu.png', '/jpg/jiaoyu.png', '01', null, null, null, null, '2016-10-25 18:23:24');
INSERT INTO mdi_fileinfo VALUES ('20', 'jiaoyu1.jpg', '/jpg/jiaoyu1.jpg', '01', null, null, null, null, '2016-10-25 18:23:50');
INSERT INTO mdi_fileinfo VALUES ('21', 'jiaoyu2.jpg', '/jpg/jiaoyu2.jpg', '01', null, null, null, null, '2016-10-25 18:24:07');
INSERT INTO mdi_fileinfo VALUES ('22', 'jiaoyu3.jpg', '/jpg/jiaoyu3.jpg', '01', null, null, null, null, '2016-10-25 18:24:25');
INSERT INTO mdi_fileinfo VALUES ('23', 'jiaoyu4.jpg', '/jpg/jiaoyu4.jpg', '01', null, null, null, null, '2016-10-25 18:25:28');
INSERT INTO mdi_fileinfo VALUES ('24', 'jiaoyu5.jpg', '/jpg/jiaoyu5.jpg', '01', null, null, null, null, '2016-10-25 18:25:52');
INSERT INTO mdi_fileinfo VALUES ('25', 'jiaoyu6.jpg', '/jpg/jiaoyu6.jpg', '01', null, null, null, null, '2016-10-25 18:25:52');

-- ----------------------------
-- Table structure for `usr_logininfo`
-- ----------------------------
DROP TABLE IF EXISTS `usr_logininfo`;
CREATE TABLE `usr_logininfo` (
  `USER_ID` char(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `SER_KEY` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '服务器密钥（获取用户Token的KEY）',
  `AUTH_TOKEN` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户Token',
  `DEV_ID` char(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '设备唯一标识',
  `TYPE` char(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '应用类型（后台管理：01、APP：02、网站：03、安卓：04、IOS：05）,',
  `IS_LOGGED` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '是否已登录',
  `LAST_LOGOUT_TIME` datetime DEFAULT NULL COMMENT '最后一次登出时间',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `RES1` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES5` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES6` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`USER_ID`,`TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='登录信息表';

-- ----------------------------
-- Records of usr_logininfo
-- ----------------------------

-- ----------------------------
-- Table structure for `usr_role`
-- ----------------------------
DROP TABLE IF EXISTS `usr_role`;
CREATE TABLE `usr_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID（APP用户：0，管理员：1）',
  `NAME` char(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色信息表（关联表是RoleAcc）';

-- ----------------------------
-- Records of usr_role
-- ----------------------------

-- ----------------------------
-- Table structure for `usr_roleacc`
-- ----------------------------
DROP TABLE IF EXISTS `usr_roleacc`;
CREATE TABLE `usr_roleacc` (
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  `ACC_ID` char(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '权限ID（ID规则：平台（2位）+模块（2位）+功能（2位）+子功能（2位）如：01010401）',
  `ACC_NAME` char(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限名称',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`ROLE_ID`,`ACC_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色权限关联表';

-- ----------------------------
-- Records of usr_roleacc
-- ----------------------------

-- ----------------------------
-- Table structure for `usr_user`
-- ----------------------------
DROP TABLE IF EXISTS `usr_user`;
CREATE TABLE `usr_user` (
  `USER_ID` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `ROLE_ID` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `REG_TYPE` char(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT '01' COMMENT '注册类型（手机号：01、邮箱：02、自定义：09）',
  `PWD` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `STATE` char(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT '01' COMMENT '状态（启用：01、冻结：02、已删除：03）',
  `RES1` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES5` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES6` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统用户信息表';

-- ----------------------------
-- Records of usr_user
-- ----------------------------

-- ----------------------------
-- Table structure for `usr_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `usr_userinfo`;
CREATE TABLE `usr_userinfo` (
  `USER_ID` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `USER_TYPE` char(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT '01' COMMENT '用户类型（企业：01，个人：02）',
  `NAME` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '姓名',
  `SEX` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '性别（男：01、女：02）',
  `MOBILE` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `ORG_NAME` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '组织名称',
  `ORG_TEL` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '组织电话',
  `ABOUT` text COLLATE utf8_unicode_ci COMMENT '简介',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES5` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES6` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表';

-- ----------------------------
-- Records of usr_userinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `ver_channel`
-- ----------------------------
DROP TABLE IF EXISTS `ver_channel`;
CREATE TABLE `ver_channel` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `CHNL_ID` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道ID(全渠道：super)',
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `ABOUT` text COLLATE utf8_unicode_ci COMMENT '简介',
  `STATE` char(2) COLLATE utf8_unicode_ci NOT NULL DEFAULT '01' COMMENT '状态（启用：01，禁用：02）',
  `RES1` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='渠道记录表';

-- ----------------------------
-- Records of ver_channel
-- ----------------------------
INSERT INTO ver_channel VALUES ('1', 'ebeijia.android.com.systemfloatwindow.content', '小U', null, '01', null, null, null, null, '2016-10-25 18:09:36');

-- ----------------------------
-- Table structure for `ver_softimg`
-- ----------------------------
DROP TABLE IF EXISTS `ver_softimg`;
CREATE TABLE `ver_softimg` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识ID',
  `SOFT_ID` int(11) NOT NULL COMMENT '软件ID',
  `IMG_ID` int(11) NOT NULL COMMENT '图片ID',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT=' 软件图片表';

-- ----------------------------
-- Records of ver_softimg
-- ----------------------------
INSERT INTO ver_softimg VALUES ('1', '1', '2', null, null, null, '2016-10-25 18:26:19');
INSERT INTO ver_softimg VALUES ('2', '1', '3', null, null, null, '2016-10-25 18:26:21');
INSERT INTO ver_softimg VALUES ('3', '1', '4', null, null, null, '2016-10-25 18:26:27');
INSERT INTO ver_softimg VALUES ('4', '1', '5', null, null, null, '2016-10-25 18:26:37');
INSERT INTO ver_softimg VALUES ('5', '1', '6', null, null, null, '2016-10-25 18:26:42');
INSERT INTO ver_softimg VALUES ('6', '2', '8', null, null, null, '2016-10-25 18:31:02');
INSERT INTO ver_softimg VALUES ('7', '2', '9', null, null, null, '2016-10-25 18:31:06');
INSERT INTO ver_softimg VALUES ('8', '2', '10', null, null, null, '2016-10-25 18:31:11');
INSERT INTO ver_softimg VALUES ('9', '2', '11', null, null, null, '2016-10-25 18:31:14');
INSERT INTO ver_softimg VALUES ('10', '2', '12', null, null, null, '2016-10-25 18:31:19');
INSERT INTO ver_softimg VALUES ('11', '3', '14', null, null, null, '2016-10-25 18:37:22');
INSERT INTO ver_softimg VALUES ('12', '3', '15', null, null, null, '2016-10-25 18:37:31');
INSERT INTO ver_softimg VALUES ('13', '3', '16', null, null, null, '2016-10-25 18:37:37');
INSERT INTO ver_softimg VALUES ('14', '3', '17', null, null, null, '2016-10-25 18:37:42');
INSERT INTO ver_softimg VALUES ('15', '3', '18', null, null, null, '2016-10-25 18:37:49');
INSERT INTO ver_softimg VALUES ('16', '4', '20', null, null, null, '2016-10-25 18:43:46');
INSERT INTO ver_softimg VALUES ('17', '4', '21', null, null, null, '2016-10-25 18:43:51');
INSERT INTO ver_softimg VALUES ('18', '4', '22', null, null, null, '2016-10-25 18:43:55');
INSERT INTO ver_softimg VALUES ('19', '4', '23', null, null, null, '2016-10-25 18:44:00');
INSERT INTO ver_softimg VALUES ('20', '4', '24', null, null, null, '2016-10-25 18:44:06');
INSERT INTO ver_softimg VALUES ('21', '4', '25', null, null, null, '2016-10-25 18:44:12');

-- ----------------------------
-- Table structure for `ver_softtype`
-- ----------------------------
DROP TABLE IF EXISTS `ver_softtype`;
CREATE TABLE `ver_softtype` (
  `TYPE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `ABOUT` text COLLATE utf8_unicode_ci COMMENT '简介',
  `RES1` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`TYPE_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='软件类型表';

-- ----------------------------
-- Records of ver_softtype
-- ----------------------------
INSERT INTO ver_softtype VALUES ('1', '1-2岁', null, null, null, null, null, '2016-10-25 18:10:28');
INSERT INTO ver_softtype VALUES ('2', '3-4岁', null, null, null, null, null, '2016-10-25 18:10:31');
INSERT INTO ver_softtype VALUES ('3', '5-6岁', null, null, null, null, null, '2016-10-25 18:10:42');

-- ----------------------------
-- Table structure for `ver_softver`
-- ----------------------------
DROP TABLE IF EXISTS `ver_softver`;
CREATE TABLE `ver_softver` (
  `SOFT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '软件ID',
  `USER_ID` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户ID',
  `CHNL_ID` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道ID(全渠道：super)',
  `TYPE_ID` int(11) NOT NULL COMMENT '类型ID',
  `NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `PACK_NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '包名',
  `VER_NO` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '版本号',
  `PACK_URL` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '安装包路径',
  `IMG_ID` int(11) NOT NULL COMMENT '图片ID',
  `ISU_TIME` datetime NOT NULL COMMENT '发布时间',
  `IS_FORCE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0' COMMENT '是否强制跟更新',
  `UPDATE_LOG` text COLLATE utf8_unicode_ci COMMENT '更新记录',
  `ABOUT` text COLLATE utf8_unicode_ci COMMENT '简介',
  `RES2` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES3` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `RES4` char(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留1',
  `LAST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`SOFT_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT=' 软件版本表';

-- ----------------------------
-- Records of ver_softver
-- ----------------------------
INSERT INTO ver_softver VALUES ('1', '1', 'ebeijia.android.com.systemfloatwindow.content', '1', '宝宝巴士', 'com.sinyee.babybus.talk2kiki', '8.8.11.22', 'http://192.168.2.205/baobaobashi_v_8_8_11_22.apk', '1', '2016-10-20 19:13:43', '0', null, '奥运会进行中！宝宝们也快来赢得属于你的奥运金牌吧！\r\n【灌篮高手、短跑飞人、跳水健将、蹦床达人】已经准备就绪！\r\n奇奇等你一起运动起来！\r\n【宝宝巴士】驾到啦！让奇奇和宝宝一起欢笑，一起成长！\r\n一款家庭必备的全能APP，满足宝宝【智力开发】+【知识探索】+【畅快游戏】的成长需求！\r\n儿歌、动画，丰富知识应有尽有；角色扮演，智能对话，趣味小游戏，开发脑力！\r\n免费无广告无内购，爸妈省心更放心！\r\n喂食，吃饭，养成宝宝吃饭好习惯~\r\n家长小帮手：\r\n【定时设置】为宝宝定制健康的作息时间，保护视力，劳逸结合！\r\n【流量监控】流量操作提示，保卫流量安全，拒绝流量浪费！\r\n【学习反馈】了解宝宝学习成果，及时查缺补漏，让宝宝每天都有进步！\r\n宝宝小玩伴：\r\n【儿歌动画唐诗】边看边听边读，打造专属宝宝的知识小百科！\r\n【宠物家园】萌宠相伴，为它装扮喂食，培养宝宝责任心；\r\n【趣味游戏】超多精彩小游戏，挖掘宝宝无限潜能！\r\n产品特色：\r\n丰富知识，缤纷游戏，更多惊喜，持续更新；\r\n应用百宝箱，130+APP一键下载；\r\n智能对话、趣味换装，精彩互动玩不停；\r\n3D动画，贴近生活场景，给宝宝逼真体验；\r\n点击操作，简单操控，宝宝轻松掌握！', null, null, null, '2016-10-25 18:18:37');
INSERT INTO ver_softver VALUES ('2', '1', 'ebeijia.android.com.systemfloatwindow.content', '1', '宝贝乐园-多纳', 'com.koolearn.ChildPark', '2.8', 'http://192.168.2.205/baobeileyuanDduona_v_2_8.apk', '7', '2016-10-20 19:16:04', '0', null, '游乐场，总是会带给孩子们很多的欢乐，是孩子们永远也玩不够的地方。那么快让他和小动物朋友们一起到游乐园里放声大笑吧！?\r\n【不可错过的游戏】?\r\n1、跷跷板：怎么才能让跷跷板上上下下的动起来呢？大象和鳄鱼谁比较重呢？来玩一玩跷跷板就知道啦！\r\n2、碰碰车： 你碰我，我碰你，碰来碰去多有趣！和好朋友一起来碰一碰吧！\r\n3、旋转木马：将小动物送到它最喜欢的木马上，然后轻轻滑动，让木马旋转起来吧。\r\n4、影子拼图：漂亮的大玩具找不到它们的影子了，快来帮帮忙吧！一定要记住它们的名字哦！\r\n【酷学多纳品牌】\r\n>>酷学多纳为2-8岁儿童和他们的家庭提供专业的教育App；\r\n>>我们研发互动教育系列App、动画片、幼儿园教材、酷学多纳系列图书等；\r\n>>酷学多纳的产品已经获得超过1500万儿童家庭的认可和喜爱！\r\n酷学多纳\r\n新东方旗下儿童启蒙教育品牌\r\n*纳粉福利\r\n*--官方微信：酷学多纳\r\n*亲子课堂\r\n*--老师微信：duona01\r\n*在线答疑\r\n*--QQ群：50945354(安卓群)\r\n*儿童电台\r\n*--新东方多纳双语电台(喜马拉雅)\r\n欢迎提出宝贵建议，共同为孩子设计更优秀的产品！', null, null, null, '2016-10-25 18:30:40');
INSERT INTO ver_softver VALUES ('3', '1', 'ebeijia.android.com.systemfloatwindow.content', '1', '孩子画画', 'virtualgl.kidspaint', '1.2.3.6', 'http://192.168.2.205/haizihuahua_v_1_2_3_6.apk', '13', '2016-10-20 19:17:34', '0', null, '孩子画画发布了。您的宝贝孩子可以在这里体验画画的乐趣。\r\n拿起手机，拍下今年具有魅力的景色，作为画布，让您的宝宝孩子在美丽漂亮的景色上留下创意画作，保存或分享宝贝的创作成果。\r\n海量画笔，油漆笔、光润笔、蜡笔、油墨笔、荧光笔、方头笔、彩环笔、毛笔、钢笔、炫彩笔、印章等让宝贝爱不释手！\r\n1.全新界面设计，全面的画笔功能让您可以带着宝贝画出有个性的作品。\r\n2.超强简笔画，让您的宝宝跟着画，可放大缩小，画出漂亮的动物、房子、交通工具等。\r\n3.画笔背景、粗细、颜色可任意调节，及时预览自己的画画作品，可保存到画册或分享给朋友。\r\n4.画笔支持手指按压力自动调节粗细，再现真实的画画体验(需硬件设备支持)。\r\n4.爸爸妈妈可以教宝贝孩子画画，您可以导入简笔画作作为画布，让宝贝孩子依简笔画来描，培养画画感觉。\r\n全新孩子画画，用心沟通。我们的点滴改变都是为您改变。我们将更加努力为您创造出更多漂亮画笔。\r\n孩子画画是一款包括各种画笔，简笔画等功能的儿童亲子类应用,让您的宝贝学习画画并分享给朋友。\r\n', null, null, null, '2016-10-26 16:35:27');
INSERT INTO ver_softver VALUES ('4', '1', 'ebeijia.android.com.systemfloatwindow.content', '2', '儿童教育游戏', 'cn.ytsmwhc.bestmother', '5.9.0912', 'http://192.168.2.205/ertongjiaoyuyouxi_v_5_9_0912.apk', '19', '2016-10-26 13:51:57', '0', null, '好妈妈儿童教育游戏平台是一个亲子早教益智应用，可以有效促进儿童对数学，动物，数字，英文，音乐等等传统知识的认知，增加儿童的知识面。现在有：\r\n1、儿童数学 1-9 2、儿童形状 3、识识动物 4、识识字母 5、青蛙过河 6、彩色钢琴 7、儿童七巧板 8、小熊钓鱼 9、数字顺序指读 10、运算集中营 11、拖拉拼图 12、旋转拼图 13、找相同 14、儿童汉字之数字 15、记忆游戏 16、儿童识颜色 17、儿童数学1-20上 18、儿童数学 1-20下 19、儿童打地鼠 20、时钟练习 21、消除方块22、拼图填空 23、只踩黑块(钢琴) 24、数字顺序填空 25、加法表 26、逆向思维 27、飞机争霸 28、数字拼拼  29、小熊钓鱼2 30、拼音游戏 31、减法表 32、高低大小 33、动物翻翻看 34、大小排序 35、过障碍的飞机 36、平衡式游戏 37、填色游戏 38、儿童木琴 39、儿童竖琴40、飞机拼图 等等的应用。\r\n', null, null, null, '2016-10-26 13:53:12');
