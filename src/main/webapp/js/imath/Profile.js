$("#closeModalProfile").click(function() {
	
});

$("#profileForm").click(function() {
	var propertyClose=document.getElementById("closeProfile");
	var propertyChangePassword=document.getElementById("changePassButton");
	var propertyChangePhotograph=document.getElementById("changePhotograph");
	propertyClose.style.backgroundColor = "FF0000";
	propertyChangePassword.style.backgroundColor = "0000FF";
	propertyChangePhotograph.style.backgroundColor = "0000FF";
	$("#imath-profile-user").modal();
	AjaxUserData(userName);
});

$("#closeProfile").click(function() {
	$("#imath-profile-user").modal('hide');
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
			if (user['photo']!=null) {
				document.getElementById("photoUser").src = "data:image/png;base64," + user['photo'];
			}
	    },
	    error: function(error) {
	    	$("#profilePhotographsg").html("");
	    	showErrorForm("Error recoverying the user data");
	    }
	});
}

function showErrorForm(message) {
	$('.imath-error-message').html(message);
	$('#imath-id-error-message-col').modal('show');
	
}