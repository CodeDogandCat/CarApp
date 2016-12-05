<?php
	//header('content-type:application/json;charset=utf8');
	include("conn.php");

	//获取分页参数
	$page=(int)($_REQUEST["page"]);//andriod传的是string类型，所以要进行强制类型转换
	//$page=1;
	$pageSize=10;
	$pageFrom=$page*$pageSize;

	try
	{
		$sql="select Mid,username,title,content,Mdate,pictureURL,replynum,picnum
		from message order by Mid desc limit ".($pageFrom).",".$pageSize;
		//$sql="select Mid,username,title,content,Mdate,pictureurl,replynum 
		//from message limit".$pageFrom.",".$pageSize."";
		

		$result = mysql_query($sql);
		$json="";
		$arr = array();

		//定义一个类，用于存放数据库中提取的数据
		class message
		{
			public $mid=1;
			public $username=null;
			public $title=null;
			public $content=null;
			public $mdate;
			public $pictureURL;
			public $replynum=0;
			public $picnum;
		}

		while($row=mysql_fetch_array($result,MYSQL_ASSOC))
		{
			$mes = new message();
			$mes->mid=$row["Mid"];
			$mes->username=$row["username"];
			$mes->title=$rpw["title"];
			$mes->content=$row["content"];
			$mes->mdate=$row["Mdate"];
			$mes->pictureURL=$row["pictureURL"];
			$mes->replynum=$row["replynum"];
			$mes->picnum=$row["picnum"];

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