$(function(){	
	
	$("#myForm").validationEngine('attach',{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true
    });
	   /**
	    * 分页和条件检索
	    */
	   var $ur_a = $(".paginItem a");
		$ur_a.on("click",function(){
			var currentPageNo = $(this).attr("data-pagenum");			
			searcher(currentPageNo);
		    $("#myForm").submit();		
		});		
		function searcher(currentPageNo){
		   $("#currentPageNo").val(currentPageNo);
		   $("#myForm").submit();
	    }	
		/***检索部门***/
		$(".deptSearch").on("click",function(){	
		      $("#myForm").submit();
		});
		
		 /**
	       * 部门信息保存
	       */
	    $(".newdeptkeep").on("click",function(){
	    	 if(!$("#myForm").validationEngine('validate')){
	    		return;
	          }
	    	   var ldUnion=$("#companyId").val();
	    	   var ldCompany=$("#company").val();
	    	   var parentId=$("#parentId").val();
		       var ldName=$(".ldName").val();
		       var ldDepict=$(".ldDepict").val();
		       var ldRemark=$(".ldRemark").val();
		        
		       $.post("/systemManager/departmentsInsert",{
		    	   "ldUnion":ldUnion,
		    	   "ldCompany":ldCompany,
		    	   "parentId":parentId,
		           "ldName":ldName,
		           "ldDepict":ldDepict,
		           "ldRemark":ldRemark
		       },function(data){
		          if(data.retCode=="00"){
		              alert(data.retMsg,function(){
		            	  window.location.href="/systemManager/getDepartments";
		              });
		          }else{
		              alert(data.retMsg);
		          }
		       
		       });      
		  
		  });
	      /**
	       * 部门信息修改
	       */
	    $(".deptInfomodify").on("click",function(){
	    	  if(!$("#myForm").validationEngine('validate')){
	    		return;
	          }
	    	  var ldUnion=$("#companyId").val();
	    	   var ldCompany=$("#company").val();
	    	   var parentId=$("#parentId").val();
		       var ldName=$(".ldName").val();
		       var ldDepict=$(".ldDepict").val();
		       var ldRemark=$(".ldRemark").val();
	    	   var deptId=$(".deptId").val();
		     $.post("/systemManager/departmentsUpdate",{
		    	   "unionId":ldUnion,
		    	   "companyId":ldCompany,
		    	   "deptId":deptId,
		    	   "parentId":parentId,
		           "name":ldName,
		           "depict":ldDepict,
		           "remark":ldRemark
		       },function(data){
		          if(data.retCode=="00"){
		              alert(data.retMsg,function(){
		            	  window.location.href="/systemManager/getDepartments";
		              });
		          }else{
		              alert(data.retMsg);
		          }
		       
		       });      
		  });
	    
         /**
          *禁用按钮 
          */
		  $(".buttonclick").on("click",function(){
		       var ldStatus =$(this).closest("tr").find(".ldStatus").val();
		       var ldDeptId =$(this).closest("tr").find(".ldDeptId").val();         
		       $.post("/systemManager/ifDeptEnable",{
		             "ldStatus":ldStatus,
		             "ldDeptId":ldDeptId
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
	    
		 /**
		  * 修改密码
		  */
		  $(".updatePassword").on("click",function(){

		       var oldPassword = $(".oldPassword").val();
		       var newPassword = $(".newPassword").val();
		       var newPasswordTwo = $(".newPasswordTwo").val();
		       $.post("/systemManager/ajaxNewPasswordModify",{
		           "oldPassword":oldPassword,
		           "newPassword":newPassword,
		           "newPasswordTwo":newPasswordTwo
		       },function(data){
		          if(data.retCode=="00"){
		              alert(data.retMsg);
		          }else{
		              alert(data.retMsg);
		          }
		       
		       });      
		  
		  });
		  
		  $(".searchproduct").on("click",function(){
			  $("#myForm").submit();			  
		  });
		/*****************部门唯一性检查******************************/  
		  
		  
		  /**
		   * 集团公司联动
		   */
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
			      $("#parentId").val("");
			      $("#parentId").prev(".uew-select-value").find(".uew-select-text").html("请选择");
		  });
		  
		  $("#company").on("change",function(){
			var value=$.trim($(this).val());
			var deptId=$(".deptId").val();
			var layer=$(".layer").val();
			if(value){
				$.ajax({
					url:"/systemManager/getDeptListByCompanyId",
					data:{"companyId":value,"layer":layer},
					async:false,
					success:function(data){
						var str='<option value="">请选择</option>';
						for(var i=0; i<data.length; i++){
							if(deptId!=data[i].deptId&&data[i].parentIds.split(",").indexOf(deptId)<0){
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

});