$(function(){
	
	/**保存借款人基本信息**/
	$("#saveBasicInfo").on("click",function(){
		/*if(!$('#orderInfo').validationEngine("validate")||!$('#borrower').validationEngine("validate")){
			return false;
		}*/
		// 借款人基本信息
		var borrower = $("#borrower").serializeJson();
		
		// 更新页面数据
		$.post("/customerManager/updateBasicInfo",{
			   "loanBorrowerInfo":JSON.stringify(borrower)
		 },function(data){
			 if(data.retCode == "00"){
				 alert(data.retMsg);
			 }else{
				 alert(data.retMsg);
			 }
		});
	});
	
	/**分页**/
	$("#queryBorrowerInfo").on("click",function(){
		var obj=$("#myForm").serializeJson();
		queryList(obj);
	});
	$("#recordList").on("click",".pagin ul li a",function(){
		if(!$(this).closest("li").hasClass("current")){
			var obj=$("#myForm").serializeJson();
			obj["page.pageIndex"]=$(this).data("pagenum")||1;
			queryList(obj);
		};
	});
	function queryList(obj){
		var selectType=$("#selectType").val();
		$.post("/customerManager/asynBorrowerInfoList", obj, function(data){
			$("#recordList").html("").html(data);
		});
	};
	
	/**保存借款人抵押物信息**/
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

	//保存抵押物信息
	$("#mortgageInfoSave").on("click",function(){
		var houseList = [],
			carList = [],
			houseForm = $("#houseInfos form:visible"),
			carForm = $("#carInfos form:visible");
			a = customerId;
			b = custId;
		for(var i=0;i<houseForm.length;i++){
	//		if($.trim($(houseForm[i]).find("input[name='propertyNum']").val())){
				$(houseForm[i]).find("input[name='customerId']").val(customerId);
				$(houseForm[i]).find("input[name='custId']").val(custId);
				houseList.push($(houseForm[i]).serializeJson());
	//		}else{
	//			alert("未输入房产证编号");
	//			return;
	//		}
		}
		for(var i=0;i<carForm.length;i++){
	//		if($.trim($(carForm[i]).find("input[name='vehicleNum']").val())){
				$(carForm[i]).find("input[name='customerId']").val(customerId);
				$(carForm[i]).find("input[name='custId']").val(custId);
				carList.push($(carForm[i]).serializeJson());
	//		}else{
	//			alert("未输入机动车登记编号");
	//			return;
	//		}
		}
		if(houseList.length >0 || carList.length > 0){
			$.post("/customerManager/saveMortgageInfo",{
				"houseList":JSON.stringify(houseList),
				"carList":JSON.stringify(carList),
				"customerId":customerId
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
	
	
	// 借款人信息管理导出为Excel
	$(".exportBorrowerInfoExcel").on("click",function(){
		$("#myForm").attr("action","/customerManager/exportBorrowerInfoExcel");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	}); 
});