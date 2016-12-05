<?php
	include("conn.php");
	

	$sql="select * from message";

	$result=mysql_query($sql);
	$numberPage=mysql_num_rows($result);
	$pageSize=10;
	$Page=ceil($numberPage / $pageSize);
	

	echo $Page;


	
?>