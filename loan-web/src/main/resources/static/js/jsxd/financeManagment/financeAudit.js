$(function(){
	
	// 列表页orderNo查看
	$("body").on("click", ".viewDetail", function(){
		var orderNo = $(this).data("orderno"),
			approvalNode=$(this).data("approvalnode");
		$.post("/approval/viewOrderDetail",{"orderNo":orderNo},function(data){
			if(data.retCode == "00"){
				window.location.href = "/approval/selectLoanApplyDetail/"+approvalNode+"?orderNo=" + orderNo ;
			}else{
				alert(data.retMsg);
			}
		});
	});
	
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
	
	/** 根据审批节点自定义查询放款响应节点数据 **/
	function queryList(obj){
		var approvalNode = $("#approvalNode").val();
		$.post("/approval/asynSelectLoanApplyList/" + approvalNode, obj, function(data){
			$("#recordList").html("").html(data);
		});
	};
	
});