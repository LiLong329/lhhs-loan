$(function() {
	var flag=false;
	var $input=null;
	var groupId=$.trim($("#groupId").val())||"";
	var updateType=$.trim($("#updateType").val())||"";
	var companyList = [];
	//名称验证
	$("#groupName").on("change",function(){
		 var groupName = $("#groupName").val();
		 $.ajax({
			 type:"POST",
			 data:{'groupName':groupName,'groupId':groupId},
		     url:'/systemManager/authorityGroupValidate',
		     success:function(data){
		    	if(!data){
		    		flag=false;
		    		alert("数据组名称已存在");
		    	}else{
		    		flag=true;
		    	}
		     }
		 });
	});
	//根据集团异步查询分公司
	$("#companyId").on("change",function(){
		var companyId = $.trim($(this).val());
		if(companyId&&companyId!="unionALL"){
			$.post("/systemManager/companyLink",{
				"companyId":companyId
			},function(data){
				var html="<option value='companyAll'>全部</option>";
				for(var i=0;i<data.companyList.length;i++){
					html+=("<option class='company' value='"+data.companyList[i].companyId+"'>"+data.companyList[i].companyName+"</option>");
				}
				$("#company").html(html);
			});
		}else{
			$("#company").html("<option value='companyAll'>全部</option>");
		}
	    $("#company").prev(".uew-select-value").find(".uew-select-text").html("全部");
	});
	//数据范围
	$("#dataZone").on("change", function() {
		var selectText = $(this).val();//选中的数据范围值
		var tempHtml = '';
		if(selectText){
			if(companyList.length==0){
				getCompanyDeptTree();
			}
			if ("6" == selectText) {//通用权限组
				$input=null;
				tempHtml = treeCompanyList(companyList);
				$("#treeId").html(tempHtml);
			}
			if ("7" == selectText) {//菜单权限组
				tempHtml = treeAuthorityList(allLastAuthyList);
				$("#treeId").html(tempHtml);
			}
			$("#treeCheckTip").hide();
			$("#treeId").checkTree();
		}else{
			$("#treeId").html("");
			$("#treeCheckTip").show();
		}
	});
	//集团公司
	$("#companyId,#company").on("change", function() {
		companyList=[];
		getCompanyDeptTree();
		$("#dataZone").change();
	});
	//添加权限
	$("#treeId").on("click", ".addAuthority", function(){
		$input=$(this).next("input");
		var tempHtml = treeCompanyList(companyList);
		$("#preview #previewTree").html("");
		$("#preview #previewTree").html(tempHtml);
		$("#preview").show();
		$("#preview #previewTree").checkTree();
		if($input.val()){
			showData(JSON.parse($input.val()));//回显
		}
	});
	//清空
	$("#treeId").on("click", ".clearAuth", function(){
		$(this).prev(".authNum").html("").prev(".authoritys").val("");
		$(this).hide();
	});
	//添加权限-确定
	$("#preview_btn").on("click", function(){
		var list=getList($("#preview #previewTree").children("li"),6);
		if(list.length>0){
			$input.val(JSON.stringify(list));
			$input.next(".authNum").html("("+ list.length +"个)").next(".clearAuth").show();
		}else{
			$input.val("");
			$input.next(".authNum").html("").next(".clearAuth").hide();
		}
		$("#preview").hide();
	});
	//添加权限-取消
	$("#preview_not").on("click", function(){
		$("#preview").hide();
	});
	//查询公司部门树形
	function getCompanyDeptTree(){
		var unionId=$("#companyId").val(),
			companyId=$("#company").val();
		unionId=(unionId=="unionALL"?"":unionId);
		companyId=(companyId=="companyAll"?"":companyId);
		$.ajax({
			 type:"POST",
			 data:{'unionId':unionId,'companyId':companyId,"a":new Date()},
		     url:'/systemManager/getCompanyDeptTree',
		     async:false,
		     success:function(data){
		    	 companyList=data;
		     }
		 });
	};
	//页面数据回显
	function showSelectData(){
		$("#dataZone").change();
		if(selectAutGupList&&selectAutGupList.length>0){
			if($.trim($("#dataZone").val())=="6"){
				showData(selectAutGupList);
			}else if($.trim($("#dataZone").val())=="7"){
				var obj={};
				for(var i = 0; i<selectAutGupList.length; i++){
					var authorityId=selectAutGupList[i].authorityId;
					if(authorityId){
						if(!obj[authorityId]){
							obj[authorityId]=[];
						}
						var sub={};
						sub.authorityId=authorityId;
						sub.deptId=selectAutGupList[i].deptId;
						sub.groupId=selectAutGupList[i].groupId;
						sub.orgId=selectAutGupList[i].orgId;
						obj[authorityId].push(sub);
					}
				}
				for(var key in obj) {
					$("#tree_authorityId_"+key).val(JSON.stringify(obj[key]));
					$("#tree_authorityId_"+key).next(".authNum").html("("+ obj[key].length +"个)").next(".clearAuth").show();
					var $temp=$("#tree_authorityId_"+key).closest("ul");
					if($temp.siblings(".arrow").hasClass("collapsed")){
						$temp.siblings(".arrow").click();
					}
					if(!$temp.siblings(".arrow").closest("ul").hasClass("treeCheck")
							&&$temp.siblings(".arrow").closest("ul").siblings(".arrow").hasClass("collapsed")){
						$temp.siblings(".arrow").closest("ul").siblings(".arrow").click();
					}
				}
			}
		}
	};
	//添加权限-数据回显
	function showData(list){
		for (var i = 0; i < list.length; i++) {
			var orgId=list[i].orgId||"",
			deptId=list[i].deptId||"";
			if(deptId){
				$("#tree_deptId_"+deptId).prev(".checkbox").click();
			}else if(orgId){
				$("#tree_orgId_"+orgId).prev(".checkbox").click();
			}
		}
	};
	//保存
	$('#btn_save').on('click',  function() {
		var loanAuthgroup={},
			loanAuthorityGroupList = [],
			selectText= $('#dataZone').val();//选中的文本
		if(!$('#myForm').validationEngine('validate')){
			return false;
		}
		if(!groupId&&!flag){
			alert("数据组名称已存在");
			return false;
		}
		loanAuthgroup.groupName=$("#groupName").val();
		loanAuthgroup.unionId=$("#companyId").val();
		loanAuthgroup.companyId=$("#company").val();
		loanAuthgroup.dataZone=selectText;
		if(groupId){
			loanAuthgroup.groupId=groupId;
		}
		loanAuthorityGroupList=getList($("#treeId").children("li"),selectText);
		if(loanAuthorityGroupList.length==0){
			alert("请选择数据权限");
			return false;
		}
		var loanAuthgroupStr=JSON.stringify(loanAuthgroup),
			loanAuthorityGroupListStr=JSON.stringify(loanAuthorityGroupList);
		var url=groupId?"/systemManager/authorityGroupUpdate":"/systemManager/authorityGroupInsert";
		$.post(url,{
			loanAuthorityGroupListStr:loanAuthorityGroupListStr,
			loanAuthgroupStr:loanAuthgroupStr
		},function(data){
			if(data.retCode=="00"){
				alert(data.retMsg,function(){
					window.location.href="/systemManager/authorityGroupList";
				});
			}else{
				alert(data.retMsg);
			}
		});
	});
	//修改页面
	if(updateType){
		if(updateType=="update"){
			showSelectData();
		}
		if(updateType=="detail"){
			showSelectData();
		}
	}
	
	//封装数据
	function getList($list, value){
		var tempList=[];
		if(value=="6"){
			if($list.children("input[type='checkbox']:checked").length>0){
				$list.children("input[type='checkbox']:checked").each(function(i,n){
					if($(n).siblings("ul").length>0&&$(n).siblings("ul").find("input[type='checkbox']:checked").length>0){
						var temp=getList($(n).siblings("ul").children("li"),value);
						tempList=listAdd(tempList,temp);
					}else{
						var obj={},
							name=$(n).attr("name"),
							val=$(n).val(),
							company=$.trim($(n).attr("data-company"));
						obj[name]=val;
						if(company){
							obj["orgId"]=company;
						}
						if($input){
							var authorityId=$input.attr("data-authority");
							obj["authorityId"]=authorityId;
						}
						if(groupId){
							obj["groupId"]=groupId;
						}
						tempList.push(obj);
					}
				});
			}
		}else if(value=="7"){
			$list.find(".authoritys").each(function(i,n){
				var val=$(n).val();
				if(val&&val!="[]"){
					var list=JSON.parse(val);
					tempList=listAdd(tempList,list);
				}
			});
		}
		return tempList;
	};
	//list追加
	function listAdd(list1,list2){
		if(list2.length>0){
			for (var i = 0; i < list2.length; i++) {
				list1.push(list2[i]);
			}
		}
		return list1;
	};
	//遍历拼装公司
	function treeCompanyList(list) {
		var tempHtml = '';
		if (list && list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				tempHtml += '<li>';
				tempHtml += ('<input type="checkbox" name="orgId" id="tree_orgId_'+ list[i].companyId +'" value="' + list[i].companyId + '"/>');
				tempHtml += ('<label>' + list[i].companyName + '</label>');
				if (list[i].deptList.length > 0) {
					tempHtml += '<ul>';
					tempHtml += treeDeptList(list[i].deptList);
					tempHtml += '</ul>';
				}
				tempHtml += '</li>';
			}
		}
		return tempHtml;
	};
	//遍历拼装部门
	function treeDeptList(list) {
		var tempHtml = '';
		if (list && list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				tempHtml += '<li>';
				tempHtml += ('<input type="checkbox" name="deptId" data-company="'+ list[i].companyId +'" id="tree_deptId_'+ list[i].deptId +'" value="' + list[i].deptId + '"/>');
				tempHtml += ('<label>' + list[i].name + '</label>');
				if (list[i].subDeptList.length > 0) {
					tempHtml += '<ul>';
					tempHtml += treeDeptList(list[i].subDeptList);
					tempHtml += '</ul>';
				}
				tempHtml += '</li>';
			}
		}
		return tempHtml;
	};
	//遍历拼装权限
	function treeAuthorityList(list) {
		var tempHtml = '';
		for (var i = 0; i < list.length; i++) {
			tempHtml += '<li>';
			tempHtml += ('<label data-authority="'+ list[i].authorityId +'">' + list[i].name + '</label>');
			if(list[i].dataFlag=="1"){
				if(updateType=="detail"){
					tempHtml += ('<a class="addAuthority" style="color:#00a4ac;cursor:pointer;text-decoration: underline;">查看权限</a>');
				}else{
					tempHtml += ('<a class="addAuthority" style="color:#00a4ac;cursor:pointer;text-decoration: underline;">添加权限</a>');
				}
				tempHtml += ('<input id="tree_authorityId_'+ list[i].authorityId +'" type="hidden" class="authoritys" data-authority="'+ list[i].authorityId +'"/>');
				tempHtml += ('<span class="authNum" style="color:red; display: inline; font-weight: bold;"></span>');
				if(updateType!="detail"){
					tempHtml += ('<a class="clearAuth" style="color:#00a4ac;cursor:pointer;text-decoration: underline;display:none;margin-left:10px;">清空</a>');
				}
			}
			if (list[i].authorityList.length > 0) {
				tempHtml += '<ul>';
				tempHtml += treeAuthorityList(list[i].authorityList);
				tempHtml += '</ul>';
			}
			tempHtml += '</li>';
		}
		return tempHtml;
	};
});