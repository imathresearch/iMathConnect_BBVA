function numberToMonth(i) {
	var ret;
	switch(i) {
    case 0:
        ret="Jan";
        break;
    case 1:
        ret="Feb";
        break;
    case 2:
        ret="Mar";
        break;
    case 3:
        ret="Apr";
        break;
    case 4:
        ret="May";
        break;
    case 5:
        ret="Jun";
        break;
    case 6:
        ret="Jul";
        break;
    case 7:
        ret="Aug";
        break; 
    case 8:
        ret="Sep";
        break;
    case 9:
        ret="Oct";
        break;
    case 10:
        ret="Nov";
        break;
    case 11:
        ret="Dec";
        break;       
    default:
        ret="";
	}
	return ret; 
}

var uuidUser = null;
var userInfo = null;
// We charge user info

function ajaxUserInfo() {
	$.ajax({
	    url: "rest/api/agora/getUserByUserName/" + userName,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(user) {
	        userInfo = user;        	
			uuidUser = user['UUID'];
			$(".username").html(userName);
			var cDate = new Date(user['creationDate']);
			var month = numberToMonth(cDate.getMonth());
			var year = cDate.getFullYear();
			$(".usercreationdate").html('<small>Member since ' + month + ". " + year+'</small>');
			ajaxOwnProjects();
	    },
	    error: function(error) {
	        console.log("Error getting user information");
	    }
	});	
}

function ajaxOwnProjects() {
	$.ajax({
	    url: "rest/api/agora/getOwnProjects/" + uuidUser,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(projects) {
	    	var htmlTable = generateTableOfProjects(projects);
			$(".imath-own-projects").html(htmlTable);
	    },
	    error: function(error) {
	        console.log("Error getting user information");
	    }
	});	
	
}

function dateToNice(date) {
	var year = date.getFullYear();
	var monthText = numberToMonth(date.getMonth());
	var day = date.getDay();
	dayText = day.toString();
	lastDigit = dayText[dayText.length-1];
	ord = getOrdinal(lastDigit);
	var ret = monthText + " " + dayText + ord + " " + year;
	return ret;
}

function getOrdinal(digitT) {
	ret = "th";
	if (digitT=="1") ret="st";
	if (digitT=="2") ret="nd";
	return ret;
}

function htmlTableRowHead(rows) {
	return htmlTableRow(rows, "th");
}

function htmlTableRowData(rows) {
	return htmlTableRow(rows, "td");
}

function faIcon(icon) {
	return '<i class="' + icon + '"></i>';
}

function htmlTableRow(rows, tag) {
	var ret = "<tr>";
	for(var i=0; i<rows.length; i++) {
		ret += "<" + tag + ">" + rows[i] + "</" + tag + ">";
	}
	ret += "</tr>";
	return ret;
}


function generateTableOfProjects(projects) {
	var ret = htmlTableRowHead(['Name', 'CreationDate', 'Description', 'Collaborators', 'Resources']);
	for(var i=0; i<projects.length; i++) {
		project = projects[i];
		var creationDate = new Date(project['creationDate']);
		var dateText = dateToNice(creationDate);
		var name = project['name'];
		var desc = project['desc'];
		var uuid = project['UUID'];
		var collaborators = project['userCol'];
		var rowCol = "";
		for(var i=0; i< collaborators.length; i++) {
			rowCol = rowCol + '<small><img src="img/avatar5.png" alt="' + name + '" class="offline"  height="42" width="42"/>' +
			collaborators[i]['userName'] + ", " + collaborators[i]['organization'] 
			+'</small> '; 
		}
		if (collaborators.length>0) {
			name += faIcon ("fa-users");
		} else {
			name += faIcon ("fa-user");
		}
		var rowInstance = faIcon("fa-gears") + " " + project['instance']['cpu'] + " ";
		rowInstance += faIcon("fa-film") + " " + project['instance']['ram'] + " MiB ";
		rowInstance += faIcon("fa-cloud") + " " + project['instance']['stg'] + " GiB ";
		ret += htmlTableRowData([name,dateText,desc,rowCol,rowInstance]);
	}
	return ret;
}

ajaxUserInfo();
