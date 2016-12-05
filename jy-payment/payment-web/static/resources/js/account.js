/**
 * Created by zhuchong on 2016/10/11.
 */

function has(obj, propName) {
    return Object.prototype.hasOwnProperty.call(obj, propName)
}

$(function initData(){

    $("#clickSpan").click(function(){
        window.location.href="index.html?rands="+10000*Math.random();
    });

    var openId = getOpenId();
    var _data ={};
    _data.openId = openId;

    $.ajax({
        type: 'POST',
        url: preUrl + "/wechat/queryBingdingList",
        dataType: "json",
        data: _data,
        success: function (data, status) {
            if(data.rspCode == "1"){

                //如果没有查询到用户绑定账户，则跳转到index页面
                if(data.data.length < 1){
                    window.location.href="index.html?sourceAccount=1&rands="+10000*Math.random();
                }

                //模块展示需要的数据
                var dataR = {};
                //获取后台传回来的数据
                var list = new Array();
                $(data.data).each(function(index,item){
                    var temp = {};
                    temp.id = item.id;
                    temp.accountType = item.accountType;
                    temp.userNo = item.userNo;
                    temp.username = item.username;
                    temp.tipsArrears = item.tipsArrears;
                    temp.remark = item.remark;
                    list.push(temp);
                });

                dataR.item = list;

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

                //转换dataR中的数据，将accountType转换成icon和name
                $(dataR.item).each(function(index,item){

                    if( has(config,item.accountType) ) {

                        item.icon = config[item.accountType].icon;
                        item.name = config[item.accountType].name;
                    }
                })

                var text = $("#item").text();
                var template = Hogan.compile(text);
                var render = template.render(dataR);
                $("#item-board").html(render);
                clickSkip();
            }else{
                alert(data.rspMsg);
            }
        },
        error: function (data, status) {
        }
    })


    // 新增内容开始
    var $edit = $('div .btn.edit');
    var $comment = $('.comment-out');
    var $cancel = $('.comment-bar .bar-cancel');
    var $confirm = $('.comment-bar .bar-confirm');
    var $return = $('.header a.link');
    //var content;  //用于存储备注信息


    $(".header a.link").click(function(){
        if( !$(this).hasClass("edit") ) {
            $(this).text("返回").addClass("edit");
            var links = $(".item-link");
            var control = $("#control").text();
            $(links).find("i.angle").remove();
            $(links).append(control);

            //绑定点击事件，用于删除
            $("div .btn.delete").click(function(){
                //获取缴费号
                var spText = $(this).parent().prev().find("span").text();
                deleteBill($(this), spText);
            });

            //-----------------------------新增点击事件开始
            //绑定点击事件，用于添加备注信息

            //用于将点击账号管理之后将备注信息放到下面来
            var remarkDiv = $(this).parent().parent().find("div[remark]");
            $(remarkDiv).each(function(index,item){
                var text = item.innerText;
                $(item).parent().parent().find("div[updateRemark]").text(text);
                item.remove();
                $('div.item-title').css('border',"none");
            })

            //修改备注
            $("a.item-link").find('div.edit').click(function(){
                //初始化修改备注弹框数据
                var id = $(this).parent().parent().find("input[userBillAccountId]").val();
                $("#updateRemarkId").val(id);
                var remark = $(this).parent().parent().find("div[updateRemark]").text();
                $("#remark").val(remark);
                var title = "修改备注";
                if(remark == null || remark == ""){
                    title = "添加备注";
                }
                $("#updateRemarkTitle").text(title);
                $('.comment-out').removeClass('comActive');
            })

            //修改备注弹窗取消按钮
            $cancel.on('click',function(event) {
                event.preventDefault();
                $comment.addClass('comActive');
            });

            //修改备注弹窗确定按钮
            $confirm.on('click', function(event) {
                event.preventDefault();
                updateRemark();
            });

            //---------------------------------------新增点击事件结束

            //绑定点击事件，用于修改是否提醒
            $("input[type=checkbox]").click(function(){
                var spText = $(this).parent().parent().parent().prev().find("span").text();
                var id = $(this).parent().parent().parent().parent().find("input[userBillAccountId]").val();
                updateBill(this, spText, id);
            });
            //进入编辑页面，禁用点击跳转
            $("a .item-inner").unbind("click");

            //初始化提醒按钮
            initCheckBox($(links));
        } else {
            //window.location.reload();
            window.location.href="account.html?rands="+10000*Math.random();

        }
    });

})

