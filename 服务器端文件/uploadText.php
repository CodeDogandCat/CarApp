<?php
	header("Content-type: text/html; charset=utf-8");
	error_reporting(0);
	include("conn.php");
	$username=$_REQUEST['username'];
	$token=$_REQUEST['token'];
	$title=$_REQUEST['title'];
	$content=$_REQUEST['content'];
	$time=$_REQUEST['send_message_time'];
	$pictureurl="./luntanimages/".$username."/".$time;
	$replynum=0;
	$publishdate=date("Y-m-d H:i:s");
	$picnum=$_REQUEST['picnum'];

	try
	{
		$sql="INSERT INTO  message(username,title,content,pictureURL,Mdate,replynum,picnum) VALUES ('$username','$title','$content','$pictureurl','$publishdate','$replynum','$picnum')";
		if(mysql_query($sql,$conn))
		{
			echo "成功向数据库添加数据";
		}
		else
		{
			echo "Error".$sql."<br>".mysqli_error($conn);
		}
		mysqli_close($conn);
	}
	catch(Exception $e)
	{
		echo $sql."<br>".$e->getMessage();
	}
?>