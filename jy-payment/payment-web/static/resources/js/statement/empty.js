/**
 * Created by zhuchong on 2016/10/11.
 */

$(function iniData(){

    var userNo = getUrlParameters().userNo;
    //判断是哪种缴费没有数据
    var type = getUrlParameters().type;

    var data = {};

    data.userNo = userNo;

    if(type == "01"){
        data.payName = "自来水缴费";
        data.imgName = "icon_water.png";
    }else if(type == "02"){
        data.payName = "远传水表充值";
        data.imgName = "icon_watermeter.png";
    }else if(type == "03"){
        data.payName = "天然气缴费";
        data.imgName = "icon_naturalgas.png";
    }

    itemInfo(data);

    //页面模板数据显示
    function itemInfo(data) {
        var text = $("#item").text();
        var template = Hogan.compile(text);
        var render = template.render(data);
        $("#item-list").html(render);
    }

    //返回点击事件
    $(".submit-btn").click(function(){
        returnEve();
    })
});

function returnEve(){
    var sou = getUrlParameters().source;
    var type = getUrlParameters().type;
    if(sou == "0"){
        if(type == "01"){
            window.location.href="../search/water.html?rands="+10000*Math.random();
            data.payName = "自来水缴费";
        }else if(type == "02"){
            window.location.href="../search/watermeter.html?rands="+10000*Math.random();
            data.payName = "远传水表充值";
        }else if(type == "03"){
            window.location.href="../search/naturalgas.html?rands="+10000*Math.random();
            data.payName = "天然气缴费";
        }
    }else{
        //返回到用户账户列表页
        window.location.href="../account.html?rands="+10000*Math.random();
    }
}
