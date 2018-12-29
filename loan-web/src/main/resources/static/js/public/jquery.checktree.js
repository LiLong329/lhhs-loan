(function($) {
	$.fn.checkTree = function() {
	    var $tree = this;
	    $tree.find("li")
	        .find("ul").hide()
	        .end()
	        
	        .find(":checkbox")
	            .prop("checked", false)
	            .hide()
	        .end()
	        
	        .find("label")
	            .click(function() {
	            	$(this).siblings(".arrow").click();
	            })
	            .hover(
	                function() { 
	                    $(this).addClass("hover");
	                },
	                function() {
	                    $(this).removeClass("hover");
	                }
	            )
	        .end()
	        
	        .each(function() {
	            var $arrow = $('<div class="arrow"></div>');
	            
	            if ($(this).is(":has(ul)")) {
	                $arrow.addClass("collapsed"); 
	                $arrow.click(function() {
	                    $(this).siblings("ul").toggle();
	                    if ($(this).hasClass("collapsed")) {
	                        $(this).addClass("expanded").removeClass("collapsed");
	                    }else {
	                        $(this).addClass("collapsed").removeClass("expanded");
	                    }
	                });
	            }
	            if($(this).children(":checkbox").length>0){
	            	var $checkbox = $('<div class="checkbox"></div>');
		            $checkbox.click(function() {
		                if(!$(this).hasClass("checked")) {
		                	if($tree.attr("data-singe")&&$.trim($tree.attr("data-singe"))=="true"){
		                		$tree.find(".checkbox").filter(".checked").removeClass("checked")
		                		.siblings(":checkbox").prop("checked",false);
		                	}
		                	$(this).addClass("checked").siblings(":checkbox").prop("checked",true);
		                	var $temp=$(this).parents("ul");
		                	$temp.each(function(i,n){
		                		if($(n).hasClass("treeCheck")){
		                			return false;
		                		}else{
		                			$(n).siblings(".checkbox").addClass("checked")
		                			.siblings(":checkbox").prop("checked",true);
		                		}
		                	});
		                }else {
		                	$(this).removeClass("checked").siblings(":checkbox").prop("checked",false);
		                	$(this).siblings("ul").find(".checkbox").filter(".checked")
		                	.removeClass("checked").siblings(":checkbox").prop("checked", false);
		                }
		            });
		            $(this).prepend($checkbox);
	            }
	            $(this).prepend($arrow);
	        });
	    return $tree;
	};
})(jQuery);