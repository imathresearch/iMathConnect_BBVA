
function placeLayoutInstances(uuid_instance) {
	// Not active in the current version
	/*
	setSelectMenu("imath-id-instances-menu");
	jQuery.get('instances.html', function(data) {
		$(".imath-main-row").html(data);
		initInstanceView(uuid_instance);
	})*/;
}

function initInstanceView(uuid) {
	ajaxOwnInstances();
	initializeSliders();
}

function initializeSliders() {
	alert("init sliders");
	
	$("#imath-id-cpu").ionRangeSlider({
        min: 0,
        max: 10,
        type: 'single',
        step: 0.1,
        postfix: " mm",
        prettify: false,
        hasGrid: true
    });
	/*
	$("#imath-id-cpu").ionRangeSlider({
        from:"1",
        type: 'single',
        step: "1",
        prettify: false,
        values: ["1", "2", "4", "8", "16", "32", "64", "128", "256", "512"],
        hasGrid: true
    });*/
	$("#imath-id-ram").ionRangeSlider({
		values: ["1", "2", "4", "8", "16", "32", "64", "128", "256", "512"],
        from:"1",
        type: 'single',
        postfix: " MB",
        prettify: false,
        hasGrid: true
    });
	$("#imath-id-stg").ionRangeSlider({
		min: 1,
        max: 5000,
        type: 'single',
        step: 1,
        postfix: " GB",
        prettify: false,
        hasGrid: true
    });
} 