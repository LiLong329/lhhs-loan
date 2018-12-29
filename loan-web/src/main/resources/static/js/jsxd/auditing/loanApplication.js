$(function(){
	//保存
	$("#loanApplicationSave").on("click",function(){
		var approvalNode = $("#approvalNode").val(), //审核节点
			loanMethod=$("input[name='loanMethod']:checked").val(),//放款方式
			amountPaidThisSum=0,//本次放款金额总和
			flag=true;
	    //判断本次放款金额与已放款金额的和不可超过放款金额
	    $("input[name='amountPaidThis']").each(function(i,n){
	    	var amountPaidThis=parseFloat($(n).val()),//本次放款金额
	 		 	amountPaidAlready=parseFloat($(n).closest("td").find(".amountPaidAlready").val()),//已放款金额
	 		 	amountPaid=parseFloat($(n).closest("td").find(".amountPaid").val()),//放款金额
	 		 	accountName=$(n).closest("td").find(".accountName").val();
	    	amountPaidThis=amountPaidThis?amountPaidThis:0;
	    	amountPaidAlready=amountPaidAlready?amountPaidAlready:0;
	    	amountPaid=amountPaid?amountPaid:0;
	    	if(loanMethod==1){//部分放款
	    		if(amountPaidThis+amountPaidAlready>amountPaid){
	    			flag=false;
	    			alert(accountName+"已放款"+amountPaidAlready+"元，本次放款金额最大为"+(amountPaid-amountPaidAlready)+"元");
	    			$(n).focus();
	    			return false;
	    		}
	    		amountPaidThisSum+=amountPaidThis;
		    }else{//全部放款
		    	$(n).val(amountPaid-amountPaidAlready);
		    }
	    });
	    if(!flag){
	    	return false;
	    }
	    if(loanMethod==1&&amountPaidThisSum==0){
	    	alert("总放款金额不可为0");
	    	return false;
	    }
	    var capitalSideList = new Array(),
	     	capitalSideForm = $("#capitalSideInfo tbody tr");
		for(var i=0;i<capitalSideForm.length;i++){
			capitalSideList.push($(capitalSideForm[i]).getOtherJson());
		}
		$.post("/auditProcess/updateAmountPaidThis",{
			"loanCapitalInfo":JSON.stringify(capitalSideList)
		},function(data){ 
			if(data.retCode=="00"){
				alert(data.retMsg);
		    }else{
		    	alert(data.retMsg);
		    }
	   });
	});
});