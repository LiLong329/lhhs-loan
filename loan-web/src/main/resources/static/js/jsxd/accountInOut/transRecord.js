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
		
		
		$("body").on("click","#export",function(){
			var formAction = $("#myForm")[0].action;
			$("#myForm").attr("action","/accountInOut/exportRecord");
			$("#myForm").submit();
			$("#myForm").attr("action",formAction);
		});

});