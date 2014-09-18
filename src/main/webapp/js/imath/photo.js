$("#uploadFile").click(function() {
	$("#user").Text = global_uuid_user;
	alert(global_uuid_user);
	ajaxModify();
});

function ajaxModify() {
	$.ajax({
	    url: "rest/api/agora/updateProfile/" + global_uuid_user,
	    cache: false,
	    dataType: "multipart/form-data",
	    type: "POST",
	    success: function(user) {
	        alert("Correct modification");
	    },
	    error: function(error) {
	        console.log("Error getting user information");
	    }
	});	
}