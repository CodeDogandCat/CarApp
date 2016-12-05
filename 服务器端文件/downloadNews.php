<?php
	include("conn.php");


	try
	{
		$sql="select * from ad order by id desc";

		$result = mysql_query($sql);
		$json="";
		$arr = array();

		//定义一个类，用于存放数据库中数据
		class comment
		{
			public $id;
			public $date;
			public $title;
			public $imgUrl;
			public $targetUrl;
		}

		while($row=mysql_fetch_array($result,MYSQL_ASSOC))
		{
			$com=new comment();
			$com->id=$row["id"];
			$com->date=$row["date"];
			$com->title=$row["title"];
			$com->imgUrl=$row["imgUrl"];
			$com->targetUrl=$row["targetUrl"];

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