$("#uploadFile").click(function() {
	ajaxModify();
});

function ajaxModify() {
    var formData = new FormData();
    var file = document.getElementById('uploadedFile').files;
    for (i = 0, j = file.length; i < j; i++) {
        formData.append('uploadedFile', file[i]);
    }
	$.ajax({
	    url: "rest/api/agora/updateProfile/" + global_uuid_user,
	    data : formData,
	    type: "POST",
	    cache: false,
	    dataType: "json",
	    processData : false,
		contentType : false,
	    success: function(user) {
	        alert("Correct modification"); 
	        alert(user);
	        console.log("Correct execution");
	    },
	    error: function(error) {
	    	alert("Error");
	        console.log("Error getting user information");
	    }
	});	
}