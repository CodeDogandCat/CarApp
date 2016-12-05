<?php
	include("conn.php");

	//获取分页参数
	//$page=$_REQUEST['page'];
	//$page=1;
	//$pageSize=10;
	//$pageFrom=$page*$pageSize;

	//获取发帖id号码
	$Mid=$_REQUEST["Mid"];

	try
	{
		$sql="select Cid,Cdate,content,username
		from comment where Mid = ".$Mid;//" limit ".($pageFrom).",".$pageSize;

		$result = mysql_query($sql);
		$json="";
		$arr = array();

		//定义一个类，用于存放数据库中数据
		class comment
		{
			public $cid;
			public $cdate;
			public $content;
			public $username;
		}

		while($row=mysql_fetch_array($result,MYSQL_ASSOC))
		{
			$com=new comment();
			$com->cid=$row["Cid"];
			$com->cdate=$row["Cdate"];
			$com->content=$row["content"];
			$com->username=$row["username"];

			$arr[] = $row;
		}

		//将数组转化成json格式
		$json=json_encode($arr,JSON_UNESCAPED_UNICODE);
		echo $json;
	}
	catch(Exception $e)
	{
		echo 'Message:'.$e->getMessage();
	}
?>