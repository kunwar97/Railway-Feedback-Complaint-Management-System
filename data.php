<?php

$servername = "localhost";
$username = "root";
$password = "root";
$database = "twitter";

$conn = new mysqli($servername, $username, $password,$database);
  if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
  }

$class = urldecode($_GET['class']);
$query = "SELECT * FROM tweets WHERE prediction='".$class."'";
$res = mysqli_query($conn,$query);
$rows=array();
if(mysqli_num_rows($res)>0){
	while($r = mysqli_fetch_array($res)){
		$rows[]=$r;
	}
}
echo '{"data":'.json_encode($rows)."}";


?>