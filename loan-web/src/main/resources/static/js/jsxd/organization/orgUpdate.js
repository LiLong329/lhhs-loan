$(function(){
	//绑定异步验证
	$('#myForm').validationEngine("attach",{
		scroll:true,
		autoPositionUpdate:true,
		autoHidePrompt:true,
		validateNonVisibleFields:true
	});
	//添加支城市
	$(".cityAdd").on("click", function(){
		var province = $.trim($("#provinceSelect").val()),//省份
			city = $.trim($("#citySelect").val()),//城市
			flag=true;
		if(city){
			$("#cityList li").each(function(i,n){
				if($.trim($(n).find("span.cityName").html())==city){
					flag=false;
					return false;
				}
			});
			if(flag){
				$("#cityList").append("<li><span class='provinceName'>"+province+"</span><span class='cityName'>"+city+"</span><span class='aStyle'>×</span></li>");
			}else{
				alert('城市已存在');
			}
		}else{
			alert('请选择城市');
		}
	});
	//删除支持城市
	$("#cityList").on("click",".aStyle",function(){
		var $this=$(this);
		$this.closest("li").remove();
	});
	//提交
	$("#update_btn").on("click",function(){
		if(!$('#myForm').validationEngine('validate')){
    		return false;
    	}
		var obj=$("#myForm").serializeJson(),
			$li=$("#cityList li"),
			cityList=[];
		if($li.length>0){
			$li.each(function(i,n){
				var temp={};
				temp.province=$.trim($(n).find("span.provinceName").html());
				temp.city=$.trim($(n).find("span.cityName").html());
				cityList.push(temp);
			});
		}else{
			alert('请选择支持的城市',false);
			return false;
		}
		if(cityList.length>0){
			obj.cityList=JSON.stringify(cityList);
		}
		$.post("/org/updateOrg",obj,function(data){
			if(data.retCode=="00"){
				alert(data.retMsg,function(){
					window.location.href="/org/list";
				});
			}else{
				alert(data.retMsg);
			}
		});		 
	});
	
	//OSS文件上传变量定义
	var OSSObject = {
		"accessid":"",
		"policyBase64":"",
		"signature":"",
		"fileFullPathAndName":"",
		"callbackbody":""
	};
	//操作变量定义
	var useObject = {
		"host":"",
		"dir":"",
		"expire":0,
		"suffix":"",
		"fileUrl":"",
		"nowTime":Date.parse(new Date()) / 1000
	};
	//获取policy信息的方法
	var getPolicy = function() {
		var htmlobj = $.ajax({
			url : "/mortgageInfo/policy",
			async : false
		});
		return htmlobj.responseText;
	};
	//获取签名
	var getSignature = function() {
		// 可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
		useObject.nowTime =  Date.parse(new Date()) / 1000;
		if (useObject.expire < useObject.nowTime + 3) {
			var obj = eval("(" + getPolicy() + ")");
			OSSObject.policyBase64 = obj['policy'];
			OSSObject.accessid = obj['accessid'];
			OSSObject.signature = obj['signature'];
			OSSObject.callbackbody = obj['callback'];
			useObject.host = obj['host'];
			useObject.expire = parseInt(obj['expire']);
			useObject.dir = obj['dir'];
			return true;
		}
		return false;
	};
	//获取文件后缀名
	var getSuffix = function(filename) {
		var pos = filename.lastIndexOf('.');
		if (pos != -1) {
			useObject.suffix = filename.substring(pos);
		}
	};
	//获取UUID
	var getUUID = function(len, radix) {
		var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
		var uuid = [], i;
		radix = radix || chars.length;
		if (len) {
			for (i = 0; i < len; i++)
				uuid[i] = chars[0 | Math.random() * radix];
		} else {
			var r;
			uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
			uuid[14] = '4';
			for (i = 0; i < 36; i++) {
				if (!uuid[i]) {
					r = 0 | Math.random() * 16;
					uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
				}
			}
		}
		return uuid.join('');
	};
	//生成文件名
	function createFileName(filename) {
		getSuffix(filename);
		var fileDate = new Date().Format("yyyyMMdd");
		OSSObject.fileFullPathAndName = useObject.dir+"/"+ + 11 + fileDate + getUUID(8, 16) + useObject.suffix;
	}
	//文件上传
	$("#uploadImg").on("change", function() {
		var $this = $(this),
			file = $this[0].files[0],
			fileSize = file.size / 1024;
		if(fileSize > 10240){
			alert("单个文件不能大于10M", false);
			return false;
		}
		getSignature();
		createFileName(file.name);
		var str=useObject.suffix.toUpperCase();
		if(str!=".JPG"&&str!=".JPEG"&&str!=".PNG"&&str!=".GIF"){
			alert("不支持非jpg,png,gif格式的文件");
			$this.val("");
			return false;
		}
		//上传文件到OSS服务器
		var request = new FormData();
		request.append("OSSAccessKeyId", OSSObject.accessid);
		request.append("policy", OSSObject.policyBase64);
		request.append("Signature", OSSObject.signature);
		request.append("key", OSSObject.fileFullPathAndName);
		request.append("success_action_status", '200');// 让服务端返回200,不然，默认会返回204
		request.append('file', file);
		request.append("callback", OSSObject.callbackbody);
		$.ajax({
			url : useObject.host,
			data : request,
			processData : false,
			cache : false,
			async : false,
			contentType : false,
			type : 'post',
			success : function(data, textStatus, request) {
				useObject.fileUrl = useObject.host + "/" + OSSObject.fileFullPathAndName;
				$("#showImg").attr("src",useObject.fileUrl);
				$("#pathurl").val(useObject.fileUrl);
			},error : function(returndata) {
				alert("上传文件出错");
			}
		});
	});
})