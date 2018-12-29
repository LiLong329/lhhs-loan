$(function(){
	//绑定验证
    $('#myForm').validationEngine('attach');
    
    //保存公用方法
    var save = function(){
    	var obj = $("#myForm").serializeJson();
    	obj['provinceName']=obj.provinceNo;
    	obj['cityName']=obj.cityNo;
    	$.post("/unionCompany/saveCompany",obj,function(data){
    		if(data && data.retCode=='00'){
    			alert("修改成功",true,function(){
    				window.location.href="/unionCompany/companyList";
    			});
    		}else{
    			alert("修改失败",true,function(){
    				window.location.reload();
    			});
    		}
    	});
    }
    
    //保存操作
    $("#saveId").on("click",function(){
    	if(!$('#myForm').validationEngine('validate')){
    		return ;
    	}
    	if($.trim($("#companyName").val()) == $.trim($("#companyNameId").val())){
    		save();
    	}else{
    		$.post("/unionCompany/ajaxCompanyName",{"companyName":$.trim($("#companyNameId").val())},function(data){
        		if(data && data.retCode == '00'){
        			save();
        		}else{
        			alert("公司名称重复",true,function(){
        				$("#companyNameId").focus();
        			});
        		}
        	})
    	}
    });
});