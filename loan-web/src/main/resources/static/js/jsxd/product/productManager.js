//启用禁用异步提交
$(".productState").on("click",function(){
	var ifEnable = $(this).attr("data-enable");
	var productNo = $(this).attr("data-numb");
	
	$.ajax({
			type: "POST",
			url: "/systemManager/productIfEnable",
			processData:true,
			data:{"ifEnable":ifEnable,"productNo":productNo},
			success: function(data){
			 if(data.retCode=="00"){
				 alert(data.retMsg,false,function(){
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

$(".searchproduct").on("click",function(){
	  $("#myForm").submit();			  
});