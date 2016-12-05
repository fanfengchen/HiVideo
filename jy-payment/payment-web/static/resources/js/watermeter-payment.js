/**
 * Created by LiYan on 2016/10/11.
 */


//获取url中的值
function getUrlParameters() {
    var str = "{";
    var aParams = document.location.search.substr(1).split('&');
    for (var i = 0; i < aParams.length; i++) {
        var aParam = aParams[i].split('=');
        var sParamName = decodeURIComponent(aParam[0]);
        var sParamValue = decodeURIComponent(aParam[1]);
        str += "'" + sParamName + "':'" + sParamValue + "'";
        if (i < (aParams.length - 1))
            str += ",";
    }
    str += "}";
    return eval('(' + str + ')');
}

$(function initData(){
    var userNo=getUrlParameters().userNo;
    $("#userNo").text(userNo);
})
//点击输入框 取消选择列表
function mouseDown() {
    $('.fix-group').find(".fix-num.active").removeClass('active');
}
//鼠标移出判断金额合法
/*function mouseUp(){
    var exp = /^([1-9][\d]{0,5}([.]?[\d]{0,2})?)$/;
    var amt=$("#amt").val();
    if(exp.test(amt)){
        //alert('right');
        var amtAr=amt.split(".");
        if(amtAr.size() == 1){
            amt=amt+".00";
        }
        if(amtAr.size() == 2){
            if(amtAr[1].length==1){
                amt=amt+"0";
            }
        }
    }else{
       // alert('输入金额');
    }

}*/

//确认支付
function sub2(){
    alert("不要重复提交");
}

function confirmSub(){
    var userNo=getUrlParameters().userNo;
    var tipsArrears=getUrlParameters().tipsArrears;
    var isRecord=getUrlParameters().isRecord;
    //获取li标签的值
    var amtClick=$('.fix-group').find(".fix-num.active").attr("value");

    var tempAmt="";
    var amt=$("#amt").val();
    //点击选择有值取点击
    if(null != amtClick && "" != amtClick){
        tempAmt=amtClick.toString();
    }else if(null != amt && "" != amt){
        //处理成两位小数
        var exp = /^([1-9][\d]{0,4}([.][\d]{1,2})?)$/;
        if(exp.test(amt)){
            var amtAr=amt.split(".");
            if(amtAr.length == 1){
                tempAmt=amt.toString()+".00";
            }
            if(amtAr.length == 2){
                if(amtAr[1].length==1){
                    tempAmt=amt.toString()+"0";
                }else {
                    tempAmt=amt.toString();
                }
            }
        }else {
            alert("输入金额超出范围");
            return;
        }
    }else{
        alert("请选缴费金额");
        return;
    }

    var money=getUrlParameters().money;
    var tempMoney=parseFloat(money);
    var tempAm=parseFloat(tempAmt);
    if(tempMoney + tempAm < 0){
        alert('缴费金额应大于欠费金额');
        return;
    }

    /*//重复提交提醒
    document.getElementById("subBut").onclick = function(){
        sub2();
    };*/

    var _data = {};
    _data.amount=tempAmt;
    _data.openId = getOpenId();
    _data.userNo=userNo;
    _data.tipsArrears=tipsArrears;
    _data.isRecord=isRecord;
    _data.type="02";

    $.ajax({
        type: 'POST',
        url: preUrl + "toPay",
        dataType: "json",
        data: _data,
        success: function (data, status){
            if("0" == data.rspCode){
                alert(data.rspMsg);
            }
            if("1" == data.rspCode){
                var token = data.data.token_id;
                window.location.href = 'https://pay.swiftpass.cn/pay/jspay?token_id=' + token + '&showwxpaytitle=1';
            }
        },
        error : function(data, status){

        }
    })

}