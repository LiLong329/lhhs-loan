$(function(){
	//分页
	$("#recordList").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			var uri="/workflow/url/actprocess/runningPage";
			var obj = $("#myForm").serializeJson();
			$.post(uri,obj,function(data){
				$("#recordList").html("").html(data);
			});
		};

	});

	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var uri="/workflow/url/actprocess/runningPage";
		var obj = $("#myForm").serializeJson();
		$.post(uri,obj,function(data){
			$("#recordList").html("").html(data);
		});
	});

});

