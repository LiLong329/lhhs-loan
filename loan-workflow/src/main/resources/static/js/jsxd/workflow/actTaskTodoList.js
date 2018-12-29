$(function(){
	
	function getData(object){
		var taskId = $(object).data('taskid');
		var taskName = $(object).data('taskname');
		var taskDefKey = $(object).data('taskdefkey');
		var procInsId = $(object).data('procinsid');
		var procDefId = $(object).data('procdefid');
		var businessId = $(object).data('businessid');
		var status = $(object).data('status');
		var executionId=$(object).data('executionid');
		var businessId = $(object).data('businessid');
		var companyId = $(object).data('companyid');
		var remark = $(object).data('remark');
		var operatorName = $(object).data('operatorname');
		var title = $(object).data('title');
		var procName = $(object).data('procname');
		var parm="taskId="+taskId+"&taskName="+taskName+"&taskDefKey="+taskDefKey+"&procInsId="+procInsId+"&procDefId="+procDefId+"&businessId="+businessId+"&status="+status;
		if(companyId!=null)parm=parm+"&companyId="+companyId;
		if(remark!=null)parm=parm+"&remark="+remark;
		if(operatorName!=null)parm=parm+"&operatorName="+operatorName;
		if(title!=null)parm=parm+"&title="+title;
		if(procName!=null)parm=parm+"&procName="+procName;
		return parm;
	}
	//执行任务办理
	$("#workflowList").on("click","table tbody tr td a.tastDo",function(){
		var formUrl = $(this).data('formurl');
		//var parm="taskId="+taskId+"&taskName="+taskName+"&taskDefKey="+taskDefKey+"&procInsId="+procInsId+"&procDefId="+procDefId+"&executionId="+executionId+"&status="+status;
		window.location.href=formUrl;
		
	});
	//删除任务tastDiagram\tastPhoto
	$("#workflowList").on("click","table tbody tr td a.tastDel",function(){
		var parm=getData(this);
		window.location.href="/workflow/url/acttask/deleteTask?"+parm;
		
	});
	//跟踪tastPhoto
	$("#workflowList").on("click","table tbody tr td a.tastDiagram",function(){
		var parm=getData(this);
		window.location.href="/workflow/url/acttask/processPic?"+parm;
		
	});
	//跟踪2tastPhoto
	$("#workflowList").on("click","table tbody tr td a.tastPhoto",function(){
		var parm=getData(this);
		window.open("/workflow/url/acttask/tracePhoto?"+parm);
		
	});
	//任务指派
	$("#workflowList").on("click","table tbody tr td a.tastCandidate",function(){
		var parm=getData(this);
		/*window.open("/workflow/acttask/candidateUser?"+parm);*/
		window.location.href="/workflow/acttask/candidateUser?"+parm;
		
	});
	//分页
	$("#workflowList").on("click",".pagin .paginItem a",function(){
		if (!$(this).parent("li").hasClass("current")) {
			var currentPageNo = $(this).attr("data-pagenum"),
				type=$("#type").val(),
				uri="";
			if(type=="1"){//待办
				uri="/workflow/acttask/todoListPage?taskType="+$("#taskType").val();
			}else if(type=="2"){//已办
				uri="/workflow/acttask/historicListSed";
			}
		    $("#currentPageNo").val(currentPageNo);
			var obj=$("#myForm").serializeJson();
			$.post(uri,obj,function(data){
				$("#workflowList").html("").html(data);
			});
		}
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
					  html+=("<option class='leGroupId' value='"+data.groupList[i].le_account+"'>"+data.groupList[i].le_staff_name+"</option>");
				  }
				  $("#accountManagerNo").html("").html(html);
			  });
		  }else{
			  $("#accountManagerNo").html("").html("<option value=''>请选择</option>");
		  }
		  $("#accountManagerNo").prev(".uew-select-value").find(".uew-select-text").html("请选择")
	  });
	  
	/******指派任务保存  任务办理人********/
	$("#btn_save").on("click",function(){
	      var obj=$("#inputForm").serializeJson();
	      var url="/workflow/acttask/todoList/3";
	      if($("#type").val()=='2'){
	    	  url="/workflow/acttask/manuallyAssignTask";
	      }
	      $.post("/workflow/acttask/candidateUserSave",obj,function(data){
			  if(data.retCode=="00"){
				  alert(data.retMsg,false,function(){
	            	  window.location.href=url;
	              });
			  }else{
				  alert(data.retMsg+',保存失败');
			  }
		  }); 
	  });
});

