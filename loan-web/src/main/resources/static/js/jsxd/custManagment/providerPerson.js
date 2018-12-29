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
		   window.location.href="/customerManager/providerPerson?"+obj;
	    }	
		/***检索部门***/
		$(".searcher").on("click",function(){	
			searcher(1);
		});
		
		
		
		
	$("#export").on("click",function(){
		$("#myForm").attr("action","/customerManager/providerExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	

	/**
	 * Provider修改
	 */
	 $(".infoModify").on("click",function(){
	  	  if(!$("#myForm").validationEngine('validate')){
	  		return;
	      }
	  	    var providerInfo=$("#myForm").serializeJson();
	  	    var loanProvider = {};
	  	    loanProvider.providerInfo = JSON.stringify(providerInfo);
		     $.post("/customerManager/providerPersonModify",
		    		 loanProvider,function(data){
		          if(data.retCode=="00"){
		              alert(data.retMsg,function(){
		            	 window.location.href="/customerManager/providerPerson";
		              });
		          }else{
		              alert(data.retMsg);
		          }
		       
		       });      
		  });
		    
	
	
});