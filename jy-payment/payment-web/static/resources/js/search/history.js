/**
 * Created by LiYan on 2016/10/10.
 */


function has(obj, propName) {
    return Object.prototype.hasOwnProperty.call(obj, propName)
}
var config = {
    "01":{
        name: "自来水缴费",
        icon: "icon_water.png"
    },
    "02":{
        name:"远传水表充值",
        icon:"icon_watermeter.png"
    },
    "03":{
        name: "天然气缴费",
        icon:"icon_naturalgas.png"
    }
};

function itemInfo(data) {
    var text = $("#item").text();
    var template = Hogan.compile(text);
    var render = template.render(data);
    $("#item-list").html(render);
}

function itemNoResult(data){
    var text = $("#noResult").text();
    var template = Hogan.compile(text);
    var render = template.render(data);
    $("#item-list-result").html(render);
}

$(function initData(){

    var _data = {};
    _data.openId = getOpenId();
    $.ajax({
        data: _data,
        type: 'POST',
        url: preUrl+'queryPaymentList',
        dataType: "json",
        success: function (data, status) {
            //模板数据
            var datas = {
                item : []
            }
            var resCode=data.rspCode;
            var rspMsg=data.rspMsg;
            if("-2" == resCode || "查询结果为空" == rspMsg){
                var dataTemp = {};
                itemNoResult(dataTemp);
                $("#item-list-result").children(".center-icon").removeClass('hides');
                return ;
            }
            if(resCode == "1"){
                var map=data.data;
                var list = new Array();

                for(var keys in map){
                    var tempa = {};
                    tempa.payMonth = keys;
                    var templ = new Array();
                    $(map[keys]).each(function(index,item){
                        var tempb = {};
                        tempb.orderType = item.orderType;
                        var sentResult=item.ordersDetail.sentResult;
                        if("01" == sentResult){
                            if("02" == item.orderType){
                                tempb.state="充值成功";
                                $("#states").addClass("fontBlue");
                            }else{
                                tempb.state="缴费成功";
                            }
                        }else{
                            if("03" == item.orderStatus){
                                tempb.state="支付成功";
                            }
                            if("05" == item.orderStatus){
                                tempb.state="退款成功";
                            }
                            if("07" == item.orderStatus){
                                tempb.state="退款失败";
                            }
                        }
                        tempb.orderNo = item.orderNo;

                        var amt=item.orderAmtString;

                        tempb.orderAmt = amt;
                        tempb.payAccountNo = item.ordersDetail.payAccountNo;
                        tempb.payTime = item.paySuccTime;
                        tempb.payType = item.payType;
                        if( has(config,item.orderType) ) {
                            tempb.icon = config[item.orderType].icon;
                            tempb.name = config[item.orderType].name;
                        }
                        templ.push(tempb);
                    })
                    tempa.list = templ;
                    list.push(tempa);
                }

                datas.item = list;
                itemInfo(datas);
            }
        },
        error: function (data, status) {
        }
    });

    $("#show_filter").click(function(){
			$("#night").show();
    })
		
    $("#hide_filter").click(function(){
			$("#night").fadeOut();
    })
		

    $(".dropdown li").click(function(){
        var type = $(this).find(".name").first().data("type");
        var _data = {};
        _data.type = type;
        _data.openId = getOpenId();
        $.ajax({
            type: 'POST',
            url: preUrl+'queryPaymentList',
            dataType: "json",
            data: _data,
            success: function (data, status) {
                //模板数据
                var datas = {
                    item : []
                }
                var map=data.data;
                var list = new Array();

                var resCode=data.rspCode;
                var rspMsg=data.rspMsg;

                if("-2" == resCode || "查询结果为空" == rspMsg){
                    var dataTemp = {};
                    itemNoResult(dataTemp);
                    $("#item-list-result").children(".center-icon").removeClass('hides');
                    itemInfo(datas);
//                  $(".dropdown").addClass('hide')
					$("#night").fadeOut();

                    $(".header .link").text("筛选");
                    return ;
                }
                if(resCode == "1") {

                    $("#item-list-result").children(".center-icon").addClass('hides');
                    for (var keys in map) {
                        var tempa = {};
                        tempa.payMonth = keys;
                        var templ = new Array();
                        $(map[keys]).each(function (index, item) {
                            var tempb = {};
                            tempb.orderType = item.orderType;
                            var sentResult=item.ordersDetail.sentResult;
                            if("01" == sentResult){
                                if("02" == item.orderType){
                                    tempb.state="充值成功";
                                    $("#states").addClass("fontBlue");
                                }else{
                                    tempb.state="缴费成功";
                                }
                            }else{
                                if("03" == item.orderStatus){
                                    tempb.state="支付成功";
                                }
                                if("05" == item.orderStatus){
                                    tempb.state="退款成功";
                                }
                                if("07" == item.orderStatus){
                                    tempb.state="退款失败";
                                }
                            }
                            tempb.orderNo = item.orderNo;

                            var amt=item.orderAmtString;

                            tempb.orderAmt = amt;

                            tempb.payAccountNo = item.ordersDetail.payAccountNo;
                            tempb.payTime = item.paySuccTime;
                            tempb.payType = item.payType;
                            if (has(config, item.orderType)) {
                                tempb.icon = config[item.orderType].icon;
                                tempb.name = config[item.orderType].name;
                            }
                            templ.push(tempb);
                        })
                        tempa.list = templ;
                        list.push(tempa);
                    }
                    datas.item = list;
                    itemInfo(datas);
//                  $(".dropdown").addClass('hide')
					$("#night").fadeOut();

                    $(".header .link").text("筛选");

                }
            },
            error: function (data, status) {

            }
        });
    })


})


