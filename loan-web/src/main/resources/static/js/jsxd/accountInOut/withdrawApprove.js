$(function(){	
	 //绑定验证
    $('#withdrawApprove').validationEngine('attach');
    
    $('#btn_save').on('click',  function() {
    	if(!$('#withdrawApprove').validationEngine('validate')){
    		return;
    	}
    	//岗位名称
    	var obj=$("#withdrawApprove").serializeJson();
    	
		$.ajax({
			url:"/accountInOut/saveWithdrawApprove",
			type: 'POST',
			data:{"withdrawApprove":JSON.stringify(obj)},
			async:false,
			dataType:"json",
			success:function(data){
				if(data.retCode=="00"){
					 alert(data.retMsg,function(){
						 window.location.href="/accountInOut/withdrawApproveRecord";
						});
				  }else{
					alert(data.retMsg,function(){
					});
				  }
			}
		});
    });
});