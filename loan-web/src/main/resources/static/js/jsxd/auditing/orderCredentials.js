$(function() {
	// 分页查询
	$("#tab4").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			var currentPageNo = $(this).data("pagenum");
			var pageSize = $(this).data("pagesize");
			$.post("/mortgageInfo/toOrderCredentials", {
				"currentPageNo" : currentPageNo,
				"pageSize" : pageSize,
				"orderNo" : orderNo,
				"productId" : productId,
				"isAgreement":isAgreement,
				"isAuditing":isAuditing
			}, function(data) {
				$("#tab4").html(data);
			});
		};
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
	$("#uploadImgs").on("change",function() {
		var $this = $(this);
		for(var i=0; i< $this[0].files.length; i++){
			var file = $this[0].files[i];
			var fileSize = file.size / 1024;
			if (fileSize > 10240) {
				alert("文件不能大于10M", false);
				$("#uploadImgs").val("");
				return false;
			}
			
			getSignature();
			createFileName(file.name);
			var str=useObject.suffix.toUpperCase();
			if(str!=".JPG"&&str!=".JPEG"&&str!=".GIF"&&str!=".PNG"&&str!=".PDF"&&str!=".DOC"&&str!=".DOCX"&&str!=".DOCM"&&str!=".XLS"&&str!=".XLSX"&&str!=".XLSM"){
				alert("不支持非pdf,doc,docx,docm,xls,xlsx,xlsm,jpg,png,Gif格式的文件",true);
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
					if(/image\/\w+/.test(file.type)&&(orderCredentialsEnglishName=="BorrowerIdCard"||orderCredentialsEnglishName=="CoborrowerIdCard"||orderCredentialsEnglishName=="GuarantorIdCard")){
						var reader = new FileReader();
						reader.onload = function(){
							var base64URL =  this.result;
							//保存URl到服务器
							$.post("/mortgageInfo/saveFileURL",{
								"orderCredentialsNo":orderCredentialsNo,
								"pathUrl":useObject.fileUrl,
								"fileExtension":useObject.suffix,
								"imgBase64Str":base64URL,
								"orderNo":orderNo
							},function(data){
								if(data.retCode=="00"){
									var htmlstr = "<li><span><a class='removeFile' data-id='"+data.urlId+"'></a>";
									if(data.fileExtension ==".xlsx" || data.fileExtension==".xlsm" || data.fileExtension==".xls"){
										htmlstr += "<a href='"+ data.credentialsUrl +"'>";
										htmlstr += "<img src='/img/excel.png'/>";
										htmlstr += "</a>";
									}else if(data.fileExtension ==".docx" || data.fileExtension ==".doc" || data.fileExtension==".docm"){
										htmlstr += "<a href='"+ data.credentialsUrl +"'>";
										htmlstr += "<img src='/img/word.png'/>";
										htmlstr += "</a>";
									}else if(data.fileExtension ==".pdf"){
										htmlstr += "<a href='"+ data.credentialsUrl +"'>";
										htmlstr += "<img src='/img/pdf.png'/>";
										htmlstr += "</a>";
									}else{
										htmlstr += "<img src='"+data.credentialsUrl+"'/>";
									}
									htmlstr += "</span></li>";
									$("ul.imglist").append(htmlstr);
									$("#uploadImgs").val("");
								}else{
									alert("文件上传失败！", false);
									$("#uploadImgs").val("");
								}
							});
						};
						reader.readAsDataURL(file);
					}else{
						//保存URl到服务器
						$.post("/mortgageInfo/saveFileURL",{
							"orderCredentialsNo":orderCredentialsNo,
							"pathUrl":useObject.fileUrl,
							"fileExtension":useObject.suffix,
							"imgBase64Str":""
						},function(data){
							if(data.retCode=="00"){
								var htmlstr = "<li><span><a class='removeFile' data-id='"+data.urlId+"'></a>";
								if(data.fileExtension ==".xlsx" || data.fileExtension==".xlsm" || data.fileExtension==".xls"){
									htmlstr += "<a href='"+ data.credentialsUrl +"'>";
									htmlstr += "<img src='/img/excel.png'/>";
									htmlstr += "</a>";
								}else if(data.fileExtension ==".docx" || data.fileExtension ==".doc" || data.fileExtension==".docm"){
									htmlstr += "<a href='"+ data.credentialsUrl +"'>";
									htmlstr += "<img src='/img/word.png'/>";
									htmlstr += "</a>";
								}else if(data.fileExtension ==".pdf"){
									htmlstr += "<a href='"+ data.credentialsUrl +"'>";
									htmlstr += "<img src='/img/pdf.png'/>";
									htmlstr += "</a>";
								}else{
									htmlstr += "<img src='"+data.credentialsUrl+"'/>";
								}
								htmlstr += "</span></li>";
								$("ul.imglist").append(htmlstr);
								$("#uploadImgs").val("");
							}else{
								alert("文件上传失败！", false);
								$("#uploadImgs").val("");
							}
						});
					}
				},error : function(returndata) {
					alert("上传文件出错", false);
				}
			});
		}
	});
	
	// 删除文件
	$("ul.imglist").on("click","a.removeFile", function() {
		var $this = $(this);
		var urlId = $this.data("id");
		var num = $("ul.imglist li").length;
		confirm("确定删除该文件么？", function() {
			$.post("/mortgageInfo/deleteFile/" + urlId + "/" + num, function(data) {
				if (data.retCode == "00") {
					alert(data.retMsg, false, function() {
						$this.closest("li").remove();
					});
				} else {
					alert(data.retMsg);
				}
			});
		});
	});
	
	//返回上一步
	$("#goBack").on("click",function(){
		if (isAgreement!=1) {
			session.set("tabNum","orderCredentials");
		}
		window.history.go(-1);
	});
	
	//打包下载
	$("#tab4").on("click","#downloadFile",function(){
		var $checkbox = $("#tab4").find("input:checkbox:checked");
		var CredentialsNoList = new Array();
		if($checkbox.length > 0){
			$checkbox.each(function(i,n){
				CredentialsNoList.push($(n).data("ordercredentialsno"));
			});
			$.post("/mortgageInfo/createFilesZip",{
				"CredentialsNos":JSON.stringify(CredentialsNoList),
				"bname":$("#tab2").find(":input[name='bname']").val()
			},function(data){
				if(data.retCode == "00"){
					window.location.href="/mortgageInfo/downloadZip?fileName="+data.fileName;
				}else{
					alert("文件打包失败");
				}
			});
		}else{
			alert("请勾选需要打包的资质清单");
		}
	});
	
	$("#tab4").on("click",".credentialDetail",function(){
		var orgId = $("#fundSide").val(),
			productNameNo=$("#productName").val();
		session.set("tab4-orgId",orgId);
		session.set("tab4-productId",productNameNo);
	});
});