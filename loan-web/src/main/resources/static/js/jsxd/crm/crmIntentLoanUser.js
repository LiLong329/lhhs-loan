$(function(){
	/******指派\转移任务保存  任务办理人********/
	$("#btn_Assign").on("click",function(){
	      var obj=$("#inputForm").serializeJson();
	      var url="/crmIntentLoanUser/crmIntentAssignList";
	      // 操作类型
		  var actionType = $("#actionType").val();
		 
		  if(actionType=="change"){
			  url="/crmIntentLoanUser/crmIntentChangeList";
		  }
	      $.post("/crmIntentLoanUserRecord/assignRecord",obj,function(data){
			  if(data.retCode=="00"){
				  alert(data.retMsg,false,function(){
	            	  window.location.href=url;
	              });
			  }else{
				  alert(data.retMsg+',保存失败');
			  }
		  }); 
	  });
	/******客户跟踪保存  ********/
	$("#btn_flow").on("click",function(){
	      var obj=$("#inputForm").serializeJson();
	      var url="/crmIntentLoanUser/crmIntentFollowList";
	      if($("#customerStatus").val()=="zhipai"){
	    	  url="/crmIntentLoanUser/crmIntentAssignList";
	      }
		  if(!$("#inputForm").validationEngine("validate")){
			  return false;
		  }
	      $.post("/crmIntentLoanUserRecord/savefollowRecord",obj,function(data){
			  if(data.retCode=="00"){
				  if(sipAccount){
					  alert(data.retMsg,false,function(){//staffName
						  var str='<div class="clearfix"><div class="HFitem"><p class="time">'
						  			+obj.visitTime
						  			+'</p><div class="HFitem-con"><p class="name clearfix"><span>'
						  			+staffName
						  			+'</span><span>&gt;</span><span>'
						  			+$("select[name='followType'] option:selected").html()
						  			+'</span></p><p class="desc">客户状态：<span>'
						  			+$("select[name='businessStatus'] option:selected").html()
						  			+'</span></p><p class="desc">跟进回访内容：<span>'
						  			+obj.remark
						  			+'</span></p><p class="desc">实际跟进时间：<span>'
						  			+obj.visitTime.substring(0,10)
						  			+'</span></p></div></div></div>';
						  	$("#histoicFlow").prepend(str);
					  });
				  }else{
					  alert(data.retMsg,false,function(){
		            	  window.location.href=url;
		              });
				  }
			  }else{
				  alert(data.retMsg+',保存失败');
			  }
		  }); 
	  });
	/*************回访意向客户保存基本信息******************/
	$("#btn_edit_flow").on("click",function(){
			if(!$("#editForm").validationEngine("validate")){
				return false;
			}
	      var obj=$("#editForm").serializeJson();
	      var url="/crmIntentLoanUser/crmIntentFollowList";
	      if($("#customerStatus").val()=="zhipai"){
	    	   url="/crmIntentLoanUser/crmIntentAssignList";
	       }
	      $.post("/crmIntentLoanUserRecord/savefollowRecord",obj,function(data){
			  if(data.retCode=="00"){
				  if(sipAccount){
					  alert(data.retMsg,false);
				  }else{
					  alert(data.retMsg,false,function(){
		            	  window.location.href=url;
		              });
				  }
			  }else{
				  alert(data.retMsg+',保存失败');
			  }
		  }); 
	  });
	
});