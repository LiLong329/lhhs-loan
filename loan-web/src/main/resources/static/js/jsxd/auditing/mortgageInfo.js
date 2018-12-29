$(function(){
	var houseInfo = $("#houseInfos ul:first").prop("outerHTML"),//房产信息ul
		carInfo = $("#carInfos ul:first").prop("outerHTML");//车产信息ul
	//虚线
	var line = '<div class="lines"><img class="del-zz" src="/img/loan_reduce.png"></div>';
	//添加抵押物-房产信息
	$("#addHouseInfo").on("click",function(){
		if($("#houseInfos ul:first").css("display")=="none"){
			$("#houseInfos ul:first").show();
		}else{
			var $newHouseInfo = $("#houseInfos").append(line+houseInfo).find("ul:last");
			$newHouseInfo.find("input").val("");
			$newHouseInfo.find("select").val("");
			$newHouseInfo.find(".uew-select-text").html("请选择");
			$newHouseInfo.find(".citySelect").html("<option value=''>请选择</option>");
			$newHouseInfo.show();
		}
	});
	//添加抵押物-车产信息
	$("#addCarInfo").on("click",function(){
		if($("#carInfos ul:first").css("display")=="none"){
			$("#carInfos ul:first").show();
		}else{
			var $newCarInfo = $("#carInfos").append(line+carInfo).find("ul:last");
			$newCarInfo.find("input").val("");
			$newCarInfo.find("select").val("");
			$newCarInfo.find(".uew-select-text").html("请选择");
			$newCarInfo.find(".citySelect").html("<option value=''>请选择</option>");
			$newCarInfo.show();
		}
	});
	//删除抵押物
	$("body").on("click",".del-zz",function(){
		$(this).closest(".lines").next("ul").remove();
		$(this).closest(".lines").remove();	
	});
	
	 /*********保存抵押物信息-房产信息重复校验**********/
	  function queryHouseExtend(){
		    var houseList = new Array();
		    var houseForm = $("#houseInfos form:visible");
			for(var i=0;i<houseForm.length;i++){
					houseList.push($(houseForm[i]).serializeJson());
			}
			$.ajax({
	    		url:"/mortgageInfo/queryHouseExtend",
	    		type: 'POST',
	    		data:{"houseList":JSON.stringify(houseList)},
	    		async:false,
	    		dataType:"json",
	    		success:function(data){
	    			if(data.retCode=="01"){
	    				alert(data.retMsg+" 房产已经申请过借款!");
	    			}
	    		}
	    	});
	  }
	
	//保存抵押物信息
	$("#mortgageInfoSave").on("click",function(){
		queryHouseExtend();
		var houseList = new Array(),
			carList = new Array(),
			houseForm = $("#houseInfos form:visible"),
			carForm = $("#carInfos form:visible");
			//orderNo = $("#orderNo_temp").val(),
			//custId = $("#custId_temp").val();
		for(var i=0;i<houseForm.length;i++){
//			if($.trim($(houseForm[i]).find("input[name='propertyNum']").val())){
				$(houseForm[i]).find("input[name='orderNo']").val(orderNo);
				$(houseForm[i]).find("input[name='custId']").val(custId);
				houseList.push($(houseForm[i]).serializeJson());
//			}else{
//				alert("未输入房产证编号");
//				return;
//			}
		}
		for(var i=0;i<carForm.length;i++){
//			if($.trim($(carForm[i]).find("input[name='vehicleNum']").val())){
				$(carForm[i]).find("input[name='orderNo']").val(orderNo);
				$(carForm[i]).find("input[name='custId']").val(custId);
				carList.push($(carForm[i]).serializeJson());
//			}else{
//				alert("未输入机动车登记编号");
//				return;
//			}
		}
		if(houseList.length >0 || carList.length > 0){
			$.post("/mortgageInfo/saveMortgageInfo",{
				"houseList":JSON.stringify(houseList),
				"carList":JSON.stringify(carList),
				"orderNo":orderNo
			},function(data){
				if(data.retCode == "00"){
					alert(data.retMsg);
				}else{
					alert(data.retMsg);
				}
			});
		}else{
			alert("没有信息可以保存");
		}
	});
	
	//查看资质图片
	$("#getUrls").on("click",function(){
	     window.open('/mortgageInfo/toViewPageByDetails?orderNo='+$("#orderNo").val());
	 });
	
});