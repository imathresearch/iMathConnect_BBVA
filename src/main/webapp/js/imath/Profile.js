$("#closeModalProfile").click(function() {
	
});

$("#profileForm").click(function() {
	$("#imath-profile-user").modal();
	AjaxUserData(userName);
});

$("#close").click(function() {
		
});

function AjaxUserData(userName) {
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
			if (user['phone2']!=null) {
				document.getElementById("photoUser").src = "data:image/png;base64," + user['photo'];
			}
			else {
				document.getElementById("photoUser").src = "img/avatar04.png";
			}
	    },
	    error: function(error) {
	    	$("#profilePhotographsg").html("");
	    }
	});
}