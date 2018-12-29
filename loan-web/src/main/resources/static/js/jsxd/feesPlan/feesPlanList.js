$(function(){	
		//绑定验证
	    $('#feesPlanForm').validationEngine('attach');
	   /**
	    * 分页
	    */
	   var $ur_a = $(".paginItem a");
		$ur_a.on("click",function(){
			var currentPageNo = $(this).attr("data-pagenum");			
			searcher(currentPageNo);
		    $("#myForm").submit();		
		});		
		function searcher(currentPageNo){
		   $("#currentPageNo").val(currentPageNo);
		   $("#myForm").submit();
	    }	
		
		
		$("#searcher").on("click",function(){
			 $("#currentPageNo").val(1);
		      $("#myForm").submit();
		});


		//展示
		var show = function(){
			$("#previewId").css("display","block");
			$("#previewBackId").css("display","block");
		}
		//隐藏
		var hidden = function(){
			$("#previewId").css("display","none");
			$("#previewBackId").css("display","none");
		}
		//取消按钮
		$("#reset").on("click",function(){
			hidden();
		});
		
		
		//执行还款弹出框
		$(".tablelist  a").on("click",function(){
			var feesId = $(this).data('feesid');
			var amount = $(this).data('amount');
			var subjectId = $(this).data('subjectid');
			$("#rightFee").val(amount);
			$("#_feesId").val(feesId);
			$("#subjectId").val(subjectId);
			show();
		});
		
		$("#feesPlanForm_submit").on("click",function(){
			if(!$('#feesPlanForm').validationEngine('validate')){
		    		return ;
		    	}
			var obj = $("#feesPlanForm").serialize();
			$.post("/feesPlan/loanFeesTrans",obj,function(data){
				if(data && data.retCode == '00'){
					alert('操作成功', false, function(){
						window.location.reload(true);
					});
				}else{
					alert(data.retMsg);
				}
			})
		});
		
		  
			
		$("#export").on("click",function(){
			var action = $("#myForm").attr("action");
			$("#myForm").attr("action","/orderInfo/export");
			$("#myForm").submit();
			$("#myForm").attr("action", action);
		});

});