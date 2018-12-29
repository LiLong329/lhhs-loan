$(function(){
			/*****************获取客户性质**********************/
			  $.post("/transAccountsManager/getCustPropertyByTypeId",{
				  "bigCategory":''
			  },function(data){
				  var html="<option value=''>请选择</option>";
				  for(var i=0;i<data.custPropertyList.length;i++){
					  html+=("<option class='leGroupId' value='"+data.custPropertyList[i].typeId+"'>"+data.custPropertyList[i].typeName+"</option>");
				  }
				  $("#outAccountCustProperty").html("").html(html);
		    });
})