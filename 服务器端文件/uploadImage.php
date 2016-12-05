
<?php  
header("Content-type: text/html; charset=utf-8");
error_reporting(0);
include("checkToken.php");
$username = htmlspecialchars($_REQUEST["username"]);
$send_message_time=$_REQUEST["send_message_time"];
$create_folder_path="./luntanimages/".$username."/".$send_message_time;

	createFolder($create_folder_path);
	$target_path = $create_folder_path."/".basename ( $_FILES ['uploadfile'] ['name'] );  
	if (move_uploaded_file ( $_FILES ['uploadfile'] ['tmp_name'], $target_path )) {  
    	$array = array (  
        "code" => "1",  
        "message" => $_FILES ['uploadfile'] ['name']   
		);  
     	echo json_encode ( $array );  
	} else {  
    	$array = array (  
                "code" => "0",  
                "message" => "There was an error uploading the file, please try again!" . $_FILES ['uploadfile'] ['error']   
        );  
        echo json_encode ( $array );  
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