//初始化提醒按钮
function initCheckBox(thiss){
    $(thiss).each(function(index,item) {
        //初始化提醒按钮
        var tipsArrears = $(item).find("input[tipsArrears]").val();
        if (tipsArrears == "0") {
            $(item).find("input[type=checkbox]").attr("checked", false);
        }
    });
}

//控制跳转
function clickSkip(){
    //绑定click事件用于跳转查询
    $("div.item-inner").click(function(){
        //获取缴费号
        var userNo = $(this).find("span").text();
        //获取点击的账号类型
        var type = $(this).find("input[accountType]").val();
        if(type == "01"){
            window.location.href="statement/water.html?userNo=" + userNo
                + "&source=1&rands="+10000*Math.random();
        }
        if(type == "02"){
            window.location.href="statement/watermeter.html?userNo=" + userNo
                + "&source=1&rands="+10000*Math.random();
        }
        if(type == "03"){
            window.location.href="statement/naturalgas.html?userNo=" + userNo
                + "&source=1&rands="+10000*Math.random();
        }
    });
}

//用于修改是否提醒
function updateBill(thiss, userNo, id){
    var tipsArrears = "0";
    if($(thiss).is(":checked")){
        tipsArrears = "1";
    }
    var _data = {};
    _data.openId = getOpenId();
    _data.userNo = userNo;
    _data.tipsArrears = tipsArrears;
    _data.id = id;
    $.ajax({
        type: 'POST',
        url: preUrl + "/wechat/updateBingding",
        dataType: "json",
        data: _data,
        success: function (data, status) {
            if(data.rspCode == "1"){
                alert("修改成功");
            }else{
                alert(data.rspMsg);
            }
        },
        error: function (data, status) {
        }
    })
}

//删除
function deleteBill(spanT ,userNo){

    var c = confirm("是否删除此账户");
    if(c == true){
        var _data = {};
        _data.openId = getOpenId();
        //_data.openId = "123qweasdzxc";
        //_data.openId = "123";
        _data.userNo = userNo;

        $.ajax({
            type: 'POST',
            url: preUrl + "/wechat/cancelBingding",
            dataType: "json",
            data: _data,
            success: function (data, status) {
                if(data.rspCode == "1"){
                    //alert("删除成功");
                    //删除a标签
                    spanT.parent().parent().remove();
                }else{
                    alert(data.rspMsg);
                }
            },
            error: function (data, status) {
            }
        })
    }
}

function updateRemark(){
    var content = $('#remark').val();
    var id = $("#updateRemarkId").val();
    if(content == ""){
        alert("输入内容不能为空！");
    }else{
        var _data = {};
        _data.id = id;
        _data.remark = content;
        $.ajax({
            type: 'POST',
            url: preUrl + "/wechat/updateRemark",
            dataType: "json",
            data: _data,
            success: function (data, status) {
                if(data.rspCode == "1"){
                    //操作成功之后将数据修改正确
                    $($("a.item-link")).each(function(index, item){
                        var idTemp = $(item).find("input[userBillAccountId]").val();
                        if(id == idTemp){
                            $(item).find("div[updateRemark]").text(content);
                        }
                    })
                    $('.comment-out').addClass('comActive');
                    alert("操作成功");
                }else{
                    alert(data.rspMsg);
                }
            },
            error: function (data, status) {
            }
        })
        // $('a.item-link').eq(index_num).find('.item-note').text(content);
    }
}
