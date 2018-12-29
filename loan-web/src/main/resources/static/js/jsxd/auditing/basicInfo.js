$(function(){
	//绑定异步验证
	$('#orderInfo,#borrower,#orderInfoDetail').validationEngine("attach",{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true,
		autoHideDelay:2000
	});
	//客户经理修改
	$("#tab2 select[name='customerManager']").on("change",function(){
		var $this=$(this),
			value=$this.val(),
			leGroupId=$this.find("option:selected").data("legroupid")||"",//组别
			leDeptId=$this.find("option:selected").data("ledeptid")||"";//部门
		if(value){
			if(leGroupId){
				$("#depId").val(leGroupId);
			}else{
				$("#depId").val(leDeptId);
			}
		}else{
			$("#depId").val("");
		}
	});
	//保存
	$("#saveBasicInfo").on("click",function(){
		if(!$('#orderInfo').validationEngine("validate")||!$('#borrower').validationEngine("validate")
				||!$('#orderInfoDetail').validationEngine("validate")){
			return false;
		}
		for(var i = 0; i < $("#contactsInfo form").length; i++) {
			if(!$($("#contactsInfo form")[i]).validationEngine("validate")){
				return false;
			}
		}
		for(var i = 0; i < $("#coborrowerInfo form").length; i++) {
			if(!$($("#coborrowerInfo form")[i]).validationEngine("validate")){
				return false;
			}
		}
		for(var i = 0; i < $("#guaranteeInfo form").length; i++) {
			if(!$($("#guaranteeInfo form")[i]).validationEngine("validate")){
				return false;
			}
		}
		
		// 订单号
		var orderNo = $("#orderNo").val();
		var customerOrigin = $("#customerOrigin").val();
		// 订单基本信息
		var orderInfo = $("#orderInfo").serializeJson();
		var orderInfoDetail = $("#orderInfoDetail").serializeJson();
		// 借款人信息
		var borrower = $("#borrower").serializeJson();
		//联系人、共借人、担保人
		var relationList=[];
		$("#contactsInfo form,#coborrowerInfo form,#guaranteeInfo form").each(function(i,n){
			var temp=$(n).serializeJson();
			if(temp.name||temp.idNum||temp.mobile){
				temp.orderNo=orderNo;
				relationList.push(temp);
			}
		});
		// 更新页面数据
		$.post("/approval/updateBasicInfo",{
			   "customerOrigin":customerOrigin,
			   "orderNo":orderNo,
			   "orderInfo":JSON.stringify(orderInfo),
			   "orderInfoDetail":JSON.stringify(orderInfoDetail),
			   "loanBorrowerInfoWithBLOBs":JSON.stringify(borrower),
			   "relationList":JSON.stringify(relationList)
		 },function(data){
			 if(data.retCode == "00"){
				 alert(data.retMsg);
			 }else{
				 alert(data.retMsg);
			 }
		});
	});
	// 验证借款期限不能超过5年
	$(".loan_term_unit").on("change",function(){
		$('.loan_term').removeClass(function(i,o){
			if(o&&o.indexOf("validate")>=0){
				var str=o.substring(o.indexOf("validate"));
				str=str.substring(0,(str.indexOf(" ")>0?str.indexOf(" "):str.length));
				return str;
			}
		});
		if($(this).val() == '个月'){
			$('.loan_term').addClass("validate[custom[integer],max[60]]");
		}else if($(this).val() == '天'){
			$('.loan_term').addClass("validate[custom[integer],max[1825]]");
		}
	});
	$(".loan_term_unit").change();
	/********************************************联系人，共借人，担保人**************************************************/
	var contactsInfo = $("#contactsInfo ul:first").prop("outerHTML"),//联系人信息ul
		coborrowerInfo = $("#coborrowerInfo ul:first").prop("outerHTML"),//共同借款人信息ul
		guaranteeInfo = $("#guaranteeInfo ul:first").prop("outerHTML");//担保人信息ul
	//虚线
	var line = '<div class="lines"><img class="del-zz" src="/img/loan_reduce.png"></div>';
	//添加联系人
	$("#addContacts").on("click",function(){
		var $newInfo = $("#contactsInfo").append(line+contactsInfo).find("ul:last");
		$newInfo.find("input").not("[type='hidden']").val("");
	});
	//添加共同借款人
	$("#addCoborrower").on("click",function(){
		var $newInfo = $("#coborrowerInfo").append(line+coborrowerInfo).find("ul:last");
		$newInfo.find("input").not("[type='radio']").not("[type='hidden']").val("");
		$newInfo.find(".differentTip").html("");
	});
	//添加担保人
	$("#addGuarantee").on("click",function(){
		var $newInfo = $("#guaranteeInfo").append(line+guaranteeInfo).find("ul:last");
		$newInfo.find("input").not("[type='radio']").not("[type='hidden']").val("");
		$newInfo.find(".differentTip").html("");
	});
	//删除联系人、共同借款人、担保人
	$("body").on("click",".del-zz",function(){
		$(this).closest(".lines").next("ul").remove();
		$(this).closest(".lines").remove();	
	});
	//下户节点-点击借款人已经申请过借款 跳转到全部报单对应页面
	$("#getBorrowerExtend").on("click",function(){
		var newWindow = window.open();
		var html = "";
		html += "<html><head></head><body><form id='borrowerForm' method='post' action='/orderInfo/list' accept-charset='UTF-8'>"; 
		html += "<input type='hidden' name='bname' value='" + $("input[name='bname']").val() + "'/>"; 
		html += "<input type='hidden' name='idNum' value='" + $("input[name='idNum']").val() + "'/>";
		html += "<input type='hidden' name='type' value='1'/>";
		html += "</form><script type='text/javascript'>document.getElementById('borrowerForm').submit();"; 
		html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, ""); 
		newWindow.document.write(html);
		return newWindow;
	});
	//下户节点-点击该房产申请过贷款 跳转到全部报单对应页面
	$("#getHouseExtend").on("click",function(){
		var newWindow = window.open();
		var html = "";
		html += "<html><head></head><body><form id='houseForm' method='post' action='/orderInfo/list' accept-charset='UTF-8'>"; 
		html += "<input type='hidden' name='propertyNum' value='" + $("input[name='propertyNum']").val() + "'/>"; 
		html += "<input type='hidden' name='propertyName' value='" + $("input[name='propertyName']").val() + "'/>";
		html += "<input type='hidden' name='type' value='1'/>";
		html += "</form><script type='text/javascript'>document.getElementById('houseForm').submit();"; 
		html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, ""); 
		newWindow.document.write(html);
		return newWindow;
	});
});