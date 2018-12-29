$(function(){
	function initProvinceSelect(flag) {
		var options=""; 
		var orgId = $("#zijin").find("option:selected").val();
		$.ajax({
			type: "POST",
			url: "/systemManager/getOrgSupportProvince",
			processData:true,
			data:{orgId:orgId},
			success: function(data){
				options+="<option value=''>请选择</option>"; 
				for(var i=0;i<data.length;i++){ 
					options+="<option value="+data[i].provinceNo+">"+data[i].provinceName+"</option>"; 
				} 
				$("#provinceSelect").prev().find(".uew-select-text").html('请选择');
				$("#citySelect").prev().find(".uew-select-text").html('请选择');
				$("#citySelect").html("<option value=''>请选择</option>"); 
				$("#provinceSelect").html(options);
				if (flag) {
					$("#cityList").html("");
				}
			}
		});  	
	}
	initProvinceSelect();
	$(".checked").dblclick(function(){  
		$(this).attr('checked',false);
	});  
	$('#updateForm').validationEngine();
	$("#updateButton").on("click",function(){
		var productParentNo = $("#productParentNo").val();
		if(!$('#updateForm').validationEngine('validate')){
			return;
		}
		if(!$("#cityList li").html()){
			alert("请添加支持的城市");
			return false;
		}
		$("#cityList input").remove();
		$("#cityList li").each(function(i,n){
			var provinceName = $.trim($(n).find("span.provinceName").html());
			var cityName = $.trim($(n).find("span.cityName").html());
			var provinceNo = $(n).find("span.provinceName").attr("provinceNo");
			var cityNo = $(n).find("span.cityName").attr("cityNo");
			
			var supportList =                                       
				"<input type='hidden' name='productSupportAreas["+i+"].provinceNo' value='"+provinceNo+"' />"+
				"<input type='hidden' name='productSupportAreas["+i+"].provinceName' value='"+provinceName+"' />"+
				"<input type='hidden' name='productSupportAreas["+i+"].cityNo' value='"+cityNo+"' />"+
				"<input type='hidden' name='productSupportAreas["+i+"].cityName' value='"+cityName+"' />";
			$("#cityList").append(supportList);                     
		});
		var formData=$("#updateForm").serializeJson();
		$.ajax({
			type: "POST",
			url: $('#updateForm')[0].action,
			processData:true,
			data:formData,
			success: function(data){
			 if(data.retCode=="00"){
				 alert(data.retMsg,function(){
					 window.location.href="/systemManager/productChildrenList/1?productNo="+productParentNo;
					});
			  }else{
				alert(data.retMsg,function(){
//						window.location.reload(true);
				});
			  }
			},error : function(e){
				//alert(e);
			}
		});  	
	});
	$("#zijin").change(function(){
		initProvinceSelect(true);
	});
	$("#provinceSelect").change(function(){
		var options=""; 
		var orgId = $("#zijin").find("option:selected").val();
		var provinceNo = $("#provinceSelect").find("option:selected").val();
		$.ajax({
			type: "POST",
			url: "/systemManager/getOrgSupportCity",
			processData:true,
			data:{orgId:orgId,provinceNo:provinceNo},
			success: function(data){
				options+="<option value=''>请选择</option>"; 
				for(var i=0;i<data.length;i++){ 
					options+="<option value="+data[i].cityNo+">"+data[i].cityName+"</option>"; 
				} 
				$("#citySelect").prev().find(".uew-select-text").html('请选择');
				$("#citySelect").html(options); 
			}
		});  	
	});
	//添加支城市
	$(".cityAdd").on("click", function(){
		var provinceNo = $("#provinceSelect").find("option:selected").val();
		var provinceName = $("#provinceSelect").find("option:selected").text();
		var cityNo = $("#citySelect").find("option:selected").val();
		var cityName = $("#citySelect").find("option:selected").text();
		if (!cityNo) {
			alert("请选择城市");
			return false;
		}
		var flag = true;
		$("#cityList li").each(function(i,n){
			if($.trim($(n).find("span.cityName").html())==cityName){
				alert("城市已存在");
				flag = false;
				return false;
			}
		});
		if (flag) {
			var li = "<li>" +
						"<span class='provinceName' provinceNo='"+provinceNo+"'>"+provinceName+"</span>" +
						"<span class='cityName' cityNo='"+cityNo+"'>"+cityName+"</span>" +
						"<span class='aStyle'>×</span>" +
					  "</li>";
			$("#cityList").append(li);
		}else{
			return false;
		}
	});
	//删除支持城市
	$("#cityList").on("click",".aStyle",function(){
		var $this=$(this);
		$this.closest("li").remove();
	});
});
	
	