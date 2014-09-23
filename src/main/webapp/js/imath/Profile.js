
$("#profileForm").click(function() {
	$.ajax({
	    url: "rest/api/agora/getUserByUserName/" + userName,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(user) {
	        userInfo = user;        	
	        global_uuid_user = user['UUID'];
			$("#username").val(userName);
			$("#email").val(user['eMail']);
			$("#phone1").val(user['phone1']);
			$("#phone2").val(user['phone2']);
			document.getElementById("photoUser").src = "data:image/png;base64," + user['photo'];
	    },
	    error: function(error) {
	        console.log("Error getting user information");
	    }
	});
});

$("#close").click(function() {
		
});