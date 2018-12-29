$(function(){
	$("#myForm").validationEngine('attach',{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true,
		promptPosition:'topLeft'
    });
	

	//保存意向客户
	$("#save_btn").on("click",function(){
		if(!$("#myForm").validationEngine("validate")){
			return false;
		}
		var obj=$("#myForm").serializeJson();
		$.post("/crmIntentLoanUser/saveCrmIntent",obj,function(data){
			if(data.retCode == "00"){
				 show("保存成功",false,function(){
					 window.location.href="/crmIntentLoanUser/crmIntentList";
				 });
			 }else{
				 alert(data.retMsg);
			 }
		});
	});
	
});