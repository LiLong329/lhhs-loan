$(function(){

	
	var $ur_a = $(".paginItem a");
	$ur_a.on("click",function(){
		var currentPageNo = $(this).attr("data-pagenum");			
		searcher(currentPageNo);
	});		
	function searcher(currentPageNo){
		 $("#currentPageNo").val(currentPageNo);
		$("#myForm").attr('action',"/orderInfo/getOrderCountList");
	    $("#myForm").submit();
    };
    
    
    $("#crmCountSearch").on("click",function(){						
		var currentPageNo = 1;			
		searcher(currentPageNo);
	});
  //导出
	$("#export").on("click",function(){
		$("#myForm").attr("action","/orderInfo/orderCountListExport");
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
			    toolbox: {
			        show : true,
			        padding:25,
			        feature : {
			            magicType: {show: true, type: ['line', 'bar']},
			        }
			    },
			    grid:{
                    x:100,
                    x2:100,
                    borderWidth:1
                },
			    legend: {
			        data:['成交量','报单量']
			    },
			    calculable : true,
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
				            	   name: '成交量',
				                   type: 'bar',
				                   itemStyle:{
	                                    normal:{
	                                        color:'#436EEE'
	                                    }
	                                },
				                   data: []
				              },
				              {
				            	   name: '报单量',
				                   type: 'bar',
				                   itemStyle:{
	                                    normal:{
	                                        color:'#EE9A49'
	                                    }
	                                },
				                   data: []
				              }
			              
			              ]
			};
	
	$.ajax({
		type: "POST",
		url:"/orderInfo/getOrderInfoEchartsList",
		processData:true,
		data:{"timeUnit":selectText},
		success: function(data){
		 if(data.retCode=="00"){
			 var dateArray = []; 
			 var totalOrderCountList=[];
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
	    		getNewData(dateArray,data,"月",totalDealCountList,totalOrderCountList);
	    	}
	    	if("周"==selectText){
		    	  dateArray =['第一周','第二周','第三周','第四周'];
		    	  getNewData(dateArray,data,"周",totalDealCountList,totalOrderCountList);
	    	}
	    	
	    	 if("日"==selectText){
		    		var myDate = new Date(); //获取今天日期   ("0" + (myDate.getDate)).slice(-2)
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
		    		getNewData(dateArray,data,"日",totalDealCountList,totalOrderCountList);
		    	}
	    	 
	    	 myChart.setOption(lineOption);
	    		  myChart.hideLoading();    //隐藏加载动画
	              myChart.setOption({        //加载数据图表
	                  xAxis: {
	                      data: dateArray
	                  },
	                  series: [{
	                      // 根据名字对应到相应的系列
	                      name: '成交量',  //显示在上部的标题
	                      data: totalDealCountList
	                  },
	                  {
	                      // 根据名字对应到相应的系列
	                      name: '报单量',  //显示在上部的标题
	                      data: totalOrderCountList
	                  }
	                  ]
	              });
		  }else{
			  alert(data.retMsg);
		  }
		}
		
	});
}
	

function getNewData(dateArray,data,selectType,totalDealCountList,totalOrderCountList){
	//成交量
	$(dateArray).each(function(index1,val1){
		var obj={};
		obj.xTime = val1;
		var tempData = 0;
		if(data.totalDealCountList && data.totalDealCountList.length>0){
			$(data.totalDealCountList).each(function(index2,val2){
				 if("周"==selectType){
					 if(val1==data.totalDealCountList[index2].time){
						 tempData = data.totalDealCountList[index2].totalDealCount;
						 return;
					 }
				 }else{
					 if(val1==data.totalDealCountList[index2].createTime){
						 tempData = data.totalDealCountList[index2].totalDealCount;
						 return;
					 }
				 }
			 });
			totalDealCountList.push(tempData);
	}else{
		totalDealCountList.push(0);
	}
});
	
	
	
 //报单量
 $(dateArray).each(function(index1,val1){
	 var obj={};
	 obj.xTime = val1;
	 var tempData = 0;
	 if(data.totalOrderCountList && data.totalOrderCountList.length>0){
		 $(data.totalOrderCountList).each(function(index2,val2){
			 if("周"==selectType){
				 if(val1==data.totalOrderCountList[index2].time){
					 tempData = data.totalOrderCountList[index2].totalOrderCount;
					 return;
				 }
			 }else{
				 if(val1==data.totalOrderCountList[index2].createTime){
					 tempData = data.totalOrderCountList[index2].totalOrderCount;
					 return;
				 }
			 }
			 
		 });
		 totalOrderCountList.push(tempData);
	}else{
		totalOrderCountList.push(0);
	}
});
}





	