var preUrl = "http://wx.jiangyoutong.com/";

/*var preUrl = "http://localhost:8080/";*/
var appid="wx63f987508d57a3f8";


function setWXConfig(url) {
   $.ajax({
        type: "GET",
        url: preUrl + 'account/getsdk/',
        async: false,
       data: {url: url},
        success: function (data) {
           if (typeof(data) == "undefined") {
                alert("访问服务器失败，请稍后重试");
            } else {
                //alert("data:" + data);
                //alert("data.resCode:" + data.resCode);
                if (data.resCode == "0000") {
                    var signnature = data.signnature;
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                       appId: appid, // 必填，公众号的唯一标识
                        timestamp: signnature.timestamp, // 必填，生成签名的时间戳
                        nonceStr: signnature.noncestr, // 必填，生成签名的随机串
                        signature: signnature.signature,// 必填，签名，见附录1
                        jsApiList: ["hideMenuItems"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                    });
                   wx.ready(function () {
                        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
                        // config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
                       // 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                        wx.hideOptionMenu();
                   });
                } else {
                    alert("获取微信JS签名失败，请稍后重试！");
                }
           }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("获取微信JS签名失败，请稍后重试！");
        }
    });
}

function getRoutePreUrl() {
    var preRouteUrl = "";
    if (preUrl.indexOf('http://') > -1) {
        preRouteUrl = preUrl.substring(7);
    } else if (preUrl.indexOf('https://') > -1) {
        preRouteUrl = preUrl.substring(8);
    }
    if (preRouteUrl.lastIndexOf('/') == (preRouteUrl.length - 1)) {
        preRouteUrl = preRouteUrl.substring(0, preRouteUrl.length - 1);
    }
    preRouteUrl = preRouteUrl.replace(new RegExp(/(\/)/g), '%2f');
    return preRouteUrl;
}

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

function refreshPages() {
    var isRefresh = sessionStorage.getItem('refresh');
    if (isRefresh && isRefresh == '2') {
        alert('服务器异常，请稍后重试！');
    } else {
        if (isRefresh == null || isRefresh == '0') {
            sessionStorage.setItem('refresh', '1');
        } else {
            sessionStorage.setItem('refresh', '2');
        }

        if (window.location.href.indexOf('user') > -1) {
            window.location.href = preUrl + 'route/target-acount.html';
        } else if (window.location.href.indexOf('bill') > -1) {
            window.location.href = preUrl + 'route/bill.html';
        } else if (window.location.href.indexOf('order') > -1) {
            window.location.href = preUrl + 'route/orderList.html';
        }
    }

}

function getOpenIdByCookie() {
    var openId = getCookie('openId');
    if (typeof(openId) == 'undefined' || openId == null || openId == '' || openId.indexOf('error') > -1) {
        refreshPages();
    }
    return openId;
}

function loadWaiting() {
    var opts = {
        lines: 9,
        length: 0,
        width: 15,
        radius: 20,
        corners: 1,
        rotate: 0,
        direction: 1,
        color: '#0101DF',
        speed: 1,
        trail: 34,
        shadow: false,
        hwaccel: false,
        className: 'spinner',
        zIndex: 2e9,
        top: '50%',
        left: '50%'
    };
    var target = document.getElementById('img_wait');
    var spinner = new Spinner(opts).spin(target);
}

function getOpenId() {
   openId = getCookie('openId');
    if (openId != null && openId != '' && openId.indexOf('error') == -1) {
        setCookie('openId', openId, 'd30');
        //initPage();
    } else {
        var jsonStr = getUrlParameters();
        jQuery.support.cors = true;
        $.ajaxSettings.async = true;
        $.ajaxSetup({async: false});
        $.ajax({
            type: "GET",
            url: preUrl + 'account/getOpenid/' + jsonStr.code,
            async: false,
            dataType: "json",
            success: function (data) {
                if (typeof(data) == "undefined") {
                    alert("访问服务器失败，请稍后重试");
                } else {
                    if (data.resCode == "0000") {
                        openId = data.openId;
                        setCookie('openId', openId, 'd30');
                        return openId;
                        //initPage();
                    } else {
                        //openIdb =  "error:" + data.resCode;
                        alert("获取用户账号失败，请稍后重试！");
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //openIdb =  "error:访问服务器失败，请稍后重试";
                alert("获取用户账号失败，请稍后重试！");
            }

        });
    }
    return openId;
}

function getFloatStr(num) {
    num += '';
    num = num.replace(/[^0-9|\.]/g, '');
    if (/^0+/)
        num = num.replace(/^0+/, '');
    if (!/\./.test(num))
        num += '.00';
    if (/^\./.test(num))
        num = '0' + num;
    num += '00';
    num = num.match(/\d+\.\d{2}/)[0];
    return num;
};

function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) return unescape(arr[2]);
    else return null;
}
function setCookie(name, value, time) {
    var strsec = getsec(time);
    var exp = new Date();
    exp.setTime(exp.getTime() + strsec * 1);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getsec(str) {
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s") {
        return str1 * 1000;
    } else if (str2 == "h") {
        return str1 * 60 * 60 * 1000;
    } else if (str2 == "d") {
        return str1 * 24 * 60 * 60 * 1000;
    }
}

