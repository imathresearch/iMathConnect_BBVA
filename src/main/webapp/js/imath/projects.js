/**
 * This module manages the projects screen
 * @author imath 
 */

var global_uuid_project_selected = null;

function placeLayoutProjects() {
	jQuery.get('projects.html', function(data) {
		$(".imath-main-row").html(data);
		initProjectView();
	});
}

function initProjectView() {
	ajaxOwnProjects();
	$("#imath-id-own-projects").delegate("tr", "click", function(e) {
		var uuid = $(e.currentTarget).attr('id');
		uploadProject(uuid);
	});
}

function ajaxInstancesComboBox() {
	
	$.ajax({
	    url: "rest/api/agora/instances/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(instances) {
	    	var htmlTableSelectPrivate = generateTableOfInstances(instances, false);
	    	$.ajax({
	    	    url: "rest/api/agora/instances/ ",
	    	    cache: false,
	    	    dataType: "json",
	    	    type: "GET",
	    	    success: function(instances) {
	    	    	var htmlTableSelectPublic = generateTableOfInstances(instances, true);
	    			$(".imath-select-instances").html(htmlTableSelectPrivate+htmlTableSelectPublic);
	    	    },
	    	    error: function(error) {
	    	        console.log("Error getting public instances");
	    	    }
	    	});	
			
	    },
	    error: function(error) {
	        console.log("Error getting own instances");
	    }
	});	
}

function generateTableOfInstancesSelect(instances, pub) {
	var ret = "";
	for(var i=0; i<instances.length; i++) {
		instance = instances[i];
		var cpu = instance['cpu'];
		var ram = instance['ram'];
		var stg = instance['stg'];
		var uuid = instance['uuid'];
		var rowIcon = '<span class="badge bg-red">Pr</span>';
		if (pub) {
			rowIcon = '<span class="badge bg-light-blue">Pu</span>';
		}
		var line = rowIcon + cpu + ram +stg;
		ret = ret + "<option value='" + uuid + "'>" + line + "</option>"
	}
	return ret;
}


function uploadProject(uuid) {
	$.ajax({
	    url: "rest/api/agora/getProject/" + global_uuid_user + "/" + uuid,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(project) {
	    	global_uuid_project_selected = uuid;
	    	$(".imath-project-name").html(project['name']);
	    	$("#idProjectName").attr("value", project['name']);
	    	$("#idProjectDescription").attr("value", project['desc']);
	    	var creationDate = new Date(project['creationDate']);
			var dateText = dateToNice(creationDate);
			$("#idCreationDate").attr("value", dateText);
			var b = false;
			var instance = project['instance'];
			var instanceTableHtml;
			var fakeInstances = [];
			fakeInstances[0] = instance
			if(project['instance']['owner']==null) {	// public
				instanceTableHtml = generateTableOfInstances(fakeInstances, true);
			} else {
				instanceTableHtml = generateTableOfInstances(fakeInstances, false);
			}
			$(".imath-select-instance").html(instanceTableHtml);
			var collaborators = project['userCol'];
			collaboratorsHtml = generateTableOfCollaborators(collaborators);
			$(".imath-collaborators"). html(collaboratorsHtml);
	    },
	    error: function(error) {
	        console.log("Error getting project");
	    }
	});	
}

function generateTableOfCollaborators(collaborators) {
	var htmlRet = htmlTableRowHead(["Pic", "User Name", "Organization"]);
	for(var ii=0; ii< collaborators.length; ii++) {
		var image = '<img src="img/avatar5.png" alt="' + collaborators[ii]['userName'] + '" class="offline"  height="32" width="32"/>';
		var name = collaborators[ii]['userName'] ;
		var org = collaborators[ii]['organization'] ;
		htmlRet = htmlRet + htmlTableRowData([image, name, org]);
	}
	return htmlRet;
}
