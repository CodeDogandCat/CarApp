<?php
header("Content-type: text/html; charset=utf-8"); 
$k = $_REQUEST['k'];
if (!empty($k))
{
$k = trim($k);
$return_array = array();
exec('python ./Price.py '.$k,$return_array);
echo $return_array[0];
}