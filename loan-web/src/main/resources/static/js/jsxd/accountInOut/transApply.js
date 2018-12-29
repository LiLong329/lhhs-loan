$(function(){	
	 //绑定验证
    $('#transApply').validationEngine('attach');
    
    $('#btn_save').on('click',  function() {
    	if(!$('#transApply').validationEngine('validate')){
    		return;
    	}
    	
    	var val=$('input:radio[name="bankId"]:checked').val();
        if(val==null){
            alert("请选择公司支付账户!");
            return false;
        }
    	//岗位名称
    	var obj=$("#transApply").serializeJson();
    	
    	var str = '';
    	$.each($('#imglist a'), function(){
    		str += $(this).data('url')+ ",";
    	});
    	str = str.substring(0, str.length-1);
		$.ajax({
			url:"/accountInOut/insertTransApply",
			type: 'POST',
			data:{"accountInOut":JSON.stringify(obj),"imageUrl":str},
			async:false,
			dataType:"json",
			success:function(data){
				if(data.retCode=="00"){
					if(obj.transType=='1006'){
						alert(data.retMsg,false,function(){
							 window.location.href="/accountInOut/list";
							});
					}else{
						alert(data.retMsg,function(){
							 window.location.reload(true);
						});
					}
				  }else{
					alert(data.retMsg,function(){
					});
				  }
			}
		});
    });
    //查询客户信息
    $('#searchCustInfo').on('click',  function() {
    	$("#customerName").val("");
		$("#certificateNo").val("");
    	var obj=$("#transApply").serializeJson();
    	$.ajax({
    		url:"/accountInOut/queryCustomerInfo",
    		type: 'POST',
    		data:{"mobileOrOwnerId":obj.mobile,"customerType":obj.customerType,"transType":obj.transType},
    		async:true,
    		dataType:"json",
    		success:function(data){
    			if(data.retCode=="00"){
    				
    				$("#customerName").val(data.ownerName);
    				$("#certificateNo").val(data.certificateNo);
    				$("#accountId").val(data.accountId);
    				$("#customerId").val(data.customerId);
    				$("#bankCardNo").val(data.bankCardNo);
    				$("#accountHolder").val(data.accountHolder);
    				$("#bankName").val(data.bankName).change();
    				
    			}else{
    				alert(data.retMsg,function(){
    				});
    			}
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
	$("#uploadImgs").on("change",function() {
		var $this = $(this);
		var file = $this[0].files[0];
		var fileSize = file.size / 1024;
		if (fileSize > 4096) {
			alert("文件不能大于4M", false);
			$("#uploadImgs").val("");
			return false;
		}
		
		getSignature();
		createFileName(file.name);
		var str=useObject.suffix.toUpperCase();
		if(str!=".JPG"&&str!=".JPEG"&&str!=".GIF"&&str!=".PNG"){
			alert("不支持非jpg,png,Gif格式的文件",true);
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
					    var htmlstr = "<span>"+
					    "<a class='removeFile' data-url='"+useObject.fileUrl+"'></a>";
					    htmlstr += "<img src='"+useObject.fileUrl+"'/>";
						htmlstr += "</span>";
						$("#imglist").append(htmlstr);
						$("#uploadImgs").val("");
			},error : function(returndata) {
				alert("上传文件出错", false);
			}
		});
	});
	
	// 删除文件
	$("#imglist").on("click","a.removeFile", function() {
		var $this = $(this);
		confirm("确定删除该文件么？", function() {
			$this.closest("span").remove();
		});
	});
	
	
	var postQuery = function(obj){
		$.post("/accountInOut/ajaxQueryList", obj , function(data) {
			$("#depositApplyId").html(data);
		});
	};
	
	$("#queryId").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = $("#myForm").serializeJson();
		postQuery(obj);
	});
	
	// 分页查询
	$("#depositApplyId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			$("#myForm input[name='page.pageIndex']").val($(this).data("pagenum"));
			$("#myForm input[name='page.pageSize']").val($(this).data("pagesize"));
			var obj = $("#myForm").serializeJson();
			postQuery(obj);
		};
	});
	
	
	//导出
	$("#depositApplyId").on("click","#export",function(){
		$("#myForm").attr("action","/accountInOut/export");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});

});