/**
 * Created by chen on 2017/3/30.
 */
$(function(){
    if($(".previewItab").length>0&&$(".previewItab a").length>0){
        var $this,isShow;
        if($(".previewItab a.selected").length>0){
            $this=$(".previewItab a.selected");
        }else{
            $this=$($(".previewItab a").get(0));
            $this.addClass("selected");
        }
        isShow=$this.data("show");
        $(".previewson").removeClass("selected");
        $(isShow).addClass("selected");
        $(".previewItab a").on("click",function(){
            var $_this=$(this);
            if($_this.hasClass("selected")) return;
            $(".previewItab a").removeClass("selected");
            $_this.addClass("selected");
            var temp=$_this.data("show");
            $(".previewson").removeClass("selected");
            $(temp).addClass("selected");
        });
    }
});