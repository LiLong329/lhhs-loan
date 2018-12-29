$(function(){
	//新增
	$("#addBtn").on("click",function(){
		window.location.href="/noticeModel/toAdd";
	});
	//模板类型选择
	$("#modelType").on("change",function(){
		var value=$(this).val(),str="<option value=''>请选择</option>";
		if(value){
			$.ajax({
				url:"/noticeModel/getByType",
				data:{"modelType":value},
				async:false,
				dataType:"json",
				success:function(data){
					if(data){
						 $.each(data,function(i,n){
							 str += ("<option data-modelType='"+value+"' value='"+ n.englishName +"'>"+ n.name +"</option>");
		   			  	 });
					}
				}
			});
		}
		$("#englishName").html(str).closest(".uew-select").find(".uew-select-value .uew-select-text").html("请选择");
		$("#noticeType").html("");
	});
	//模板名称选择
	$("#englishName").on("change",function(){
		var value=$(this).val(),str="",modelType=$(this).find("option:selected").data("modelType");
		if(value){
			$.ajax({
				url:"/noticeModel/getByName",
				data:{"modelType":modelType,"englishName":value},
				async:false,
				dataType:"json",
				success:function(data){
					if(data){
						$.each(data,function(k,v){
							var noticeType = "";
							if (k==1) {
								noticeType="短信"
							}else if (k==2) {
								noticeType="邮件"
							}else if (k==3) {
								noticeType="站内消息"
							}
							var li = "";
							for (var i = 0; i < v.length; i++) {
								li += "<li><input type='radio' name='radio"+k+"' class='chooseRdo' value='"+v[i].id+"'/><p>"+v[i].content+"</p></li>";
							}
							str += "<p class='levelTitle'><img src='/img/loan_reduce.png' style='width:15px; margin-right:8px;'/>"+noticeType+"</p>"+
								   "<ul class='chooseUl'>"+
								   		li+
								   "</ul>";
						});
					}
				}
			});
		}
		$("#noticeType").html(str);
	});
	
	$("#noticeType").on("click",".levelTitle",function(){
		var img = $(this).find("img").attr("src");
		if (img=="/img/loan_reduce.png") {
			$(this).find("img").attr("src","/img/add_red.png");
		}else{
			$(this).find("img").attr("src","/img/loan_reduce.png");
		}
		$(this).next("ul").slideToggle(1);
	});
	$("#noticeType").on("mousedown","input[type='radio']",function(){
		if($(this).prop("checked")){
			$(this).prop("checked",false);
		}else{
			$(this).prop("checked",true);
		}
	});
	$("#noticeType").on("click","input[type='radio']",function(e){
		if (e && e.preventDefault) {
			e.preventDefault();
		}else{
			e.returnValue = false;
		}
	});
	$("#noticeType").on("change","input[type='radio']",function(e){
		if (e && e.preventDefault) {
			e.preventDefault();
		}else{
			e.returnValue = false;
		}
	});

	
/*	$("#quartersName").on("input",function() {
		var quartersName = $("#quartersName").val();
		$.ajax({
			type: "POST",
			url: "/noticeModel/getQuarters",
			data:{queryName:quartersName},
			async:true,
			processData:true,
			success: function (data) {
				var options="";
				 for(var i=0;i<data.length;i++){ 
					 options+="<div class='downSelect'><input type='checkbox' value='"+data[i].englishName+"' style='float: left;margin-top: 10px;'/><span>"+data[i].name+"</span></div>"; 
				 } 
				$("#quartersAuto").html(options);
			}
		});
	});*/
	
	//绑定验证
    $('#myForm').validationEngine('attach');
    $("#saveButton").on("click",function(){
    	if(!$('#myForm').validationEngine('validate')){
    		return ;
    	}
    	var modelType = $("#modelType").val();
    	var englishName = $("#englishName").val();
    	var noticeTypeAll = "";
    	for(var i = 0; i < $("#noticeType input[type='radio']").length; i++) {
			if($($("#noticeType input[type='radio']")[i]).prop("checked")){
				noticeTypeAll += $($("#noticeType input[type='radio']")[i]).val()+",";
			}
		}
    	if (!noticeTypeAll) {
    		alert("至少选择一个通知类型");
    		return false;
		}
    	var receiverAll = "";
    	/*for(var i = 0; i < $("#quartersAuto input[type='checkbox']").length; i++) {
    		if($($("#quartersAuto input[type='checkbox']")[i]).prop("checked")){
    			receiverAll += $($("#quartersAuto input[type='checkbox']")[i]).val()+",";
    		}
    	}
    	if (!receiverAll) {
    		alert("至少选择一个通知对象");
    		return false;
    	}*/
		$.ajax({
			type: "POST",
			url: "/noticeModel/save",
			data:{
				"modelType":modelType,
				"englishName":englishName,
				"noticeTypeAll":noticeTypeAll,
				"receiverAll":receiverAll
				},
			async:true,
			processData:true,
			success: function (data) {
				if(data && data.retCode=='00'){
	    			alert("操作成功",true,function(){
	    				window.location.href="/noticeModel/list/1";
	    			});
	    		}else{
	    			if (data.retMsg) {
						alert(data.retMsg);
					}else{
						alert("操作失败",true,function(){
							window.location.reload();
						});
					}
	    		}
			}
		});
    });
    $("#editButton").on("click",function(){
    	var modelType = $("#modelType").val();
    	var englishName = $("#englishName").val();
    	var noticeTypeAll = "";
    	for(var i = 0; i < $("#noticeType input[type='radio']").length; i++) {
    		if($($("#noticeType input[type='radio']")[i]).prop("checked")){
    			noticeTypeAll += $($("#noticeType input[type='radio']")[i]).val()+",";
    		}
    	}
    	var receiverAll = "";
    	/*for(var i = 0; i < $("#quartersAuto input[type='checkbox']").length; i++) {
    		if($($("#quartersAuto input[type='checkbox']")[i]).prop("checked")){
    			receiverAll += $($("#quartersAuto input[type='checkbox']")[i]).val()+",";
    		}
    	}
    	if (!receiverAll) {
    		alert("至少选择一个通知对象");
    		return false;
    	}*/
    	$.ajax({
    		type: "POST",
    		url: "/noticeModel/save",
    		data:{
    			"modelType":modelType,
    			"englishName":englishName,
    			"modelStatus":"1",
    			"noticeTypeAll":noticeTypeAll,
    			"receiverAll":receiverAll
    		},
    		async:true,
    		processData:true,
    		success: function (data) {
    			if(data && data.retCode=='00'){
    				alert("操作成功",true,function(){
    					window.location.href="/noticeModel/list/1";
    				});
    			}else{
    				if (data.retMsg) {
    					alert(data.retMsg);
    				}else{
    					alert("操作失败",true,function(){
    						window.location.reload();
    					});
    				}
    			}
    		}
    	});
    });
});