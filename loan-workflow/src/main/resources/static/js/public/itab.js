/**
 * Created by chen on 2017/3/30.
 */
$(function(){
    if($(".itab").length>0&&$(".itab a").length>0){
        var $this,isShow;
        if($(".itab a.selected").length>0){
            $this=$(".itab a.selected");
        }else{
            $this=$($(".itab a").get(0));
            $this.addClass("selected");
        }
        isShow=$this.data("show");
        $(".tabson").removeClass("selected");
        $(isShow).addClass("selected");
        $(".itab a").on("click",function(){
            var $_this=$(this);
            if($_this.hasClass("selected")) return;
            $(".itab a").removeClass("selected");
            $_this.addClass("selected");
            var temp=$_this.data("show");
            $(".tabson").removeClass("selected");
            $(temp).addClass("selected");
        });
    }
});