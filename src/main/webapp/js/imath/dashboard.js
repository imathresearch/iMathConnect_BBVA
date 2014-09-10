/**
 * This module manages the dash board screen
 * @author imath 
 */

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

// This links each id menu with its title
var gloabal_menu_text = JSON.parse('{"imath-id-projects-menu":"My Projects","imath-id-dashboard-menu": "Dashboard"}');

var global_uuid_user = null;

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
	        global_uuid_user = user['UUID'];
			$(".username").html(userName);
			var cDate = new Date(user['creationDate']);
			var month = numberToMonth(cDate.getMonth());
			var year = cDate.getFullYear();
			$(".usercreationdate").html('<small>Member since ' + month + ". " + year+'</small>');
			ajaxOwnProjects();
			ajaxOwnInstances();
			ajaxPublicInstances();
			ajaxColProjects();
			setSelectMenu("imath-id-dashboard-menu");
	    },
	    error: function(error) {
	        console.log("Error getting user information");
	    }
	});	
}

function setSelectMenu(idDOM) {
	// first we unselect everything
	$(".imath-menu").removeClass("active");
	// and we select the concrete menu item
	$("#" + idDOM).addClass("active");
	// We plce the proper text in the title
	$(".imath-title-menu").html(gloabal_menu_text[idDOM]);
}

function ajaxOwnProjects(callbackString) {
	$.ajax({
	    url: "rest/api/agora/getOwnProjects/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(projects) {
	    	var htmlTable = generateTableOfProjects(projects, callbackString);
			$(".imath-own-projects").html(htmlTable);
	    },
	    error: function(error) {
	        console.log("Error getting own projects");
	    }
	});	
	
}

function ajaxColProjects() {
	$.ajax({
	    url: "rest/api/agora/getColProjects/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(projects) {
	    	var htmlTable = generateTableOfColProjects(projects);
			$(".imath-collaborations").html(htmlTable);
	    },
	    error: function(error) {
	        console.log("Error getting own projects");
	    }
	});	
	
}

function ajaxOwnInstances() {
	$.ajax({
	    url: "rest/api/agora/instances/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(instances) {
	    	var htmlTable = generateTableOfInstances(instances, false, true);
			$(".imath-own-instances").html(htmlTable);
	    },
	    error: function(error) {
	        console.log("Error getting own instances");
	    }
	});	
}

