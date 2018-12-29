$(function(){
	
	var postQuery = function(obj){
		$.post("/payPlan/ajaxQueryList", obj , function(data) {
			$("#payPlanId").html(data);
		});
	};
	
	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj);
	});
	
	// 分页查询
	$("#payPlanId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj);
		};
	});

	//导出
	$("#payPlanId").on("click","#export",function(){
		$("#myForm").attr("action","/payPlan/payPlanExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
	//取消按钮
	$("#previewId").on("click","input#goBackId",function(){
		$("#previewId").hide();
	});
	
	//checkbox点击时，还款总额变化
	$("#previewId").on("click","input#mode0",function(){//加减利息
		var changeInterest = parseFloat($("form#payTempForm input[name='paidOverdue']").val())
		+parseFloat($("form#payTempForm input[name='paidCompensate']").val());
		if($("#previewId input#mode1").is(':checked')){
			changeInterest = parseFloat(capital)+changeInterest;
		}
		if($(this).is(':checked')){
			changeInterest = changeInterest+parseFloat($(this).data('interest'));
		}
		$("form#payTempForm input[name='paidTotal']").val(changeInterest.toFixed(4));
	});
	$("#previewId").on("click","input#mode1",function(){//加减本金
		var changeCapital = parseFloat($("form#payTempForm input[name='paidOverdue']").val())
		+parseFloat($("form#payTempForm input[name='paidCompensate']").val());
		if($("#previewId input#mode0").is(':checked')){
			changeCapital = parseFloat(interest)+changeCapital;
		}
		if($(this).is(':checked')){
			changeCapital = changeCapital + parseFloat($(this).data('capital'));
		}
		$("form#payTempForm input[name='paidTotal']").val(changeCapital.toFixed(4));
	});
	//change事件计算还款总额  说明：total来自于弹出框页面js
	//利息改变时计算
	$("#previewId").on("change","form#payTempForm input[name='paidInterest']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
			$(this).val(0);
		}
		var amount = parseFloat(total)+parseFloat(thisAmount)
		+parseFloat($("form#payTempForm input[name='paidOverdue']").val())
		+parseFloat($("form#payTempForm input[name='paidCompensate']").val());
		$("form#payTempForm input[name='paidTotal']").val(amount.toFixed(4));
		$("form#payTempForm #showPaidTotal").html(amount.toFixed(4));
	});
	//逾期罚息改变时计算
	$("#previewId").on("change","form#payTempForm input[name='paidOverdue']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
			$(this).val(0);
		}
		var amount = 0;
		if($("form#payTempForm input[name='payFlag']").val() == '0'){
			amount = parseFloat(thisAmount)+parseFloat($("form#payTempForm input[name='paidCompensate']").val());
			if($("#previewId input#mode0").is(':checked')){
				amount = parseFloat(interest)+amount;
			}
			if($("#previewId input#mode1").is(':checked')){
				amount = parseFloat(capital)+amount;
			}
		}else{
			amount = parseFloat(total)+parseFloat(thisAmount)
			+parseFloat($("form#payTempForm input[name='paidInterest']").val())
			+parseFloat($("form#payTempForm input[name='paidCompensate']").val());
			
			$("form#payTempForm #showPaidTotal").html(amount.toFixed(4));
		}
		 
		$("form#payTempForm input[name='paidTotal']").val(amount.toFixed(4));
	});
	//违约金改变时计算
	$("#previewId").on("change","form#payTempForm input[name='paidCompensate']",function(){
		var thisAmount = $(this).val();
		if(!thisAmount){
			thisAmount = 0;
			$(this).val(0);
		}
		var amount = 0;
		if($("form#payTempForm input[name='payFlag']").val() == '0'){
			amount = parseFloat(thisAmount)+parseFloat($("form#payTempForm input[name='paidOverdue']").val());	
			if($("#previewId input#mode0").is(':checked')){
				amount = parseFloat(interest)+amount;
			}
			if($("#previewId input#mode1").is(':checked')){
				amount = parseFloat(capital)+amount;
			}
		}else{
			amount = parseFloat(total)+parseFloat(thisAmount)
			+parseFloat($("form#payTempForm input[name='paidInterest']").val())
			+parseFloat($("form#payTempForm input[name='paidOverdue']").val());
			
			$("form#payTempForm #showPaidTotal").html(amount.toFixed(4));
		}
		
		$("form#payTempForm input[name='paidTotal']").val(amount.toFixed(4));
	});
	
	//异步计算罚息及违约金
	$("#previewId").on("blur","form#payTempForm input[name='actualTransTime']",function(){
		var $this=$(this);
		setTimeout(function(){
			if(!$this.val()){
				alert("还款时间不能为空",false)
				return false;
			}
			var obj = $("#payTempForm").serializeJson();
			if(obj.payFlag == '0'){
				if(!$("#mode0").is(':checked') && !$("#mode1").is(':checked')){
					alert("请选择还款类型",false);
					$("form#payTempForm input[name='paidTotal']").focus();
					return false;
				}
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
			$.post("/payPlan/ajaxCalculation",obj,function(data){
				if(data && data.retCode=='00'){
					var prePay = data.prePay;
					if(obj.payFlag == '0'){
						total = parseFloat(prePay.repaymentCapital)+parseFloat(prePay.repaymentInterest);
						$("form#payTempForm input[name='paidOverdue']").val(prePay.overdueInterestTotal.toFixed(4));
						$("form#payTempForm #paidOverdueTempId").val(prePay.overdueInterestTotal.toFixed(4));
						$("form#payTempForm input[name='paidCompensate']").val(prePay.compensate.toFixed(4));
						$("form#payTempForm #paidCompensateTempId").val(prePay.compensate.toFixed(4));
						$("form#payTempForm input[name='paidTotal']").val(prePay.repaymentTotal.toFixed(4));
						$("form#payTempForm span[name='overdueDays']").html(prePay.overdueDays);
						$("form#payTempForm input[name='paidTotal']").focus();
					}else{
						total = parseFloat(prePay.repaymentCapital);
						if(prePay.overdueDays == 1){
							$("form#payTempForm #warningId").html("* 当前还款时间产生了逾期，无法提前还款");
						}else{
							$("form#payTempForm #warningId").html("");
						}
						$("form#payTempForm #useDaysId").html(prePay.useDays);
						$("form#payTempForm #compensateDaysId").html(prePay.compensateDays);
						$("form#payTempForm input[name='paidInterest']").val(prePay.repaymentInterest.toFixed(4));
						$("form#payTempForm input[name='paidOverdue']").val(prePay.overdueInterestTotal.toFixed(4));
						$("form#payTempForm #paidOverdueTempId").val(prePay.overdueInterestTotal.toFixed(4));
						$("form#payTempForm input[name='paidCompensate']").val(prePay.compensate.toFixed(4));
						$("form#payTempForm #paidCompensateTempId").val(prePay.compensate.toFixed(4));
						$("form#payTempForm input[name='paidTotal']").val(prePay.repaymentTotal.toFixed(4));
						$("form#payTempForm #showPaidTotal").html(prePay.repaymentTotal.toFixed(4));
					}
				}else{
					if(obj.payFlag == '0'){
						alert("罚息计算失败",false);
						$("form#payTempForm input[name='paidTotal']").focus();
					}else{
						alert("违约金计算失败",false);
					}
				}
			});
		},300)
	});
	
	//执行还款弹出框
	$("#payPlanId").on("click","table tbody tr td a.pay",function(){
		var paidPeriod = $(this).data('paidperiod');
		var period = $(this).data('period');
		var payId = $(this).data('payid');
		var mainId = $(this).data('mainid');
		if(period != paidPeriod){
			alert("请先还第"+paidPeriod+"期");
			return false;
		}
		$.post("/payPlan/payDetail",{
//			"id":payId,
			"transMainId":mainId
			},function(data){
			$("#previewId").html(data).show();
			$(".right-content").animate({
				"scrollTop":0
			},0);
		});
	});
	//还款提交
	$("#previewId").on("click","input#paySubmit",function(){
		if(!$('#payTempForm').validationEngine('validate')){
    		return false;
    	}
		var obj = $("#payTempForm").serializeJson();
		if(obj.payFlag == '0'){
			if(!$("#mode0").is(':checked') && !$("#mode1").is(':checked')){
				alert("请选择还款类型",false);
				return false;
			}
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
			alert("还款总额必须大于0",false);
			return false;
		}
		var flag1 = parseFloat($("#paidOverdueTempId").val()) != parseFloat(obj['paidOverdue']);
		var flag2 = parseFloat($("#paidCompensateTempId").val()) != parseFloat(obj['paidCompensate']);
		if(obj.payFlag == '0'){
			var amount = parseFloat(obj['paidOverdue'])+parseFloat(obj['paidCompensate']);
			if(parseFloat(obj['paidTotal'])<=amount){
				alert("还款总额必须大于罚息与违约金之和");
				return false;
			}
			if((flag1 || flag2) && !$("#transRemarkId").val()){
				alert("罚息、违约金被修改，需要填写备注");
				return false;
			}
		}else{
			var defaultAmount = $("form#payTempForm input[name='paidInterest']").data('paidinterest');
			var editAmount = $("form#payTempForm input[name='paidInterest']").val();
			if(parseFloat(editAmount) < parseFloat(defaultAmount)){
				alert("利息必须大于等于应还利息");
				return false;
			}
			var amount = parseFloat(obj['paidOverdue'])+parseFloat(obj['paidCompensate'])+parseFloat(obj['paidInterest']);
			if(parseFloat(obj['paidTotal'])<=amount){
				alert("还款总额必须大于本金、利息、罚息与违约金之和");
				return false;
			}
			var flag3 = parseFloat(editAmount) != parseFloat(defaultAmount);
			if((flag1 || flag2 || flag3) && !$("#transRemarkId").val()){
				alert("利息、罚息、违约金被修改，需要填写备注");
				return false;
			}
		}
		
		$.post("/payPlan/payHandler",obj,function(data){
			if(data && data.retCode=='00'){
				alert("还款成功",false,function(){
					window.location.reload();
				});
			}else{
				alert(data.retMsg);
			}
		});
	});
	//全部还款弹出框
	$("#previewId").on("click","a.payAll",function(){
		var period = $(this).data('period');
		var mainId = $(this).data('mainid');
		$.post("/payPlan/payAllDetail",{"transMainId":mainId},function(data){
			$("#previewId").html(data).show();
			$(".right-content").animate({
				"scrollTop":0
			},0);
		})
	});
	
	//部分还本弹出框
	$("#previewId").on("click","a.payCapital",function(){
		var period = $(this).data('period');
		var mainId = $(this).data('mainid');
		$.post("/payPlan/payCapitalDetail",{"transMainId":mainId},function(data){
			$("#previewId").html(data).show();
			$(".right-content").animate({
				"scrollTop":0
			},0);
		})
	});
	
	//部分还本异步计算
	/*$("#previewId").on("change","form#payCapitalTempForm input[name='actualTransTime']",function(){
		setTimeout(function(){
			var obj = $("#payCapitalTempForm").serializeJson();
			$.post("/payPlan/payCapitalDetail",obj,function(data){
				$("#previewId").html(data);
				$(".right-content").animate({
					"scrollTop":0
				},0);
			});
		},300);
	});*/
	//部分还本异步计算
	$("#previewId").on("change","form#payCapitalTempForm input[name='paidCapital']",function(){
		var obj = $("#payCapitalTempForm").serializeJson();
		$.post("/payPlan/payCapitalDetail",obj,function(data){
			$("#previewId").html(data);
			$(".right-content").animate({
				"scrollTop":0
			},0);
		});
	});
	
	//部分还本提交
	$("#previewId").on("click","input#payCapitalBtn",function(){
		if(!$('#payCapitalTempForm').validationEngine('validate')){
    		return false;
    	}
		var obj = $("#payCapitalTempForm").serializeJson();
		if(!obj['paidCapital'] || obj['paidCapital']<=0){
			alert("还款本金必须大于0",false);
			return false;
		}
		if(parseFloat(obj['paidCapital']) > parseFloat($("#previewId input#lastCapitalId").val())){
			alert("还款本金不能多于剩余本金",false);
			return false;
		}
		$.post("/payPlan/payHandler",obj,function(data){
			if(data && data.retCode=='00'){
				alert("还款成功",false,function(){
					window.location.reload();
				});
			}else{
				alert(data.retMsg);
			}
		});
	});
});
//部分还本异步计算---date
function changeFun(){
	var obj = $("#payCapitalTempForm").serializeJson();
	$.post("/payPlan/payCapitalDetail",obj,function(data){
		$("#previewId").html(data);
		$(".right-content").animate({
			"scrollTop":0
		},0);
	});
}
