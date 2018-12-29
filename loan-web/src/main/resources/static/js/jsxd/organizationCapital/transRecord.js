$(function(){
	$("#direction").on("change",function(){
		var $this=$(this),
			value=$.trim($this.val()),
			str='<option value="">请选择</option>';
		if(value){
			$.ajax({
				url:"/accountsSubjectInfo/subjectList",
				data:{direction:value,"field3":"Y"},
				async:false,
				dataType:"json",
				success:function(data){
					for(var i=0;i<data.length;i++){
						str+=('<option value="'+ data[i].subjectId +'">'+ data[i].subjectName +'</option>');
					}
				}
			});
		}
		$("#subjectId").html(str).prev(".uew-select-value").find("em.uew-select-text").html("请选择");
	});
	$("#queryBtn").on("click",function(){
		var obj=$("#myForm").serializeJson();
		queryList(obj);
	});
	$("#recordList").on("click",".pagin ul li a",function(){
		if(!$(this).closest("li").hasClass("current")){
			var obj=$("#myForm").serializeJson();
			obj["page.pageIndex"]=$(this).data("pagenum")||1;
			queryList(obj);
		};
	});
	function queryList(obj){
		$.post("/organizationCapital/ajaxTransRecord",obj,function(data){
			$("#recordList").html("").html(data);
		});
	};
});