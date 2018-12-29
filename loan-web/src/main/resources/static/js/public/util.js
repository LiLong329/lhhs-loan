(function($){
    /*
     * 下拉打开弹窗
     * */
    $.fn.downModel=function(){
        var $_this = this,
            winWidth = $(window).width(),
            winHeight = $(window).height(),
            $_this_width = $_this.outerWidth(false),
            $_this_height = $_this.outerHeight(false);
        $_this.css({
            top:0,
            opacity:0
        }).show().animate({
            top:winHeight/2-$_this_height/2,
            opacity:1
        },500);
        return $_this
    };
    /*
     * 向上隐藏弹窗
     * */
    $.fn.upModel=function(){
        var $_this = this,
            winWidth = $(window).width(),
            winHeight = $(window).height(),
            $_this_width = $_this.outerWidth(false),
            $_this_height = $_this.outerHeight(false);
        $_this.animate({
            top:-$_this_height,
            opacity:0
        },500);
        return $_this
    };
    /*
     * 设置弹窗垂直居中
     * */
    $.fn.refresh=function(){
        var $_this = this,
            winWidth = $(window).width(),
            winHeight = $(window).height(),
            $_this_width = $_this.outerWidth(false),
            $_this_height = $_this.outerHeight(false);
        $_this.css({
        	top:winHeight/2-$_this_height/2
        });
        return $_this
    };
    /*
     * 组装序列化表单
     * */
    $.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        //var str=this.serialize();
        var obj={};
        $(array).each(function(i,n){
        	if(this.name.indexOf("_sub_")>0){
        		if(this.value){
        			var temp=this.name.split("_");
            		if(obj[temp[0]]){
            			obj[temp[0]][parseInt(temp[2])-1]=this.value;
            		}else{
            			obj[temp[0]]=[];
            			obj[temp[0]][parseInt(temp[2])-1]=this.value;
            		}
        		}
        		delete array[i];
        	}
        });
        for(var singe in obj){
        	serializeObj[singe]=$.trim(obj[singe].join("-"));
        }
        $(array).each(function(){
        	if(this.name+""){
        		if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push($.trim(this.value));  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],$.trim(this.value)];  
                    }  
                }else{  
                    serializeObj[this.name]=$.trim(this.value);   
                }
        	}
        }); 
        return serializeObj;
    };
    /*
     * 组装序列化非表单元素
     * */
    $.fn.getOtherJson=function(){  
        var serializeObj={},
        	serializeStr=$(this).find("input,select").serialize(),
        	serializeStr= decodeURIComponent(serializeStr,true);
        	array=serializeStr.split("&");
        $(array).each(function(){
        	var temp=this.split("=");
        	if(temp[0]){
        		if(serializeObj[temp[0]]){  
                    if($.isArray(serializeObj[temp[0]])){  
                        serializeObj[temp[0]].push($.trim(temp[1]));  
                    }else{  
                        serializeObj[temp[0]]=[serializeObj[temp[0]],$.trim(temp[1])];  
                    }  
                }else{  
                    serializeObj[temp[0]]=$.trim(temp[1]);   
                }
        	}
        });
        return serializeObj;
    };
   
    /*
     * 获取手机验证码倒计时
     * */
    $.fn.getMobileVerFormat=function(){
        var $_this = this,
            timeNum=60,
            tempHtml=$_this.html(),
            backColor=$_this.css("background");
        $_this.html(timeNum+"s重新发送");
        $_this.css("background","#B0A3A3");
        var timer=setInterval(function(){
            if(timeNum<=1){
                clearInterval(timer);
                $_this.html(tempHtml);
                $_this.css("background",backColor);
                $_this.attr("data-available",true);
            }else{
                $_this.css("background","#B0A3A3");
                --timeNum;
                $_this.html(timeNum+"s重新发送");
            }
        }, 1000);
    };
    /*
     * dom的int类型转换
     * */
    $.fn.domValueToInt=function(){
        var $_this = this,
            temp=parseInt($.trim($_this.val()));
        if(temp){
            $_this.val(temp);
        }else{
            $_this.val("");
        }
    };
    /*
     * dom的float类型转换
     * */
    $.fn.domValueToFloat=function(){
        var $_this = this,
            temp=$.trim($_this.val());
        if(!temp.match("^[0-9]+([.][0-9]{0,2}){0,1}$")){
            temp=parseFloat(temp);
            if(temp.toString().indexOf('.')>-1){
                temp=temp.toString().substring(0, temp.toString().indexOf('.')+3);
            }
        }
        if(temp){
            $_this.val(temp);
        }else{
            $_this.val("");
        }
    };
    /*
     * 判断手机浏览器
     * */
    $.fn.browser=function(){
    	var rwebkit = /(webkit)\/([\w.]+)/,  
        	ropera = /(opera)(?:.*version)?[ \/]([\w.]+)/,  
        	rmsie = /(msie) ([\w.]+)/,  
        	rmozilla = /(mozilla)(?:.*? rv:([\w.]+))?/,      
        	browser = {},  
        	ua = window.navigator.userAgent,  
        	browserMatch = (function(){
        		ua = ua.toLowerCase();
        		var match = rwebkit.exec(ua) || ropera.exec(ua) || rmsie.exec(ua) || ua.indexOf("compatible") < 0 && rmozilla.exec(ua) || [];
        		return {  
    	            browser : match[1] || "",  
    	            version : match[2] || "0"  
    	        };
        	})();
        if (browserMatch.browser) {  
            browser[browserMatch.browser] = true;  
            browser.version = browserMatch.version;  
        }  
        return browser;
    };
})(jQuery);
/*
 * 时间格式化--------例如：yyyy-MM-dd HH:mm:ss
 * */
Date.prototype.Format=function(fmt){
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
/*
 * 对象类型
 * */
function getType(v){
	return Object.prototype.toString.call(v);
};
/*
 * cookie的操作
 * */
cookie={
    get:function(key){
        return decodeURI(($.cookie(key) || ""),"utf-8");
    },
    set:function(key,val,day){
        if(null!=val){ val=encodeURI(val); }
        if(day){
            $.cookie(key,val,{expires:day,path:"/"});
        }else{
            $.cookie(key,val,{path:"/"});
        }
    },
    del : function(key){
        $.cookie(key,null,{path:"/"});
    }
};
/*
 * sessionStorage的操作
 * */
session={
    get:function(key){
        return sessionStorage.getItem(key);
    },
    set:function(key,val){
        return sessionStorage.setItem(key,val);
    },
    del : function(key){
        return sessionStorage.removeItem(key);
    }
};
/*
 * 手机浏览器类型判断
 * */
function isMobile(){   //是否为手机访问
    var ua = navigator.userAgent;
    var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
        isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
        isAndroid = ua.match(/(Android)\s+([\d.]+)/),
        isMobile = isIphone || isAndroid;
    return isMobile;
};
function isWeiXin(){    //是否为微信访问
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
};
function isAndroid(){    //是否为安卓访问
    var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    return isAndroid;
};
function isIphone(){     //是否为苹果访问
    var ua = navigator.userAgent;
    var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
        isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/);
    return isIphone;
};
function isIpad(){      //是否为ipad访问
    var ua = navigator.userAgent;
    var ipad = ua.match(/(iPad).*OS\s([\d_]+)/);
    return ipad;
}