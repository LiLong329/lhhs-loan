$(function(){
	var postQuery = function(obj,typeId){
		$.post("/reportStatistics/ajaxQueryList", obj , function(data) {
			  $("#"+typeId+"").html(data);
		});
	};
	
	$(".searcher").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj,$("input[name='searcherId']").val());
	});
	
	// 固定理财统计-分页查询
	$("#transAccountsId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj,$("input[name='searcherId']").val());
		};
	});
	
	// 对接放款统计-分页查询
	$("#buttJointReportId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj,$("input[name='searcherId']").val());
		};
	});
	
	// 公司放款统计-分页查询
	$("#companyId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj,$("input[name='searcherId']").val());
		};
	});
	// 同行放款统计-分页查询
	$("#peerReportId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj,$("input[name='searcherId']").val());
		};
	});
	// 同行放款统计-分页查询
	$("#organizationReportId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj,$("input[name='searcherId']").val());
		};
	});
	
     function conmmentQuery(){
    		if (!$(this).parent("li").hasClass("current")) {
    			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
    			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
    			var obj = $("#myForm").serializeJson();
    			postQuery(obj,$("input[name='searcherId']").val());
    		};
     }
     
    function reportExport(){
    	$("#myForm").attr("action","/reportStatistics/reportExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
    }
	//固定理财-导出
	$("#transAccountsId").on("click","#export",function(){
		reportExport();
	});
	//对接放款统计-导出
	$("#buttJointReportId").on("click","#export",function(){
		reportExport();
	});
	//公司放款统计-导出
	$("#companyId").on("click","#export",function(){
		reportExport();
	});
	//机构放款统计-导出
	$("#organizationReportId").on("click","#export",function(){
		reportExport();
	});
	//同行放款统计=-导出
	$("#peerReportId").on("click","#export",function(){
		reportExport();
	});
	
	
	
	var postQueryPayPlan = function(obj,typeId){
		$.post("/repaymentManager/ajaxQueryList/"+typeId, obj , function(data) {
			$("#"+typeId+"").html(data);
		});
	};
	
	// 还款金额统计-分页查询(事业部)
	$("#repaymentReportId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#deptMentForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#deptMentForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#deptMentForm").serializeJson();
			postQueryPayPlan(obj,"repaymentReportId");
		};
	});
	// 还款金额统计-分页查询(借款人)
	$("#repaymentPersonId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#personForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#personForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#personForm").serializeJson();
			postQueryPayPlan(obj,"repaymentPersonId");
		};
	});
	
	$("#deptSearcherId").on("click",function(){
		$("#deptMentForm input[name='page.pageIndex']").val("1");
		var obj = $("#deptMentForm").serializeJson();
		postQueryPayPlan(obj,"repaymentReportId");
	});
	
	$("#personSearcherId").on("click",function(){
		$("#personForm input[name='personPage.pageIndex']").val("1");
		var obj = $("#personForm").serializeJson();
		postQueryPayPlan(obj,"repaymentPersonId");
	});
	
	//还款金额统计(事业部)=-导出
	$("#deptExport").on("click",function(){
		$("#deptMentForm").attr("action","/repaymentManager/export/dept");
		$("#deptMentForm").submit();
		$("#deptMentForm").attr("action","");
	});
	//还款金额统计(借款人)=-导出
	$("#personExport").on("click",function(){
		$("#personForm").attr("action","/repaymentManager/export/person");
		$("#personForm").submit();
		$("#personForm").attr("action","");
	});
	
});

