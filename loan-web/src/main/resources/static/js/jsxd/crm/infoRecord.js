$(function(){

	var postQueryPayPlan = function(obj){
		$.post("/inviteIntentLoaner/ajaxQueryDetailList", obj , function(data) {
			$("#searcherId").html(data);
		});
	};
	
	// 分页查询
	$("#searcherId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQueryPayPlan(obj);
		};
	});
	
	
	//导出
	$("#export").on("click",function(){
		$("#myForm").attr("action","/inviteIntentLoaner/export");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
});