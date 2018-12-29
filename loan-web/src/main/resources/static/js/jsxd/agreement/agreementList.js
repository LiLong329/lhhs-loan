$(function(){
	/**
	 * 分页
	 */
	var $ur_a = $(".paginItem a");
	$ur_a.on("click",function(){
		var currentPageNo = $(this).attr("data-pagenum");			
		$("#currentPageNo").val(currentPageNo);
		$("#myForm").submit();		
	});	
	
	//搜索
	$("#searcher").on("click",function(){	
		$("#currentPageNo").val(1);
		$("#myForm").submit();
	});
});