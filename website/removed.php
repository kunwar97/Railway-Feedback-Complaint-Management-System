<?php
    $servername = "127.0.0.1";
    $username = "root";
    $password = "root";
    $database = "twitter";

    $conn = new mysqli($servername, $username, $password,$database);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    } 

	$query = "SELECT * FROM tweets,reply WHERE tweets.reply_status=1 and tweets.tweet_id=reply.tweet_id";
	$res = mysqli_query($conn,$query);
	$rows=array();
	if(mysqli_num_rows($res)>0)
	{
		while($r = mysqli_fetch_array($res)){
			$rows[]=$r;
		}
	}
	else{
		echo "failed";
}
echo '{"data":'.json_encode($rows)."}";

?>
