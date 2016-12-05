<?php
header("Content-type: text/html; charset=utf-8");
error_reporting(0);
include("conn.php");
session_start();


$username = htmlspecialchars($_REQUEST['username']);
$token=$_REQUEST['token'];

if ($_SESSION['token']==$token) {
	$obj->resultcode="有效token"	;
	echo json_encode($obj,JSON_UNESCAPED_UNICODE);
}else{
	$check_query = mysql_query("select * from user where username='$username' and token='$token' limit 1");
	if($result = mysql_fetch_array($check_query)){
		$_SESSION['token'] = $token;
		$obj->resultcode="有效token"	;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		// exit;
	}else {
		$obj->resultcode="无效token"	;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		// exit;
	}
}


?>