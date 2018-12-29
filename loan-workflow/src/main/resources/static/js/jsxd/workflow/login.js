$(function(){
	//回车时，默认是登陆
	$(document).keydown(function (event) {
	    if (event.keyCode == 13) {
	    	 $(".loginbtn").click();
	    }
	});
	//登录事件
	$(".loginbtn").on("click",function(){
		var loginAccount = $("#userName").val();
		var loginPassword = $("#userPassword").val();
	    if(null==loginAccount||loginAccount==""){
			alert("用户名称不能为空");
			return false;
		}
	    if(null==loginPassword||loginPassword==""){
	    	alert("密码不能为空");
	    	return false;
	    }else if(loginPassword.length<4||loginPassword.length>16){
	    	alert("密码长度为4-16个字符");
	    	return false;
	    }
    	$.post('/workflow/login/logincheck', {'userId': loginAccount, 'password': loginPassword}, function(data) {
    		
    		if(data.code=="00"){
					 window.location.href="/workflow/index";
			  }else{
				alert(data.retMsg,function(){
				});
			  }
    	});
	
	});
	
	
	
});