function ajaxPublicInstances() {
	$.ajax({
	    url: "rest/api/agora/instances/ ",
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(instances) {
	    	var htmlTable = generateTableOfInstances(instances, true, true);
			$(".imath-public-instances").html(htmlTable);
	    },
	    error: function(error) {
	        console.log("Error getting public instances");
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

function htmlTableRowData(rows, uuid) {
	return htmlTableRow(rows, "td", uuid);
}

function faIcon(icon) {
	return '<i class="fa ' + icon + '"></i>';
}

function htmlTableRow(rows, tag, uuid) {
	var ret;
	if (uuid != null) {
		ret = "<tr id='" + uuid + "'>";
	} else {
		ret = "<tr>";
	} 
	
	for(var i=0; i<rows.length; i++) {
		ret += "<" + tag + ">" + rows[i] + "</" + tag + ">";
	}
	ret += "</tr>";
	return ret;
}


function generateTableOfColProjects(projects) {
	var ret = htmlTableRowHead(['#', 'Name', 'Date', 'Description', 'Owner', 'Collaborators', 'Resources']);
	for(var i=0; i<projects.length; i++) {
		project = projects[i];
		var creationDate = new Date(project['creationDate']);
		var dateText = dateToNice(creationDate);
		var name = project['name'];
		var desc = project['desc'];
		var uuid = project['UUID'];
		var collaborators = project['userCol'];
		var rowCol = "";
		for(var ii=0; ii< collaborators.length; ii++) {
			rowCol = rowCol + "<table><tr>";
			rowCol = rowCol + '<td><img src="img/avatar04.png" alt="' + collaborators[ii]['userName'] + '" class="offline"  height="32" width="32"/></td><td><i>' + collaborators[ii]['userName'] + "</i><br><small>" + collaborators[ii]['organization'] +'</small> </td></tr></table>'; 
		}
		var rowIcon = null;
		if (collaborators.length>0) {
			rowIcon = faIcon ("fa-users");
		} else {
			rowIcon = faIcon ("fa-eye");
		}
		var rowInstance = faIcon("fa-gears") + " <b>" + project['instance']['cpu'] + "</b> <small>vCPUs</small> <br>";
		rowInstance += faIcon("fa-film") + " <b>" + project['instance']['ram'] + "</b> <small>MiB</small><br> ";
		rowInstance += faIcon("fa-cloud") + " <b>" + project['instance']['stg'] + "</b> <small>GiB</small> ";
		//rowName = "<a href='onclick=showProjectPage(\""+uuid+ "\")'>" + name + "</a>";
		rowName = name;
		var owner = project['owner'];
		var rowOwner = "<table><tr>";
		rowOwner = rowOwner + '<td><img src="img/avatar04.png" alt="' + owner['userName'] + '" class="offline"  height="32" width="32"/></td><td><i>' + owner['userName'] + "</i><br><small>" + owner['organization'] + '</small> </td></tr></table>'; 
		ret = ret + htmlTableRowData([rowIcon, rowName,dateText,desc,rowOwner,rowCol,rowInstance], uuid);	
	}
	return ret;
}

function generateTableOfProjects(projects, callbackString) {
	var ret = htmlTableRowHead(['#', 'Name', 'Date', 'Description', 'Collaborators', 'Resources']);
	for(var i=0; i<projects.length; i++) {
		project = projects[i];
		var creationDate = new Date(project['creationDate']);
		var dateText = dateToNice(creationDate);
		var name = project['name'];
		var desc = project['desc'];
		var uuid = project['UUID'];
		var collaborators = project['userCol'];
		var rowCol = "";
		for(var ii=0; ii< collaborators.length; ii++) {
			rowCol = rowCol + "<table><tr>";
			rowCol = rowCol + '<td><img src="img/avatar04.png" alt="' + collaborators[ii]['userName'] + '" class="offline"  height="32" width="32"/></td><td><i>' +
			collaborators[ii]['userName'] + "</i><br><small>" + collaborators[ii]['organization'] 
			+'</small> </td></tr></table>'; 
		}
		var rowIcon = null;
		if (collaborators.length>0) {
			rowIcon = faIcon ("fa-users");
		} else {
			rowIcon = faIcon ("fa-eye");
		}
		var rowInstance = faIcon("fa-gears") + " <b>" + project['instance']['cpu'] + "</b> <small>vCPUs</small> <br>";
		rowInstance += faIcon("fa-film") + " <b>" + project['instance']['ram'] + "</b> <small>MiB</small><br> ";
		rowInstance += faIcon("fa-cloud") + " <b>" + project['instance']['stg'] + "</b> <small>GiB</small> ";
		if (typeof callbackString == "undefined") {
			rowName = "<a onclick='placeLayoutProjects(\""+uuid+ "\")' style='color:blue;cursor: pointer;text-decoration: underline;'>" + name + "</a>";
		} else {
			rowName = "<a onclick='" + callbackString + "(\""+uuid+ "\")' style='color:blue;cursor: pointer;text-decoration: underline;'>" + name + "</a>";
		}
			
		ret = ret + htmlTableRowData([rowIcon, rowName,dateText,desc,rowCol,rowInstance], uuid);	
	}
	return ret;
}

/**
 * Generates the content of a html table for instances 
 * @param instances		The list of InstanceDTO received from the server
 * @param pub			Boolean indicating they are public (true) or private (false)
 * @param putHeader		
 * @returns {String}
 */
function generateTableOfInstances(instances, pub, putHeader) {
	var ret = "";
	if (putHeader) {
		ret = htmlTableRowHead(['#', faIcon("fa-gears")+' vCPUs', 
	                            faIcon("fa-film") + ' RAM', 
	                            faIcon("fa-cloud") + 'Storage', 'Date']);
	}
	
	for(var i=0; i<instances.length; i++) {
		instance = instances[i];
		var creationDate = new Date(instance['creationDate']);
		var dateText = dateToNice(creationDate);
		var cpu = instance['cpu'];
		var ram = instance['ram'];
		var stg = instance['stg'];
		var uuid = instance['UUID'];
		var rowIcon = '<span class="badge bg-red">Pr</span>';
		if (pub) {
			rowIcon = '<span class="badge bg-light-blue">Pu</span>';
		}
		
		//rowMore = "<a href='onclick=showInstancePage(\""+uuid+ "\")'>+</a>"; 
		ret = ret + htmlTableRowData([rowIcon, cpu, ram, stg, dateText], uuid);	
	}
	return ret;
}


ajaxUserInfo();
