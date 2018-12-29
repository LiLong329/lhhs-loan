$(function(){	
	 //绑定验证
	  $('#quarters').validationEngine('attach');
	  $('#btn_save').on('click',  function() {
	    	if(!$('#quarters').validationEngine('validate')){
	    		return;
	    	}
	    	//岗位名称
	    	var obj=$("#quarters").serializeJson();
	    	$.post('/systemManager/quartersUpdate', obj, function(data) {
	    		if(data.retCode=="00"){
					 alert(data.retMsg,function(){
						 window.location.href="/systemManager/getQuarters";
						});
				  }else{
					alert(data.retMsg,function(){
					});
				  }
	    	});
	    });
});