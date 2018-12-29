$(function(){
	var submitObj={},notSubmit=true;
	
	//绑定异步验证
	$('#shForm').validationEngine("attach",{
		autoPositionUpdate:true,
		autoHidePrompt:true,
		maxErrorsPerField:1,
		showOneMessage:true,
		autoHideDelay:2000
	});
	
	//显示流转方向或会签
	$(".treatmentResult select").on("change",function(){
		if($(this).val()&&$(this).val()==4){
			var $patchDirection=$(this).closest("ul").find(".patchDirection").show(),
				$select=$patchDirection.find("select"),
				$option=$select.find("option:first");
			$option.prop("selected",true);
			$select.prev(".uew-select-value").find(".uew-select-text").html($option.text());
		}else{
			$(this).closest("ul").find(".patchDirection").hide();
		}
		if($(this).val()&&$(this).val()==5){
			var $jointlySign=$(this).closest("ul").find(".jointlySign").show(),
				$select=$jointlySign.find("select"),
				$option=$select.find("option:first");
			$option.prop("selected",true);
			$select.prev(".uew-select-value").find(".uew-select-text").html($option.text());
			$(".jointfkSign").hide();
			$(".area").removeClass("validate[required]");
		}else{
			$(this).closest("ul").find(".jointlySign").hide();
			$(".jointfkSign").show();
			
			if($(this).val()&&$(this).val()==91||$(this).val()&&$(this).val()==92||$(this).val()&&$(this).val()==-2){
				$(".jointfkSign").hide();
				$(".area").addClass("validate[required]");
				$("#actualLoanDate").removeClass("validate[required]");
			}else{
				$(".area").removeClass("validate[required]");
				$(".jointfkSign").show();
				$("#actualLoanDate").addClass("validate[required]");
			}
		}
		
		
	});	
	
	//还款计划表与付款计划表单击绑定
	$("#isPayPlanTemp,#isPayPlanCompanyTemp").on("click",function(){
		if($(this).prop("checked")){
			$("#isPayPlanTemp").prop("checked",true);
			$("#isPayPlanCompanyTemp").prop("checked",true);
		}else{
			$("#isPayPlanTemp").prop("checked",false);
			$("#isPayPlanCompanyTemp").prop("checked",false);
		}
	});
	
	//部分放款与全部放款
	$("input[name='loanMethod']").on("change",function(){
		var value=$("input[name='loanMethod']:checked").val();
		if(value==1){
			$("#tab5 .partialLoan").show();
		}else{
			$("#tab5 .partialLoan").hide();
		}
	});
	
	//审批流程节点
	$("#auditingSubmit").on("click",function(){
		if(!validateForm()){
			return false;
		}
		/*if(!$("#shForm").validationEngine("validate")){
			return false;
		}*/
		var orderNo = $("#orderNo").val(), // 订单编号
			letNode = $("#approvalNode").val(), // 节点
			processNodeNo = $("#processNodeNo").val(), // 节点编号
			flowLetNode = $("#flowLetNode").val()||"", // 流转方向
			taskId = $("#taskId").val()||"", // 任务id
			procInsId = $("#procInsId").val()||"", // 流程实例id
			letResult = $("#shForm select[name='letResult']").val()||"", // 审批结果
			loanMethod = $("#shForm input[name='loanMethod']").val()||"", // 放款方式
			letRemark = $("#shForm textarea[name='letRemark']").val()||"", // 备注
			isLoanAdd = $("#isLoanAdd").val(),//是否为小贷自己报单
			obj = {};
		if(letNode==0){
			var fundSide=$("#fundSide").val(), // 资金方
				productTypeNo=$("#productName option:selected").data("producttypeno"), // 一级产品编号
				productId=$("#productName").val(), // 二级产品编号
				managerAccount=$("#managerAccount").val();// 客户经理账号
			obj.fundSide=fundSide;
			obj.childProductNo=productId;
			obj.productNo=productTypeNo;
			obj.managerAccount =managerAccount;
		}else if(letNode==9){
			var actualLoanDate=$("#actualLoanDate").val(), // 实际放款时间
				productId=$("#productId").val(), // 二级产品编号
				isPayPlanTemp=$("input[id='isPayPlanTemp']:checked").val()||"N",// 是否生成还款计划表
				isLoanRecordTemp=$("input[id='isLoanRecordTemp']:checked").val()||"N",// 是否生成放款记录表
				isPayPlanCompanyTemp=$("input[id='isPayPlanCompanyTemp']:checked").val()||"N",// 是否生成待付款计划表
				loanMethod=$("input[name='loanMethod']:checked").val(),//还款方式
				historyLoanMethod=$("#historyLoanMethod").val();//历史还款方式
			obj.childProductNo=productId;
			if(letResult=="1"){
				obj.historyLoanMethod=historyLoanMethod;
				obj.loanMethod=loanMethod;
				obj.actualLoanDate=actualLoanDate;
				obj.isPayPlanTemp=isPayPlanTemp;
				obj.isLoanRecordTemp=isLoanRecordTemp;
				obj.isPayPlanCompanyTemp=isPayPlanCompanyTemp;
			}
		}else if(letNode==2){
			var type= $("input[name='type']:checked").val();//是否跳过下户
			var businessType= $("input[name='businessType']:checked").val();//业务类型  居间非居间
			if((type==null||type==''||type=='undefined')&&$("input[name='taskDefKeySed']").val()=='mianshen_hq'
				&& $("input[name='orderStatuSed']").val()=='2' && $("input[name='field3']").val()=='1'){
				type="0";
			}
			obj.type=type;
			obj.businessType =businessType;
		}
		if(letNode==0||letNode==2||letNode==3||letNode==5||letNode==21||letNode==20){
			var signer=$("#signer").val();//会签人或者指派人
			obj.wf_group=signer;
		}
		if(letNode==3){
			 var  signerAccount = $("#signerAccount").val();
			 var type= $("input[name='type']:checked").val();//是否跳过签约公证
			 obj.wf_user =signerAccount;
			 obj.type=type;
		}
		obj.orderNo=orderNo;
		obj.letNode=letNode;
		obj.taskId=taskId;
		obj.procInsId=procInsId;
		obj.taskDefKey=processNodeNo;
		obj.flowLetNode=flowLetNode;
		obj.letResult=letResult;
		obj.letRemark=letRemark;
		obj.isLoanAdd=isLoanAdd;
		if(notSubmit){
			notSubmit=false;
		}else{
			return false;
		}
		if(letNode == 9){
			if(letResult=="1"){
				confirm("确认要提交审核吗？",function(){
					$.post("/approval/makePreview",obj,function(htmlTemp){
						submitObj=obj;
						$("#preview").html("").html(htmlTemp).show();
						$(".right-content").animate({
							"scrollTop":0
						},0);
						notSubmit=true;
					});
				},function(){
					notSubmit=true;
				});
			}else{
				confirm("确认要提交审核吗？", function(){
					$.post("/auditProcess/handler",obj,function(data){
						if(data.retCode == "00"){
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
			}
		}else{
			confirm("确认要提交审核吗？", function(){
				$.post("/auditProcess/handler",obj,function(data){
					if(data.retCode == "00"){
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
		}
	});
	
	//预览提交
	$("#preview").on("click","#previewSubmit",function(){
		if(notSubmit){
			notSubmit=false;
		}else{
			return false;
		}
		$.post("/auditProcess/handler",submitObj,function(data){
			if(data.retCode == "00"){
				alert(data.retMsg, false, function(){
					window.location.href = "/workflow/acttask/todoList/1";
				});
			}else{
				alert(data.retMsg);
				notSubmit=true;
			}
		});
	});
	
	//预览取消
	$("#preview").on("click","#previewCancel",function(){
		$("#preview").hide();
	});
	
	//验证方法
	function validateForm(){
		$('#shForm').find("input,select,textarea").removeClass(function(i,o){
			if(o&&o.indexOf("validate")>=0){
				var str=o.substring(o.indexOf("validate"));
				str=str.substring(0,(str.indexOf(" ")>0?str.indexOf(" "):str.length));
				return str;
			}
		});
		var letResult=$("#shForm select[name='letResult']").val(),
			approvalNode=$("#approvalNode").val(),//审核节点
			flag;
		if(letResult){
			if(letResult==1){//同意
				if(approvalNode==0){//初评
					$("#shForm #fundSide,#shForm #productName").addClass("validate[required]");
				}else if(approvalNode==20){//下户指派
					$("#shForm #signer").addClass("validate[required]");
				}else if(approvalNode==1){//下户
					var idCard=$.trim($("#idCard").val());//身份证号
					if(idCard){
						if(/^(\d{18}|\d{15}|\d{17}[xX])$/.test(idCard)){
							return true;
						}else{
							alert("身份证号不正确");
							return false;
						}
					}else{
						alert("请输入身份证号");
						return false;
					}
				}else if(approvalNode==9){
					$("#shForm #actualLoanDate").addClass("validate[required]");
					flag=$('#shForm').validationEngine("validate");
					if(flag){
						var loanAmount=$.trim($("#tab2 input[name='loanAmount']").val()),//借款金额
							loanTerm=$.trim($("#tab2 input[name='loanTerm']").val()),//借款期限
							loanRate=$.trim($("#tab2 input[name='loanRate']").val()),//借款利率
							repayment=$.trim($("#tab2 select[name='repayment']").val()),//还款方式
							loanMethod=$("#shForm input[name='loanMethod']").val()||"", //放款方式
							idCard=$.trim($("#idCard").val());//身份证号
						var amountPaidThis=$("#tab5 input[name='amountPaidThis']");//本次放款金额对象
						if(!loanAmount){
							alert("借款金额不能为空");
							return false;
						}
						if(!loanTerm){
							alert("借款期限不能为空");
							return false;
						}
						if(!loanRate){
							alert("借款利率不能为空");
							return false;
						}
						if(!repayment){
							alert("还款方式不能为空");
							return false;
						}
						if(idCard){
							if(!/^(\d{18}|\d{15}|\d{17}[xX])$/.test(idCard)){
								alert("身份证号不正确");
								return false;
							}
						}else{
							alert("请输入身份证号");
							return false;
						}
						if(loanMethod==1){
							for(var i=0;i<amountPaidThis.length;i++){
								if(!$.trim($(amountPaidThis[i]).val())){
									alert("本次放款金额不能为空");
									return false;
								}
							}
						}
						return flag;
					}
				}
			}else if(letResult==90||letResult==91||letResult==92||letResult==-2){//拒贷、撤单、放款终止、驳回修改
				$("#shForm textarea[name='letRemark']").addClass("validate[required]");
			}else if(letResult==5){//会签
				$("#shForm textarea[name='letRemark']").addClass("validate[required]");
				$("#shForm #signer").addClass("validate[required]");
			}
		}else{
			if(approvalNode==80||approvalNode==81||approvalNode==82){//补件
				$("#shForm textarea[name='letRemark']").addClass("validate[required]");
			}
		}
		return $('#shForm').validationEngine("validate");
	};
	
	// 资金方异步查询产品名称
	$("#fundSide").on("change",function(){
		var orgId = $(this).val(),//资金方id
			orgName = $(this).find("option:selected").text(),//资金方名称
		    orderNo =$("#orderNo").val();//订单编号
		if(orgId){
			$.ajax({
				url:"/approval/asynQueryChildProduct",
				data:{"orgId":orgId,"orderNo":orderNo},
				async:false,
				dataType:"json",
				success:function(data){
					var html = "<option value=''>请选择</option>";
					if(data){
						 $.each(data,function(i,n){
							 html += ("<option value='"+ n.productId +"' data-producttypeno='"+n.productParentNo+"' data-producttype='"+ n.productType +"'>"+ n.productName +"</option>");
		   			  	 });
					}
					$("#productName").html(html);
				}
			});
		}else{
			$("#productName").html("<option value=''>请选择</option>");
		}
		$("#productName").prev(".uew-select-value").find(".uew-select-text").html("请选择");
		modifyInfo((orgId?orgName:""),"","","");
	});
	
	// 产品名称修改事件
	$("#productName").on("change",function(){
		var orgId = $("#fundSide").val(),
			orgName=$("#fundSide option:selected").text(),
			productType=$(this).find("option:selected").data("producttype")||"",
			productNameNo=$(this).val(),
			productName=$(this).find("option:selected").text(),
			orderNo=$("#orderNo").val();
		$.ajax({
			url:"/approval/changeOrderCredentials",
			data:{"orderNo":orderNo,"productId":productNameNo},
			async:false,
			dataType:"json",
			success:function(data){
				if(data.retCode=="00"){
					modifyInfo((orgId?orgName:""),productType,productNameNo,(productNameNo?productName:""));
				}else{
					alert(data.retMsg,function(){
						window.location.reload();
					});
				}
			},
			error:function(){
				alert("系统错误",function(){
					window.location.reload();
				});
			}
		});
	});
	
	//修改资金方、产品、资质、客户经理
	function modifyInfo(orgName,productType,productNameNo,productName){
		$("#org_name").html(orgName);
		$("#product_type").html(productType);
		$("#product_name").html(productName);
		productId=productNameNo;
	};
});
//放款时间校验
function checkTime(){
	var orderNo = $("#orderNo").val(), // 订单编号
		actualLoanDate=$("#actualLoanDate").val(); // 实际放款时间
	$.post("/approval/checkTime",{
		orderNo:orderNo,
		actualLoanDate:actualLoanDate
	},function(data){
		if(data.retCode!="00"){
			alert(data.retMsg);
			$("#actualLoanDate").val("");
			return false;
		}
	});
}