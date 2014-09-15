
function placeLayoutInstances(uuid_instance) {
	setSelectMenu("imath-id-instances-menu");
	jQuery.get('instances.html', function(data) {
		$(".imath-main-row").html(data);
		initInstanceView(uuid_instance);
	});
}
