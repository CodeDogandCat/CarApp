<?php
header("Content-type: text/html; charset=utf-8"); 
$k = $_REQUEST['k'];
if (!empty($k))
{
$k = trim($k);
$return_array = array();
// $canshu=passthru('python ./Canshu.py '.$k);
exec('python ./Canshu.py '.$k,$return_array);
// echo $canshu;
echo $return_array[0];
}

