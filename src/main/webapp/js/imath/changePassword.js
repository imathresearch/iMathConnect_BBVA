$("#closeModalPassword").click(function() {
	
});

$("#changePassButton").click(function(){
	$("#profilePasswordMsg").html("");
	$("#passwordOld").val("");
	$("#passwordNew").val("");
	$("#passwordNewConf").val("");
	$("#imath-modify-password-user").modal();
});

function serializeToJson(serializer){
    var _string = '{';
    for(var ix in serializer)
    {
        var row = serializer[ix];
        _string += '"' + row.name + '":"' + row.value + '",';
    }
    var end =_string.length - 1;
    _string = _string.substr(0, end);
    _string += '}';
    return JSON.parse(_string);
}

$("#changePassword").click(function() {

    var url = "changePassword";
    var SerializedForm = $('#userPasswordForm').serializeArray();
    //var Data = JSON.stringify(SerializedForm);
    var Data = serializeToJson(SerializedForm);
    $("#profilePasswordMsg").html("");
    $.ajax({
        url: url,
        cache: false,
        data: Data,
        type: "POST",
        success: function(data) {
            $("#profilePasswordMsg").html("<span style='color:green'>" + data + "</span>");
            setTimeout("$('#imath-modify-password-user').modal('hide')",1500);
        },
        error: function(data) {
            $("#profilePasswordMsg").html("<span style='color:red'>" + data.responseText + "</span>");
            showErrorForm("Error saving the new password");
        }
    });
});

function showErrorForm(message) {
	$('.imath-error-message').html(message);
	$('#imath-id-error-message-col').modal('show');
	
}