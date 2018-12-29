$(function(){	
	 //绑定验证
    $('#depositApprove').validationEngine('attach');
    
    $('#btn_save').on('click',  function() {
    	if(!$('#depositApprove').validationEngine('validate')){
    		return;
    	}
    	//岗位名称
    	var obj=$("#depositApprove").serializeJson();
    	
		$.ajax({
			url:"/accountInOut/saveDepositApprove",
			type: 'POST',
			data:{"depositApprove":JSON.stringify(obj)},
			async:false,
			dataType:"json",
			success:function(data){
				if(data.retCode=="00"){
					 alert(data.retMsg,function(){
						 window.location.href="/accountInOut/depositApproveRecord";
						});
				  }else{
					alert(data.retMsg,function(){
					});
				  }
			}
		});
    });
   
});