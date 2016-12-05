-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016-04-16 16:23:35
-- 服务器版本： 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `qrqc`
--

-- --------------------------------------------------------

--
-- 表的结构 `ad`
--

CREATE TABLE IF NOT EXISTS `ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `topicFrom` varchar(50) NOT NULL,
  `topic` varchar(50) NOT NULL,
  `imgUrl` varchar(100) NOT NULL,
  `targetUrl` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='广告表' AUTO_INCREMENT=11 ;

--
-- 转存表中的数据 `ad`
--

INSERT INTO `ad` (`id`, `date`, `title`, `topicFrom`, `topic`, `imgUrl`, `targetUrl`) VALUES
(1, '2016年3月27日', '正青春  爱领跑', '奇瑞官方', '艾瑞泽5', 'http://www.chery.cn/Public/Home/img/arrizo5.jpg', 'http://www.chery.cn/car/arrizo5'),
(2, '2016年3月27日', '10年100万超长延保 奇瑞新瑞虎5百万信赖版携新颜色盎然上市', '奇瑞新闻', '新瑞虎5', 'http://www.chery.cn/Public/Home/img/pic_1603_07_2.jpg', 'http://www.chery.cn/news/detail?nid=935'),
(3, '2016年3月26日', '为什么在车商城买新瑞虎5 1.5T 手动家悦信赖版', '车商城', '瑞虎5', 'http://www.chery.cn/Public/Home/img/pic_1603_21_2.jpg', 'http://mall.autohome.com.cn/detail/40829-0-0.html'),
(4, '2016年3月18日', '送工时 拿好礼 奇瑞2016春季关爱邀您“约惠春天“', '奇瑞新闻', '约惠春天', 'http://www.chery.cn/Public/Home/img/pic_1603_17_1.jpg', 'http://www.chery.cn/news/detail?nid=941'),
(5, '2016年3月26日', '汽车之家2015年乘用车新车质量报告 奇瑞列第五', '奇瑞官方', '品质奇瑞', 'http://www.chery.cn/Public/Home/img/20151106/qly_01.jpg', 'http://www.chery.cn/into/quality'),
(6, '2016年04月05日 11:17', '或北京车展正式上市 国产新款奥迪Q3申报图', '车主之家', '新款奥迪Q3', 'http://i.img16888.com/upload/Images/2016/04/2016040511101879410_600.jpg', 'http://news.16888.com/a/2016/0405/3533679.html'),
(7, '2016年04月06日 10:16', '本田新中型SUV实车曝光 或北京车展首发 ', '汽车之家', '本田新中型SUV', 'http://i3.img16888.com/upload/Images/2016/04/2016040610150045073_600.jpg', 'http://news.16888.com/a/2016/0406/3548921.html'),
(8, '2016年03月31日 09:00', '这些交通法规‘冷知识’ 你还真不一定知道', '车主之家', '交通法规', 'http://i.img16888.com/upload/Images/2016/03/2016032812560811802_600.png', 'http://news.16888.com/a/2016/0331/3467793.html'),
(9, '2016年03月22日 14:27', '车翻了人会怎样？编辑亲身体验心得分享', '车主之家', '体验心得', 'http://image.16888.com/news/area/hd/2016/03/25094009601_45514_area.jpg', 'http://news.16888.com/a/2016/0322/3289426.html'),
(10, '2016年04月01日 08:59', '下雨天坏了雨刮 这样干竟然还能继续开！', '车主之家', '生活小知识', 'http://i2.img16888.com/upload/Images/2016/03/2016033104095634076_600.png', 'http://news.16888.com/a/2016/0401/3495372.html');

-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `Cid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `Cdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Mid` int(11) NOT NULL,
  PRIMARY KEY (`Cid`),
  KEY `Mid` (`Mid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- 转存表中的数据 `comment`
--

INSERT INTO `comment` (`Cid`, `username`, `content`, `Cdate`, `Mid`) VALUES
(1, 'qwertyuio', '我也想要买新车(ง •̀_•́)ง', '2016-03-28 05:48:20', 48),
(2, 'qwertyuio', '宽敞实用，(≧▽≦)/', '2016-03-28 05:48:57', 47),
(3, 'qwertyuio', '买的不错', '2016-03-28 05:55:50', 71),
(4, 'qwertyuio', '不错', '2016-03-28 05:56:22', 70),
(5, 'qwertyuio', '好东西', '2016-03-28 05:59:21', 70),
(6, 'qwertyuio', '我怎能没想到呢(๑• . •๑)', '2016-03-28 05:59:55', 70),
(9, 'qwertyuio', '你好', '2016-03-28 06:27:41', 75),
(10, 'qwertyuio', '欢迎加入奇瑞车友论坛', '2016-03-28 06:28:52', 75),
(14, 'qazwsx', '欧克欧克', '2016-03-28 09:44:30', 77),
(15, 'poiuyt', '哈哈哈♡', '2016-03-28 09:44:56', 77),
(16, 'poiuyt', '春天也不能忽视哦(⊙o⊙)哦', '2016-03-28 09:46:24', 77),
(17, 'poiuyt', '呵呵(^_^)', '2016-03-28 13:43:27', 77),
(18, 'qwertyuio', '加油', '2016-04-06 13:12:23', 76),
(19, 'qwertyuio', '彩虹好漂亮', '2016-04-06 13:36:41', 78),
(20, 'hfuter1', '我也是好开心', '2016-04-16 13:24:10', 79),
(21, 'hfuter2', '很高兴认识你╮(╯▽╰)╭', '2016-04-16 13:24:50', 79),
(22, 'hfuter1', 'so nice~', '2016-04-16 13:25:23', 79),
(23, 'hfuter2', '欢迎你', '2016-04-16 13:26:02', 75),
(24, 'hfuter1', '赞(≧▽≦)/', '2016-04-16 13:54:52', 80);

-- --------------------------------------------------------

--
-- 表的结构 `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `Mid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `pictureURL` varchar(100) NOT NULL,
  `Mdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `replynum` int(11) NOT NULL,
  `picnum` int(11) NOT NULL,
  PRIMARY KEY (`Mid`),
  KEY `fk_1` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=81 ;

--
-- 转存表中的数据 `message`
--

INSERT INTO `message` (`Mid`, `username`, `title`, `content`, `pictureURL`, `Mdate`, `replynum`, `picnum`) VALUES
(39, 'qwertyuio', '买新车了', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n<(￣︶￣)>', './luntanimages/qwertyuio/1457788788367', '2016-03-12 13:19:48', 0, 2),
(40, 'qwertyuio', '好开心', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457788963426', '2016-03-12 13:22:43', 0, 5),
(46, 'qwertyuio', '秀一秀', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457790442007', '2016-03-12 13:47:22', 0, 4),
(47, 'qwertyuio', '新品来了', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457840976412', '2016-03-13 03:49:37', 1, 2),
(48, 'qwertyuio', '来比一比', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457841118652', '2016-03-13 03:51:59', 1, 4),
(49, 'qwertyuio', '服不服', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457841383379', '2016-03-13 03:56:24', 0, 3),
(50, 'qwertyuio', '今天去逛车', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457841606433', '2016-03-13 04:00:07', 0, 2),
(51, 'qwertyuio', '奇瑞还不错', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457841920872', '2016-03-13 04:05:21', 0, 2),
(52, 'qwertyuio', '奇瑞的性价比就是高', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457842496834', '2016-03-13 04:14:57', 0, 2),
(53, 'qwertyuio', '为什么选择奇瑞', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457842587531', '2016-03-13 04:16:28', 0, 1),
(54, 'qwertyuio', '奇瑞不只有低端市场', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457842775716', '2016-03-13 04:19:36', 0, 1),
(55, 'qwertyuio', '赞一个', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457844538198', '2016-03-13 04:48:59', 0, 2),
(56, 'qwertyuio', '卡哇伊', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457844860270', '2016-03-13 04:54:21', 0, 1),
(57, 'qwertyuio', '棒棒哒', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457845698275', '2016-03-13 05:08:19', 0, 1),
(58, 'qwertyuio', '嘻嘻，开心', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457846891297', '2016-03-13 05:28:13', 0, 1),
(59, 'qwertyuio', '这个车真心不错', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457846892943', '2016-03-13 05:28:14', 0, 1),
(60, 'qwertyuio', '还没打定主意吗', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457847365627', '2016-03-13 05:36:06', 0, 1),
(61, 'qwertyuio', '快来围观', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457847584180', '2016-03-13 05:39:45', 0, 1),
(62, 'qwertyuio', '你喜欢吗？', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457847844441', '2016-03-13 05:44:05', 0, 1),
(63, 'qwertyuio', '有些不满意', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457848158135', '2016-03-13 05:49:19', 0, 1),
(64, 'qwertyuio', '样子有点丑', '但是这么实惠，难免是吧\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457848921850', '2016-03-13 06:02:02', 0, 1),
(65, 'qwertyuio', '哈哈，买到好东西的', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457849949995', '2016-03-13 06:19:11', 0, 1),
(66, 'qwertyuio', '逗我，这就坏了', '不开森', './luntanimages/qwertyuio/1457850007421', '2016-03-13 06:20:08', 0, 1),
(67, 'qwertyuio', '最近新get', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457850055621', '2016-03-13 06:20:56', 0, 4),
(68, 'qwertyuio', '不来看看你就out了', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457850100707', '2016-03-13 06:21:41', 0, 4),
(69, 'qwertyuio', '66666', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457850179473', '2016-03-13 06:23:00', 0, 0),
(70, 'qwertyuio', '超值', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457850687719', '2016-03-13 06:31:28', 3, 3),
(71, 'qwertyuio', '家庭实用轿车', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457868837276', '2016-03-13 11:33:58', 1, 1),
(72, 'qwertyuio', '还不错哦', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457877252001', '2016-03-13 13:54:12', 0, 2),
(73, 'qwertyuio', '你买新车了吗？', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1457877298304', '2016-03-13 13:54:59', 0, 2),
(74, 'qwertyuio', '尝试一下吧', '还不错吧，<(￣︶￣)>这是我精挑细选了半天才买到的。\r\n是不是很赞。\r\n	晒晒图。\r\n\r\n╰(￣▽￣)╰', './luntanimages/qwertyuio/1459100246255', '2016-03-27 17:37:26', 0, 1),
(75, 'poiuyt', '第一次发帖', '请大家多多关照', './luntanimages/poiuyt/1459145011500', '2016-03-28 06:03:31', 3, 1),
(76, 'qwertyuio', '新的一天', '天天向上', './luntanimages/qwertyuio/1459145281596', '2016-03-28 06:08:02', 1, 0),
(77, 'poiuyt', '注意保养哦', '要爱护你的汽车', './luntanimages/poiuyt/1459156726371', '2016-03-28 09:18:46', 4, 1),
(78, 'qwertyuio', '今天下雨了', '下的好大', './luntanimages/qwertyuio/1459949785558', '2016-04-06 13:36:26', 1, 1),
(79, 'hfuter2', '好开心╮(╯▽╰)╭', '啦啦啦☆', './luntanimages/hfuter2/1460813026597', '2016-04-16 13:23:36', 3, 1),
(80, 'hfuter1', '今天下雨了', '哈哈哈哈哈哈(° ﾛ °)✧˖°宝宝方了', './luntanimages/hfuter1/1460814863752', '2016-04-16 13:54:22', 1, 3);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `regdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`username`, `password`, `email`, `regdate`, `token`) VALUES
('hfuter1', '25d55ad283aa400af464c76d713c07ad', '2662083658@qq.com', '2016-04-16 13:09:37', '26744d2b'),
('hfuter2', '25d55ad283aa400af464c76d713c07ad', '2663589652@qq.com', '2016-04-16 13:16:52', 'a0445ca0'),
('poiuyt', '25d55ad283aa400af464c76d713c07ad', '2669853043@qq.com', '2016-03-28 06:02:10', '26744d2b'),
('qazwsx', '25d55ad283aa400af464c76d713c07ad', '2662085637@qq.com', '2016-03-28 09:17:09', 'a0445ca0'),
('qwertyuio', '25d55ad283aa400af464c76d713c07ad', '2662083658@qq.com', '2016-03-04 10:56:09', '26744d2b');

--
-- 限制导出的表
--

--
-- 限制表 `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`Mid`) REFERENCES `message` (`Mid`);

--
-- 限制表 `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `fk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
