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
			   window.location.href="/transAccountsManager/transAccountsExamine?"+$("#myForm").serialize();
		    }	
		/***查询***/
		$(".searcher").on("click",function(){
			searcher(1);
		});
	/************审核提交examine_save *******************/
	$("#examine_save").on("click",function(){
		if(!$("#myForm").validationEngine('validate')){
			return false;
		}
		  var obj=$("#myForm").serializeJson();
		  $.post("/transAccountsManager/transExamineAdd",obj,function(data){
			  if(data.retCode=="00"){
				  alert(data.retMsg,function(){
	          	  window.location.href="/transAccountsManager/transAccountsExamine";
	            });
			  }else{
				  alert(data.retMsg);
			  }
		  }); 
	});
	
	/**************导出*******************/
	$("#export").on("click",function(){
		$("#myForm").attr("action","/transAccountsManager/transAccountsExamine");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
	/***************转账记账审核查询***************/
	function searcherMExamine(currentPageNo){
		   $("#currentPageNo").val(currentPageNo);
		   window.location.href="/transAccountsManager/transManageExamine?"+$("#myForm").serialize();
	    }
	/***查询***/
	$(".searcherMExamine").on("click",function(){
		searcherMExamine(1);
	});
});