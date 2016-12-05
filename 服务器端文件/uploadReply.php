<?php
	include("conn.php");

	$Mid=$_REQUEST["Mid"];
	$username=$_REQUEST["username"];
	$content=$_REQUEST["content"];
	$time=date("Y-m-d H:i:s");

	try
	{
		$sql="INSERT into comment(Mid,username,content,Cdate)
		values('$Mid','$username','$content','$time')";
		$sql2="UPDATE message SET replynum=replynum+1 where mid='$Mid'";;
		if(mysql_query($sql,$conn))
		{
			if(mysql_query($sql2,$conn)){
				echo "成功向数据库添加数据";
			}
			
		}
		else
		{
			echo "Error".$sql."<br>".mysqli_error($conn);
		}
		
	}
	catch(Exception $e)
	{
		echo $sql."<br>".$e->getMessage();
	}
?>