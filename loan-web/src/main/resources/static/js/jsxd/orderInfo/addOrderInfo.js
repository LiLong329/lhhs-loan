$(function(){
	$("#myForm").validationEngine('attach',{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true
    });
	//资金方改变事件
	$("#fundOwner").on("change",function(){
		var value=$(this).val(),str="<option value=''>请选择</option>";
		if(value){
			$.ajax({
				url:"/orderInfo/selectChildProductByFundOwner",
				data:{"orgId":value},
				async:false,
				dataType:"json",
				success:function(data){
					if(data){
						 $.each(data,function(i,n){
							 str += ("<option value='"+ n.productId +"' data-productno='"+n.productParentNo+"'>"+ n.productName +"</option>");
		   			  	 });
					}
				}
			});
		}
		$("#productName").html(str).closest(".uew-select").find(".uew-select-value .uew-select-text").html("请选择");
	});
	//产品名称改变事件
	$("#productName").on("change",function(){
		var value=$(this).val(),
			productNo=$(this).find("option:selected").data("productno");
		if(value){
			$("#productNo").val(productNo);
		}else{
			$("#productNo").val("");
		}
	});
	//客户经理改变事件
	$("#customerManager").on("change",function(){
		var value=$(this).val(),
			unionId=$(this).find("option:selected").data("unionid"),
			companyId=$(this).find("option:selected").data("companyid"),
			deptId=$(this).find("option:selected").data("deptid"),
			groupId=$(this).find("option:selected").data("groupid"),
			province=$(this).find("option:selected").data("province"),
			city=$(this).find("option:selected").data("city");
		if(value){
			$("#unionId").val(unionId);
			$("#companyId").val(companyId);
			$("#province").val(province);
			$("#city").val(city);
			if(groupId){
				$("#depId").val(groupId);
			}else{
				$("#depId").val(deptId);
			}
		}else{
			$("#unionId").val("");
			$("#companyId").val("");
			$("#depId").val("");
			$("#province").val("");
			$("#city").val("");
		}
	});
	
	 /*********借款人重复校验**********/
	  function queryBorrower(){
			$.ajax({
	    		url:"/orderInfo/queryBorrower",
	    		type: 'POST',
	    		data:{"bname":$("input[name='bname']").val(),idNum:$("input[name='idNum']").val()},
	    		async:false,
	    		dataType:"json",
	    		success:function(data){
	    			if(data.retCode=="01"){
	    				alert("该借款人已经申请过借款!");
	    		}
	    	}
	    });
	  }
	  
	//新增报单
	$("#save_btn").on("click",function(){
		if(!$("#myForm").validationEngine("validate")){
			return false;
		}
		queryBorrower();
		var obj=$("#myForm").serializeJson();
		$.post("/orderInfo/saveOderInfo",obj,function(data){
			if(data.retCode == "00"){
				 show("报单成功",false,function(){
					 if($("#type").val()=='1'){
						 window.location.href="/crmIntentLoanUser/crmIntentFollowList";
					 }else{
						 window.location.reload();
					 }
				 });
			 }else{
				 alert(data.retMsg);
			 }
		});
	});
	
	//suncj 新增报单--根据手机号检索借款人信息
    $('#searchOrderInfo').on('click',  function() {
	if($("input[name='mobile']").val()==""||$("input[name='mobile']").val()==null){
		alert("请填写借款人手机号");
		return false;
	}
   	$.ajax({
		url:"/orderInfo/searchOrderInfo",
		type: 'POST',
		data:{"mobile":$("input[name='mobile']").val()},
		async:false,
		dataType:"json",
		success:function(data){
			if(data.retCode=="00"){
				$("input[name='bname']").val(data.name);
				$("input[name='idNum']").val(data.idNumber);
				$("input[name='loanAmount']").val(data.loanAmount);
				$("input[name='loanTerm']").val(data.duration);
				$("input[name='loanRate']").val(data.interestRate);
			}else{
				alert(data.retMsg,function(){
			   });
			}
		}
	});
 });
    
    $("#loanTerm").on("change",function(){
    	if($("#loanTerm").val()<0){
    		alert("借款期限不能为负数,请修改");
    		return false;
    	}
	  });
    $("#loanAmount").on("change",function(){
    	if($("#loanAmount").val()<0){
    		alert("借款金额不能为负数,请修改");
    		return false;
    	}
    });
    
});