$(function(){
	 getDep();
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
//		   var obj=$("#myForm").serialize();
		  // window.location.href="/systemManager/employeeList?"+obj;
		   $("#myForm").submit();
	    }	
		/***检索部门***/
		$("#query").on("click",function(){	
			searcher(1);
		});
		
		
	$("#export").on("click",function(){
		var obj=$("#myForm").serialize();
		window.location.href="/systemManager/employeeExport?"+obj;
	});
	
	/************账号禁用****************/
   $(".buttonclick").on("click",function(){
	       var leStatus = $(this).closest("tr").find(".leStatus").val();
	       var leEmpId = $(this).closest("tr").find(".leEmpId").val();     
	       $.post("/systemManager/empEnbleDisable",{
	           "leStatus":leStatus,
	           "leEmpId":leEmpId
	       },function(data){
	          if(data.retCode=="00"){
	              alert(data.retMsg,function(){
	            	  window.location.reload();
	              });
	          }else{
	              alert(data.retMsg,function(){
	            	  window.location.reload();
	              });
	          }
	       
	       });      
	  
	  });
	    
	  //根据集团异步查询分公司
	  $("#companyId").on("change",function(){
		  var companyId = $(this).val();
		  if(companyId){
			  $.post("/systemManager/companyLink",{
				  "companyId":companyId
			  },function(data){
				  var html="<option value=''>请选择</option>";
				  for(var i=0;i<data.companyList.length;i++){
					  html+=("<option class='company' value='"+data.companyList[i].companyId+"'>"+data.companyList[i].companyName+"</option>");
				  }
				  $("#company").html("").html(html);
			  });
		  }else{
			  $("#company").html("").html("<option value=''>请选择</option>");
		  }
	      $("#company").prev(".uew-select-value").find(".uew-select-text").html("请选择");
	      $(".treeCheck").html("");
		  $("#treeCheckTip").show();
	  });
	  
	  //根据分公司异步查询部门
	  $("#company").on("change",function(){
		  getDep();
	  });
	  //根据分公司异步查询部门
	  $("#companyFen").on("change",function(){
			var value=$.trim($(this).val());
			var deptId=$(".deptId").val();
			if(value){
				$.ajax({
					url:"/systemManager/getDeptListByCompanyId",
					data:{companyId:value},
					async:false,
					success:function(data){
						var str='<option value="">请选择</option>';
						for(var i=0; i<data.length; i++){
							if(deptId!=data[i].deptId){
								str+=('<option value="'+ data[i].deptId +'">'+ data[i].name +'</option>');
							}
						}
						$("#parentId").html(str);
						$("#depId").html(str);
					},
					error:function(){
						alert("查询部门信息失败");
					}
				});
			}else{
				$("#parentId").html('<option value="">请选择</option>');
				$("#depId").html('<option value="">请选择</option>');
			}
			$("#parentId").closest(".uew-select").find(".uew-select-value .uew-select-text").html("请选择");
			$("#depId").closest(".uew-select").find(".uew-select-value .uew-select-text").html("请选择");
		});
	  //让部门为空的回显 但不勾选
	  function getDep(){
		  var $this=$("#company"),
		  	  value=$.trim($this.val());
		  if(value){
			  $.ajax({
				 url:"/systemManager/queryDeptJsonByCompanyId",
				 data:{companyId:value},
				 async:false,
				 success:function(data){
					 if(data.length>0){
						 var html=treeDeptList(data);
						 $("#treeCheckTip").hide();
						 $(".treeCheck").html(html);
						 $("ul.treeCheck").checkTree();
					 }else{
						 $(".treeCheck").html("");
						 $("#treeCheckTip").html("请先创建部门").show();
					 }
				 }
			  });
		  }else{
			  $(".treeCheck").html("");
			  $("#treeCheckTip").html("请先选择集团和分公司").show();
		  }
	  }
	
	  //遍历拼装组织机构
	  function treeDeptList(list){
		  var tempHtml='';
		  for(var i=0; i<list.length; i++){
			  tempHtml+='<li>';
			  tempHtml+=('<input type="checkbox" id="tree_'+ list[i].deptId +'" value="'+ list[i].deptId +'"/>');
			  tempHtml+=('<label>'+ list[i].name +'</label>');
			  if(list[i].subDeptList.length>0){
				  tempHtml+='<ul>';
				  tempHtml+=treeDeptList(list[i].subDeptList);
				  tempHtml+='</ul>';
			  }
			  tempHtml+='</li>';
		  }
		  return tempHtml;
	  };
	  
	  if($.trim($("#leDeptId").val())){
		  $("#company").change();
		  $("#tree_"+$("#leDeptId").val()).siblings(".checkbox").click();
	  }
	  
	  /******员工信息更改*******/
	  $(".empUpdate").on("click",function(){
		  if(!$("#myForm").validationEngine('validate')){
	    		return;
		  }
		  var length=$(".treeCheck input[type='checkbox']:checked").length;
		  if(length==0){
			  alert("请选择部门");
			  return false;
		  }
		  var lrRoleId = [];
		  var lqQuartersId=[];
		  var authgroupIds=[];
		  $(".lrRoleId").each(function(i,n){
			  if( $(n).prop("checked")){
				  lrRoleId.push($(n).val());
			  }
		  });
		  $(".lqQuartersId").each(function(i,n){
			  if( $(n).prop("checked")){
				  lqQuartersId.push($(n).val());
			  }
		  });
		  $(".authgroup").each(function(i,n){
			  if($(n).prop("checked")){
				  authgroupIds.push($(n).val());
			  }
		  });
		  $("#lrRoleIdList").val(lrRoleId);
		  $("#lqQuartersIdList").val(lqQuartersId);
		  $("#authgroupIds").val(authgroupIds);
		  $("#leDeptId").val($($(".treeCheck input[type='checkbox']:checked").get(length-1)).val());
		  var obj=$("#myForm").serializeJson();
		  var loginPassword = $("#password").val();
		  if(loginPassword!=undefined){
			  var pas = encryption($.trim(loginPassword));
			  obj.password = pas;
		  }
		  $.post("/systemManager/empAddOrUpdate",obj,function(data){
			  if(data.retCode=="00"){
				  alert(data.retMsg,false,function(){
	            	  window.location.href="/systemManager/employeeList";
	              });
			  }else{
				  alert(data.retMsg);
			  }
		  }); 
	  });
		  
	/********账号验证************/  
	$(".account").blur(function(){
		var account=$(".account").val();
		$.post("/systemManager/empAcountCheck",{"account":account},function(data){
			if(data.retCode=="01"){
				alert(data.retMsg);
			}
		});
	});  
   /***********重置密码***************/	  
	$("table.tablelist tbody td .resetpassword").on("click",function(){
		var leEmpId = $(this).closest("tr").find(".leEmpId").val(); 
		confirm("确定重置密码吗？",function(){
			$.post("/systemManager/resetEmpPass",{"userId":leEmpId},function(data){
				if(data && data.retCode=="00"){
					alert(data.retMsg);
				}else{
					alert(data.retMsg);
				}
			});
		});
	});
	/*****************************/
	//*********新增修改返回*******************************
	$(".scbtn2").on("click",function(){
		  window.location.href="/systemManager/employeeList";
	})
});