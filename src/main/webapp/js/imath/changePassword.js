$("#closeModalPassword").click(function() {
	
});

$("#changePassButton").click(function(){
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

$("#changePassButton").click(function() {

    var url = "changePassword";
    var SerializedForm = $('#profileForm').serializeArray();
    //var Data = JSON.stringify(SerializedForm);
    var Data = serializeToJson(SerializedForm);
    $("#profileMsg").html("");
    $.ajax({
        url: url,
        cache: false,
        data: Data,
        type: "POST",
        success: function(data) {
            $("#profileMsg").html("<span style='color:green'>" + data + "</span>");
            setTimeout("$('#profilePopup').dialog('close')",1500);
        },
        error: function(data) {
            $("#profileMsg").html("<span style='color:red'>" + data.responseText + "</span>");
        }
    });
});