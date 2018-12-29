$(function(){
	
	var postQueryPayPlan = function(obj){
		$.post("/inviteIntentLoaner/ajaxQueryList", obj , function(data) {
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
	
	//查詢
	$("#search").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		$("#myForm").attr('action',"/inviteIntentLoaner/inviteList");
		$("#myForm").submit();
		
	});
	

	
	//导出
	$("#export").on("click",function(){
		$("#myForm").attr("action","/inviteIntentLoaner/export");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
	$("#searcherId").on("click",".showDetail",function(){
		var $this=$(this),
			href=$this.attr("href"),
			beginingTime=$.trim($("input[name='beginingTime']").val())||"",
			endingTime=$.trim($("input[name='endingTime']").val())||"";
		href+=("&beginingTime="+beginingTime+"&endingTime="+endingTime);
		$this.attr("href",href);
	})
	
	
});