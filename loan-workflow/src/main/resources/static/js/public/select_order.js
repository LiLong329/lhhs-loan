var strHtml="",
	cityHtml="",
	option="<option value=''>请选择</option>",
	provinceOptions = get_province_options();
	strHtml = option+provinceOptions;
	$(".provinceSelect").html(strHtml);
	$(".provinceSelect").each(function(i,n){
		var data_val = $(n).attr("data-val");
		if(data_val){
			$(n).val(data_val);
			cityHtml=option+get_city_options(data_val);
			($(n).closest("td").find(".citySelect").html(cityHtml))&&(cityHtml="");
		}else{
			$(n).closest("td").find(".citySelect").html(option);
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
			$(this).closest("td").find(".citySelect").html("").html(strHtml).val("").prev().find("em")[0].innerHTML="请选择";
		}else{
			$(this).closest("td").find(".citySelect").html(option).val("").prev().find("em")[0].innerHTML="请选择";
		}
	});
	$("body").on("change",".select-common",function(){
		var tempHtml=$(this).find("option:selected").html();
		$(this).prev(".uew-select-value").find(".uew-select-text").html(tempHtml);
	});