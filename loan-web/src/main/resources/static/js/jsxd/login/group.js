$(function(){	
	
	/*********************分页*******************/
	var $ur_a = $(".paginItem a");
	$ur_a.on("click",function(){
		var currentPageNo = $(this).attr("data-pagenum");			
		searcher(currentPageNo);
	});		
	function searcher(currentPageNo){
	   $("#currentPageNo").val(currentPageNo);
	   var obj=$("#myForm").serialize();
	   window.location.href="/systemManager/groupList?"+obj;
    }	
	/***检索部门***/
	$("#searcher").on("click",function(){	
		searcher(1);
	});
	
	 $(".keepNewGroup").on("click",function(){
		      if(!$("#myForm").validationEngine('validate')){
	    		    return;
	    	   }
		       var lgDeptId=$(".lgDeptId").val();
		       var lgName=$(".lgName").val();
		       var lgDepict=$(".lgDepict").val();
		       var lgRemark=$(".lgRemark").val();
		       if(!lgDeptId){
		    	   alert("请为组别选择部门！");
		    	   return;
		       }
		       $.post("/systemManager/newGroupAdd",{
		           "lgDeptId":lgDeptId,
		           "lgName":lgName,
		           "lgDepict":lgDepict,
		           "lgRemark":lgRemark
		       },function(data){
		          if(data.retCode=="00"){
		              alert(data.retMsg,function(){
		            	  window.location.href="/systemManager/groupList";
		              });
		          }else{
		              alert(data.retMsg);
		          }
		       
		       });      
		  
		  });
	      /**
	       * 组别信息修改
	       */
	    $(".groupInfoModify").on("click",function(){
	    	  if(!$("#myForm").validationEngine('validate')){
	    		return;
	    	   }
	    	   var lgGroupId=$(".lgGroupId").val();
		       var lgDeptId=$(".lgDeptId").val();
		       var lgName=$(".lgName").val();
		       var lgDepict=$(".lgDepict").val();
		       var lgRemark=$(".lgRemark").val();
		     $.post("/systemManager/groupModify",{
		    	   "lgGroupId":lgGroupId,
		           "lgDeptId":lgDeptId,
		           "lgName":lgName,
		           "lgDepict":lgDepict,
		           "lgRemark":lgRemark
		       },function(data){
		          if(data.retCode=="00"){
		              alert(data.retMsg,function(){
		            	  window.location.href="/systemManager/groupList";
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

		       var lgStatus = $(this).closest("tr").find(".lgStatus").val();
		       var lgGroupId = $(this).closest("tr").find(".lgGroupId").val();        
		       $.post("/systemManager/enableOrDisableSet",{
		           "lgStatus":lgStatus,
		           "lgGroupId":lgGroupId
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
			  if(!$("#myForm").validationEngine('validate')){
		    		return;
		        }
		       var oldPassword = $(".oldPassword").val();
		       var newPassword = $(".newPassword").val();
		       var newPasswordTwo = $(".newPasswordTwo").val();
		       var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$/;
				if(null==oldPassword||oldPassword==""){
			    	alert("原始密码不能为空!");
			    	return false;
			    }
			    if(null==newPassword||newPassword==""){
			    	alert("密码不能为空!");
			    	return false;
			    }else if(!reg.test(newPassword)){
			    	alert("密码为6~18位数字+字母");
			    	return false;
			    }
			    if(null==newPasswordTwo||newPasswordTwo==""){
					alert("确认密码不能为空!");
					return false;
				}
			    if(newPasswordTwo!=newPasswordTwo){
					alert("两次输入的密码不一致!");
					return false;
				}
			    var password=encryption($.trim(oldPassword));
				var newPassword=encryption($.trim(newPassword));
		       $.post("/systemManager/newPassSet",{
		           "password":password,
		           "newPassword":newPassword
		       },function(data){
		    	   if(data && data.retCode=="00"){
						alert("操作成功",true,function(){
		    				window.location.href="/logout";
		    			});
					}else{
						alert(data.retMsg);
					}
		          
		       });      
		  
		  });
		  
		  $(".searchproduct").on("click",function(){
			  $("#myForm").submit();			  
		  });
		  
		  /*****************************部门组别级联查询************************************/
		  $("#leDept").on("change",function(){
			  var deptId = $(this).val();
			  if(deptId){
				  $.post("/systemManager/deptGroupLink",{
					  "lgDeptId":deptId
				  },function(data){
					  var html="<option value=''>请选择</option>";
					  for(var i=0;i<data.groupList.length;i++){
						  html+=("<option class='leGroupId' value='"+data.groupList[i].lg_group_id+"'>"+data.groupList[i].lg_name+"</option>");
					  }
					  $("#leGroup").html("").html(html);
				  });
			  }else{
				  $("#leGroup").html("").html("<option value=''>请选择</option>");
			  }
			  $("#leGroup").prev(".uew-select-value").find(".uew-select-text").html("请选择")
		  });
});