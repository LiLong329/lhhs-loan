$(function() {
	$("#payForm").validationEngine('attach', {
		autoPositionUpdate : true,
		autoHidePrompt : true,
		maxErrorsPerField : 1,
		showOneMessage : true
	});

	/**
	 * 分页
	 */
	var $ur_a = $(".paginItem a");
	$ur_a.on("click", function() {
		var currentPageNo = $(this).attr("data-pagenum");
		searcher(currentPageNo);
	});
	function searcher(currentPageNo) {
		$("#currentPageNo").val(currentPageNo);
		var obj = $("#myForm").serialize();
		window.location.href = "/customerManager/loanPaymentList?" + obj;
	}
	/** *检索部门** */
	$(".searcher").on("click", function() {
		searcher(1);
	});

	$("#export").on("click", function() {
		$("#myForm").attr("action", "/customerManager/loanPaymentExport");
		$("#myForm").submit();
		$("#myForm").attr("action", "");
	});
	
	
	// 展示
	var show = function() {
		$("#payment").css("display", "block");
		$("#paymentAll").css("display", "block");
	}
	// 隐藏
	var hidden = function() {
		$("#payment").css("display", "none");
		$("#paymentAll").css("display", "none");
	}

	// 取消按钮
	$("#paymentAll").on("click", "input#goBackId", function() {
		hidden();
	});

	/**
	 * 执行付款
	 */

	$(".topayment").on("click", function() {
		var investPeriod = $(this).data('investperiod');
		var period = $(this).data('period');
		var payId = $(this).data('payid');
		var mainId = $(this).data('mainid');
		var accountId = $(this).data('accountid');
		if (period != investPeriod) {
			alert("请先付第" + investPeriod + "期");
			return false;
		}
		$.post("/customerManager/payPlanDetail", {
			"id" : payId,
			"accountId":accountId,
			"transMainId" : mainId
		}, function(data) {
			$("#paymentAll").html(data).show();
			$(".right-content").animate({
				"scrollTop":0
			},0);
		});
	});

	
	/**
	 * 全部付款
	 */

	$("#paymentAll").on("click", "a.topayAll", function() {
		var period = $(this).data('period');
		var mainId = $(this).data('mainid');
		var customerId = $(this).data('customerid');
		var accountId = $(this).data('accountid');
		$.post("/customerManager/payPlanDetailAll", {
			"transMainId" : mainId,
			"customerId":customerId,
			"accountId":accountId,
			"period" : period
		}, function(data) {
			if(data.overdueDays>=1){
				alert("当前已产生逾期，不能提前付款!");
				return;
			}else{
			   $("#paymentAll").html(data).show();
			   $(".right-content").animate({
					"scrollTop":0
				},0);
			}
		});
	});
	
	
	/**
	 * 部分还本-弹出页面
	 * 
	 */
	$("#paymentAll").on("click", "a.toPayPortion", function() {
		var period = $(this).data('period');
		var mainId = $(this).data('mainid');
		var customerId = $(this).data('customerid');
		var accountId = $(this).data('accountid');
		$.post("/customerManager/payPlanDetailPortion", {
			"transMainId" : mainId,
			"customerId":customerId,
			"accountId":accountId,
			"period" : period
		}, function(data) {
			 $("#paymentAll").html(data).show();
			   $(".right-content").animate({
					"scrollTop":0
			 },0);
		});
	});
	//部分还本异步计算
	$("#paymentAll").on("change","form#payPortionForm input[name='advanceAmount']",function(){
		if($("input[name='actualTransTime']").val()==''){
			alert("请选择还款时间!");
			$("form#payPortionForm input[name='actualTransTime']").focus();
			return false;
		}
		var obj = $("#payPortionForm").serializeJson();
		if(!obj['advanceAmount'] || obj['advanceAmount']<=0){
			alert("还款本金必须大于0");
			$("form#payPortionForm input[name='advanceAmount']").focus();
			return false;
		}
		if(parseFloat(obj['advanceAmount']) > parseFloat($("#paymentAll input#investAmount").val())){
			alert("还款本金不能大于剩余本金");
			$("form#payPortionForm input[name='investAmount']").focus();
			return false;
		}
		$.post("/customerManager/getPayPortion",obj,function(data){
			$("#paymentAll").html(data);
			$(".right-content").animate({
				"scrollTop":0
			},0);
		});
	});

	//异步计算付款计划--根据还款时间
	$("#paymentAll").on("blur","form#payPortionForm input[name='actualTransTime']",function(){
		var $this=$(this);
		setTimeout(function(){
			if(!$this.val()){
				alert("还款时间不能为空",false)
				return false;
			}
			var obj = $("#payPortionForm").serializeJson();
			if(!obj['advanceAmount'] || obj['advanceAmount']<=0){
				alert("还款本金必须大于0");
				$("form#payPortionForm input[name='advanceAmount']").focus();
				return false;
			}
			if(parseFloat(obj['advanceAmount']) > parseFloat($("#paymentAll input#investAmount").val())){
				alert("还款本金不能大于剩余本金");
				$("form#payPortionForm input[name='investAmount']").focus();
				return false;
			}
			$.post("/customerManager/getPayPortion",obj,function(data){
				$("#paymentAll").html(data);
				$(".right-content").animate({
					"scrollTop":0
				},0);
			});
		},300)
	});
	
	/**
	 * 部分还本--确定提交
	 */
	$("#paymentAll").on("click","input#payPortionSubmit",function(){
		if(!$('#payPortionForm').validationEngine('validate')){
    		return false;
    	}
		var obj = $("#payPortionForm").serializeJson();
		if(!obj['advanceAmount'] || obj['advanceAmount']<=0){
			alert("还款本金必须大于0",false);
			$("form#payPortionForm input[name='advanceAmount']").focus();
			return false;
		}
		if(parseFloat(obj['advanceAmount']) > parseFloat($("#paymentAll input#investAmount").val())){
			alert("还款本金不能多于剩余本金",false);
			$("form#payPortionForm input[name='investAmount']").focus();
			return false;
		}
		$.post("/customerManager/executeToPayPortion",obj,function(data){
			if(data && data.retCode=='00'){
				alert("操作成功",function(){
					window.location.reload();
				});
			}else{
				alert(data.retMsg);
			}
		});
	});
	
	$("#paymentAll").on("click","input#paySubmit",function(){
		if(!$('#payForm').validationEngine('validate')){
    		return false;
    	}
		var obj = $("#payForm").serializeJson();
		if(obj.payFlag == '0'){
			if(!$("#mode0").is(':checked') && !$("#mode1").is(':checked')){
				alert("请选择付款类型",false);
				return false;
			}
			var obj = $("#payForm").serializeJson();
			if($("#mode0").is(':checked')){
				obj['isInterest']='1';
			}else{
				obj['isInterest']='0';
			}
			if($("#mode1").is(':checked')){
				obj['isCapita']='1';
			}else{
				obj['isCapita']='0';
			}
		}
		if(obj['paidTotal']<=0){
			alert("付款总额必须大于0",false);
			return false;
		}
		var amount = 0;
		var thisAmount=$("form#payForm input[name='paidTotal']").val();//利息
		var paidInterest=$("form#payForm input[name='paidInterest']").val();//利息
		var paidOverdue=$("form#payForm input[name='paidOverdue']").val();//逾期利息
		var paidCompensate=$("form#payForm input[name='paidCompensate']").val();//還款違約金
		
		if($("form#payForm input[name='payFlag']").val() == '0'){
			amount = parseFloat(paidOverdue)+parseFloat(paidCompensate)
			if(thisAmount<amount){
				$("form#payForm #warningId").html("* 收款总额不正确，无法付款");
				return false;
			}
		}else{
			amount = parseFloat(paidOverdue)+parseFloat(paidCompensate)+parseFloat(paidInterest);
			if(thisAmount<amount){
				$("form#payForm #warningId").html("* 收款总额不正确，无法付款");
				return false
			}
		}
		
	    var $transRemark=$("form#payForm textarea[name='transRemark']").val();
		var $paidOverdue=$(".paidOverdue").val();
        if($paidOverdue!=paidOverdue && $transRemark==""){
        	$("form#payForm #remarkId").html("* 请添加金额修改的原因");
			return false;
		}
		var $paidCompensate=$(".paidCompensateId").val();
        if($paidCompensate!=paidCompensate && $transRemark==""){
        	$("form#payForm #remarkId").html("* 请添加金额修改的原因");
			return false;
		}
		
		$.post("/customerManager/executeToPay",obj,function(data){
			console.log(data)
			console.log(data.retMsg)
			if(data && data.retCode=='00'){
				alert("付款成功",function(){
					window.location.reload();
				});
			}else{
				alert(data.retMsg);
			}
		});
	});

	/**
	 * 异步计算罚金
	 */
	$("#paymentAll").on("blur","form#payForm input[name='actualTransTime']",function() {
			var $this = $(this);
			setTimeout(function(){
				if (!$this.val()) {
					alert("付款时间不能为空", false)
					return false;
					}
					var payEntity = $("#payForm").serializeJson();
					if ($("#mode0").is(':checked')) {
					payEntity['isInterest'] = '1';
					} else {
					payEntity['isInterest'] = '0';
                            }
					if ($("#mode1").is(':checked')) {
					payEntity['isCapita'] = '1';
						} else {
					payEntity['isCapita'] = '0';
						}
			        $.post("/customerManager/amountCheck",payEntity,function(data) {
						if (data.retCode == '00') {
						var pay = data.pay;
							if (payEntity.payFlag == '0') {
							var repaymentCapital=parseFloat(pay.repaymentCapital),
								repaymentInterest=parseFloat(pay.repaymentInterest),
								overdueInterestTotal=parseFloat(pay.overdueInterestTotal);
							if(!$("#paymentAll #mode1").prop("checked")){
								repaymentCapital=0;
							}
							if(!$("#paymentAll #mode0").prop("checked")){
								repaymentInterest=0;
							}
							var paidTotal=(repaymentCapital+repaymentInterest+overdueInterestTotal).toFixed(4);
							$("form#payForm input[name='paidOverdue']").val(pay.overdueInterestTotal.toFixed(4));
						    $("form#payForm input[name='paidCompensate']").val(pay.paidCompensate.toFixed(4));
						    
						    $("form#payForm input[name='paidTotal']").val(paidTotal);
						    $("form#payForm span[name='overdueDays']").html(pay.overdueDays);
						    $("form#payForm input[class='paidOverdue']").val(pay.overdueInterestTotal.toFixed(4));
						    $("form#payForm input[class='paidCompensate']").val(pay.paidCompensate.toFixed(4));
						    } else {
						    	
							    if(pay.overdueDays == 1){
									$("form#payForm #warning").html("* 当前付款时间产生了逾期，无法提前付款");
								}else{
									$("form#payForm #warning").html("");
								}
							    
							$("form#payForm input[name='paidInterest']").val(pay.repaymentInterest.toFixed(4));
							$("form#payTempForm input[name='paidOverdue']").val(pay.overdueInterestTotal.toFixed(4));
							$("form#payForm input[name='paidCompensate']").val(pay.paidCompensate.toFixed(4));
							$("form#payForm input[name='paidTotal']").val(pay.repaymentTotal.toFixed(4));
							$("form#payForm #useDays").html(pay.useDays+"天");
							$("form#payForm #compensateDays").html(pay.compensateDays+"天");
							
							$("form#payForm input[class='paidOverdue']").val(pay.overdueInterestTotal.toFixed(4));
							$("form#payForm input[class='paidCompensate']").val(pay.paidCompensate.toFixed(4));
						}
						  } else {
							 alert("计算错误:"+data.retMsg);
						}
				  });
				}, 100)
			});
	

	

	
	$("#paymentAll").on("change","form#payForm input[name='paidInterest']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
		}
		var amount = parseFloat(total)+parseFloat(thisAmount)
		+parseFloat($("form#payForm input[name='paidOverdue']").val())
		+parseFloat($("form#payForm input[name='paidCompensate']").val());
		$("form#payForm input[name='paidTotal']").val(amount.toFixed(4));
		$("form#payForm #showPaidTotal").html(amount.toFixed(4));
	});
	
	//逾期罚息改变时计算
	$("#paymentAll").on("change","form#payForm input[name='paidOverdue']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
		}
		var amount = 0;
		
		var $transRemark=$("form#payForm textarea[name='transRemark']").val();//逾期利息
		var $paidOverdue=$(".paidOverdue").val();
		var flog=$paidOverdue != thisAmount;
		if($paidOverdue != thisAmount && $transRemark==""){
			$("form#payForm #remarkId").html("* 请添加金额修改的原因");
		}else{
			$("form#payForm #remarkId").html("");
		}
		
		if($("form#payForm input[name='payFlag']").val() == '0'){
			amount = parseFloat(total)+parseFloat(thisAmount)
			+parseFloat($("form#payForm input[name='paidCompensate']").val());	
		}else{
			amount = parseFloat(total)+parseFloat(thisAmount)
			+parseFloat($("form#payForm input[name='paidInterest']").val())
			+parseFloat($("form#payForm input[name='paidCompensate']").val());
			$("form#payForm #showPaidTotal").html(amount.toFixed(4));
		}
		$("form#payForm input[name='paidTotal']").val(amount.toFixed(4));
	});
	
	
	//违约金改变时计算
	$("#paymentAll").on("change","form#payForm input[name='paidCompensate']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
		}
		var amount = 0;
		
		var $transRemark=$("form#payForm textarea[name='transRemark']").val();//逾期利息
		var $paidCompensate=$(".paidCompensate").val();
		if($paidCompensate != thisAmount && $transRemark==""){
			$("form#payForm #remarkId").html("* 请添加金额修改的原因");
		}else{
			$("form#payForm #remarkId").html("");
		}
		
		if($("form#payForm input[name='payFlag']").val() == '0'){
			amount = parseFloat(total)+parseFloat(thisAmount)
			+parseFloat($("form#payForm input[name='paidOverdue']").val());	
		}else{
			amount = parseFloat(total)+parseFloat(thisAmount)
			+parseFloat($("form#payForm input[name='paidInterest']").val())
			+parseFloat($("form#payForm input[name='paidOverdue']").val());
			$("form#payForm #showPaidTotal").html(amount.toFixed(4));
		}
		
		$("form#payForm input[name='paidTotal']").val(amount.toFixed(4));
	});
	
	
	/***校验输入总额度***/
	$("#paymentAll").on("change","form#payForm input[name='paidTotal']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
		}
		var amount = 0;
		var paidInterest=$("form#payForm input[name='paidInterest']").val();//利息
		var paidOverdue=$("form#payForm input[name='paidOverdue']").val();//逾期利息
		var paidCompensate=$("form#payForm input[name='paidCompensate']").val();//還款違約金
		
		if($("form#payForm input[name='payFlag']").val() == '0'){
			amount = parseFloat(paidOverdue)+parseFloat(paidCompensate)
			if(thisAmount<amount){
				$("form#payForm #warningId").html("* 收款总额不正确，无法付款");
			}else{
				$("form#payForm #warningId").html("");
			}
			
		}else{
			amount = parseFloat(paidOverdue)+parseFloat(paidCompensate)+parseFloat(paidInterest);
			if(thisAmount<amount){
				$("form#payForm #warningId").html("* 收款总额不正确，无法付款");
			}else{
				$("form#payForm #warningId").html("");
			}
		}
	});
	
	$("#paymentAll").on("click","input#mode0",function(){//加减利息
		var changeInterest = 0;
		if($(this).is(':checked')){
			changeInterest = parseFloat($("form#payForm input[name='paidTotal']").val())+parseFloat($(this).data('interest'));
		}else{
			changeInterest = parseFloat($("form#payForm input[name='paidTotal']").val())-parseFloat($(this).data('interest'));
		}
		$("form#payForm input[name='paidTotal']").val(changeInterest.toFixed(4));
	});
	$("#paymentAll").on("click","input#mode1",function(){//加减本金
		var changeCapital = 0;
		if($(this).is(':checked')){
			changeCapital = parseFloat($("form#payForm input[name='paidTotal']").val())+parseFloat($(this).data('capital'));
		}else{
			changeCapital = parseFloat($("form#payForm input[name='paidTotal']").val())-parseFloat($(this).data('capital'));
		}
		$("form#payForm input[name='paidTotal']").val(changeCapital.toFixed(4));
	});

});
//部分还本异步计算---date
function changeFun(){
	var obj = $("#payPortionForm").serializeJson();
	if(!obj['advanceAmount'] || obj['advanceAmount']<=0){
		$("form#payPortionForm input[name='advanceAmount']").focus();
		return false;
	}
	$.post("/customerManager/getPayPortion",obj,function(data){
		$("#paymentAll").html(data);
		$(".right-content").animate({
			"scrollTop":0
		},0);
	});
	
}