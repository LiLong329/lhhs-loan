$(function(){
	$("#queryBtn").on("click",function(){
		var obj=$("#myForm").serializeJson();
		queryList(obj);
	});
	$("#recordList").on("click",".pagin ul li a",function(){
		if(!$(this).closest("li").hasClass("current")){
			var obj=$("#myForm").serializeJson();
			obj["pageIndex"]=$(this).data("pagenum")||1;
			queryList(obj);
		};
	});
	function queryList(obj){
		$.post("/systemManager/ajaxOperateRecord",obj,function(data){
			$("#recordList").html("").html(data);
		});
	};
});