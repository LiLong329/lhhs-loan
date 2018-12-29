$(function(){
	var $html=$("#cardInfo tbody tr:first").prop("outerHTML");
	
	$("#myForm").validationEngine('attach',{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true
    });
    
    $("#cardForm").validationEngine('attach',{
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
	   window.location.href="/systemManager/companyAccount?"+obj;
    }	
	/***检索部门***/
	$(".searcher").on("click",function(){	
		searcher(1);
	});
		
	
	
	
	$("#addCardInfo").on("click",function(){
		var	$newInfo = $("#cardInfo tbody").append($html).find("tr:last");
		$newInfo.find("input").val("");
		$newInfo.find("select").each(function(i,n){
			$(n).find("option:first").prop("selected",true);
			$(n).prev(".uew-select-value").find(".uew-select-text").html($(n).find("option:first").text());
		});
		$newInfo.find("td:last").html('<a class="tablelink delTr">删除</a>');
		addOdd();
	});
	
	//删除信息
	$("body").on("click",".delTr",function(){
		$(this).closest("tr").remove();
		addOdd();
	});
	
	//添加偶数行样式
	function addOdd(){
		$("#tab5 .tablelist tbody tr").removeClass("odd");
		$("#tab5 .tablelist").each(function(i,n){
            $(this).find("tbody tr:odd").addClass("odd");
        });
	};
	
    
	//保存账户信息
	$(".accountSubmit").on("click",function(){
		if(!$('#myForm').validationEngine('validate')){
    		return;
    	}
		if(!$('#cardForm').validationEngine('validate')){
    		return;
    	}
		var card= new Array(),
		cardForm = $("#cardInfo tbody tr");
	  for(var i=0;i<cardForm.length;i++){
		  card.push($(cardForm[i]).getOtherJson());
	     }
	   var account=$("#myForm").serializeJson();
	$.post("/systemManager/accountAddOrModify",{
		"loanAccountInfo":JSON.stringify(account),
		"loanAccountCard":JSON.stringify(card)
	},function(data){ 
		if(data.retCode=="00"){
		  alert(data.retMsg,function(){
			  window.location.href="/systemManager/companyAccount?accountType="+$(".accountType").val();
		  });
	    }else{
		  alert(data.retMsg);
	  }
    }); 
  });

	/**
	 * 动态显示修改记录
	 */    
	$(".radiobox").on("click",function(){
		var cardId = $(this).closest("tr").find(".radiobox").val();
		$.post("/systemManager/ajaxModifyDetail",{
			"cardId":cardId
		},function(data){
			$("#cardRevise").html(data);
		});
	});
	
});


