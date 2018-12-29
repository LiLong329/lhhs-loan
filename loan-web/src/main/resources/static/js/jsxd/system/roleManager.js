$(function() {
	/**
	 * 分页
	 */
	var $ur_a = $(".paginItem a");
	$ur_a.on("click", function() {
		var currentPageNo = $(this).attr("data-pagenum");
		searcher(currentPageNo);
	});
	function searcher(currentPageNo) {
		$("#currentPageNo").val(currentPageNo);
		$("#myForm").submit();
	}
	$("#searcher").on("click", function() {
		searcher(1);
	});

	// 禁用启用
	$(".tablelink.mlt10").on("click", function() {
		var ifEnable = $(this).attr("data-enable");
		var roleId = $(this).attr("data-numb");
		$.ajax({
			type : "POST",
			url : "/systemManager/roleIfEnable",
			processData : true,
			data : {
				"ifEnable" : ifEnable,
				"roleId" : roleId
			},
			success : function(data) {
				if (data.retCode == "00") {
					alert(data.retMsg, function() {
						window.location.reload(true);
					});
				} else {
					alert(data.retMsg, function() {
						window.location.reload(true);
					});
				}
			}
		});
	});

	// 数据权限组-禁用启用
	$("a[name='qijinyong']").on("click", function() {
		var ifEnable = $(this).attr("data-enable");
		var roleId = $(this).attr("data-numb");
		$.ajax({
			type : "POST",
			url : "/systemManager/authorityGroupChange",
			processData : true,
			data : {
				"ifEnable" : ifEnable,
				"roleId" : roleId
			},
			success : function(data) {
				if (data.retCode == "00") {
					alert(data.retMsg, function() {
						window.location.reload(true);
					});
				} else {
					alert(data.retMsg, function() {
						window.location.reload(true);
					});
				}
			}
		});
	});

});