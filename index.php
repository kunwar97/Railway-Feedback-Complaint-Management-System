	<html>
	<head>
	  <title>IR Complaint &amp; Management System</title>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	  <style>

		tr{
			border-bottom: 3px groove;
		}
		td {

	  		padding:20px;

		}
	    .active{color:white;}
	  	.line{border-right: 2px groove;min-height:98.5vh;overflow-x:hidden;}
	  	.left{
	  		width:70%;
	  		float:left;
	  	}
	  	.right{
	  		float: left;
	  		width:25%;
	  	}
			.centered {
    		position: absolute;
    		top: 40vh;
    		left: 0%;
    		// transform: translate(-50%, -50%);
			}
			.bg {
    		position: relative;
    		text-align: center;
    		color: white;
			}
			html, body {
    		overflow: auto;
			}



			.mystyle{
				top:0;
				position: fixed;
				display: block;
				width: 100%;
			}
			.newmystyle{
				position: fixed;
				top:50px;
				margin-left: 70%;
			}
	  </style>
	  <script>
		window.setInterval(function() {
	        if (window.XMLHttpRequest) {
	            xmlhttp = new XMLHttpRequest();
	        } else {
	            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	        }
	        xmlhttp.onreadystatechange = function() {
	            if (this.readyState == 4 && this.status == 200) {
	                bind();
	                document.getElementById("feedback-content").innerHTML = this.responseText;
	            }
	        };
	        xmlhttp.open("GET","feedback.php",true);
	        xmlhttp.send();
	},5000);

	window.setInterval(function() {
	        if (window.XMLHttpRequest) {
	            xmlhttp = new XMLHttpRequest();
	        } else {
	            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	        }
	        xmlhttp.onreadystatechange = function() {
	            if (this.readyState == 4 && this.status == 200) {
	                bind();
	                document.getElementById("emergency-content").innerHTML = this.responseText;
	            }
	        };
	        xmlhttp.open("GET","emergency.php",true);
	        xmlhttp.send();

	},5000);

	function func(){
		var windowHeight = window.innerHeight;
		var scrolltop = window.pageYOffset;
		var element = document.getElementById("main");
		var newelement = document.getElementById("rightDiv");
		console.log(windowHeight - scrolltop);
		if(windowHeight - scrolltop < '20'){
    	element.classList.add("mystyle");
			newelement.classList.add("newmystyle");
			//document.getElementById('main').style.padding-top = 50 px;
		}
		else{
			element.classList.remove("mystyle");
			newelement.classList.remove("newmystyle");
		}
	}

	// Code for Accordion

	$(document).ready(function bind(){

	    $('body').on('click','.add',function(){
	        //... event handler code ....
	        document.getElementById("selection").innerHTML = "";
	          var $this = $(this),
	          myCol = $this.closest("td"),
	          myRow = myCol.closest("tr"),
	          targetArea = $("#selection");
	          targetArea.append(myRow.children().not(myCol).text() + "<br />");
	       	document.getElementById("idvalue").value = myRow.children(".value").children(".myval").val();
		document.getElementById("screen_name").value = myRow.children(".value2").children(".myval2").val();
	    });
	  });


			function sendReply(form){
	  				var tweet_id = form.idvalue.value;
	  				var tweet_reply = form.tweet_reply.value;
					var tweet_name = form.screen_name.value;
	        	console.log("tweet_id="+tweet_id+"&tweet_reply="+tweet_reply+"&screen_name="+tweet_name);

	  				if (tweet_reply) {
	        	console.log("tweet_id="+tweet_id+"&tweet_reply="+tweet_reply+"&screen_name="+tweet_name);

	  					 if (window.XMLHttpRequest) {
	           					 xmlhttp = new XMLHttpRequest();
	        						} else {
	            	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	        }
	        xmlhttp.onreadystatechange = function() {
	            if (this.readyState == 4) {
	            		if (this.status == 200) {
	            			alert('Tweet Sent !');
	            			form.tweet_reply.value= "";
	            		}	else {
	            			alert('Tweet Not Sent !');

	            		}
	            }
	        };
	        	var query = "tweet_id="+tweet_id+"&tweet_reply="+tweet_reply+"&screen_name="+tweet_name;
	        	xmlhttp.open("GET","twitter/index.php?"+query,true);
	        	xmlhttp.send();

	  				}

	  			}

	</script>
	</head>

	<body class="line" onscroll="func()">
		<div class="bg" id="header">
  		<img src="train.jpg" alt="" style="width:100%;height:100vh;">
  		<div class="centered"><p style="font-size:60px;">Railway Complaint & Feedback Managemet System</p></div>
		</div>
		<div class="navbar navbar-inverse"  style="width:101%;padding-bottom:1px;" id="main">
			<ul style="font-size: 24px; color: gray;margin-top: 5px;cursor: pointer;">
				<li class="active" style="display: inline;margin-right: 20px"; data-toggle="tab" href="#emergency">
					Emergency
				</li>
				<li style="display:inline;" data-toggle="tab" href="#feedback">
					Feedback
				</li>
				<li class="active" style="display: inline;float:right;margin-right:50px;";><a href="responses.php" target="_blank">
					Previous Complaints</a>
				</li>

			</ul>
		</div>
			<div class='tab-content left' style="min-height:90vh;">
				<div id="emergency" class='tab-pane fade in active'>
					<div id='emergency-content' style="width:90%;padding: 50px;margin:auto;background:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
						<table>

	<?php
	$servername = "127.0.0.1";
	$username = "root";
	$password = "root";
	$database = "twitter";

	$conn = new mysqli($servername, $username, $password,$database);
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

							$sql = "select * from tweets where prediction=1 order by id desc;";
							$result = $conn->query($sql);
							$i=1;
							if ($result->num_rows > 0) {
	    					while($row = $result->fetch_assoc()) {
	        					echo "<tr><td style='color:black' width='180%'>";
	        					echo $row["tweet"];
	        					echo "</td><td width='20%'><button type='button' class='btn btn-info add'><b>Reply</b></button></td>";
	        					echo "<td class='value'><input type='hidden' id='tweet_value' class='myval' name='tweet_value' value=".$row["tweet_id"]."></td>";
							echo "<td class='value2'><input type='hidden' id='user_name' class='myval2' name='user_name' value=".$row["username"]."></td></tr>";
	        					$i++;
	    						}


								} ?>

						</table>
					</div>

				</div>
		        <div id="feedback" class='tab-pane fade'>
					<div id='feedback-content' style="width:90%;padding: 50px;margin:auto;background:white; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
						<table>
						<?php

							$sql = "select * from tweets where prediction=0 order by id desc;";
							$result = $conn->query($sql);
							$i=1;
							if ($result->num_rows > 0) {
	    					while($row = $result->fetch_assoc()) {
	        					echo "<tr><td style='color:black' width='180%'>";
	        					echo $row["tweet"];
	        					echo "</td><td width='20%'><button type='button' class='btn btn-info add'><b>Reply</b></button></td>";
	        					echo "<td class='value'><input type='hidden' id='tweet_value' class='myval' name='tweet_value' value=".$row["tweet_id"]."></td>";
							echo "<td class='value2'><input type='hidden' id='user_name' class='myval2' name='user_name' value=".$row["username"]."></td></tr>";

	        					$i++;
	    						}

								} ?>
						</table>
					</div>
				</div>

			</div>

			<div class="container-fluid right" style="padding-top:50px;" id="rightDiv">
				<div id="selection" style="min-height:200px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);font-size: 16px;padding:10px;"></div>
				<h3 style="color:#c6c6c6;">Type your response...</h3>
				<form>
					<input type="hidden" name="idvalue" id="idvalue" value="">
					<input type="hidden" name="screen_name" id="screen_name" value="">
	  				<textarea id="tweet_reply" name="tweet_reply" rows="10" style="width:100%;"></textarea>
	  					<button type="button" class="btn btn-info" style="width:100%;margin-top: 10px;" onclick="sendReply(this.form)">Reply</button>
	  			</form>
	  		</div>


	</body>
	</html>
