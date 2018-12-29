(function(){
	var procInsId_=$("#procInsId").val();
	$.post("/workflow/url/acttask/histoicFlow",{
		"procInsId":procInsId_
	} , function(data) {
		$("#histoicFlow").html(data);
	});
})();