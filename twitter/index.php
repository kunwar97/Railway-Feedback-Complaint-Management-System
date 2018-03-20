
<?php
ini_set('display_errors', 1);
require_once('TwitterAPIExchange.php');

   $servername = "127.0.0.1";
    $username = "root";
    $password = "root";
    $database = "twitter";

    $conn = new mysqli($servername, $username, $password,$database);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    } 


$settings = array(
    'oauth_access_token' => "4619963718-1xXU25ds9RKugYg7wYCbKe4WiKN4Rop6gqvuoAc",
    'oauth_access_token_secret' => "NjURtECQ8GjBRwZnzIyFlfYDkytQbt2L2vTAhrrgcg88j",
   'consumer_key' => "i2hvcyZZdRXacNqsgQesf8m36",
    'consumer_secret' => "PszjU89PMX3q2mZDtUBJ92haoppVlD7kzWFQ1oWMxN92Wgf3KX"
);
$twitter = new TwitterAPIExchange($settings);

$status_id = urldecode($_GET['tweet_id']);
$m = urldecode($_GET['tweet_reply']);
$screen = urldecode($_GET['screen_name']);
//$status_id = "975936236132036608";
//$m = "hELLO kaRTIK";
//$screen = "yogesh_gju40";

//$status_id='975848669416120320';
$message="@".$screen." ".$m;
$postfields = array( 
    'status' => $message,
    'in_reply_to_status_id'=> $status_id,
);

$url = 'https://api.twitter.com/1.1/statuses/update.json';
$requestMethod = 'POST';
if($twitter->buildOauth($url, $requestMethod)
             ->setPostfields($postfields)
             ->performRequest())
{
	$query = "UPDATE tweets SET reply_status=1 WHERE tweet_id='".$status_id."'";
	$query2 = "INSERT INTO reply(tweet_id,ans) VALUES (".$status_id.",'".$message."')";
	if(mysqli_query($conn,$query) && mysqli_query($conn,$query2)){
	   	 echo "200";
	}
	else
		echo "400";
}
else
    echo "400";
