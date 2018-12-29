$(function(){
	   /**
	    * 分页
	    */
	   var $ur_a = $(".paginItem a");
	   $ur_a.on("click",function(){
		   var currentPageNo = $(this).attr("data-pagenum");			
		   $("#currentPageNo").val(currentPageNo);
		   $("#myForm").submit();		
		});		
		
		$("#searcher").on("click",function(){	
			$("#currentPageNo").val(1);
			$("#myForm").submit();
		});
	
	
/*	var postQuery = function(obj){
		$.post("/empQuarters/ajaxQueryList", obj , function(data) {
			$("#empQuartersId").html(data);
		});
	};
	
	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj);
	});
	
	// 分页查询
	$("#empQuartersId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj);
		};
	});*/
	
	
//	 /************公司-部门级联查询*****************/
//	  $("#companyId").on("change",function(){
//		  var companyId = $(this).val();
//		  if(companyId){
//			  $.post("/empQuarters/companyDeptLink",{
//				  "companyId":companyId
//			  },function(data){
//				  var html="<option value=''>请选择</option>";
//				  for(var i=0;i<data.deptList.length;i++){
//					  html+=("<option class='leGroupId' value='"+data.deptList[i].ld_dept_id+"'>"+data.deptList[i].ld_name+"</option>");
//				  }
//				  $("#deptId").html("").html(html);
//			  });
//		  }else{
//			  $("#deptId").html("").html("<option value=''>请选择</option>");
//		  }
//		  $("#deptId").prev(".uew-select-value").find(".uew-select-text").html("请选择")
//	  });
});
