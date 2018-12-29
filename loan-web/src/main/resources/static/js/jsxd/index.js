$(function(){
	//初始化
	init();
	function init(){
		var $this=$($(".fastBtn").get(0)),
			obj={},
			queryType=$this.data("type"),
			lendingTime=$this.data("time"),
			compareType="single";
		obj.queryType=queryType;
		obj.lendingTime=lendingTime;
		obj.compareType=compareType;
		queryAjaxData(obj); 
	};
	//快捷查询
	$(".fastBtn").on("click",function(){
		var $this=$(this),
			obj={},
			queryType=$this.data("type"),
			lendingTime=$this.data("time"),
			compareType="single";
		obj.queryType=queryType;
		obj.lendingTime=lendingTime;
		obj.compareType=compareType;
		queryAjaxData(obj);
	});
	//自定义时间查询
	$("#search_btn").on("click",function(){
		var obj={},
			queryType="month",
			beginTime=$("#beginTime").val()||"",
			endTime=$("#endTime").val()||"",
			compareType="section";
		if(!beginTime){
			alert("请选择开始时间");
			return false;
		}
		if(!endTime){
			alert("请选择结束时间");
			return false;
		}
		obj.queryType=queryType;
		obj.beginTime=beginTime;
		obj.endTime=endTime;
		obj.compareType=compareType;
		queryAjaxData(obj);
	});
	//查询方法
	function queryAjaxData(obj){
		$.post("/index/queryTransAmount",obj,function(data){
			initEchartDate(data);
		})
	};
	//绘制图表
	var myChart = echarts.init(document.getElementById('myDataChart')),
		timeList=[],
		amountList=[],
		echartOption={
			tooltip : {
			    trigger: 'axis'
			},
			toolbox: {
			    show : true,
			    top:5,
			    right:40,
			    feature : {
			        magicType: {show: true, type: ['line', 'bar']},
			    }
			},
			grid:{
			    x:60,
			    x2:60,
			    y:40,
			    y2:40,
			    borderWidth:1
			},
			xAxis : [
			    {
			        type : 'category',
			        name : '日期',
			        data : []
			    }
			],
			yAxis : [
			    {
			        type : 'value',
			        name : '数量（万元）'
			    }
			],
			series : [{
						name: '数量（万元）',
						type: 'line',
						data: []
				}]
		};
	function initEchartDate(data){
		myChart.showLoading();
		myChart.clear();
		timeList=[];
		amountList=[];
		for(var i=0;i<data.length;i++){
			timeList.push(data[i].time);
			amountList.push(data[i].amount);
		}
		myChart.setOption(echartOption);
		//加载数据图表
		myChart.setOption({
            xAxis: {
                data: timeList
            },
            series: [{
                name: '数量（万元）',
                data: amountList
            }]
        });
		myChart.hideLoading();
	};
});