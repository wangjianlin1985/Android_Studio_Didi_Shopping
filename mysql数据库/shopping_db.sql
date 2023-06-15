/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : shopping_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2017-11-19 01:28:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `coupon`
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `couponId` int(11) NOT NULL auto_increment,
  `couponName` varchar(60) default NULL,
  `couponMoney` float default NULL,
  `sellerObj` varchar(20) default NULL,
  `userObj` varchar(20) default NULL,
  `couponTime` varchar(20) default NULL,
  PRIMARY KEY  (`couponId`),
  KEY `FK78A7C4462BE10D19` (`sellerObj`),
  KEY `FK78A7C446C80FC67` (`userObj`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES ('1', '双十二优惠卷', '10', 'sj001', 'user1', '2017-12-12 23:00:00');
INSERT INTO `coupon` VALUES ('2', '111', '22', 'sj001', 'user1', '33');
INSERT INTO `coupon` VALUES ('3', '菜市优惠券', '10', 'sj002', 'user1', '2017-12-30');

-- ----------------------------
-- Table structure for `orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `orderId` int(11) NOT NULL auto_increment,
  `productObj` varchar(20) default NULL,
  `arrivePlace` varchar(60) default NULL,
  `latitude` float(9,6) unsigned default NULL,
  `longitude` float(9,6) unsigned default NULL,
  `stateObj` int(11) default NULL,
  `telephone` varchar(20) default NULL,
  `orderUser` varchar(20) default NULL,
  `addTime` varchar(20) default NULL,
  `receiveSeller` varchar(60) default NULL,
  `receiveTime` varchar(20) default NULL,
  `memo` varchar(5000) default NULL,
  PRIMARY KEY  (`orderId`),
  KEY `FK601628FCFDFDEF34` (`orderUser`),
  KEY `FK601628FC8A0F42B` (`stateObj`),
  KEY `FK601628FC1D6F5E55` (`productObj`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES ('1', 'SP001', '一环路南二段时代数码大厦', '30.639210', '104.073425', '5', '13583943243', 'user1', '2017-11-20 15:12:13', 'sj001', '2017-11-18 23:42:44', '尽快送货');
INSERT INTO `orderinfo` VALUES ('3', 'SP002', '成都东门大桥', '30.648506', '104.091904', '1', '13545128452', 'user2', '2017-11-19 01:26:13', '--', '--', '快点啊');

-- ----------------------------
-- Table structure for `orderstate`
-- ----------------------------
DROP TABLE IF EXISTS `orderstate`;
CREATE TABLE `orderstate` (
  `stateId` int(11) NOT NULL auto_increment,
  `stateName` varchar(20) default NULL,
  PRIMARY KEY  (`stateId`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderstate
-- ----------------------------
INSERT INTO `orderstate` VALUES ('1', '抢单中');
INSERT INTO `orderstate` VALUES ('2', '待付款');
INSERT INTO `orderstate` VALUES ('3', '已付款');
INSERT INTO `orderstate` VALUES ('4', '配送中');
INSERT INTO `orderstate` VALUES ('5', '交易完成');

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `productNo` varchar(20) NOT NULL,
  `productClassObj` int(11) default NULL,
  `productName` varchar(20) default NULL,
  `productDesc` varchar(5000) default NULL,
  `price` float default NULL,
  `productPhoto` varchar(50) default NULL,
  `stockDesc` varchar(50) default NULL,
  PRIMARY KEY  (`productNo`),
  KEY `FK50C664CF16092339` (`productClassObj`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('SP001', '1', '六个核桃', '很好喝的', '70', 'upload/5B3B279859B88E4FDA258CF642C8F5DE.jpg', '充足');
INSERT INTO `product` VALUES ('SP002', '1', '农夫山泉24拼', '厂名: 农夫山泉吉林长白山有限公司 \r\n配料表: 水\r\n净含量: 13200ml', '45', 'upload/13C1BB2833E3E67928E2691E75227C46.jpg', '充足');

-- ----------------------------
-- Table structure for `productclass`
-- ----------------------------
DROP TABLE IF EXISTS `productclass`;
CREATE TABLE `productclass` (
  `classId` int(11) NOT NULL auto_increment,
  `className` varchar(20) default NULL,
  `addTime` datetime default NULL,
  `classDesc` varchar(2000) default NULL,
  PRIMARY KEY  (`classId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productclass
-- ----------------------------
INSERT INTO `productclass` VALUES ('1', '食品类', '2017-11-08 00:00:00', '常见的零食小吃');

-- ----------------------------
-- Table structure for `seller`
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `sellUserName` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `sellerName` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `addDate` datetime default NULL,
  `address` varchar(80) default NULL,
  `latitude` float(9,6) unsigned default NULL,
  `longitude` float(9,6) unsigned default NULL,
  PRIMARY KEY  (`sellUserName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seller
-- ----------------------------
INSERT INTO `seller` VALUES ('sj001', '123', '红旗超市', '028-83394934', '2017-11-02 00:00:00', '玉林东街红旗超市', '30.633118', '104.072906');
INSERT INTO `seller` VALUES ('sj002', '123', '光明路菜市', '13598349834', '2017-11-01 00:00:00', '成都水碾河路', '30.653044', '104.100723');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `sex` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userImage` varchar(50) default NULL,
  `qq` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `regTime` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  `latitude` float(9,6) unsigned default NULL,
  `longitude` float(9,6) unsigned default NULL,
  PRIMARY KEY  (`user_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', '双鱼林', '男', '2017-11-02 00:00:00', 'upload/D3EF7E7BEF47754D7832F1EE1D011DC6.jpg', '254540457', 'dashen@163.com', '2017-11-12 15:13:42', '成都一环路南二段18号', '30.639294', '104.073814');
INSERT INTO `userinfo` VALUES ('user2', '123', '李敏', '女', '2017-11-19 00:00:00', 'upload/noimage.jpg', '5798884', 'gfdvvv@126.com', '2017-11-19 01:24:20', '成都', '30.648506', '104.091904');
