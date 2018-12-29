$(function(){
   var $ur_a = $(".paginItem a");
	$ur_a.on("click",function(){
		var currentPageNo = $(this).attr("data-pagenum");			
		searcher(currentPageNo);
	});		
	function searcher(currentPageNo){
	   $("#currentPageNo").val(currentPageNo);
		// 操作类型
		var isAssign = $("#isAssign").val();
		var newUrl="/crmIntentLoanUser/crmIntentList";
		if(isAssign=="follow"){
			newUrl="/crmIntentLoanUser/crmIntentFollowList";
		}else if(isAssign=="assign"){
			newUrl="/crmIntentLoanUser/crmIntentAssignList";
		}else if(isAssign=="change"){
			newUrl="/crmIntentLoanUser/crmIntentChangeList";
		}
		$("#myForm").attr('action',newUrl);
	   $("#myForm").submit();
    };
	$("#queryBorrowerInfo").on("click",function(){						
		var currentPageNo = 1;			
		searcher(currentPageNo);
	});
	//导出
	$("#export").on("click",function(){
		$("#myForm").attr("action","/crmIntentLoanUser/intentLoanUserExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
	
	
	
	
	
	//事业部,组别
	/*$('#appointDepId').on('change',function(){
		var deptId = $('#appointDepId option:selected').val(); //选中的值
		$.ajax({
			type: "POST",
			url:"/crmIntentLoanUser/getDeptOrGroup",
			processData:true,
			data:{"deptId":deptId},
			success: function(data){
			 if(data.retCode=="00"){
				 var strHtml="<option value=''>请选择</option>";	
				if(data.groupList && data.groupList.length>0){
					 for(var i = 0; i < data.groupList.length; i++){
						 strHtml += ("<option value="+data.groupList[i].lgGroupId +">"+ data.groupList[i].lgName+"</option>" );
					 }
					 $('#appointGroupId').html(strHtml);
				}
				
			  }
			}
			
		});
	});*/
	//意向客户条件筛选
	$(".itemUl li").on("click",function(){
		var $this=$(this),name=$this.closest("ul").data("name"),value=$this.data("value");
		if(!$this.hasClass("on")){
			$this.addClass("on").siblings().removeClass("on");
			$("#"+name).val(value);
			if($this.find("span").hasClass("timeClick")){
				$(".timeSearch").show();
				$("#dateFlag").val("");
			}else{
				$(".timeSearch").hide();
				$("#beginingTime").val("");
				$("#endingTime").val("");
			}
		}
	});
	
	
	/******指派保存********/
	$("#fpSave").on("click",function(){
		var appointEmpId=$("#fpCustomer .on").val();
		if(!appointEmpId){
			alert("请选择客户经理");
			return false;
		}
		$.each($(".tablelist tbody .checkInput:checked"),function(i,n){
			var value=$(n).val();
			$.post("/crmIntentLoanUserRecord/assignRecordZP",{"appointEmpId":appointEmpId,"id":value,"actionType":"assign"},function(data){
				  if(data.retCode=="00"){
					  window.location.reload();
					  $(".FPalertCon").hide();
					  alert(data.retMsg); 		         
				  }else{
					  alert(data.retMsg);
				  }
			  }); 
		})
	      
	  });
	
	/******转移保存********/
	$("#zySave").on("click",function(){
		var appointCompanyId=$("#zyCompany .on").val();
		var appointEmpId=$("#zyCustomer .on").val();
		$.each($(".tablelist tbody .checkInput:checked"),function(i,n){
			var value=$(n).val();
			$.post("/crmIntentLoanUserRecord/assignRecordChange",{"appointCompanyId":appointCompanyId,"appointEmpId":appointEmpId,"id":value,"actionType":"change"},function(data){
				  if(data.retCode=="00"){	
					  window.location.reload();
					  $(".YHalertCon").hide();
					  alert(data.retMsg); 		         
				  }else{
					  alert(data.retMsg);
				  }
			  }); 
		})
	      
	  });
	
	
	
	
	/******分配,转移按钮********/
	$(".operation").on("click",function(){
		var len=$(".tablelist tbody .checkInput:checked").length;
		if(len<=0){
			$(".FPalertCon").hide();
			$(".YHalertCon").hide();
			alert("请至少选择一条数据。");
			return false;
		}
		if($.trim($(".operation").html())=="指派"){
			$(".FPalertCon").show();
			//分配
			$.post("/crmIntentLoanUserRecord/getCustomerList/assign",function(data){
				  if(data.retCode=="00"){
					  var html="";
					  if(data.allManager && data.allManager.length>0){
						  for (var int = 0; int <data.allManager.length; int++) {
							  console.log(data.allManager[int].staffName);
							  html+="<li value="+data.allManager[int].userId+">"+data.allManager[int].staffName+"("+data.allManager[int].mobile+")"+"</li>";	  
						}
					  }
					  $("#fpCustomer").html(html);
				  }else{
					  alert(data.retMsg);
				  }
			  });  
			
		}else if($.trim($(".operation").html())=="转移"){
			$(".YHalertCon").show();
			//转移 获取分公司列表
			$.post("/crmIntentLoanUserRecord/getZYCompanyList",function(data){
				  if(data.retCode=="00"){
					  var html="";
					  if(data.companyList && data.companyList.length>0){
						  for (var int = 0; int <data.companyList.length; int++) {
							  console.log(data.companyList[int].companyName);
							  html+="<li value="+data.companyList[int].companyId+">"+data.companyList[int].companyName+"</li>";	  
						}
					  }
					  $("#zyCompany").html(html);
				  }else{
					  alert(data.retMsg);
				  }
			  });  
		}
    });
	
	$("#fpCustomer").on("click","li",function(){
		$(this).addClass("on").siblings().removeClass("on");
	});
	$("#zyCompany").on("click","li",function(){
		$(this).addClass("on").siblings().removeClass("on");
		//分公司下的客户经理列表
		var companyId=$("#zyCompany .on").val();
		$.post("/crmIntentLoanUserRecord/getZYCompanyEmpList",{"companyId":companyId},function(data){
			  if(data.retCode=="00"){
				  var html="";
				  if(data.LoanEmployeeList && data.LoanEmployeeList.length>0){
					  for (var int = 0; int <data.LoanEmployeeList.length; int++) {
						  html+="<li value="+data.LoanEmployeeList[int].userId+">"+data.LoanEmployeeList[int].staffName+"("+data.LoanEmployeeList[int].mobile+")"+"</li>";	  
					}
				  }
				  $("#zyCustomer").html(html);
			  }else{
				  alert(data.retMsg);
			  }
		  });  
	});
	$("#zyCustomer").on("click","li",function(){
		$(this).addClass("on").siblings().removeClass("on");
	});
	
	$(".tablelist tbody .checkInput").on("change",function(){
		var len=$(".tablelist tbody .checkInput:checked").length;
		$(".checkNum").html(len);
	});
	//导入
	$("#importBtn").on("click",function(){
		$("#preview").show();
		var winHeight = $(window).height()-160,
	        $_this_height = $("#preview .preview").height();
		$("#preview .preview").css({
	     	top:winHeight/2-$_this_height/2
	    });
		$("#importFile").val("");
	});
	$("#previewSubmit").on("click",function(){
		var $this = $("#importFile");
		if($this.val()&&$this[0].files.length>0){
			var option={
				url:"/crmIntentLoanUser/intentLoanUserImport",
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.retCode=='00'){
						alert("导入成功",false,function(){
							$("#preview").hide();
						});
					}else{
						alert(data.retMsg);
					}
				}
			};
			$("#myFileForm").ajaxSubmit(option);
			return false;
		}else{
			alert("请选择文件");
		}
	});
	$("#previewCancel").on("click",function(){
		$("#preview").hide();
	});
	
	$(".zhuanyi").on("click",function(){
		$(".YHalertCon").show();
	});
	$(".closeAlert").on("click",function(){
		$(".FPalertCon").hide();
		$(".YHalertCon").hide();
	});
	
	//外呼电话
	$(".call-mobile").on("click",function(){
		var use=$(this).data("use");
		if(use){
			$(this).data("use",0);
			cookie.set("is-call-mobile",true);
		}else{
			return false;
		}
	});
});
function timeChange(){
	var $this=$(this),
		name=$this.attr("name"),
		value=$this.val();
	$("#"+name).val(value);
}
function checkAll() {  
	var all=document.getElementById('getAll');//获取到点击全选的那个复选框的id  
	var one=document.getElementsByName('checkname[]');//获取到复选框的名称  
	var count = 0;
	if(all.checked){
		count = one.length;
	}else{
		count = 0;
	}
	$(".checkNum").html(count);
	//因为获得的是数组，所以要循环 为每一个checked赋值  
	for(var i=0;i<one.length;i++){  
		one[i].checked=all.checked; //直接赋值不就行了嘛  
	}  
	$(".checkNum").html(count);
}