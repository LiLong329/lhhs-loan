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
	
	$("#searcher").on("click",function(){	
		$("#currentPageNo").val(1);
		$("#myForm").submit();
	});
	    
    //禁用启用
	$(".tablelink.mlt10").on("click",function(){
		var ifEnable = $(this).attr("data-enable");
		var quartersId=$(this).attr("data-numb");
		$.ajax({
			type: "POST",
			url: "/systemManager/quartersEnable",
			processData:true,
			data:{"status":ifEnable,"quartersId":quartersId},
			success: function(data){
			if(data.retCode=="00"){
				alert(data.retMsg,function(){
					window.location.reload(true);
				});
			}else{
				alert(data.retMsg,function(){
					window.location.reload(true);
				});
			  }
			}
		});  	
	});

});