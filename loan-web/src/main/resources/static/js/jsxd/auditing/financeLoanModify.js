$(function(){
	
	var notSubmit=true;
    var mobileOrCom =$("#mobileOrCompanyId").val();
	if(mobileOrCom){
		var isHasLenders=true;
	}else{
		var isHasLenders=false;
	}
	
	//绑定异步验证
	$("form").validationEngine("attach",{
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
    				$("#"+name).find("form").validationEngine('updatePromptsPosition');
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
	//添加偶数行样式
	function addOdd(){
		$("#tab5 .tablelist tbody tr").removeClass("odd");
		$("#tab5 .tablelist").each(function(i,n){
            $(this).find("tbody tr:odd").addClass("odd");
        });
	};
	//查询团队信息
	function asyncTeamManager(id){
		if(id){
			$.post("/orderInfo/asyncTeamManager/"+id,function(data){
				data = eval(data);
				$("#ldName").val(data[0]);
				$("#tuandui").val(data[1]);
				$("#zongjingli").val(data[2]);
			})
		}
	};
	asyncTeamManager($("#leEmpId").val());
	$("#leEmpId").change(function(){
		asyncTeamManager(this.value);
	});
	//修改客户性质事件
	function customerType(){
		$("#capitalSideInfo  tr").each(function(i,e){
			var tr = $(e);
			var select = tr.find("select[name='customerNature']");
			var bigcategory = select.find("option:selected").data('bigcategory');
			tr.find("input[name='customerType']").val(bigcategory);
		})
	};
	customerType();
	$("#capitalSideInfo").on("change","select[name='customerNature']",function(){
		customerType();
	});
	//支出类型修改事件
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
	expenditureVariety();
	$("#expenditureInfo").on("change","select[name='expenditureVariety']",function(){
		expenditureVariety();
	});
	//收入类型修改事件
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
	};
	earningVariety();
	$("#incomeInfo").on("change","select[name='earningVariety']",function(){
		earningVariety();
	});
	//异步查询资金方信息
	$("#capitalSideInfo").on("change","input[name='mobile'],select[name='customerNature']",function(){
		var $tr = $(this).parents('tr');
		var $select = $tr.find("select[name='customerNature']");
		var $mobile = $tr.find("input[name='mobile']");
		var bigcategory = $select.find("option:selected").data('bigcategory');
		var mobile = $.trim($mobile.val());
		if(mobile&&bigcategory){
			$.post("/orderInfo/queryAccountCardList",{
				"accountType":bigcategory,
				"mobile":mobile
			},function(jsonstr){
				var data = {};
				if(jsonstr==''){
					alert('未找到放款人');
					isHasLenders=false;
				}else{
					data = JSON.parse(jsonstr);
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
					isHasLenders=true;
				}
			});
		}
	});
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
	//保存
	$("#saveBasicInfo").on("click",function(){
        var	flag=$('form').validationEngine("validate",{validateNonVisibleFields:true}),
        	msg="";
        if(!flag){
        	return false;
        }
        if(!isHasLenders){
			alert("请填写正确的放款人");
	   		return false;
		}
    	//借款金额
    	var loanAmount = parseInt($("#loanAmount").val());
    	var amountCount = 0; 
    	$("input[name='amountPaid']").each(function(i,e){
    		 amountCount = amountCount + parseInt($(e).val());
    		 if(loanAmount * 10000 < amountCount ){
    			 msg = '放款金额总和不可大于借款金额';
    			 flag = false;
    			 return false;
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
	    var baseRate = _calculate(loanRate,loanRateUnit);
		$("input[name='amountRate']").each(function(i,e){
	   		 var amountRate = parseInt($(e).val());
	   		 var amountRateUnit =  $(e).closest('td').find('select[name="amountRateUnit"]').val();
	   		 if(loanRateUnit == '次' && amountRateUnit !='次' ){
	   			 msg = '借款利率和放款利率只要有一个为次，就都为次';
	   			 flag = false;
	   			 return false;
	   		 }else{
	   			 var memRate = _calculate(amountRate,amountRateUnit);
		   		 if(baseRate  < memRate ){
		   			 msg = '放款利率不可大于借款利率';
		   			 flag =  false;
		   			 return false;
		   		 }
	   		 }
	   	})
	   	if(!flag){
	   		alert(msg);
	   		return flag; 
	   	}
		var orderNo=$("#orderNo").val(),
			orderInfo = $("#orderInfo").serializeJson(), 
			borrower = $("#borrower").serializeJson(), 
			parasConfig= $("#parasConfig").serializeJson();
		var houseForm =  $("#orderHouse form"),
			capitalSideForm = $("#capitalSideInfo tbody tr"),
			incomeForm = $("#incomeInfo tbody tr"),
			expenditureForm = $("#expenditureInfo tbody tr"),
			coborrowerForm = $("#coborrowerInfo tbody tr"),
			houseList = new Array() ,
			loanCapitalExpenditure = new Array(), 
			loanCapitalEarning = new Array(), 
			loanCapitalInfo = new Array(),
			coborrowerInfo = new Array();
		var processNodeNo = $("#processNodeNo").val()||"", // 节点编号
			taskId = $("#taskId").val()||"", // 任务id
			procInsId = $("#procInsId").val()||"", // 流程实例id
			letResult = 1, // 审批结果---1：同意，11:重走审批 ，91：撤单，-3：返回驳回节点
			letRemark = "", // 备注
			isLoanAdd = $("#isLoanAdd").val(),//是否为小贷自己报单
			letNode = $("#approvalNode").val(), // 节点
			type=$("input[name='type']:checked").val();
		if(processNodeNo=="modify"){
			letResult=$("select[name='letResult']").val();
		}
		for(var i=0;i<houseForm.length;i++){
			houseList.push($(houseForm[i]).serializeJson());
		}
		for(var i=0;i<capitalSideForm.length;i++){
			loanCapitalInfo.push($(capitalSideForm[i]).getOtherJson());
		}
		for(var i=0;i<incomeForm.length;i++){
			loanCapitalEarning.push($(incomeForm[i]).getOtherJson());
		}
		for(var i=0;i<expenditureForm.length;i++){
			loanCapitalExpenditure.push($(expenditureForm[i]).getOtherJson());
		}
		for(var i=0;i<coborrowerForm.length;i++){
			if(i%2==0){
				var temp=$(coborrowerForm[i]).getOtherJson(),
					temp1=$(coborrowerForm[i+1]).getOtherJson();
				temp.orderNo=orderNo;
				for(var key in temp1) {
					temp[key]=temp1[key];
				}
				coborrowerInfo.push(temp);
			}
		}
		if(notSubmit){
			notSubmit=false;
		}else{
			return false;
		}
		confirm("确认要提交审核吗？", function(){
			$.post("/auditProcess/handler",{
				"orderInfo":JSON.stringify(orderInfo),
				"borrower":JSON.stringify(borrower),
				"houseList":JSON.stringify(houseList),
				"loanCapitalExpenditure":JSON.stringify(loanCapitalExpenditure),
				"loanCapitalEarning":JSON.stringify(loanCapitalEarning),
				"loanCapitalInfo":JSON.stringify(loanCapitalInfo),
				"parasConfig":JSON.stringify(parasConfig),
				"orderNo":orderNo,
				"procInsId":procInsId,
				"taskDefKey":processNodeNo,
				"letResult":letResult,
				"letRemark":letRemark,
				"isLoanAdd":isLoanAdd,
				"type":type,
				"taskId":taskId,
				"letNode":letNode,
				"relationPeopleInfo":JSON.stringify(coborrowerInfo)
			},function(data){ 
				if(data.retCode=="00"){
					alert(data.retMsg, false, function(){
						window.location.href = "/workflow/acttask/todoList/1";
					});
				}else{
					alert(data.retMsg);
					notSubmit=true;
				}
			}); 
		},function(){
			notSubmit=true;
		});
	});
	//结息方式和付息方式修改
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