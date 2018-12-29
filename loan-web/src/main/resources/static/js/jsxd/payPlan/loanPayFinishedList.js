$(function(){
	
	var postQuery = function(obj){
		$.post("/payPlan/ajaxQueryPayFinishedList", obj , function(data) {
			$("#payFinishedId").html(data);
		});
	};
	
	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj);
	});
	
	// 分页查询
	$("#payFinishedId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			var obj = $("#myForm").serializeJson();
			obj['page.pageIndex'] = $(this).data("pagenum");
			obj['page.pageSize'] = $(this).data("pagesize");
			postQuery(obj);
		};
	});

	//导出
	$("#payFinishedId").on("click","#export",function(){
		var type=$("#cleanUpStatus").val();
		$("#myForm").attr("action","/payPlan/payFinishedExport/"+type);
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
});