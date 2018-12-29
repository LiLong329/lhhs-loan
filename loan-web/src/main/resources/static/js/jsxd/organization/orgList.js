$(function(){
	
	var postQuery = function(obj){
		$.post("/org/ajaxQueryList", obj , function(data) {
			$("#orgTempId").html(data);
		});
	};
	
	$("#queryId").on("click",function(){
		var obj = $("#myForm").serializeJson();
		obj["page.pageIndex"]=1;
		postQuery(obj);
	});
	
	// 分页查询
	$("#orgTempId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			var obj = $("#myForm").serializeJson();
			obj["page.pageIndex"]=$(this).data("pagenum");
			obj["page.pageSize"]=$(this).data("pagesize");
			postQuery(obj);
		};
	});

});