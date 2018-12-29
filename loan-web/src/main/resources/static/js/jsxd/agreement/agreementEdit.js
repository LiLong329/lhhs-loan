$(function(){
	//绑定验证
    $('#myForm').validationEngine('attach');
    $('#myForm1').validationEngine('attach');
    $("#totalLoanInfo form,#guaranteeInfo form").validationEngine('attach');
	// 验证借款期限不能超过20年
	$(".loan_term_unit").on("change",function(){
		$('.loan_term').removeClass(function(i,o){
			if(o&&o.indexOf("validate")>=0){
				var str=o.substring(o.indexOf("validate"));
				str=str.substring(0,(str.indexOf(" ")>0?str.indexOf(" "):str.length));
				return str;
			}
		});
		if($(this).val() == '个月'){
			$('.loan_term').addClass("validate[required,custom[integer],max[240]]");
		}else if($(this).val() == '天'){
			$('.loan_term').addClass("validate[required,custom[integer],max[7300]]");
		}
	});
	$(".loan_term_unit").change();
    //保存操作
    $("#saveButton").on("click",function(){
    	if(!$('#myForm').validationEngine('validate')){
    		return ;
    	}
    	if(!$('#myForm1').validationEngine('validate')){
    		return ;
    	}
    	for(var i = 0; i < $("#totalLoanInfo form").length; i++) {
			if(!$($("#totalLoanInfo form")[i]).validationEngine("validate")){
				return false;
			}
		}
    	for(var i = 0; i < $("#guaranteeInfo form").length; i++) {
    		if(!$($("#guaranteeInfo form")[i]).validationEngine("validate")){
    			return false;
    		}
    	}
    	//前半部分表单
    	var objFirst = $("#myForm").serializeJson();
    	//后半部分表单
    	var objLast = $("#myForm1").serializeJson();
    	//合并参数
    	var obj = $.extend({},objFirst, objLast);
    	//共借人、担保人列表
    	var rpaList=[];
		$("#totalLoanInfo form,#guaranteeInfo form").each(function(i,n){
			var temp=$(n).serializeJson();
			if(temp.name||temp.idNum){
				temp.agreementNo=obj['agreementNo'];
				rpaList.push(temp);
			}
		});
		obj['rpaList'] = JSON.stringify(rpaList);
    	$.post("/agreement/save",obj,function(data){
    		if(data && data.retCode=='00'){
    			alert("操作成功",true,function(){
    				window.location.href="/agreement/list/0";
    			});
    		}else{
    			alert("操作失败",true,function(){
    				window.location.reload();
    			});
    		}
    	});
    });
	function addInfo(type) {
		var addInfo = "<ul class='nbbdgl-Ui'>"+
						"<form>"+
						   "<li>"+
							   "<span class='tips'>&nbsp;</span>"+
							   "<input type='text' class='txt validate[custom[chineseName]]' value='' name='name'/>"+
							   "<input type='hidden' value='"+type+"' name='type'/>"+
						   "</li>"+
						   "<li>"+
							   "<span class='tips'>&nbsp;</span>"+
							   "<input type='text' class='txt validate[custom[chinaIdLoose]]' value='' name='idNum'/>"+
							   "<img src='/img/loan_reduce.png' class='del' style='margin-left: 100px;'/>"+
						   "</li>"+
						"</form>"+
					  "</ul>";
		return addInfo;
	}
	//添加共同借款人
	$("#addTotalLoan").on("click",function(){
		var $newInfo = $("#totalLoanInfo").append(addInfo(2));
	});
	//添加担保人
	$("#addGuarantee").on("click",function(){
		var $newInfo = $("#guaranteeInfo").append(addInfo(3));
	});
	//删除共同借款人、担保人
	$("body").on("click",".del",function(){
		$(this).closest("ul").remove();
	});
});