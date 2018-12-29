$(function(){	
	/**
	* 分页
	*/
	var $ur_a = $(".paginItem a");
	$ur_a.on("click",function(){
		var currentPageNo = $(this).attr("data-pagenum");			
		searcher(currentPageNo);
		$("#myForm").submit();		
	});		
	function searcher(currentPageNo){
		$("#currentPageNo").val(currentPageNo);
		$("#myForm").submit();
	}	
	
	
	$("#searcher").on("click",function(){						
		$("#myForm").submit();
	});
});