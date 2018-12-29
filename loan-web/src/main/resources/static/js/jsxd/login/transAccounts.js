$(function(){
	var submitObj={};
	$("#myForm").validationEngine('attach',{
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
			searcher2(currentPageNo);
	});	
		
	
	function searcher2(currentPageNo){
		$("#currentPageNo").val(currentPageNo);
		$("#myForm").attr('action',"/transAccountsManager/transManagementList");
	    $("#myForm").submit();
    };	
		
	
$("#btn_save").on("click",function(){
	      if(!$("#myForm").validationEngine('validate')){
	  		 return;
	  	  }
		  var obj=$("#myForm").serializeJson();
		  $.post("/transAccountsManager/transAccountsAdd",obj,function(data){
			  if(data.retCode=="00"){
				  alert(data.retMsg,function(){
	 				  window.open ('/transAccountsManager/getTransObligations?transId='+data.transId,'newwindow','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
	              });
			  }else{
				  alert(data.retMsg);
			  }
		  });
	  });
    /***************** 固定理财转账记录导出 ********************/
	$("#export").on("click",function(){
		$("#myForm").attr("action","/transAccountsManager/totransAccountsExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
       function searcher(currentPageNo){
		   $("#currentPageNo").val(currentPageNo);
		   window.location.href="/transAccountsManager/transAccountsList?"+$("#myForm").serialize();
 }	                                                  
   
		/***固定理财转账记账记录查询***/
		$(".searcher").on("click",function(){	
			searcher(1);
		});
		
	/************客户类型-客户性质-联动**************/
	  $("#outAccountCustType").on("change",function(){
		  getCustPropertyByTypeId($(this).val(),$(this).data("category"),"out");//转出账号
	  });
	  
	  $("#inAccountCustType").on("change",function(){
		  getCustPropertyByTypeId($(this).val(),$(this).data("category"),"in");//收款账户
	  });
	  
	  function getCustPropertyByTypeId(typeId,category,str){
		  var custType=null;
		  if(!category){
			  category = "";
		  }
		  if(str=="out"){
				  custType="outAccountCustProperty";
		  }else{
			  custType="inAccountCustProperty";
		  }
		  if(typeId){
			  $.post("/transAccountsManager/getCustPropertyByTypeId",{
				  "bigCategory":typeId,
				  "category":category
			  },function(data){
				  var html="<option value=''>请选择</option>";
				  for(var i=0;i<data.custPropertyList.length;i++){
					/*  if(data.custPropertyList[i].typeName=="公司"){
						  html+=("<option class='leGroupId' value='"+data.custPropertyList[i].typeId+"' selected='selected'>"+data.custPropertyList[i].typeName+"</option>");
					  }else{
					  }*/
					  html+=("<option class='leGroupId' value='"+data.custPropertyList[i].typeId+"'>"+data.custPropertyList[i].typeName+"</option>");
				  }
				  $("#"+custType+"").html("").html(html);
			  });
		  }else{
			  $("#"+custType+"").html("").html("<option value=''>请选择</option>");
		  }
		      $("#"+custType+"").prev(".uew-select-value").find(".uew-select-text").html("请选择")
	  }
	  
		/************根据充值订单号查询相关信息*******************/
		$("#checkRecharge").on("click",function(){
			  $.post("/transAccountsManager/checkRechargeOrderNo",{orderNo:$("#rechargeOrderNo").val()},function(data){
				  if(data.retCode=="00"){
					  alert(data.retMsg,function(){
		            });
				  }else{
					  alert(data.retMsg);
				  }
			  }); 
		});
		
		/************转账管理 -转账记账申请*************/
		$("#btn_save_manege").on("click",function(){
				 if(!$("#myForm").validationEngine('validate')){
			  		 return;
			  	  }
				  var obj=$("#myForm").serializeJson();
				  $.post("/transAccountsManager/transAccountsAdd",obj,function(data){
					  if(data.retCode=="00"){
						  alert(data.retMsg,function(){
							  window.location.href="/transAccountsManager/toTransferManagementAdd";
			              });
					  }else{
						  alert(data.retMsg);
					  }
				  });
       });
		
		
		//转出账户-根据用户id查询用户信息
	    $('#searchCustInfo').on('click',  function() {
	    	if($("#outAccountCustType").val()==""||$("#outAccountCustType").val()==null){
	    		alert("请选择客户类型");
	    		return false;
	    	}
	    	if($("#outAccountCustProperty").val()==""||$("#outAccountCustProperty").val()==null){
	    		alert("请选择客户性质");
	    		return false;
	    	}
	    	if($("input[name='outAccountCustId']").val()==""||$("input[name='outAccountCustId']").val()==null){
	    		alert("请填写用户ID");
	    		return false;
	    	}
	    	$.ajax({
	    		url:"/transAccountsManager/queryCustomerInfo",
	    		type: 'POST',
	    		data:{"custId":$("input[name='outAccountCustId']").val(),"outCustType":$("#outAccountCustType").val(),"outCustProperty":$("#outAccountCustProperty").val()},
	    		async:false,
	    		dataType:"json",
	    		success:function(data){
	    			if(data.retCode=="00"){
	    				$("#outAccountMobile").val("");
	    				$("#outAccountRealName").val("");
	    				$("input[name='outAccountMobile']").val(data.customerMobile);
	    				$("input[name='outAccountRealName']").val(data.customerName);
	    				document.getElementById("outAccountPreBalance").innerHTML = data.balance;
	    				$("input[name='outAccountPreBalance']").val(data.balance);
	    			}else{
	    				alert(data.retMsg,function(){
	    			   });
	    			}
	    		}
	    	});
	    });
		//收款账户-根据用户id查询用户信息
	    $('#searchInCustInfo').on('click',  function() {
	    	if($("#inAccountCustType").val()==""||$("#inAccountCustType").val()==null){
	    		alert("请选择客户类型");
	    		return false;
	    	}
	    	if($("#inAccountCustProperty").val()==""||$("#inAccountCustProperty").val()==null){
	    		alert("请选择客户性质");
	    		return false;
	    	}
	    	if($("input[name='inAccountCustId']").val()==""||$("input[name='inAccountCustId']").val()==null){
	    		alert("请填写用户ID");
	    		return false;
	    	}
	    	$.ajax({
	    		url:"/transAccountsManager/queryCustomerInfo",
	    		type: 'POST',
	    		data:{"custId":$("input[name='inAccountCustId']").val(),"outCustType":$("#inAccountCustType").val(),"outCustProperty":$("#inAccountCustProperty").val()},
	    		async:false,
	    		dataType:"json",
	    		success:function(data){
	    			if(data.retCode=="00"){
	    				$("#inAccountMobile").val("");
	    				$("#inAccountRealName").val("");
	    				$("input[name='inAccountMobile']").val(data.customerMobile);
	    				$("input[name='inAccountRealName']").val(data.customerName);
	    				document.getElementById("inAccountPreBalance").innerHTML = data.balance;
	    				$("input[name='inAccountPreBalance']").val(data.balance);
	    			}else{
	    				alert(data.retMsg,function(){
	    			   });
	    			}
	    		}
	    	});
	    });
	    
		//固定理财转账申请-根据充值订单编号查询充值信息
	    $('#searchTransInfo').on('click',  function() {
	    	if($("input[name='rechargeOrderNo']").val()==""||$("input[name='rechargeOrderNo']").val()==null){
	    		alert("请填写充值订单编号");
	    		return false;
	    	}
	   	$.ajax({
    		url:"/transAccountsManager/getTransAccountsInfo",
    		type: 'POST',
    		data:{"rechargeOrderNo":$("input[name='rechargeOrderNo']").val()},
    		async:false,
    		dataType:"json",
    		success:function(data){
    			if(data.retCode=="00"){
    				$("input[name='outAccountCustId']").val(data.out_account_cust_id);
    				$("input[name='outAccountMobile']").val(data.out_account_mobile);
    				$("input[name='outAccountRealName']").val(data.out_account_real_name);
    				$("input[name='outAccountCardholder']").val(data.out_account_cardholder);
    				$("input[name='outAccountBankCard']").val(data.out_account_bank_card);
    				$("#outAccountCustType").val(data.out_account_cust_type).change();
    				$("#outAccountCustProperty").val(data.out_account_cust_property).change();
    				$("input[name='outAccountCustPropertySed']").val(data.out_account_cust_property);
    				$("input[name='inAccountCustId']").val(data.in_account_cust_id);
    				$("input[name='inAccountMobile']").val(data.in_account_mobile);
    				$("input[name='inAccountRealName']").val(data.in_account_real_name);
    				$("input[name='inAccountCardholder']").val(data.in_account_cardholder);
    				$("input[name='inAccountBankCard']").val(data.in_account_bank_card);
    				$("#inAccountCustType").val(data.account_type).change();
    				$("input[name='outAccountPreBalance']").val(data.out_account_pre_balance);//转出账户金额
    				$("input[name='inAccountPreBalance']").val(data.in_account_pre_balance);//转入账户金额
    				$("#outAccountPreBalance").html(data.out_account_pre_balance+" 元");//转出账户金额
    				$("#inAccountPreBalance").html(data.in_account_pre_balance+" 元");//转入账户金额
    			}else{
    				alert(data.retMsg,function(){
    			   });
    			}
    		}
    	});
	 });
	    /************ 付息方式为指定日方式则 提供可选日期******************/
	    $("#interestPaymentMethod").on("change",function(){
	    	if($("#interestPaymentMethod").val()=="2"){
	    		$("#appointLendingTime").css("display","block");
	    		/*document.getElementById("img123").style.display="block"; */
	    	}else{
	    		$("#appointLendingTime").css("display","none");
	    	}
		  });
	    
	    function searcherManage(currentPageNo){
			   $("#currentPageNo").val(currentPageNo);
			   window.location.href="/transAccountsManager/transManagementList?"+$("#myForm").serialize();
	    }	
		/***转账记账记录查询***/
		$(".searcherManage").on("click",function(){	
			searcherManage(1);
		});
			
		
		 /********转出转入用户ID校验**********/
		  function checkCustId(custId,type){
			  var flag=true;
				$.ajax({
		    		url:"/transAccountsManager/queryCustomerId",
		    		type: 'POST',
		    		data:{"custId":custId},
		    		async:false,
		    		dataType:"json",
		    		success:function(data){
		    			if(data==0){
		    				if(type=="out"){
		    					alert("转出账户用户ID不存在,请重新填写 ");
		    					 flag=false;
		    				}else{
		    					alert("收款账户用户ID不存在,请重新填写 ");
		    					 flag=false;
		    				}
		    		   }
		    	}
		   });
		return flag
	}
		  
	   /************我要预览---生成预览数据btn_preview*******************/
		$("#btn_preview").on("click",function(){
			 if(!$("#myForm").validationEngine('validate')){
		  		 return;
		  	 }
			 if(checkCustId($("input[name='outAccountCustId']").val(),"out")==false){
				 $("#outAccountCustId").focus();
				 return false;
			 }
			 if(checkCustId($("input[name='inAccountCustId']").val(),"in")==false){
				 $("#inAccountCustId").focus();
				 return false;
			 }
			 //比较理财期限--合同期限： 理财期限应该小于等于合同期限
			 if($("input[name='financialTerm']").val()>$("input[name='contractTerm']").val()){
				 alert("理财期限应该小于等于合同期限,请修改 ");
				 return false;
			 }
			/*if(($("#outAccountCustProperty").find("option:selected").text()=="请选择"&&$("input[name='outAccountCustPropertySed']").val()==undefined)
			  ||($("#outAccountCustProperty").val()==""&&$("#outAccountCustProperty").find("option:selected").text()=="请选择"&&$("input[name='outAccountCustPropertySed']").val()!=undefined)){
				 alert("请选择转出账户客户性质 ");
				 return false;
			 }*/
			 if($("#inAccountCustProperty").val()==""||$("#inAccountCustProperty").find("option:selected").text()=="请选择"){
				 alert("请选择收款账户客户性质");
				 return false;
			 }
			 
			 var obj=$("#myForm").serializeJson();
			 if($("#outAccountCustProperty").find("option:selected").text()!=""&&$("#outAccountCustProperty").val()){
				 obj.outPropertyName =$("#outAccountCustProperty").find("option:selected").text();
			 }
			 if($("#accountManagerDepartmentNo").find("option:selected").text()!=""&&$("#accountManagerDepartmentNo").val()){
				 obj.accountManagerDepartmentName =$("#accountManagerDepartmentNo").find("option:selected").text();
			 }
			 if($("#accountManagerNo").find("option:selected").text()!=""&&$("#accountManagerNo").val()){
				 obj.accountManagerName =$("#accountManagerNo").find("option:selected").text();
			 }
			 $.post("/transAccountsManager/getTransObligations",obj,function(htmlTemp){
					submitObj=obj;
					$("#preview").html("").html(htmlTemp).show();
					$(".right-content").animate({
						"scrollTop":0
					},0);
			 });
        });
			
		//固定理财申请--预览提交
		$("#preview").on("click","#previewSubmit",function(){
				$.post("/transAccountsManager/transAccountsAdd",submitObj,function(data){
					if(data.retCode == "00"){
						alert(data.retMsg,false, function(){
					       window.location.href="/transAccountsManager/totransAccountsAdd";
						});
					}else{
						alert(data.retMsg);
					}
				});
			});
			
			//预览取消
			$("#preview").on("click","#previewCancel",function(){
				$("#preview").hide();
			});
			
		 /************公司-客户经理级联查询*****************/
		  $("#accountManagerDepartmentNo").on("change",function(){
			  var accountManagerDepartmentNo = $(this).val();
			  if(accountManagerDepartmentNo){
				  $.post("/transAccountsManager/queryCusmanagerList",{
					  "accountManagerDepartmentNo":accountManagerDepartmentNo
				  },function(data){
					  var html="<option value=''>请选择</option>";
					  for(var i=0;i<data.groupList.length;i++){
						  html+=("<option class='leGroupId' value='"+data.groupList[i].le_emp_id+"'>"+data.groupList[i].le_staff_name+"</option>");
					  }
					  $("#accountManagerNo").html("").html(html);
				  });
			  }else{
				  $("#accountManagerNo").html("").html("<option value=''>请选择</option>");
			  }
			  $("#accountManagerNo").prev(".uew-select-value").find(".uew-select-text").html("请选择")
		  });
			  
})	