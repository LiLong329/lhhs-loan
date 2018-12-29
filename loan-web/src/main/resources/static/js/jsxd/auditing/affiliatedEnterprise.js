$(function(){
	var enterpriseInfo = $("#enterpriseInfos ul:first").prop("outerHTML");//关联企业信息ul
	//虚线
	var line = '<div class="lines"><img class="del-zz" src="/img/loan_reduce.png"></div>';
	//添加关联企业信息
	$("#addEnterpriseInfo").on("click",function(){
		if($("#enterpriseInfos ul:first").css("display")=="none"){
			$("#enterpriseInfos ul:first").show();
		}else{
			var $newInfo = $("#enterpriseInfos").append(line+enterpriseInfo).children("ul:last");
			$newInfo.find("input").not("[type='button']").val("");
			$newInfo.find("select").val("");
			$newInfo.find("textarea").html("");
			$newInfo.find(".uew-select-text").html("请选择");
			$newInfo.find(".citySelect").html("<option value=''>请选择</option>");
			$newInfo.find(".imglist").html("");
			$newInfo.show();
		}
	});
	//删除关联企业信息
	$("body").on("click",".del-zz",function(){
		$(this).closest(".lines").next("ul").remove();
		$(this).closest(".lines").remove();	
	});
	//关联企业信息保存
	$("#enterpriseInfoSave").on("click",function(){
		if(!$("#enterpriseInfos form:visible").validationEngine("validate")){
			return false;
		}
		var enterpriseList = new Array(),
			enterpriseForm = $("#enterpriseInfos form:visible");
		for(var i=0;i<enterpriseForm.length;i++){
			var temp=$(enterpriseForm[i]).serializeJson();
			temp.orderNo=orderNo;
			if($(enterpriseForm[i]).find(".imglist li").length>0){
				var idList=[];
				$(enterpriseForm[i]).find(".imglist li").each(function(index,node){
					idList.push($(node).data("id"));
				});
				temp.urlIdList=idList.join(",");
			}
			enterpriseList.push(temp);
		}
		if(enterpriseList.length>0){
			$.post("/auditProcess/saveAffiliatedEnterprise",{
				"enterpriseList":JSON.stringify(enterpriseList),
				"orderNo":orderNo
			},function(data){
				if(data.retCode == "00"){
					alert(data.retMsg);
				}else{
					alert(data.retMsg);
				}
			});
		}else{
			alert("没有信息可以保存");
		}
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
	$("#tab6").on("change",".uploadImgs",function() {
		var $this = $(this);
		for(var i=0; i< $this[0].files.length; i++){
			var file = $this[0].files[i];
			var fileSize = file.size / 1024;
			if (fileSize > 10240) {
				alert("单个文件不能大于10M", false);
				return false;
			}
			getSignature();
			createFileName(file.name);
			var str=useObject.suffix.toUpperCase();
			if(str!=".JPG"&&str!=".JPEG"&&str!=".PNG"&&str!=".GIF"){
				alert("不支持非jpg,png,gif格式的文件",true);
				$("#uploadImgs").val("");
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
					//保存URl到服务器
					$.post("/auditProcess/saveEnterpriseUrl",{
						"pathUrl":useObject.fileUrl,
						"fileExtension":useObject.suffix
					},function(data){
						if(data.retCode=="00"){
							var htmlstr = "<li data-id='"+data.id+"'><span><a class='removeFile' data-id='"+data.id+"'></a>";
							htmlstr += "<img src='"+useObject.fileUrl+"'/>";
							htmlstr += "</span></li>";
							$this.closest("p").prev("ul.imglist").append(htmlstr);
						}else{
							alert("文件上传失败！", false);
							$this.val("");
						}
					});
				},error : function(returndata) {
					alert("上传文件出错", false);
				}
			});
		}
	});
	// 删除文件
	$("ul.imglist").on("click","a.removeFile", function() {
		var $this = $(this);
		var id = $this.data("id");
		confirm("确定删除该文件么？", function() {
			$.post("/auditProcess/deleteEnterpriseUrl",{
				id:id
			}, function(data) {
				if (data.retCode == "00") {
					$this.closest("li").remove();
				} else {
					alert(data.retMsg);
				}
			});
		});
	});
});