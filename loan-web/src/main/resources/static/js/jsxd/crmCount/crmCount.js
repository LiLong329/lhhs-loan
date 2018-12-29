$(function(){
   var $ur_a = $(".paginItem a");
	$ur_a.on("click",function(){
		var currentPageNo = $(this).attr("data-pagenum");			
		searcher(currentPageNo);
	});		
	function searcher(currentPageNo){
		$("#currentPageNo").val(currentPageNo);
		$("#myForm").attr('action',"/crmIntentUserCount/getcrmIntentUserCountList");
	    $("#myForm").submit();
    };
    $("#crmCountSearch").on("click",function(){						
		var currentPageNo = 1;			
		searcher(currentPageNo);
	});
  
  //导出
	$("#export").on("click",function(){
		$("#myForm").attr("action","/crmIntentUserCount/intentLoanUserExport");
		$("#myForm").submit();
		$("#myForm").attr("action","");
	});
	
    //初始化图表
    initEchartDate("月");
    
    $("#echartsSelect").on("change",function(){
    	var selectText= $('#echartsSelect option:selected').text();//选中的文本
    	initEchartDate(selectText);
    });
    
	
});

function timeChange(){
	var $this=$(this),
		name=$this.attr("name"),
		value=$this.val();
	$("#"+name).val(value);
}


function initEchartDate(selectText){
	  var myChart = echarts.init(document.getElementById('showChart'));
	  myChart.clear();
	    lineOption = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['客户量','约见量','签约量']
			    },
			    toolbox: {
			        show : true,
			        padding:25,
			        feature : {
			            magicType: {show: true, type: ['line', 'bar']},
			        }
			    },
			    calculable : true,
			    grid:{
                    x:100,
                    x2:100,
                    borderWidth:1
                },
			    xAxis : [
			        {
			            type : 'category',
			            data : []
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			          
			        }
			    ],
			    series : [
				              {
				            	   name: '客户量',
				                   type: 'bar',
				                   itemStyle:{
	                                    normal:{
	                                        color:'#436EEE'
	                                    }
	                                },
				                   data: []
				              },
				              {
				            	   name: '约见量',
				                   type: 'bar',
				                   itemStyle:{
	                                    normal:{
	                                        color:'#CD00CD'
	                                    }
	                                },
				                   data: []
				              },
				              {
				            	   name: '签约量',
				                   type: 'bar',
				                   itemStyle:{
	                                    normal:{
	                                        color:'#EEEE00'
	                                    }
	                                },
				                   data: []
				              }
			              ]
			};
	    
	    
	$.ajax({
		type: "POST",
		url:"/crmIntentUserCount/getCrmEchartsList",
		processData:true,
		data:{"timeUnit":selectText},
		success: function(data){
		 if(data.retCode=="00"){
			 var dateArray = []; 
			 var totalIntentCountList=[];
			 var totalMianshenCountList=[];
			 var totalDealCountList=[];
			 if("月"==selectText){
	    		var myDate = new Date((new Date()).Format("yyyy-MM")); //获取今天日期
	    		var nowDate = myDate.getFullYear()+"-"+("0" + (myDate.getMonth() + 1)).slice(-2);
	    		myDate.setMonth(myDate.getMonth()-11);
	    		var dateTemp; 
	    		var flag = 1; 
	    		for (var i = 0; i < 11; i++) {
	    			dateTemp = myDate.getFullYear()+"-"+("0" + (myDate.getMonth() + 1)).slice(-2);
	    		    dateArray.push(dateTemp);
	    		    myDate.setMonth(myDate.getMonth()+flag);
	    		}
	    		dateArray.push(nowDate);
	    		getNewData(dateArray,data,"月",totalDealCountList,totalIntentCountList,totalMianshenCountList);
	    	}
	    	if("周"==selectText){
		    	  dateArray =['第一周','第二周','第三周','第四周'];
		    	  getNewData(dateArray,data,"周",totalDealCountList,totalIntentCountList,totalMianshenCountList);
		    	  
	    	}
	    	 if("日"==selectText){
		    		var myDate = new Date(); //获取今天日期   
		    		var nowDate = myDate.getFullYear()+"-"+("0" + (myDate.getMonth() + 1)).slice(-2)+"-"+("0" + (myDate.getDate())).slice(-2);
		    		myDate.setDate(myDate.getDate() - 6);
		    		var dateTemp; 
		    		var flag = 1; 
		    		for (var i = 0; i < 6; i++) {
		    		    dateTemp = myDate.getFullYear()+"-"+("0" + (myDate.getMonth() + 1)).slice(-2)+"-"+("0" + (myDate.getDate())).slice(-2);
		    		    dateArray.push(dateTemp);
		    		    myDate.setDate(myDate.getDate() + flag);
		    		}
		    		dateArray.push(nowDate);
		    		getNewData(dateArray,data,"日",totalDealCountList,totalIntentCountList,totalMianshenCountList);
		    	}
	 
	    	 console.log(dateArray);
	    	 myChart.setOption(lineOption);
	    		console.log("totalIntentCountList"+totalIntentCountList);
	    		console.log("totalMianshenCountList"+totalMianshenCountList);
	    		console.log("totalDealCountList"+totalDealCountList);
	    		  myChart.hideLoading();    //隐藏加载动画
	              myChart.setOption({        //加载数据图表
	                  xAxis: {
	                      data: dateArray
	                  },
	                  series: [{
	    
	                	  // 根据名字对应到相应的系列
	                      name: '客户量',  //显示在上部的标题
	                      data: totalIntentCountList
	                  },
	                  {
	                      // 根据名字对应到相应的系列
	                      name: '约见量',  //显示在上部的标题
	                      data: totalMianshenCountList
	                  },
	                  {
	                      // 根据名字对应到相应的系列
	                      name: '签约量',  //显示在上部的标题
	                      data: totalDealCountList
	                  }
	                  ]
	              });
		  }else{
			  alert(data.retMsg);
		  }
		}
		
	});
}





