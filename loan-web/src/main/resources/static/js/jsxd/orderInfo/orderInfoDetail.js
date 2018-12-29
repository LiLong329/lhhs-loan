$(function(){
	var obj ="orderNo="+$("#orderNo").val() +"&productId="+ $("#childProductNo").val();
	$.post("/orderInfo/toOrderCredentials", obj , function(data) {
		$("#tab3").html(data);
	});
	
	
	// 分页查询
	$("#tab3").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			var currentPageNo = $(this).data("pagenum");
			var pageSize = $(this).data("pagesize");
			$.post("/orderInfo/toOrderCredentials", {
				"currentPageNo" : currentPageNo,
				"pageSize" : pageSize,
				"orderNo" : orderNo,
				"productId" : productId
			}, function(data) {
				$("#tab3").html(data);
			});
		};
	});
	//打包下载
	$("#tab3").on("click","#downloadFile",function(){
		var $checkbox = $("#tab3").find("input:checkbox:checked");
		var CredentialsNoList = new Array();
		if($checkbox.length > 0){
			$checkbox.each(function(i,n){
				CredentialsNoList.push($(n).data("ordercredentialsno"));
			});
			$.post("/mortgageInfo/createFilesZip",{
				"CredentialsNos":JSON.stringify(CredentialsNoList),
				"bname":$("#bname").text()
			},function(data){
				if(data.retCode == "00"){
					window.location.href="/mortgageInfo/downloadZip?fileName="+data.fileName;
				}else{
					alert("文件打包失败");
				}
			});
		}else{
			alert("请勾选需要打包的资质清单");
		}
	});
	

	
});