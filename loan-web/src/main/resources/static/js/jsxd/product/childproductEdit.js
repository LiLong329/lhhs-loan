$(function(){
//	if($("#jsd").val() == $("#productNo").val()){
//		$('#url').show();//判断是否是极速贷产品
//	}
	initAreaSelect();
});

//初始化省市区域
function initAreaSelect(){
	var option="<option value=''>请选择</option>";
	var provinceOptions = get_province_options();
	var strHtml = option+provinceOptions;
	$("select.provinceSelect").each(function(i,e){
		var value = $(e).val();
		$(e).html(strHtml);
		$(e).val(value);
		$(e).parents('li').find("select.citySelect").each(function(i,s){
			$(e).on('change',function(){
			$(s).prev().find("em").first().text("请选择");
			var p=$(e).children('option:selected').val();
			var cityHtml = get_city_options(p);
			var cityOption="<option value=''>请选择</option>";
			var cityHtml = cityOption+cityHtml;
			$(s).html(cityHtml);
		})
	});
	})

}