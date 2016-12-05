<?php
header("Content-type: text/html; charset=utf-8");
error_reporting(0);
$conn = @mysql_connect("localhost","root","");
mysql_query("set names utf8");
mysql_query("set character set 'utf8'");

if (!$conn){
	die("连接失败：" . mysql_error());
}
mysql_select_db("qrqc", $conn);

mysql_query("set character set 'utf8'");
date_default_timezone_set('PRC'); //设置中国时区
?>