function getNewData(dateArray,data,selectType,totalDealCountList,totalIntentCountList,totalMianshenCountList){
	//签约量
	$(dateArray).each(function(index1,val1){
		var obj={};
		obj.xTime = val1;
		var tempData = 0;
		if(data.dealCountList && data.dealCountList.length>0){
			$(data.dealCountList).each(function(index2,val2){
				 if("周"==selectType){
					 if(val1==data.dealCountList[index2].time){
						 tempData = data.dealCountList[index2].totalDealCount;
						 return;
					 }
				 }else{
					 if(val1==data.dealCountList[index2].createTime){
						 tempData = data.dealCountList[index2].totalDealCount;
						 return;
					 }
				 }
			 });
		totalDealCountList.push(tempData);
	}else{
		totalDealCountList.push(0);
	}
});
	
	
	
 //客户量
 $(dateArray).each(function(index1,val1){
	 var obj={};
	 obj.xTime = val1;
	 var tempData = 0;
	 if(data.intentCountList && data.intentCountList.length>0){
		 $(data.intentCountList).each(function(index2,val2){
			 if("周"==selectType){
				 if(val1==data.intentCountList[index2].time){
					 tempData = data.intentCountList[index2].totalIntentCount;
					 return;
				 }
			 }else{
				 if(val1==data.intentCountList[index2].createTime){
					 tempData = data.intentCountList[index2].totalIntentCount;
					 return;
				 }
			 }
			 
		 });
		 totalIntentCountList.push(tempData);
	}else{
		totalIntentCountList.push(0);
	}
});
//约见量
 $(dateArray).each(function(index1,val1){
		 var obj={};
		 obj.xTime = val1;
		 var tempData = 0;
		 if(data.mianshenCountList && data.mianshenCountList.length>0){
			 $(data.mianshenCountList).each(function(index2,val2){
				 if("周"==selectType){
					 if(val1==data.mianshenCountList[index2].time){
						 tempData = data.mianshenCountList[index2].totalMianshenCount;
						 return;
					 }
				 }else{
					 if(val1==data.mianshenCountList[index2].createTime){
						 tempData = data.mianshenCountList[index2].totalMianshenCount;
						 return;
					 }
				 }
			 });
		 totalMianshenCountList.push(tempData);
	}else{
		totalMianshenCountList.push(0);
	}
});
	
	
}
