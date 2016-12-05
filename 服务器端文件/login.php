<?php
header("Content-type: text/html; charset=utf-8");
error_reporting(0);
include("conn.php");
session_start();


$username = htmlspecialchars($_REQUEST['username']);
$password = MD5($_REQUEST['password']);
$token=$_REQUEST['token'];

//检测用户名及密码是否正确
$check_query = mysql_query("select * from user where username='$username' and password='$password' limit 1");
if($result = mysql_fetch_array($check_query)){
	$_SESSION['token'] = $token;
	$sql = "UPDATE user SET token='$token' where username='$username'";
	if(mysql_query($sql,$conn)){
		$obj->resultcode="login succeed"	;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		exit;
	} else {
		$obj->resultcode="login failed"	;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		exit;
	}
} else {
	$obj->resultcode="login failed"	;
	echo json_encode($obj,JSON_UNESCAPED_UNICODE);
	exit;
}

?>
