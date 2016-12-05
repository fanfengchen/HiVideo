/**
 * Created by zhuchong on 2016/10/17.
 */

$(function(){
    //判断进入页面来源，如果是在没有账户列表数据时进入该页面，则不让返回
    var sou = getUrlParameters().sourceAccount;

    if(sou == "1"){
        document.getElementById("iAngel").style.display = "none";
    }

        $("div .header i").click(function(){
            if(sou != "1") {
                window.location.href = "account.html?rands=" + 10000 * Math.random();
            }
        })
})
