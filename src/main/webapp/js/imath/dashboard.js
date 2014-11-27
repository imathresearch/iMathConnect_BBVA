/**
 * This module manages the dash board screen
 * @author imath 
 */

// This links each id menu with its title
var jsonMenus = '{"imath-id-projects-menu": "My Projects","imath-id-dashboard-menu": "Dashboard","imath-id-instances-menu": "My Instances"}';

var gloabal_menu_text = JSON.parse(jsonMenus);

var global_uuid_user = null;

var userInfo = null;
// We charge user info
var global_month_array = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

function numberToMonth(i) {
	ret = "";
	if (i>=0 && i <=11) {
		ret = global_month_array[i];
	}
	return ret; 
}


function placeDashboard(){
	setSelectMenu("imath-id-dashboard-menu");
	jQuery.get('dashboard.html', function(data) {
		$(".imath-main-row").html(data);		
		if ($( "#angle" ).hasClass( "fa-angle-down" )){
			$("#angle" ).toggleClass( "fa-angle-down" );
			$("#angle" ).toggleClass( "fa-angle-left" );
		}
		$(".treeview-menu").css("display", "none");
		$(".imath-section").css("display", "none");
		$(".content").css("display", "block");
		$(".content-header").css("display", "block");
		ajaxUserInfo();
		
	});
	
}

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
			if (user['photo']!=null) {
				document.getElementById("userphoto").src = "data:image/png;base64," + user['photo'];
				document.getElementById("userphoto2").src = "data:image/png;base64," + user['photo'];
	    	}
			ajaxOwnProjects();
			ajaxOwnInstances();
			ajaxOwnNotifications();
			ajaxPublicInstances();
			ajaxColProjects();
			ajaxInfo();
			setSelectMenu("imath-id-dashboard-menu");
			$("#section_iMathCloud").css("display", "none");
			$("#imath-id-new-project-button-dashboard").click(function() {
				var uuid = undefined;
				var source = "dashboard";
				placeLayoutProjects(uuid, source);
			});

	    },
	    error: function(error) {
	        console.log("Error getting user information");
	    }
	});	
}

function ajaxInfo() {
	$.ajax({
	    url: "rest/api/agora/getInfo/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(info) {
	        var numUsers = info['numUsers'];
	        var numProjects = info['numProjects'];
	        var numInstances = info['numInstances'];
	        var numUsersCol = info['numUsersCol'];
	        $(".imath-num-users-col").html(numUsersCol);
	        $(".imath-num-users").html(numUsers);
	        $(".imath-num-projects").html(numProjects);
	        $(".imath-num-instances").html(numInstances);
	    },
	    error: function(error) {
	        console.log("Error getting information");
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
	placeWaiting("imath-waiting-own-projects");
	$.ajax({
	    url: "rest/api/agora/getOwnProjects/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(projects) {
	    	unplaceWaiting("imath-waiting-own-projects");
	    	var htmlTable = generateTableOfProjects(projects, callbackString);
			//$(".imath-own-projects").html(htmlTable);
	    },
	    error: function(error) {
	    	unplaceWaiting("imath-waiting-own-projects");
	        console.log("Error getting own projects");
	    }
	});	
	
}

function ajaxColProjects() {
	placeWaiting("imath-waiting-col-projects");
	$.ajax({
	    url: "rest/api/agora/getColProjects/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(projects) {
	    	unplaceWaiting("imath-waiting-col-projects");
	    	var htmlTable = generateTableOfColProjects(projects);
			//$(".imath-collaborations").html(htmlTable);
	    },
	    error: function(error) {
	    	unplaceWaiting("imath-waiting-col-projects");
	        console.log("Error getting own projects");
	    }
	});	
	
}

