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
	var ret = monthText + " " + dayText + ord + " " year;
	return ret;
}

function getOrdinal(digitT) {
	ret = "th";
	if (digitT=="1") ret="st";
	if (digitT=="2") ret="nd";
	return ret;
}

function generateTableOfProjects(projects) {
	for(var i=0; i<projects.length; i++) {
		project = projects[i];
		var creationDate = new Date(project['creationDate']);
		var dateText = dateToNice(creationDate);
		var name = project['name'];
		var desc = project['desc'];
		var uuid = project['UUID'];
		var date = new Date(job['startDate']);
		var aux = "<tr id='" + uuid + "' class='" + genClassJobContextMenu(job['id']) + "' >";
		aux = aux +"<td>" + getImageJobStatus(job['state']) + "</td>";
		aux = aux + "<td><a onclick='showJobStatus(\""+ job['id'] + "\")' + >" + job['id'] + "</a></td>";
		aux = aux + "<td><a onclick='showJobStatus(\""+ job['id'] + "\")' + >" + job['description'] + "</a></td>";
		aux = aux + '<td data-value="' + job['startDate'] + '">' + date + '</td>';		
		aux = aux + '<td data-value="' + jobPercentCompletion(job) + '">' + jobPercentCompletion(job) + '</td>';
		aux = aux + "</tr>";
		$( "#jobsTBODY" ).append(aux);
		jobsTable[job['id'].toString()] = job['state'];
		
	}	
}

ajaxUserInfo();
ajaxOwnProjects();
