/**
 * Created by zhuchong on 2016/10/10.
 */


$(function initData(){

    var sou = getUrlParameters().source;
    //0表示由新增页面跳转过来，显示保存和提示选择按钮
    if(sou == "0"){
        //是否显示保存提示按钮
        var dataTemp = {};
        item_button(dataTemp);
        buttonClick();
    }

    $("#night").append($("#boardwalk").clone());
    //确认支付
    $("#night").on("click","#submit",function(){
        //console.log('Todo')
    });
    //取消
    $("#night").on("click","#cancel",function(){
        //$("#night").fadeOut("fast");
    });

    var _data = {};
    _data.openId = getOpenId();
   // _data.openId = "qwer123";
    _data.userNo = getUrlParameters().userNo;
    //_data.userNo = "6400784";

    $.ajax({
        type: 'POST',
        url: preUrl + "/wechat/queryRechargeBill",
        dataType: "json",
        data: _data,
        success: function (data, status) {
            if(data.rspCode == "1"){
                var datas = {
                    item : []
                }
                var list = new Array();

                //计算合计金额，用于下一个页面判断
                var moneyTemp = 0;
                //遍历data
                $(data.data.bill).each(function(index,item){
                    //处理上次余额显示
                    var balance = item.balance;
                    if(balance == 'null' || balance == null){
                        balance = '';
                    }
                    if(balance.length > 4){
                        balance = balance.substr(0, balance.length - 4);
                    }
                    //处理小计金额显示
                    var money = item.money;
                    if(money == 'null' || money == null){
                        money = '';
                    }
                    if(money.length > 4){
                        money = money.substr(0, money.length - 4);
                    }
                    var temp = {};
                    temp.userNo = item.userNo;
                    temp.username = item.username;
                    temp.startNum = item.startNum;
                    temp.endNum = item.endNum;
                    temp.totalPowe = item.totalPowe;
                    temp.shouldMoney = item.shouldMoney;
                    temp.punishMoney = item.punishMoney;
                    temp.balance = balance;
                    //temp.money = money;
                    list.push(temp);
                    moneyTemp = parseFloat(moneyTemp) + parseFloat(item.shouldMoney) + parseFloat(balance);
                    $("#moneyTemp").val(moneyTemp);
                })
                datas.item = list;
                //调用欠费账单信息展示
                itemInfo(datas);
                user_info(data.data.userInfo)
                $("#sumbit").click(function(){
                    //$("#night").show();
                    affirmSub();
                })
            }else if(data.rspCode == "0"){
                alert(data.rspMsg);
                //出现错误则跳转页面
                if(sou == "0"){
                    window.location.href="../search/watermeter.html?rands="+10000*Math.random();
                }else{
                    window.location.href="../account.html?rands="+10000*Math.random();
                }
            }else if(data.rspCode == "-1"){
                //用户不欠费不需要缴费
                window.location.href="../statement/empty.html?userNo=" + userNo
                    + "&source=" + sou + "&type=02&rands="+10000*Math.random();
            }
        },
        error: function (data, status) {
        }
    });

})

//添加按钮绑定事件
function buttonClick(){
    //当选中提醒时，将保存按钮选中
    //当取消保存时，将提醒也取消保存
    $("#tipsArrears").click(function(){
        if($("#tipsArrears").is(":checked")){
            $("#isRecord").prop("checked", true);
        }
    });
    $("#isRecord").click(function(){
        if(!$("#isRecord").is(":checked")){
            $("#tipsArrears").prop("checked", false);
        }
    });
}

//欠费账单信息
function itemInfo(data) {
    var text = $("#item").text();
    var template = Hogan.compile(text);
    var render = template.render(data);
    $("#item-list").html(render);
}

//保存和提示按钮
function item_button(data) {
    var text = $("#item-button").text();
    var template = Hogan.compile(text);
    var render = template.render(data);
    $("#button-div").html(render);
}
//用户信息
function user_info(data) {
    var text = $("#user-info").text();
    var template = Hogan.compile(text);
    var render = template.render(data);
    $("#user-info-html").html(render);
}
//充值
function affirmSub(){
    //是否缴费提醒
    var tipsArrears = "0";
    if($("#tipsArrears").is(":checked")){
        tipsArrears = "1";
    }
    //是否保存账号
    var isRecord = "0";
    if($("#isRecord").is(":checked")){
        isRecord = "1";
    }
    var userNo = getUrlParameters().userNo;
    var openId = getOpenId();
    var data = {
        userNo : userNo,
        openId : openId,
        tipsArrears : tipsArrears,
        isRecord : isRecord,
        type : "02"//表示远传表水
    }
    var moneyTemp = $("#moneyTemp").val();
   window.location.href="../watermeter-payment.html?userNo="
       +userNo+"&openId="+openId+"&tipsArrears="+tipsArrears+
       "&money=" + moneyTemp+"&isRecord="+isRecord;
}
