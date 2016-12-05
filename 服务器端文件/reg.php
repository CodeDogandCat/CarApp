<?php
header("Content-type: text/html; charset=utf-8");
error_reporting(0);
//包含数据库连接文件
include("conn.php");
$username = htmlspecialchars($_REQUEST['username']);
$password = $_REQUEST['password'];
$email = $_REQUEST['email'];

//检测用户名是否已经存在
$check_query = mysql_query("select * from user where username='$username' limit 1");
if(mysql_fetch_array($check_query)){
	$obj->resultcode="already exists"	;
	echo json_encode($obj,JSON_UNESCAPED_UNICODE);
	exit;
}

$password = MD5($password);
$regdate=date("Y-m-d H:i:s");
$sql = "INSERT INTO user(username,password,email,regdate)VALUES('$username','$password','$email','$regdate')";
if(mysql_query($sql,$conn)){
	$var=createFolder("luntanimages/".$username);
	if($var==1){
		$obj->resultcode="register succeed"	;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		exit;
	}else{
		$obj->resultcode="register succeed"	;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		exit;
	}
	
} else {
	$obj->resultcode="register failed"	;
	echo json_encode($obj,JSON_UNESCAPED_UNICODE);
	exit;
}
function createFolder($path)
 {
  if (!file_exists($path))
  {
   createFolder(dirname($path));
   return mkdir($path, 0777);
  }
 }

?>
