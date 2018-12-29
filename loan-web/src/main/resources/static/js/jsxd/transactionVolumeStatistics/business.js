$(function(){
	loadData();
	var postQuery = function(obj){
		$.post("/transactionVolumeStatistics/ajaxQueryList", obj , function(data) {
			  $("#companyId").html(data);
		});
	};
	
	$(".searcher").on("click",function(){
		$("#myForm input[name='page.pageIndex']").val("1");
		var obj = "";
        if($(this).data('id')=='1' || $(this).data('id')=='11'){
        	$("#myform1 input[name='page.pageIndex']").val("1");
        	obj=$("#myform1").serializeJson();
        	obj.statisticsName=1;
//        	if($("select[name='companyName']").val()){
//        		obj.companyName=$.trim($("select[name='companyName'] option:selected").html());
//        	}
        	 $('#myform1').show();
    		 $('#myform2').hide();
    		 $('#myform3').hide();
    		 $('#fileName').text("统计维度：事业部");
		}
        if($(this).data('id')=='2' || $(this).data('id')=='22'){
        	$("#myform2 input[name='page.pageIndex']").val("1");
        	obj=$("#myform2").serializeJson();
        	obj.statisticsName=2;
        	 $('#myform1').hide();
    		 $('#myform2').show();
    		 $('#myform3').hide();
    		 $('#fileName').text("统计维度：借款人");
		}
        if($(this).data('id')=='3' || $(this).data('id')=='33'){
        	$("#myform3 input[name='page.pageIndex']").val("1");
        	obj=$("#myform3").serializeJson();
        	obj.statisticsName=3;
        	 $('#myform1').hide();
    		 $('#myform2').hide();
    		 $('#myform3').show();
    		 $('#fileName').text("统计维度：放款人");
		}
		postQuery(obj);
	});
	
	// 成交额统计分页查询
	$("#companyId").on("click", ".pagin ul li a", function() {
		if (!$(this).parent("li").hasClass("current")) {
			var type= $('#fileName').text();//当前的统计维度
			var obj = "";
			if(type.indexOf("事业部")>0){
				$("#myform1 input[name='page.pageIndex']").val($(this).data("pagenum"));
				$("#myform1 input[name='page.pageSize']").val($(this).data("pagesize"));
				obj=$("#myform1").serializeJson();
				obj.statisticsName=1;
			}
			if(type.indexOf("借款人")>0){
				$("#myform2 input[name='page.pageIndex']").val($(this).data("pagenum"));
				$("#myform2 input[name='page.pageSize']").val($(this).data("pagesize"));
				obj=$("#myform2").serializeJson();
				obj.statisticsName=2;
			}
			if(type.indexOf("放款人")>0){
				$("#myform3 input[name='page.pageIndex']").val($(this).data("pagenum"));
				$("#myform3 input[name='page.pageSize']").val($(this).data("pagesize"));
				obj=$("#myform3").serializeJson();
				obj.statisticsName=3;
			}
			
			postQuery(obj);
		};
	});
	

     
    function reportExport(){
    	$("#myForm1").attr("action","/transactionVolumeStatistics/export");
		$("#myForm1").submit();
		$("#myForm1").attr("action","");
    }
    
	
	
  //导出事业部
	$("#export1").on("click",function(){
		$("#myform1").attr("action","/transactionVolumeStatistics/export");
		$("#myform1").submit();
		$("#myform1").attr("action","");
	});
	 //导出借款人
	$("#export2").on("click",function(){
		$("#myform2").attr("action","/transactionVolumeStatistics/export");
		$("#myform2").submit();
		$("#myform2").attr("action","");
	});
	 //导出放款人
	$("#export3").on("click",function(){
		$("#myform3").attr("action","/transactionVolumeStatistics/export");
		$("#myform3").submit();
		$("#myform3").attr("action","");
	});
	/**
	 * chart图表
	 */
	//柱状图onchange事件
    $("#type").on("change",function(){
    	loadData();
	 });

//加载图表数据
	function loadData(){
		 $.get("/transactionVolumeStatistics/queryStatisticsByType?type="+$('#type').val(), function(result){
	   		 var x=result[0].keyArray;
	   		 var y=result[0].valueArray;
	   		initEchartDate(x,y);
	   	    });
	}

	
	function initEchartDate(x,y){
		  var myChart = echarts.init(document.getElementById('showChart'));
		  myChart.clear();
		    lineOption = {
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['成交额']
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
				            data : x
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            data : y
				        }
				    ],
				    series : [
					              {
					            	   name: '成交额（元）',
					                   type: 'bar',
					                   itemStyle:{
		                                    normal:{
		                                        color:'#436EEE'
		                                    }
		                                },
					                   data: y
					              }
				              ]
				};
		    	 myChart.setOption(lineOption);
		    		  myChart.hideLoading();    //隐藏加载动画
		              myChart.setOption({        //加载数据图表
		                  xAxis: {
		                      data: x
		                  },
		                  series: [{
		                	  // 根据名字对应到相应的系列
		                      name: '成交额'  //显示在上部的标题
//		                      data: totalIntentCountList
		                  }
		                  ]
		              });
	}
	
	
});

