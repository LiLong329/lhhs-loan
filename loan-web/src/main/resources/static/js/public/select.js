var strHtml="",
	cityHtml="",
	option="<option value=''>请选择</option>",
	provinceOptions = get_province_options();
strHtml = option+provinceOptions;
if($(".provinceSelect").length>0&&($(".provinceSelect").data("useselect")==undefined||($(".provinceSelect").data("useselect")!=undefined&&$(".provinceSelect").data("useselect")!=false))){
	$(".provinceSelect").html(strHtml);
}
$(".provinceSelect").each(function(i,n){
	var data_val = $(n).attr("data-val");
	if(data_val){
		$(n).val(data_val);
		cityHtml=option+get_city_options(data_val);
		($(n).closest("li").find(".citySelect").html(cityHtml))&&(cityHtml="");
	}else{
		$(n).closest("li").find(".citySelect").html(option);
	}
});
$(".citySelect").each(function(i,n){
	var data_val = $(n).attr("data-val");
	if(data_val) {
		$(n).val(data_val);
	}
});		
$("body").on("change",".provinceSelect",function(){
	var value=$.trim($(this).val()),
		cityOptions = get_city_options(value);
		strHtml="";
	if(value&&cityOptions){
		strHtml+=option;
		strHtml+=cityOptions;
		$(this).closest("li").find(".citySelect").html("").html(strHtml).val("").closest(".uew-select").find(".uew-select-value em")[0].innerHTML="请选择";
	}else{
		$(this).closest("li").find(".citySelect").html(option).val("").closest(".uew-select").find(".uew-select-value em")[0].innerHTML="请选择";
	}
});
$("body").on("change",".select-common",function(){
	var tempHtml=$(this).find("option:selected").html();
	$(this).closest(".uew-select").find(".uew-select-value .uew-select-text").html(tempHtml);
});
$(".common-company").on("change",function(){
	var value=$.trim($(this).val());
	if(value){
		$.ajax({
			url:"/systemManager/getDeptListByCompanyId",
			data:{companyId:value},
			async:false,
			success:function(data){
				var str='<option value="">请选择</option>';
				for(var i=0; i<data.length; i++){
					str+=('<option value="'+ data[i].deptId +'">'+ data[i].name +'</option>');
				}
				$(".common-dept").html(str);
			},
			error:function(){
				alert("查询部门信息失败");
			}
		});
	}else{
		$(".common-dept").html('<option value="">请选择</option>');
	}
	$(".common-dept").closest(".uew-select").find(".uew-select-value .uew-select-text").html("请选择");
});
if($(".common-company").length>0&&$(".common-dept").length>0){
	var company=$(".common-company").val(),
		dept=$(".common-dept").attr("data-val");
	company&&$(".common-company").change();
	dept&&$(".common-dept").val(dept);
}