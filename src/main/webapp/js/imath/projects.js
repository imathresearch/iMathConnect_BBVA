/**
 * This module manages the projects screen
 * @author imath 
 */

var global_uuid_project_selected = null;
var global_instances = [];

function placeLayoutProjects() {
	jQuery.get('projects.html', function(data) {
		$(".imath-main-row").html(data);
		initProjectView();
	});
}

function initProjectView() {
	global_uuid_project_selected=null;
	global_instances = [];
	ajaxOwnProjects();
	ajaxInstancesLoad();
	$("#imath-id-own-projects").delegate("tr", "click", function(e) {
		if (!(typeof $(e.currentTarget).attr('id') == "undefined")){
			var uuid = $(e.currentTarget).attr('id');
			uploadProject(uuid);
		}
	});
	$("#imath-id-instances-select").delegate("tr", "click", function(e) {
		if (!(typeof $(e.currentTarget).attr('id') == "undefined")){
			var uuid = $(e.currentTarget).attr('id');
			if (uuid !== null) {
				var instance = getGlobalInstance(uuid);
				if (instance!==null) {
					var fakeInstances = [];
					fakeInstances[0] = instance;
					instanceTableHtml = generateTableOfInstances(fakeInstances, instance['pub'], true);
					$(".imath-select-instance").html(instanceTableHtml);
					$('#imath-id-cancel-button-select').trigger('click');
				} else {
					console.log("Error getting selected instances");
				}
			}
		}
	});
	$( "#imath-id-save-buton-project" ).click(function() {
		if (global_uuid_project_selected!==null) {
			var newDesc = $("#idProjectDescription").val();
			alert(newDesc);
			var uuid_project = global_uuid_project_selected;
			var uuid_instance = $('#imath-id-instances tr').eq(1).attr("id");	// We get the id of the second tr of the table
			saveProject(uuid_project, newDesc, uuid_instance);
		}
	});
}

function getGlobalInstance(uuid) {
	for(var i=0; i<global_instances.length; i++) {
		if (global_instances[i]['UUID'] == uuid) {
			return global_instances[i];
		}
	}
	return null;
}

function saveProject(uuid_project, newDesc, uuid_instance) {
	$.ajax({
	    url: "rest/api/agora/updateProject/" + global_uuid_user + "/" + uuid_project + "/" + newDesc + "/" + uuid_instance,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(project) {
	    	ajaxOwnProjects();
	    },
	    error: function(error) {
	        console.log("Error saving project");
	    }
	});	
}

function keepIntancesGlobal(instances, pub) {
	var j = global_instances.length;
	for(var i=0; i<instances.length; i++) {
		global_instances[i+j]=[];
		global_instances[i+j]['pub'] = pub;
		global_instances[i+j]['cpu'] = instances[i]['cpu'];
		global_instances[i+j]['ram'] = instances[i]['ram'];
		global_instances[i+j]['stg'] = instances[i]['stg'];
		global_instances[i+j]['UUID'] = instances[i]['UUID'];
		global_instances[i+j]['creationDate'] = instances[i]['creationDate'];
	}
} 

function ajaxInstancesLoad() {
	
	$.ajax({
	    url: "rest/api/agora/instances/" + global_uuid_user,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(instances) {
	    	keepIntancesGlobal(instances, false);
	    	var htmlTableSelectPrivate = generateTableOfInstances(instances, false, true);
	    	$.ajax({
	    	    url: "rest/api/agora/instances/ ",
	    	    cache: false,
	    	    dataType: "json",
	    	    type: "GET",
	    	    success: function(instances) {
	    	    	keepIntancesGlobal(instances, true);
	    	    	var htmlTableSelectPublic = generateTableOfInstances(instances, true, false);
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
		var uuid = instance['UUID'];
		var rowIcon = '<span class="badge bg-red">Pr</span>';
		if (pub) {
			rowIcon = '<span class="badge bg-light-blue">Pu</span>';
		}
		var line = rowIcon + cpu + ram +stg;
		ret = ret + "<option value='" + uuid + "'>" + line + "</option>";
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
	    	$("#idProjectName").val(project['name']);
	    	$("#idProjectDescription").val(project['desc']);
	    	var creationDate = new Date(project['creationDate']);
			var dateText = dateToNice(creationDate);
			$("#idCreationDate").val(dateText);
			var instance = project['instance'];
			var instanceTableHtml;
			var fakeInstances = [];
			fakeInstances[0] = instance;
			if(project['instance']['owner']===null) {	// public
				instanceTableHtml = generateTableOfInstances(fakeInstances, true, true);
			} else {
				instanceTableHtml = generateTableOfInstances(fakeInstances, false, true);
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
	var htmlRet = htmlTableRowHead(["Pic", "User Name", "eMail", "Organization"]);
	for(var ii=0; ii< collaborators.length; ii++) {
		var image = '<img src="img/avatar04.png" alt="' + collaborators[ii]['userName'] + '" class="offline"  height="32" width="32"/>';
		var name = collaborators[ii]['userName'] ;
		var org = collaborators[ii]['organization'];
		var email = collaborators[ii]['eMail'];
		htmlRet = htmlRet + htmlTableRowData([image, name, email, org]);
	}
	return htmlRet;
}
