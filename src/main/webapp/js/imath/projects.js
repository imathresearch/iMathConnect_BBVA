/**
 * This module manages the projects screen
 * @author imath 
 */

var global_uuid_project_selected = null;
var global_uuid_project_selected_prev = null;
var global_instances = [];

function placeLayoutProjects(uuid_project) {
	setSelectMenu("imath-id-projects-menu");
	jQuery.get('projects.html', function(data) {
		$(".imath-main-row").html(data);
		initProjectView(uuid_project);
	});
}

function disableLink(e) {
    // cancels the event
    e.preventDefault();
    return false;
}

function unselectProject() {
	disable('imath-id-select-resource-button');
	disable('imath-id-add-colls-button');
	disable('imath-id-cancel-buton-project');
	global_uuid_project_selected=null;
	$('#imath-id-instances').html("");			// We empty out the instance tables
	$("#imath-id-project-name").val("");		// We empty the project name input
	$("#imath-id-project-desc").val("");		// We empty the project description input
	$("#imath-id-project-date").val("");		// We empty the project creation date input
	$(".imath-collaborators"). html("");
	$(".imath-project-name").html("");
}

function initProjectView(uuid_project) {
	unselectProject();
	if (!(typeof uuid_project =="undefined")) {
		uploadProject(uuid_project);
	}
	global_instances = [];
	ajaxOwnProjects("uploadProject");
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
		var uuid_instance = $('#imath-id-instances tr').eq(1).attr("id");	// We get the id of the second tr of the table
		var newDesc = $("#imath-id-project-desc").val();
		if (global_uuid_project_selected!==null) {
			// saving the project
			var uuid_project = global_uuid_project_selected;
			saveProject(uuid_project, newDesc, uuid_instance);
		} else {
			// if global_uuid_project_selected === null, we create a new project!
			var newName = $("#imath-id-project-name").val();
			newProject(newName, newDesc, uuid_instance);
		}
	});
	
	$("#imath-id-add-colls-button").click(function() {
		if (global_uuid_project_selected!==null) {
			var other = $("#idCollaborationBox").val();
			other = other.trim();
			if(other !=="") {
				addCollaborator(other, global_uuid_project_selected);
			}
		}
	});
	
	$("#imath-id-select-resource-button").click(function() {
		$('#instance-select').modal('show');
	});
	
	$("#imath-id-new-project-button").click(function() {
		setNewProjectForm();
	});
	
	$('#imath-id-project-name').on('input', function() {
		$(".imath-project-name").html($("#imath-id-project-name").val());
	});
}

function setNewProjectForm() {
	// We set the cancel button to upload the previous uploaded project
	global_uuid_project_selected_prev = global_uuid_project_selected;
	$("#imath-id-cancel-buton-project").click(function () {
		if (global_uuid_project_selected_prev !== null) {
			uploadProject(global_uuid_project_selected_prev);
			global_uuid_project_selected_prev == null;
		} else {
			unselectProject();
		}
	});
	
	global_uuid_project_selected=null;	// We "unselect" any possible current selected project
	
	enable('imath-id-cancel-buton-project');	// We enable the cancel button;
	$('#imath-id-instances').html("");			// We empty out the instance tables
	$("#imath-id-project-name").val("");		// We empty the project name input
	$("#imath-id-project-desc").val("");		// We empty the project description input
	$("#imath-id-project-date").val("");		// We empty the project createion date input
	enable("imath-id-project-name");			// we enable the name input
	enable("imath-id-select-resource-button");	// We enable the resource selection button
	disable("imath-id-add-colls-button");			// We disable the collaboration button
	$(".imath-collaborators"). html("");			// We empty out the table of collaborators
	$(".imath-project-name").html("");

}

function getGlobalInstance(uuid) {
	for(var i=0; i<global_instances.length; i++) {
		if (global_instances[i]['UUID'] == uuid) {
			return global_instances[i];
		}
	}
	return null;
}

function addCollaborator(other, uuid_project) {
	$.ajax({
	    url: "rest/api/agora/addCollaboratorByOther/" + global_uuid_user + "/" + uuid_project + "/" + other,
	    cache: false,
	    dataType: "json",
	    type: "POST",
	    success: function(project) {
	    	var collaborators = project['userCol'];
			collaboratorsHtml = generateTableOfCollaborators(collaborators);
			$(".imath-collaborators"). html(collaboratorsHtml);
			ajaxOwnProjects("uploadProject");
			$("#idCollaborationBox").val("");
	    },
	    error: function(error) {
	    	console.log("Error adding collaborator");
	    	showErrorForm("Error adding a collaborator");
	    }
	});		
}