function ajaxOwnInstances() {
	placeWaiting("imath-waiting-own-instances");
	$.ajax({
	    url: "rest/api/agora/instances/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(instances) {
	    	unplaceWaiting("imath-waiting-own-instances");
	    	var htmlTable = generateTableOfInstances(instances, false, true);
			$(".imath-own-instances").html(htmlTable);
	    },
	    error: function(error) {
	    	unplaceWaiting("imath-waiting-own-instances");
	        console.log("Error getting own instances");
	    }
	});	
}

function ajaxPublicInstances() {
	placeWaiting("imath-waiting-pub-instances");
	$.ajax({
	    url: "rest/api/agora/instances/ ",
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(instances) {
	    	unplaceWaiting("imath-waiting-pub-instances");
	    	var htmlTable = generateTableOfInstances(instances, true, true);
			$(".imath-public-instances").html(htmlTable);
	    },
	    error: function(error) {
	    	unplaceWaiting("imath-waiting-pub-instances");
	        console.log("Error getting public instances");
	    }
	});	
}

function dateToNice(date) {
	var year = date.getFullYear();
	var monthText = numberToMonth(date.getMonth());
	var day = date.getDate();
	dayText = day.toString();
	lastDigit = dayText[dayText.length-1];
	ord = getOrdinal(lastDigit);
	if (day==11) {
		ord = "th";
	}
	else {
		if (day==12) {
			ord = "th";
		}
	}
	var ret = monthText + " " + dayText + ord + " " + year;
	return ret;
}

function getOrdinal(digitT) {
	ret = "th";
	if (digitT=="1") ret="st";
	if (digitT=="2") ret="nd";
	if (digitT=="3") ret="rd";
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
	var ret = htmlTableRowHead(['', 'Name', 'Date', 'Description', 'Owner', 'Collaborators', 'Resources']);
	var names = [];
	var byteFotos = [];
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
			var nameImage = "TableOfColProjects" + i + ii;
			rowCol = rowCol + '<td><div class="wrapImgTable"><img class="userImg" src="img/avatar04.png" id="' + nameImage + '" alt="' + collaborators[ii]['userName'] + '" class="offline"  /></td><td><i>' + collaborators[ii]['userName'] + "</i><br><small>" + collaborators[ii]['organization'] +'</small></div></td></tr></table>';
			names.push(nameImage);
			if (collaborators[ii]['photo']!=null) {
				byteFotos.push(collaborators[ii]['photo']);
			}
			else {
				byteFotos.push(null);
			}
		}
		var rowIcon = null;
		rowIcon = "<a onclick='runEmbebediMathCloud(\""+uuid+ "\", \"" + name + "\")' style='cursor: pointer;' title='Run iMathCloud'>" + faIcon("fa-play") + "</a>";
		
		var rowInstance = faIcon("fa-gears") + " <b>" + project['instance']['cpu'] + "</b> <small>vCPUs</small> <br>";
		rowInstance += faIcon("fa-film") + " <b>" + project['instance']['ram'] + "</b> <small>MiB</small><br> ";
		rowInstance += faIcon("fa-cloud") + " <b>" + project['instance']['stg'] + "</b> <small>GiB</small> ";
		rowName =  name;
		
		var owner = project['owner'];
		var rowOwner = "<table><tr>";
		var nameImage2 = "TableOfColProjects" + i;
		names.push(nameImage2);
		if (owner['photo']!=null) {
			byteFotos.push(owner['photo']);
		}
		else {
			byteFotos.push(null);
		}
		rowOwner = rowOwner + '<td><div class="wrapImgTable"><img class="userImg" id="' + nameImage2 + '" src="img/avatar04.png" alt="' + owner['userName'] + '" class="offline"/></td><td><i>' + owner['userName'] + "</i><br><small>" + owner['organization'] + '</small></div></td></tr></table>';
		ret = ret + htmlTableRowData([rowIcon, rowName,dateText,desc,rowOwner,rowCol,rowInstance], uuid);	
	}
	$(".imath-collaborations").html(ret);
	for (ii=0; ii<names.length; ii++) {
		if (byteFotos[ii]!=null) {
			document.getElementById(names[ii]).src = "data:image/png;base64," + byteFotos[ii];
		}
	}

	return ret;
}

