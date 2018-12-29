//开启校验
$("#productForm").validationEngine('attach',{
	showOneMessage:true,
	autoPositionUpdate:true,
	maxErrorsPerField:1
});
$("#update_btn").on("click",function() {
	if(!$("#productForm").validationEngine("validate")) return;
	var productNo = $("input[name='productNo']").val();
	var productType = $("input[name='productType']").val();
	var thumbnailPicture = $("input[name='thumbnailPicture']").val();
	$.ajax({
		url :"/systemManager/updateProduct",
		data : {productNo:productNo,productType:productType,thumbnailPicture:thumbnailPicture},
		type : 'post',
		success : function(data) {
			if(data.retCode=='00'){
				alert(data.retMsg,false,function(){
					window.location.href="/systemManager/productInfoList";
				});
			}else{
				alert(data.retMsg,false);
			}
		},
		error : function(returndata) {
			alert("保存失败",false);
		}
	});
});