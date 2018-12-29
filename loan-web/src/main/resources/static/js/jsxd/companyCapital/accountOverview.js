$(function(){
	$("#recordList").on("click",".pagin ul li a",function(){
		if(!$(this).closest("li").hasClass("current")){
			var obj={};
			obj.pageIndex=$(this).data("pagenum")||1;
			queryList(obj);
		};
	});
	function queryList(obj){
		$.post("/companyCapital/ajaxAccountOverview",obj,function(data){
			$("#recordList").html("").html(data);
		});
	};
});