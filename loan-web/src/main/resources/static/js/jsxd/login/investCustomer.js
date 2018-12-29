$(function(){
	if($("input[name='msg']").val()!="edit"){
		$("input[name='sex']").eq(0).attr("checked","checked");
	}
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
			searcher(currentPageNo);
		});		
		function searcher(currentPageNo){
		   $("#currentPageNo").val(currentPageNo);
		   var obj=$("#myForm").serialize();
		   window.location.href="/investCustomer/investCustomerList?"+obj;
	    }	
		
		/***查询***/
		$(".searcher").on("click",function(){	
			searcher(1);
		});
		
		
	$("#export").on("click",function(){
		$("#myForm").attr("action","/investCustomer/investCustomerExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	  
	 /*********校验手机号唯一性**********/
	  function checkMobile(mobile,type){
		  var flag=true;
			$.ajax({
	    		url:"/investCustomer/queryCustomerMobile",
	    		type: 'POST',
	    		data:{"mobile":mobile,type:type},
	    		async:false,
	    		dataType:"json",
	    		success:function(data){
	    			if(data.retCode!="00"){
	    				alert("手机号已经存在，请修改！ ");
	    				flag=false;
	    			}
	    		}
	    	});
			return flag;
	  }
	  
	  /******理财人信息子新增或更改*******/
	  $("#btn_save").on("click",function(){
		  if(!$("#myForm").validationEngine('validate')){
	    		return false;
	      }
		  if($("input[name='msg']").val()=="save"
			  &&checkMobile($("input[name='investCustomerMobile']").val(),$("input[name='msg']").val())==false){
			  return false;
		  };
		  if($("input[name='msg']").val()!="save"&&($("input[name='hiddenMobile']").val()
				 !=$("input[name='investCustomerMobile']").val())){
			  if(checkMobile($("input[name='investCustomerMobile']").val(),$("input[name='msg']").val())==false){
				  return false;
			  }
		  }
		  var obj=$("#myForm").serializeJson();
		  obj.bankName= $("#bankId").find("option:selected").text();
		  $.post("/investCustomer/investCusAddOrUpdate",obj,function(data){
			  if(data.retCode=="00"){
				  alert(data.retMsg,function(){
	            	  window.location.href="/investCustomer/investCustomerList";
	              });
			  }else{
				  alert(data.retMsg);
			  }
		  }); 
	  });
	  /************公司-客户经理级联查询*****************/
	  $("#affiliatedCompanyId").on("change",function(){
		  var affiliatedCompanyId = $(this).val();
		  if(affiliatedCompanyId){
			  $.post("/investCustomer/companyManagerNoLink",{
				  "affiliatedCompanyId":affiliatedCompanyId
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
});