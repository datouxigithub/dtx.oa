-- phpMyAdmin SQL Dump
-- version 4.4.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2017-03-08 13:18:16
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dtaoa`
--

-- --------------------------------------------------------

--
-- 表的结构 `node`
--

CREATE TABLE IF NOT EXISTS `node` (
  `uuid` varchar(128) NOT NULL,
  `title` varchar(200) NOT NULL,
  `address` text,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `remark` text,
  `parent_id` varchar(128) DEFAULT NULL,
  `node_type` int(11) NOT NULL COMMENT '节点类型'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `node`
--

INSERT INTO `node` (`uuid`, `title`, `address`, `status`, `remark`, `parent_id`, `node_type`) VALUES
('ff80808159ac6d4b0159ac74e8980001', '节点组1', '', 1, '', '', 2),
('ff80808159ac6d4b0159ac755ebf0002', '节点11', 'abc', 1, '', 'ff80808159ac6d4b0159ac74e8980001', 1),
('ff80808159ac6d4b0159ac75a5960003', '节点12', 'cde', 1, '', '', 1),
('ff80808159ac6d4b0159ac75f2460004', '节点组11', '', 1, '', 'ff80808159ac6d4b0159ac74e8980001', 2),
('ff80808159ac6d4b0159ac7645970005', '节点111', 'ddd', 0, '', 'ff80808159ac6d4b0159ac75f2460004', 1),
('ff80808159ac6d4b0159ac7688580006', '节点2', 'eee', 1, '', '', 1);

-- --------------------------------------------------------

--
-- 表的结构 `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `uuid` varchar(128) NOT NULL,
  `role_name` varchar(200) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `remark` text,
  `parent_id` varchar(128) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role`
--

INSERT INTO `role` (`uuid`, `role_name`, `status`, `remark`, `parent_id`) VALUES
('ff808081598818400159881a149a0001', 'A', 1, '', ''),
('ff808081598818400159881a49100002', 'B', 1, '', 'ff808081598818400159881a149a0001'),
('ff808081598818400159881a696b0003', 'C', 1, '', 'ff808081598818400159881a149a0001'),
('ff808081598818400159881a89a90004', 'D', 1, '', 'ff808081598818400159881a149a0001'),
('ff808081598818400159881aae8b0005', 'E', 1, '', 'ff808081598818400159881a49100002'),
('ff808081598818400159881ad1980006', 'F', 1, '', 'ff808081598818400159881a49100002'),
('ff808081598818400159881aea690007', 'G', 1, '', 'ff808081598818400159881a49100002'),
('ff808081598818400159881b1aa90008', 'H', 1, '', 'ff808081598818400159881a696b0003'),
('ff808081598818400159881b48530009', 'I', 1, '', 'ff808081598818400159881a696b0003'),
('ff808081598818400159881bd54c000a', 'J', 1, '', 'ff808081598818400159881a89a90004'),
('ff80808159931440015993174aee0001', 'X', 1, '', '');

-- --------------------------------------------------------

--
-- 表的结构 `role_node`
--

CREATE TABLE IF NOT EXISTS `role_node` (
  `uuid` varchar(128) NOT NULL,
  `role_id` varchar(128) NOT NULL,
  `node_id` varchar(128) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_node`
--

INSERT INTO `role_node` (`uuid`, `role_id`, `node_id`) VALUES
('2', 'ff808081598818400159881a49100002', 'ff80808159ac6d4b0159ac74e8980001'),
('1', 'ff808081598818400159881a49100002', 'ff80808159ac6d4b0159ac755ebf0002'),
('3', 'ff808081598818400159881a49100002', 'ff80808159ac6d4b0159ac75a5960003'),
('4', 'ff808081598818400159881a49100002', 'ff80808159ac6d4b0159ac7688580006'),
('ff80808159da31850159db0b9e86000b', 'ff808081598818400159881aae8b0005', 'ff80808159ac6d4b0159ac7688580006'),
('ff80808159da31850159db0b9e87000c', 'ff808081598818400159881aae8b0005', 'ff80808159ac6d4b0159ac74e8980001'),
('ff80808159da31850159db0b9e88000d', 'ff808081598818400159881aae8b0005', 'ff80808159ac6d4b0159ac755ebf0002'),
('ff8080815a40d285015a40e22e620001', 'ff808081598818400159881a696b0003', 'ff80808159ac6d4b0159ac75a5960003'),
('ff80808159da31850159db0b51ec000a', 'ff808081598818400159881a149a0001', 'ff80808159ac6d4b0159ac75a5960003'),
('ff8080815a40d285015a40e259990002', 'ff808081598818400159881b48530009', 'ff80808159ac6d4b0159ac75a5960003');

-- --------------------------------------------------------

--
-- 表的结构 `role_user`
--

CREATE TABLE IF NOT EXISTS `role_user` (
  `uuid` varchar(128) NOT NULL,
  `user_id` varchar(128) NOT NULL,
  `role_id` varchar(128) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_user`
--

INSERT INTO `role_user` (`uuid`, `user_id`, `role_id`) VALUES
('ff80808159a23bb70159a23c34910002', 'ff80808159a0bfb40159a0c035f40001', 'ff808081598818400159881a89a90004'),
('ff80808159a23bb70159a23c34890001', 'ff80808159a0bfb40159a0c035f40001', 'ff808081598818400159881aae8b0005');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `uuid` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'uuid',
  `account` varchar(200) NOT NULL COMMENT '帐户名称',
  `password` text NOT NULL COMMENT '经过md5加密',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '帐户创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '帐户最近登录时间',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '帐户最近登录IP',
  `status` tinyint(1) NOT NULL COMMENT '帐户启用状态',
  `remark` text
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`uuid`, `account`, `password`, `create_time`, `login_time`, `login_ip`, `status`, `remark`) VALUES
('ff80808159922a2c01599237ab430001', '臭屎洋', 'kAFQmDzST7DWlj99KOF/cg==', '2017-01-12 18:27:18', NULL, NULL, 1, ''),
('ff80808158e69e720158e69e73510001', 'admin', 'ISMvKXpXpadDiUoOSoAfww==', '2016-12-10 10:44:53', NULL, NULL, 0, '注释!'),
('ff80808159a0bfb40159a0c035f40001', '大头希', 'uk88CT/IB2cLk7HlefDCUA==', '2017-01-15 14:11:08', NULL, NULL, 1, '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `node`
--
ALTER TABLE `node`
  ADD PRIMARY KEY (`uuid`),
  ADD UNIQUE KEY `title` (`title`),
  ADD UNIQUE KEY `title_2` (`title`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`uuid`),
  ADD UNIQUE KEY `role_name` (`role_name`),
  ADD UNIQUE KEY `role_name_2` (`role_name`);

--
-- Indexes for table `role_node`
--
ALTER TABLE `role_node`
  ADD PRIMARY KEY (`uuid`);

--
-- Indexes for table `role_user`
--
ALTER TABLE `role_user`
  ADD PRIMARY KEY (`uuid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uuid`),
  ADD UNIQUE KEY `account` (`account`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
