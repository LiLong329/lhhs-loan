$(function(){

	$("#myForm").validationEngine('attach',{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true
    });
    
	/**
     * 分页
     */
	 var $ur_a = $(".paginItem a");
		$ur_a.on("click",function(){
			var currentPageNo = $(this).attr("data-pagenum");			
			searcher(currentPageNo);
		});		
		function searcher(currentPageNo){
		   $("#currentPageNo").val(currentPageNo);
		   var obj=$("#myForm").serialize();
		   window.location.href="/customerManager/loanPayRecord?"+obj;
	    }	
		/***检索部门***/
		$(".searcher").on("click",function(){	
			searcher(1);
		});
		
		
		
		
	$("#export").on("click",function(){
		$("#myForm").attr("action","/customerManager/loanPayRecordExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
   
    
	    
     
});