$(function(){
	
	// 待办任务列表页orderNo查看
	$(".viewDetail").on("click",function(){
		var orderNo = $(this).data("orderno");
		$.post("/approval/viewOrderDetail",{"orderNo":orderNo},function(data){
			if(data.retCode == "00"){
				window.location.href = "/approval/selectAuditing?orderNo=" + orderNo ;
			}else{
				alert(data.retMsg);
			}
		});
	});
	
	// 分页查询
	$(".pagin ul li a").on("click", function() {
		var approvalNode = $("#approvalNode").val();
		if (!$(this).parent("li").hasClass("current")) {
			var currentPageNo = $(this).data("pagenum");
			window.location.href = "/approval/selectMyNewTaskList/" + approvalNode + "?pageNumber=" + currentPageNo;
		}
	});
});