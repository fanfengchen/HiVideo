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
        //显示按钮之后添加绑定事件
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
    //_data.openId = "aaa";
    var userNo = getUrlParameters().userNo;
    //var userNo = "30002030";
    _data.userNo = userNo;

    //请求自来水数据
    $.ajax({
        type: 'POST',
        url: preUrl + "/wechat/queryGasBill",
        dataType: "json",
        data: _data,
        success: function (data, status) {
            if(data.rspCode == "1"){
                if(data.data.bill.length == 1){
                    //表示用户不欠费不需要缴费
                    window.location.href="../statement/empty.html?userNo=" + userNo
                        + "&source=" + sou + "&type=03&rands="+10000*Math.random();
                    return;
                }
                //模板数据
                var datas = {
                    item : []
                }
                var list = new Array();
                //遍历传回来的数据
                $(data.data.bill).each(function(index,item){
                    //由于最后一条数据的值是前面几条数据的和，不遍历最后一条数据
                    if(index == data.data.bill.length - 1){
                        return;
                    }

                    //去除balance的值null
                    var balance = item.balance;
                    if(balance == 'null' || balance == null){
                        balance = '';
                    }
                    //去除null的值
                    var money = item.money;
                    if(money == 'null' || money == null){null
                        money = '';
                    }
                    //去除负号
                    money = money.substr(1,money.length);
                    //处理年月
                    var mon = item.statementMonth.split("-");
                    var monthStr = "";
                    if(mon != null && mon.length > 1){
                        monthStr = mon[0] + " 年 " + mon[1] + " 月"
                    }
                    var temp = {};
                    temp.monthStr = monthStr;
                    temp.userNo = item.userNo;
                    temp.startNum = item.startNum;
                    temp.endNum = item.endNum;
                    temp.totalPowe = item.totalPowe;
                    temp.shouldMoney = item.shouldMoney;
                    temp.punishMoney = item.punishMoney;
                    temp.username = item.username;
                    temp.address = item.address;
                    //temp.balance = balance;
                    temp.money = money;
                    list.push(temp);
                })
                datas.item = list;
                user_info(data.data.userInfo);
                itemInfo(datas);
                //获取金额总计
                if(data.data.bill.length > 0){
                    var monTemp = data.data.bill[data.data.bill.length - 1].money;
                    var xs = monTemp.substr(monTemp.indexOf(".") + 1, monTemp.length);
                    if(monTemp.indexOf(".") == -1){
                        monTemp = monTemp + ".00";
                    }
                    if(monTemp.indexOf(".") != -1 && xs.length == 1){
                        monTemp = monTemp + "0";
                    }
                    $("#totalMoney").text(monTemp);
                }
                $("#sumbit").click(function(){
                    //$("#night").show();
                    affirmSub();
                })
            }else{
                alert(data.rspMsg);
                //出现错误则跳转页面
                //从新增页面跳转进入，也跳转到新增页面
                if(sou == "0"){
                    window.location.href="../search/naturalgas.html?rands="+10000*Math.random();
                }else{
                    window.location.href="../account.html?rands="+10000*Math.random();
                }
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

//确认支付
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
    var amt =  $("#totalMoney").text();
    var data = {
        userNo : userNo,
        openId : openId,
        tipsArrears : tipsArrears,
        isRecord : isRecord,
        amount : amt,
        type : "03" //表示燃气
    }
    $.ajax({
        type: 'POST',
        url: preUrl + "toPay",
        dataType: "json",
        data: data,
        success: function (data, status){
            if("0" == data.rspCode){
                alert(data.rspMsg);
            }
            if("1" == data.rspCode){
                var token = data.data.token_id;
                window.location.href = 'https://pay.swiftpass.cn/pay/jspay?token_id=' + token + '&showwxpaytitle=1';
            }
        },
        error : function(){

        }
    })
}

