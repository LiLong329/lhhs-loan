$(function(){
    //绑定验证
    $('#role').validationEngine('attach');
    //初始化
	init();
	//构建权限树
	function init(){
		if(authList){
			$('#authList').jstree({
				'core':{
					'data':authList
				},
				'plugins':['checkbox']
			});
		}
	};
	//修改角色
    $('#btn_save').on('click',  function() {
    	if(!$('#role').validationEngine('validate')){
    		return false;
    	}
    	var ref=$('#authList').jstree(true),
			ids1=ref.get_selected(),//选中的
	    	ids2=ref.get_undetermined();//未确定的
	    if(ids1.length<=0){
	    	alert("请选择角色权限");
	    	return false;
	    }
	    var obj=$("#role").serializeJson();
	    	authorityIds=[];
	    for(var i=0; i<ids1.length; i++){
	    	var temp=ids1[i].replace("auth_","");
	    	authorityIds.push(temp);
		}
	    for(var i=0; i<ids2.length; i++){
	    	var temp=ids2[i].replace("auth_","");
	    	authorityIds.push(temp);
	    }
	    obj.authorityIds=authorityIds.join(",");
    	$.post("/systemManager/roleAdd", obj, function(data){
    		if(data.retCode=="00"){
				 alert("新增角色成功",function(){
					 window.location.href="/systemManager/getRoles";
				 });
    		}else{
				alert(data.retMsg);
			}
		});
    });
});