$(function(){
	var $imgzs = $('.imgzs'),//图片容器对象
	    $img = $imgzs.find('img'),//图片对象
		imgWidth= 0,
		imgHeight= 0,
		currentDeg = 0;
	function rotate(deg) {
		currentDeg += deg;
		if(currentDeg%180!=0){
			$imgzs.css("height",imgWidth+3+"px");
			$img.css({'transform':'rotate('+currentDeg%360+'deg)','margin-top':(imgWidth-imgHeight)/2+'px'});
		}else{
			$imgzs.css("height",imgHeight+3+"px");
			$img.css({'transform':'rotate('+currentDeg%360+'deg)','margin-top':'0px'});
		}
	}
	//图片单击
	$(".imglist li").on("click",function(){
		if($(this).find("a").length>0) return;
		var src = $(this).find("img").attr("src");
		$("#singe").attr("src",src);
		$(".imgList-singe").show();
		imgWidth=$img.width();
		imgHeight=$img.height();
		$imgzs.css("height",imgHeight+3+"px");
		$img.css({'transform':'rotate(0deg)','margin-top':'0px'});
	});
	//垂直旋转
	$('#vertical').on('click', function() {
		rotate(180);
	});
	//顺时针旋转
	$('#clockwise').on('click', function() {
		rotate(90);
	});
	//查看原图
	$('#lookPic').on('click', function() {
		$imgzs.css("height",imgHeight+3+"px");
		$img.css({'transform':'rotate(0deg)','margin-top':'0px'});
		currentDeg = 0;
	});
	//返回上一步
	$("#goBack").on("click",function(){
		if (isAgreement!=1) {
			session.set("tabNum","orderCredentials");
		}
		window.history.go(-1);
	});
	//关闭窗口close
	$("#close").on("click",function(){
		window.close();
	});
});