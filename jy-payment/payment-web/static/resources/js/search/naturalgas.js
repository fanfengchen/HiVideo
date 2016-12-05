/**
 * Created by zhuchong on 2016/10/10.
 */
/**
 * 点击查询
 */
function query(){

    var userNo = $("#userNo").val();
    if(userNo == null || userNo == ""){
        alert("用户户号不能为空");
        return;
    }
    var checkNum = /^(30\d{6})$/;
    if(!checkNum.test(userNo)){
        alert("用户户号不正确，请核对");
        return;
    }

    window.location.href="../statement/naturalgas.html?userNo=" + userNo
        + "&source=0&rands="+10000*Math.random();
}


