$(function(){
	//绑定验证
    $('#myForm').validationEngine('attach');
    
    //保存操作
    $("#saveId").on("click",function(){
    	if(!$('#myForm').validationEngine('validate')){
    		return ;
    	}
    	$.post("/unionCompany/ajaxCompanyName",{"companyName":$.trim($("#companyNameId").val())},function(data){
    		if(data && data.retCode == '00'){
    			var obj = $("#myForm").serializeJson();
            	obj['provinceName']=obj.provinceNo;
            	obj['cityName']=obj.cityNo;
            	$.post("/unionCompany/saveCompany",obj,function(data){
            		if(data && data.retCode=='00'){
            			alert("添加成功",true,function(){
            				window.location.href="/unionCompany/companyList";
            			});
            		}else{
            			alert("添加失败",true,function(){
            				window.location.reload();
            			});
            		}
            	});
    		}else{
    			alert("公司名称重复",true,function(){
    				$("#companyNameId").focus();
    			});
    		}
    	})
    	
    });
});