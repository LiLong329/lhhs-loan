$(function() {
    
	/**
	 * 垫付分页
	 */
	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj);
	});
	
	var postQuery = function(obj){
		$.post("/customerManager/ajaxRepayAmountDue",obj, function(data) {
			$("#payPlanId").html(data);
		});
	};
	
	// 分页查询
	$("#payPlanId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj);
		};
	});
	
	//取消按钮
	$("#previewId").on("click","input#goBackId",function(){
		$("#previewId").hide();
	});
	
	
	
	
	
	
	
});