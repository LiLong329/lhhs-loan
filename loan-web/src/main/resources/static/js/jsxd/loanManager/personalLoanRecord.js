$(function(){
	$("#queryBtn").on("click",function(){
		var obj=$("#myForm").serializeJson();
		queryList(obj);
	});
	$("#recordList").on("click",".pagin ul li a",function(){
		if(!$(this).closest("li").hasClass("current")){
			var obj=$("#myForm").serializeJson();
			obj["page.pageIndex"]=$(this).data("pagenum")||1;
			queryList(obj);
		};
	});
	function queryList(obj){
		var selectType=$("#selectType").val();
		$.post("/loanManager/ajaxLoanRecord/"+selectType,obj,function(data){
			$("#recordList").html("").html(data);
		});
	};
	$("#recordList").on("click","#export",function(){
		var selectType=$("#selectType").val();
		$("#myForm").attr("action","/loanManager/exportLoanRecord/"+selectType);
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
});