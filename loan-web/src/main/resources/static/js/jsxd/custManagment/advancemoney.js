$(function() {
    
	/**
	 * 垫付分页
	 */
	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj);
	});
	
	var postQuery = function(obj){
		$.post("/customerManager/ajaxQueryList", obj , function(data) {
			$("#payPlanId").html(data);
		});
	};
	

	// 分页查询
	$("#payPlanId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj);
		};
	});
	
	//取消按钮
	$("#previewId").on("click","input#goBackId",function(){
		$("#previewId").hide();
	});
	
	
	/**
	 * 　弹出垫付页面
	 */
	$("#payPlanId").on("click","table tbody tr td a.pay",function(){
		var paidPeriod = $(this).data('paidperiod');
		var period = $(this).data('period');
		var payId = $(this).data('payid');
		var mainId = $(this).data('mainid');
		if(period != paidPeriod){
			alert("请先垫付"+paidPeriod+"期");
			return false;
		}
		$.post("/customerManager/capitalPrepaidView",{
			"id":payId,
			"transMainId":mainId
			},function(data){
			$("#previewId").html(data).show();
			$(".right-content").animate({
				"scrollTop":0
			},0);
		});
	});
	
	/**
	 * 执行垫付
	 */
	$("#previewId").on("click","input#paySubmit",function(){
		
		if(!$("#mode0").is(':checked') && !$("#mode1").is(':checked')){
			alert("请选择垫付类型",false);
			return false;
	     }
		var obj = $("#payForm").serializeJson();
		$.post("/customerManager/executeCapotalPrepaid",obj,function(data){
			if(data && data.retCode=='00'){
				alert("垫付成功",false,function(){
					window.location.reload();
				});
			}else{
				alert(data.retMsg);
			}
		});
	});

	
	/****复选款改变时候值域的变化*****/
	$("#previewId").on("click","input#mode0",function(){//加减利息
		var changeInterest = 0;
		if($(this).is(':checked')){
			changeInterest = parseFloat($("form#payForm input[name='advanceTotal']").val())+parseFloat($(this).data('interest'));
		
		}else{
			changeInterest = parseFloat($("form#payForm input[name='advanceTotal']").val())-parseFloat($(this).data('interest'));
		
		}
		$("form#payForm input[name='advanceTotal']").val(changeInterest.toFixed(4));
	});
	$("#previewId").on("click","input#mode1",function(){//加减本金
		var changeCapital = 0;
		if($(this).is(':checked')){
			changeCapital = parseFloat($("form#payForm input[name='advanceTotal']").val())+parseFloat($(this).data('capital'));
		}else{
			changeCapital = parseFloat($("form#payForm input[name='advanceTotal']").val())-parseFloat($(this).data('capital'));
		}
		$("form#payForm input[name='advanceTotal']").val(changeCapital.toFixed(4));
	});
	
	
	
	
	
	
});