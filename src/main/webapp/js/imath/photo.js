$("#closeModalPhotograph").click(function() {
	
});

$("#changePhotograph").click(function() {
	var propertyClose=document.getElementById("closeFile");
	var propertyUpload=document.getElementById("uploadFile");
	propertyClose.style.backgroundColor = "FF0000";
	propertyUpload.style.backgroundColor = "0000FF";
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
    formData.append('uploadedFile', file[0]);
    
    var formatOK = checkImage(file[0]);
    
    if (formatOK){
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
	            setTimeout("$('#imath-modify-user-photograph').modal('hide')",1000);            
	            $('.userImg[alt="User Image"]').attr("src", "data:image/png;base64," + user['photo']);
	            $('.userImg[alt=\"' + user['userName'] + '\"]').attr("src", "data:image/png;base64," + user['photo']);
	            //location.reload();
		    },
		    error: function(error) {
		    	$("#profilePhotographsg").html("<span style='color:red'>Error modifying the photo</span>");
		    	//showErrorForm("Error saving the photograph");
		    }
		});
    }
    else{
    	$("#profilePhotographsg").html("<span style='color:red'>Maximum image size is 5MB. Supported types files: png, gif, jpeg, pjpeg</span>");
    }
}

function checkImage(image){
	var size = image.size;
	var type = image.type;
	
	if (size > 5048576){ // the size is bigger than 5MB
		return false;
	}
	        
   switch(type){
        case 'image/png':
        case 'image/gif':
        case 'image/jpeg':
        case 'image/pjpeg':
            return true;
            break;
        default:
        	return false;
           	break;
    }	
}

$("#closeFile").click(function() {
	$("#imath-modify-user-photograph").modal('hide');
});

function showErrorForm(message) {
	$('.imath-error-message').html(message);
	$('#imath-id-error-message-col').modal('show');
	
}