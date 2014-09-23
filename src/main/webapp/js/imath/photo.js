$("#closeModalPhotograph").click(function() {
	
});

$("#changePhotograph").click(function() {
	$("#profilePhotographsg").html("");
	$("#uploadedFile").val("");
	$("#imath-modify-user-photograph").modal();
});

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
	    	$("#profilePhotographsg").html("<span style='color:green'>Photograph modified</span>");
            setTimeout("$('#imath-modify-user-photograph').modal('hide')",1500);
	    },
	    error: function(error) {
	    	$("#profilePhotographsg").html("<span style='color:green'>Error modifying the photo</span>");
	    }
	});
}