function removeCollaborator(uuid_col) {
	if (global_uuid_project_selected!==null) {
		$.ajax({
		    url: "rest/api/agora/removeCollaborator/" + global_uuid_user + "/" + global_uuid_project_selected + "/" + uuid_col,
		    cache: false,
		    dataType: "json",
		    type: "POST",
		    success: function(project) {
		    	var collaborators = project['userCol'];
				collaboratorsHtml = generateTableOfCollaborators(collaborators);
				$(".imath-collaborators"). html(collaboratorsHtml);
				ajaxOwnProjects("uploadProject");
				$("#idCollaborationBox").val("");
		    },
		    error: function(error) {
		    	console.log("Error removing collaborator");
		    	showErrorForm("Error removing the collaborator");
		    }
		});
	}
}
function newProject(newName, newDesc, uuid_instance) {
	alert(newName + "  " + newDesc + "  " + uuid_instance);
}

function saveProject(uuid_project, newDesc, uuid_instance) {
	$.ajax({
	    url: "rest/api/agora/updateProject/" + global_uuid_user + "/" + uuid_project + "/" + newDesc + "/" + uuid_instance,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(project) {
	    	ajaxOwnProjects("uploadProject");
	    },
	    error: function(error) {
	        console.log("Error saving project");
	        showErrorForm("Error saving the project");
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
	    	$("#imath-id-project-name").val(project['name']);
	    	disable("imath-id-project-name");
	    	$("#imath-id-project-desc").val(project['desc']);
	    	disable("imath-id-cancel-buton-project");
	    	disable("imath-id-select-resource-button"); // We disable the selection of resources
	    	enable("imath-id-add-colls-button");		// we enable the add collaborators button
	    	var creationDate = new Date(project['creationDate']);
			var dateText = dateToNice(creationDate);
			$("#imath-id-project-date").val(dateText);
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
	var htmlRet = htmlTableRowHead(["Pic", "User Name", "eMail", "Organization", "#"]);
	for(var ii=0; ii< collaborators.length; ii++) {
		var image = '<img src="img/avatar04.png" alt="' + collaborators[ii]['userName'] + '" class="offline"  height="32" width="32"/>';
		var name = collaborators[ii]['userName'] ;
		var org = collaborators[ii]['organization'];
		var email = collaborators[ii]['eMail'];
		var uuid = collaborators[ii]['UUID'];
		var action = "<a onclick='removeCollaborator(\"" + uuid + "\")' style='cursor: pointer;' title='Remove' )><i class='fa fa-minus-circle'></i></a>";
		htmlRet = htmlRet + htmlTableRowData([image, name, email, org, action], uuid);
	}
	return htmlRet;
}

function runiMathCloud(uuid_project) {
	$.ajax({
	    url: "rest/api/agora/getProjectCredentials/" + global_uuid_user + "/" + uuid_project,
	    cache: false,
	    dataType: "json",
	    type: "GET",
	    success: function(project) {
	    	var linux = project['linuxGroup'];
	    	var key = project['key'];
	    	var url = project['url'] + "/login.jsp";
	    	// Ugly... but it works
	    	document.body.innerHTML += '<form target="_blank" id="fakeForm" action="' + url + '" method="post"><input type="hidden" name="j_username" value="' + linux + '"><input type="hidden" name="j_password" value="' + key + '"></form>';
	    	document.getElementById("fakeForm").submit();
	    	document.getElementById("fakeForm").remove();
	    	//win.focus();
	    },
	    error: function(error) {
	        console.log("Error opening iMathCloud");
	        showErrorForm("Error opening iMath Cloud. Try again in few minutes.");
	    }
	});	
}

function disable(id) {
	$("#" + id).prop('disabled', true);
}

function enable(id) {
	$("#" + id).prop('disabled', false);
}

function enableLink(id) {
	$('#' + id).unbind('click', disableLink);
}

function disableLink(id) {
	$('#' + id).bind('click', disableLink);
}

function showErrorForm(message) {
	$('.imath-error-message').html(message);
	$('#imath-id-error-message-col').modal('show');
	
}

