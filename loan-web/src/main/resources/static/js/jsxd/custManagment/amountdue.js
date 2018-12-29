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
		$.post("/payPlan/ajaxAmountDueList",obj, function(data) {
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
	 * 　弹出还债页面
	 */
	$("#payPlanId").on("click","table tbody tr td a.pay",function(){
		var paidPeriod = $(this).data('paidperiod');
		var period = $(this).data('period');
		var payId = $(this).data('payid');
		var mainId = $(this).data('mainid');
		/*if(period != paidPeriod){
			alert("请先垫付"+paidPeriod+"期");
			return false;
		}*/
		$.post("/payPlan/amountDueView",{
			"advanceId":payId,
			"transMainId":mainId
			},function(data){
			$("#previewId").html(data).show();
			$(".right-content").animate({
				"scrollTop":0
			},0);
		});
	});
	
	/**
	 * 异步计算还债金额
	 */
	$("#previewId").on("blur","form#payForm input[name='paidAdvanceTime']",function(){
		var $this=$(this);
		
		if(!$this){
			alert("还债时间不能为空",false)
			return false;
		}
		
		var advance = $("#payForm").serializeJson();
		$.post("/payPlan/ajaxAmountCompute",advance,function(data){
			if (data.retCode == '00') {
				var pay = data.pay;
				var interestTotal=0;
				var advanceTotal=0;
				var paidTotal=0;
				//债务利息
				$("form#payForm input[name='interest']").val(pay.remainRepaymentInterest);
				
				if($("#mode0").is(':checked')){
					interestTotal=pay.remainRepaymentInterest;
				}
				if($("#mode1").is(':checked')){
					advanceTotal=pay.advanceTotal;
				}
				paidTotal=interestTotal+advanceTotal;
				$("form#payForm input[name='paidInterest']").val(interestTotal.toFixed(4));
				//还债金额
				$("form#payForm input[name='paidTotal']").val(paidTotal.toFixed(4));
				//债务天数
				$("form#payForm #useDays").html(pay.overdueDays+"天");
				//待还垫付金额
				//$("form#payForm #paidTotal").html(pay.paidTotal.toFixed(4));
				
				} else {
				  alert("计算错误:"+data.retMsg);
				}
		    }); 
		
	});
	
	
	/**
	 * 债务利息手动改变时候的校验
	 */
	
	$("#previewId").on("change","form#payForm input[name='paidInterest']",function(){
		var amount = 0;
		var advanceTotal=0;
		var $transRemark=$("#remarkId").val();
		
		var $interest=$("#interest").val();
		var interest=$("#paidInterest").val();
		if(!interest){
			interest=parseFloat(0.0000);
		}
		if($("#mode1").is(':checked')){
			advanceTotal=$("form#payForm #advanceTotal").val();
		}
		amount=parseFloat(advanceTotal)+parseFloat(interest);
		if($interest!=interest && $transRemark==""){
			$("form#payForm input[name='paidTotal']").val(amount.toFixed(4));
			$("form#payForm #remarkId").html("* 请添债务利息修改备注！");
		}else{
			$("#remarkId").html("");
		}
	});
	
	
	
	/**
	 * 执行还债
	 */
	$("#previewId").on("click","input#paySubmit",function(){
		var advance = $("#payForm").serializeJson();
		
		var $transRemark=$("#remark").val();
		var $interest=$("#interest").val();
		var interest=$(".interest").val();
		if(!interest){
			interest=parseFloat(0.0000);
		}
        if($interest!=interest && $transRemark==""){
        	$("form#payForm #remarkId").html("* 请添债务利息修改备注！");
			alert("请填写债务利息修改后原因",false)
			return false;
		}
		$.post("/payPlan/executeAmount",advance,function(data){
			if(data && data.retCode=='00'){
				alert("还债成功",false,function(){
					window.location.reload();
				});
			}else{
				alert(data.retMsg);
			}
		});
	});

	
	/**********还债复选框点击计算*************/
	
	/****复选款改变时候值域的变化*****/
	$("#previewId").on("click","input#mode0",function(){//加减利息
		var interestTotal=0;
		var advanceTotal=0;
		var paidTotal=0;
		if($("#mode0").is(':checked')){
			interestTotal=parseFloat($("form#payForm input[name='interest']").val());
		}
		if($("#mode1").is(':checked')){
			advanceTotal=parseFloat($("form#payForm input[name='advanceTotal']").val());
		}
		paidTotal=interestTotal+advanceTotal;
		$("form#payForm input[name='paidInterest']").val(interestTotal.toFixed(4));
		//还债金额
		$("form#payForm input[name='paidTotal']").val(paidTotal.toFixed(4));
	});
	
	$("#previewId").on("click","input#mode1",function(){//加减本金
		var interestTotal=0;
		var advanceTotal=0;
		var paidTotal=0;
		if($("#mode0").is(':checked')){
			interestTotal=parseFloat($("form#payForm input[name='interest']").val());
		}
		if($("#mode1").is(':checked')){
			advanceTotal=parseFloat($("form#payForm input[name='advanceTotal']").val());
		}
		paidTotal=interestTotal+advanceTotal;
		$("form#payForm input[name='paidInterest']").val(interestTotal.toFixed(4));
		//还债金额
		$("form#payForm input[name='paidTotal']").val(paidTotal.toFixed(4));
	});
	
	
	
	
});