function generateTableOfProjects(projects, callbackString) {
	var ret = htmlTableRowHead(['', 'Name', 'Date', 'Description', 'Collaborators', 'Resources', '#']);
	var names = [];
	var byteFotos = [];
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
			var nameImage = "TableOfProjects" + i + ii;
			rowCol = rowCol + '<td><div class="wrapImgTable"><img class="userImg" src="img/avatar04.png" id="' + nameImage + '" alt="' + collaborators[ii]['userName'] + '" class="offline"/></td><td><i>' + collaborators[ii]['userName'] + "</i><br><small>" + collaborators[ii]['organization'] + '</small> </div></td></tr></table>';
			names.push(nameImage);
			if (collaborators[ii]['photo']!=null) {
				byteFotos.push(collaborators[ii]['photo']);
			}
			else {
				byteFotos.push(null);
			}
		}
		var rowIcon = null;
		rowIcon = "<a onclick='runEmbebediMathCloud(\""+uuid+ "\", \"" + name + "\")' style='cursor: pointer;' title='Run iMathCloud'>" + faIcon("fa-play") + "</a>";
		
		var rowInstance = faIcon("fa-gears") + " <b>" + project['instance']['cpu'] + "</b> <small>vCPUs</small> <br>";
		rowInstance += faIcon("fa-film") + " <b>" + project['instance']['ram'] + "</b> <small>MiB</small><br> ";
		rowInstance += faIcon("fa-cloud") + " <b>" + project['instance']['stg'] + "</b> <small>GiB</small> ";
		var rowName = "";
		var action = "";
		if (typeof callbackString == "undefined") {
			rowName = "<a onclick='placeLayoutProjects(\""+uuid+ "\")' style='color:blue;cursor: pointer;text-decoration: underline;'>" + name + "</a>";
			action = "<a onclick='removeProject(\"" + uuid + "\")' style='cursor: pointer;' title='Remove' )><i class='fa fa-minus-circle'></i></a>";
		} else {
			rowName = "<a onclick='" + callbackString + "(\""+uuid+ "\")' style='color:blue;cursor: pointer;text-decoration: underline;'>" + name + "</a>";
			action = "<a onclick='removeProject(\"" + uuid + "\",\"" + callbackString + "\")' style='cursor: pointer;' title='Remove' )><i class='fa fa-minus-circle'></i></a>";
		}		
		ret = ret + htmlTableRowData([rowIcon, rowName,dateText,desc,rowCol,rowInstance, action], uuid);	
	}
	$(".imath-own-projects").html(ret);
	for (ii=0; ii<names.length; ii++) {
		if (byteFotos[ii]!=null) {
			document.getElementById(names[ii]).src = "data:image/png;base64," + byteFotos[ii];
		}
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
function generateTableOfInstances(instances, pub, putHeader, callbackString) {
	var ret = "";
	if (putHeader) {
		ret = htmlTableRowHead(['#', 'Name', faIcon("fa-gears")+' vCPUs', 
	                            faIcon("fa-film") + ' RAM <small>MiB</small>', 
	                            faIcon("fa-cloud") + 'Storage <small>GiB</small>', 'Date']);
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
		var name = instance['name'];
		var rowName = "";
		if (typeof callbackString == "undefined") {
			rowName = "<a onclick='placeLayoutInstances(\""+uuid+ "\")' style='color:blue;cursor: pointer;text-decoration: underline;'>" + name + "</a>";
		} else {
			rowName = "<a onclick='" + callbackString + "(\""+uuid+ "\")' style='color:blue;cursor: pointer;text-decoration: underline;'>" + name + "</a>";
		}
		//rowMore = "<a href='onclick=showInstancePage(\""+uuid+ "\")'>+</a>"; 
		ret = ret + htmlTableRowData([rowIcon, rowName, cpu, ram, stg, dateText], uuid);	
	}
	return ret;
}


ajaxUserInfo();
