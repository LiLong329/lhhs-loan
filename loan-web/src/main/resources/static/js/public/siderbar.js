$(function () {
	$('#barbg').off("click", ".title").on("click", ".title", function () {
		if(!loadComplete) return;
        var $this = $(this),
        	$ul = $this.next('ul');
        $('#barbg').find("dd .menuson").slideUp();
        if($ul.is(':visible')){
        	$ul.slideUp();
        }else{
        	$ul.slideDown();
        }
    });
	$("#barbg").off("click", ".menuson .header").on("click",".menuson .header",function () {
		if(!loadComplete) return;
		var $this = $(this),
			$parent = $this.parent();
		$('#barbg').find("dd .menuson>li.active").not($parent).removeClass("active open").find('.sub-menus').hide();
		$parent.addClass("active");
		if($this.next('.sub-menus').length>0){
            if($parent.hasClass("open")){
                $parent.removeClass("open").find('.sub-menus').hide();
            }else{
                $parent.addClass("open").find('.sub-menus').show();
            }
        }
    });
    $('#barbg').off("click", ".sub-menus li").on("click",".sub-menus li",function(){
    	if(!loadComplete) return;
    	$('#barbg').find(".sub-menus li.active").removeClass("active");
        $(this).addClass("active");
    });
    var loadComplete=true;
});