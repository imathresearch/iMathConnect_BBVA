/**
 * 
 */

function ajaxOwnNotifications(){
	$.ajax({
	    url: "rest/api/agora/getNotifications/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(notifications) {
	    	var numNotifications = notifications.length;
	    	var numNotificationHTML = generateNumNotifications(numNotifications);
	    	$(".number-notifications").html(numNotificationHTML);
	    	var listNotificationsHTML = generateListNotifications(notifications);
	    	$(".list-notifications").html(listNotificationsHTML);
	    	for(var i=0; i<notifications.length; i++) {
	    		notification = notifications[i];
	    		console.log(notification['subject']);
	    		console.log(notification['creationDate']);
	    	}
	    },
	    error: function(error) {
	        console.log("Error getting own notifications");
	    }
	});
}

function generateNumNotifications(numNotifications){	
    code = '<i class="fa fa-warning"></i>';
    code = code + '<span class="label label-warning">' + numNotifications + '</span>';
	return code;
}

function generateListNotifications(notifications){
	var dicTypeNotification = [];
	dicTypeNotification[0] = '<i class="fa fa-users info"></i>';
	dicTypeNotification[1] = '<i class="fa fa-lock success"></i>';
	
	var numNotifications = notifications.length;	
	var code = '<li class="header">You have ' + numNotifications + ' notifications</li>';
	code = code + '<li><ul class="menu">';
	
	for (var i = 0; i < numNotifications; i++){
		notification = notifications[i];
		type = notification['type'];
		code = code + '<li>';
		var jsonNotification = JSON.stringify(notification).replace(/"/g, '\'');
		code = code + '<a href="javascript:showDetailNotification(' + jsonNotification + ')">';
		code = code + dicTypeNotification[notification['type']] + notification['subject'];
		code = code + '</a>';
		code = code + '</li>';
	}
	
	code = code + '</ul></li>';
	console.log(code);
	return code;
	
}


function showDetailNotification(notification){
	
	var notificationInfoHTML = generateNotificationInfo(notification);
	$(".notification-info").html(notificationInfoHTML);	
	$("#notification-detail").modal();
}


function generateNotificationInfo(notification){
	var typeNotification = [];
	typeNotification [0] = "Public Notification";
	typeNotification [1] = "Private Notification";
		
	var code = '<div class="modal-body">';
	code = code + '<h3 style="text-align: center; color:#4c4c4c">'+ notification['subject'] +'</h3>';
	
	code = code + '<div class="form-group">';
	code = code + '<label>Description</label>';
	code = code + '<input type="text" class="form-control" placeholder="' + notification['text'] + '" disabled>';
	code = code + '</div>';
	code = code + '<div class="form-group">';
	code = code + '<label>Creation Date</label>';
	code = code + '<input type="text" class="form-control" placeholder="' + transformDate(notification['creationDate']) + '" disabled>';
	code = code + '</div>';
	code = code + '</div>'
	
	code = code + '<div class="modal-footer">';
	code = code + '<p style="color:#7f7f7f"><i>' + typeNotification[notification['type']] + '</i></p>'
    
	code = code + '</div>';
	
	
	return code;
}


function transformDate(dateEpoch){
	var timestamp = dateEpoch;
	var date = new Date(timestamp);
	var stringFormat = date.toDateString() + " " + date.toTimeString();
	return stringFormat;
}