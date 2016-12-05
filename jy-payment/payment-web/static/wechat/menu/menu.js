var authUrl="https://open.weixin.qq.com/connect/oauth2/authorize";
var redirect_uri="http%3A%2F%2F" + getRoutePreUrl() +toHtml ;
var params="response_type=code&scope=snsapi_base#wechat_redirect";
var targetUrl=authUrl+"?appid="+appid+"&redirect_uri="+redirect_uri+"&"+params;
window.location.href=targetUrl;