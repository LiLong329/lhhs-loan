$(function(){
	
	//绑定异步验证
	$('form').validationEngine("attach",{
			autoPositionUpdate:true,
			autoHidePrompt:true,
			maxErrorsPerField:1,
			showOneMessage:true,
			autoHideDelay:2000,
			validateNonVisibleFields:true,
        	onFailure:function(){
        		var name=$($(this).attr("InvalidFields")[0]).closest(".tabson").attr("id");
        		$(".itab li a").each(function(i,n){
        			if($.trim($(n).data("show"))==("#"+name)){
        				$(n).click();
        				return false;
        			}
        		});
        	}
		});
	
	//添加资金方基本信息
	$("#addCapitalSideInfo").on("click",function(){
		var capitalSideInfo = $("#capitalSideInfo tbody tr:first").prop("outerHTML"),
			$newInfo = $("#capitalSideInfo tbody").append(capitalSideInfo).find("tr:last");
		$newInfo.find("input").val("");
		$newInfo.find("select").each(function(i,n){
			$(n).find("option:first").prop("selected",true);
			$(n).prev(".uew-select-value").find(".uew-select-text").html($(n).find("option:first").text());
		});
		$newInfo.find("td:last").html('<a class="tablelink delTr">删除</a>');
		addOdd();
	});
	//添加收入列表信息
	$("#addIncomeInfo").on("click",function(){
		var incomeInfo = $("#incomeInfo tbody tr:first").prop("outerHTML"),//收入列表tr
			$newInfo = $("#incomeInfo tbody").append(incomeInfo).find("tr:last");
		$newInfo.find("input").val("");
		$newInfo.find("select").each(function(i,n){
			$(n).find("option:first").prop("selected",true);
			$(n).prev(".uew-select-value").find(".uew-select-text").html($(n).find("option:first").text());
		});
		$newInfo.find("td:last").html('<a class="tablelink delTr">删除</a>');
		addOdd();
	});
	//添加支出列表信息
	$("#addExpenditureInfo").on("click",function(){
		var expenditureInfo = $("#expenditureInfo tbody tr:first").prop("outerHTML"),//支出信息tr
			$newInfo = $("#expenditureInfo tbody").append(expenditureInfo).find("tr:last");
		$newInfo.find("input").val("");
		$newInfo.find("select").each(function(i,n){
			$(n).find("option:first").prop("selected",true);
			$(n).prev(".uew-select-value").find(".uew-select-text").html($(n).find("option:first").text());
		});
		$newInfo.find("td:last").html('<a class="tablelink delTr">删除</a>');
		addOdd();
	});
	//删除信息
	$("body").on("click",".delTr",function(){
		$(this).closest("tr").remove();
		addOdd();
	});
	
	function asyncTeamManager(id){
		$.post("/orderInfo/asyncTeamManager/"+id,function(data){
			data = eval(data);
			$("#ldName").val(data[0]);
			$("#tuandui").val(data[1]);
			$("#zongjingli").val(data[2]);
		})
	};
	asyncTeamManager($("#leEmpId").val());
	
	$("#leEmpId").change(function(){
		asyncTeamManager(this.value);
	})
	
	//添加偶数行样式
	function addOdd(){
		$("#tab5 .tablelist tbody tr").removeClass("odd");
		$("#tab5 .tablelist").each(function(i,n){
            $(this).find("tbody tr:odd").addClass("odd");
        });
	};
	
	function customerType(){
		$("#capitalSideInfo  tr").each(function(i,e){
			var tr = $(e);
			var select = tr.find("select[name='customerNature']");
			var bigcategory = select.find("option:selected").data('bigcategory');
			
			if(bigcategory == 30 || bigcategory == 40){
				tr.find('input[name="mobile"]').removeClass('validate[required,custom[mobile]]');
				tr.find('input[name="mobile"]').removeClass('validate[required]');
				tr.find('input[name="mobile"]').addClass('validate[required]');
			}else{
				tr.find('input[name="mobile"]').removeClass('validate[required]');
				tr.find('input[name="mobile"]').removeClass('validate[required,custom[mobile]]');
				tr.find('input[name="mobile"]').addClass('validate[required,custom[mobile]]');
			}
			
			tr.find("input[name='customerType']").val(bigcategory);
		})
	}
	customerType();
	
	$("#capitalSideInfo").on("change","select[name='customerNature']",function(){
		customerType();
	})
	
	
	function expenditureVariety(){
		$("#expenditureInfo  tr").each(function(i,e){
			var tr = $(e);
			var select = tr.find("select[name='expenditureVariety']");
			var text = select.find("option:selected").text();
			if(select.val() == "" ){
				tr.find('input').hide();
			}else{
				tr.find('input').show();
			}
			
			tr.find("input[name='subjectName']").val(text);
		})
	}
	$("#expenditureInfo").on("change","select[name='expenditureVariety']",function(){
		expenditureVariety();
	})
	expenditureVariety();
	
	
	function earningVariety(){
		$("#incomeInfo  tr").each(function(i,e){
			var tr = $(e);
			var select = tr.find("select[name='earningVariety']");
			var text = select.find("option:selected").text();
			if(select.val() == "" ){
				tr.find('input').hide();
			}else{
				tr.find('input').show();
			}
			tr.find("input[name='subjectName']").val(text);
		})
	}
	$("#incomeInfo").on("change","select[name='earningVariety']",function(){
		earningVariety();
	})
	earningVariety();
	
	$("#capitalSideInfo").on("change","input[name='mobile'],select[name='customerNature']",function(){
		var $tr = $(this).parents('tr');
		var $select = $tr.find("select[name='customerNature']");
		var $mobile = $tr.find("input[name='mobile']");
		if($mobile.validationEngine("validate")){
			//alert('不合法的手机号');
			return;
		}
		
		var bigcategory = $select.find("option:selected").data('bigcategory');
		var mobile = $mobile.val();
		$.post("/orderInfo/queryAccountCardList","accountType="+bigcategory +"&mobile=" + mobile,function(jsonstr){
//			console.log(jsonstr);
			var data = {};
			if(jsonstr ==''){
				alert('未找到放款人');
			}else{
				data =JSON.parse(jsonstr);
				
				$tr.find("input[name='accountName']").val(data.accountName);
				$tr.find("input[name='bankcardId']").val(data.bankCardNo);
				$tr.find("input[name='bankName']").val(data.bankName);
				$tr.find("input[name='branchBank']").val(data.branchName);
				$tr.find("#balance").text(parseFloat(data.amount)?parseFloat(data.amount).toFixed(2):0.00);
				
				$tr.find("input[name='cardId']").val(data.id);
				$tr.find("input[name='orgId']").val(data.orgId);
				$tr.find("input[name='accountId']").val(data.accountId);
				
				$tr.find("input[name='mobile']").val(data.mobile);
				
				$tr.find("input[name='ownerId']").val(data.ownerId);
				$tr.find("input[name='ownerName']").val(data.ownerName);
			}
			
		});
		
	})
	
    $("input.saveBasicInfo").on("click",function(){
    		
        var	 flag=$('form').validationEngine("validate",{
        	validateNonVisibleFields:true
        });
        if(!flag){
        		return false;
        }
        var msg ='';
	    
	    	//借款金额
	    	var loanAmount = parseInt($("#loanAmount").val());
	    	var amountCount = 0; 
	    	
	    	$("input[name='amountPaid']").each(function(i,e){
	    		 amountCount = amountCount + parseInt($(e).val());
//	    		 console.log(amountCount);
	    		 if(loanAmount * 10000 < amountCount ){
	    			 msg = '放款金额总和不可大于借款金额';
	    			 flag =  false;
	    		 }
	    	})
	    	if(!flag){
		   		alert(msg);
		   		return false; 
		}
	    	  //借款利率
	    	var loanRate = parseInt($("#loanRate").val());
	    	//借款利率单位
	    	var loanRateUnit = $("#loanRateUnit").val();
	    	
	    	//比较利率
	    	var _calculate = function(rateFrom,unit){
	    		var rate = 0 + rateFrom + 0;
	    		var cal  = 0;
	    		if(unit == '年'){
	    			cal = rate / 365;
	    		}else if(unit == '月'){
	    			cal = rate * 12 / 365;
	    		}else if(unit == '天'){
	    			cal = rate * 1;
	    		}else if(unit == '次'){
	    			cal = rate + 1000000000;
	    		}
	    		return cal;
	    	}
	    	
	    var baseRate = _calculate(loanRate,loanRateUnit);
	  
	    	
		$("input[name='amountRate']").each(function(i,e){
	   		 var amountRate = parseInt($(e).val());
	   		 var amountRateUnit =  $(e).closest('td').find('select[name="amountRateUnit"]').val();
	   		 if(loanRateUnit == '次' && amountRateUnit !='次'  ){
	   			 msg ='借款利率和放款利率只要有一个为次，就都为次';
	   			 flag =false;
	   		 }else{
	   			 var  memRate = _calculate(amountRate,amountRateUnit);
		   		 if(baseRate  < memRate ){
		   			 msg = '放款利率不可大于借款利率';
		   			 flag =  false;
		   		 }
	   		 }
	   	})
	   	
	   	if(!flag){
	   		alert(msg);
	   		return flag; 
	   	 }
		
		var orderInfo = $("#orderInfo").serializeJson(), borrower = $("#borrower").serializeJson(), orderHouse = $("#orderHouse").serializeJson(),parasConfig= $("#parasConfig").serializeJson();
		var houseForm =  $("#orderHouse form");
		var houseList = new Array() ,loanCapitalExpenditure = new Array(), loanCapitalEarning = new Array(), loanCapitalInfo = new Array();
		for(var i=0;i<houseForm.length;i++){
			houseList.push($(houseForm[i]).serializeJson());
		}
		
		var capitalSideForm = $("#capitalSideInfo tbody tr"),incomeForm = $("#incomeInfo tbody tr"),expenditureForm = $("#expenditureInfo tbody tr");
		
		for(var i=0;i<capitalSideForm.length;i++){
			loanCapitalInfo.push($(capitalSideForm[i]).getOtherJson());
		}
		for(var i=0;i<incomeForm.length;i++){
			loanCapitalEarning.push($(incomeForm[i]).getOtherJson());
		}
		for(var i=0;i<expenditureForm.length;i++){
			loanCapitalExpenditure.push($(expenditureForm[i]).getOtherJson());
		}
		
		confirm("确认要提交放款申请吗？", function(){
			$.post("/orderInfo/updateOrderInfo",{
				"letEmployeeno": $("#letEmployeeno").val(),
				"orderInfo":JSON.stringify(orderInfo),
				"borrower":JSON.stringify(borrower),
				"orderHouse":JSON.stringify(orderHouse),
				"houseList":JSON.stringify(houseList),
				"loanCapitalExpenditure":JSON.stringify(loanCapitalExpenditure),
				"loanCapitalEarning":JSON.stringify(loanCapitalEarning),
				"loanCapitalInfo":JSON.stringify(loanCapitalInfo),
				"parasConfig":JSON.stringify(parasConfig),
				"orderNo":$("#orderNo").val(),
				"loanApplyDate":(new Date()).Format("yyyy-MM-dd HH:mm:ss"),
				"applyNode":4,
				"currentNode":4
			},function(data){ 
				if(data.retCode=="00"){
					alert(data.retMsg, false, function(){
						window.location.href="/approval/selectLoanApplyList/4";
					});
				}else{
					alert(data.retMsg);
				}
			}); 
		});
		
	})
	
	$("#lpcPayType,#lpcInterestType").on("change",function(){
		var value = $(this).val();
		var input = $(this).closest("td").next('td').find('input');
		if(value == 2){
			input.show();
			input.addClass('validate[required]');
		}else{
			input.hide();
			input.removeClass('validate[required]');
		}
		
	});
	//修改借款期限单位
	$("select[name='loanTermUnit']").on("change",function(){
		var value=$.trim($(this).val());
		if(value=="个月"){
			$("input[name='loanTerm']").removeClass("validate[required,custom[integer],max[1825]]").addClass("validate[required,custom[integer],max[60]]");
		}
		if(value=="天"){
			$("input[name='loanTerm']").removeClass("validate[required,custom[integer],max[60]]").addClass("validate[required,custom[integer],max[1825]]");
		}
	});
	//是否垫还改变修改债务利率
	$("select[name='lpcIsAdvance']").on("change",function(){
		var value=$(this).val();
		if(value=="0"){
			$("input[name='lpcDebtInteRate']").val(0);
		}
	});
})

	
	
	

