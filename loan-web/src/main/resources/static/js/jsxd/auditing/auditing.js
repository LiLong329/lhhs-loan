$(function(){
	$("#orderCredentials").on("click",function(){
		$.post("/mortgageInfo/toOrderCredentials",{
			"orderNo":orderNo,
			"productId":productId
		},function(data){
			$("#tab4").html(data);
		});
	});
	init();
	function init(){
		var tab=session.get("tabNum")||"";
		if(tab){
			session.get("tab4-orgId")&&$("#fundSide").val(session.get("tab4-orgId")).change();
			session.get("tab4-productId")&&$("#productName").val(session.get("tab4-productId")).change();
			$("#"+tab).click();
			session.del("tabNum");
			session.del("tab4-orgId");
			session.del("tab4-productId");
		}
	};
	  /**
	   * 银行卡号-开户行-开户支行联动查询
	   */
	  $("#bankCardNo").on("change",function(){
		  var bankCardNo = $(this).val();
		  if(bankCardNo){
			  $.post("/auditProcess/getBankByCard",{
				  "bankCardNo":bankCardNo
			  },function(data){
				  $("input[name='bankId']").val(data.bankId);
				  $("input[name='bankName']").val(data.bankName);
				  $("input[name='branchName']").val(data.branchName);
			  });
		  }else{
			  $("input[name='bankId']").val("");
			  $("input[name='bankName']").val("");
			  $("input[name='branchName']").val("");
		  }
	  });
});