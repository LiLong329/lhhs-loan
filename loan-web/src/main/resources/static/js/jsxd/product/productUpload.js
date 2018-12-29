accessid = ''
accesskey = ''
host = ''
policyBase64 = ''
signature = ''
callbackbody = ''
filename = ''
key = ''
expire = 0
g_object_name = ''
fileUrl = ''
now = timestamp = Date.parse(new Date()) / 1000; 

send_request1 = function() {
	var htmlobj = $.ajax({
		url : "/mortgageInfo/policy",
		async : false
	});
	return htmlobj.responseText;
};

function get_signature() {
	// 可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
	now = timestamp = Date.parse(new Date()) / 1000;
	if (expire < now + 3) {
		body = send_request1();
		console.log(body);
		var obj = eval("(" + body + ")");
		host = obj['host']
		policyBase64 = obj['policy']
		accessid = obj['accessid']
		signature = obj['signature']
		expire = parseInt(obj['expire'])
		callbackbody = obj['callback']
		key = obj['dir']
		return true;
	}
	return false;
};

function get_suffix(filename) {
	pos = filename.lastIndexOf('.')
	suffix = ''
	if (pos != -1) {
		suffix = filename.substring(pos)
	}
	return suffix;
}

function calculate_object_name(filename) {
	suffix = get_suffix(filename)
	var fileDate = new Date().Format("yyyyMMdd");
	g_object_name = key + "/" + 11 + fileDate + uuids(8, 16) + suffix;
	return ''
}

function uuids(len, radix) {
	var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
			.split('');
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
}
$("#stateUpload").on("click",function(){
	var $this=$(this),temp=$this.val();
	var str=null;
	var file=document.getElementById("uploadImgs").files[0];
		var fileSize = file.size/1024;
		if(fileSize>10240){
			alert("文件不能大于10M",false);
			return false;
		}
	    ret = get_signature();
		g_object_name = key;
		var subName = '';
		if (file != '') {
			suffix = get_suffix(file.name)
			subName = suffix;
			calculate_object_name(file.name)
		}
		subName = subName.toLocaleLowerCase();
		var filepath =/^.*\.(jpg|png)$/i;
		if(!filepath.test(subName)){
			alert("请选择正确的图片!",false);
			return false;
		}
		var request = new FormData();
		request.append("OSSAccessKeyId",accessid);
		request.append("policy",policyBase64);
		request.append("Signature",signature);
		request.append("key",g_object_name);
		request.append("success_action_status",'200');
		request.append('file', file);
		request.append("callback",callbackbody);
		 $.ajax({  
		        url : host,  
		        data : request,
		        processData: false,
	            cache: false,
	            async: false,
	            contentType: false,
	            type : 'post',
		        success : function(callbackHost,request) { 
		        	fileUrl = host+"/"+g_object_name;
		        	$("#pathurl").val(fileUrl);
					$("#showImg").attr("src",fileUrl);
					$("#showImg").attr("value",fileUrl);
					$("#showImg").css("height","40px");
		        },  
		        error : function(returndata) {  
		            alert("上传文件出错",false);  
		        }  
		    }); 
    });
