var soketUserName ;
var soket;
var soketFalg = false;
var platNo;
var soketBaseUrl = "ws://"+location.host+"/admin/webSocket/message/";
getProtocol();
function getProtocol() {
    var protocol = window.location.protocol.split(':')[0];
    if (protocol == 'https'){
        soketBaseUrl =  "wss://"+location.host+"/admin/webSocket/message/"
    } else {
        soketBaseUrl =  "ws://"+location.host+"/admin/webSocket/message/"
    }
}
$(function(){
    getLoginUser();
})
function getLoginUser() {
    var userName = window.localStorage.getItem('userName');
    $.ajax({
        type: "get",
        url: "/admin/user/info/"+userName,
        cache:false,
        dataType: "json",
        success: function (result) {
            if (result && result.code == 200) {
                var item = result.data;
                console.log("登录用户",item.userName)
                $("#loginUserName").text(item.userName);
                soketUserName = localStorage.getItem("soketUserName");
                if (!soketUserName) {
                    soketUserName = item.userName +"-"+ Math.floor(Math.random()*999)+Math.floor(Math.random()*999)
                    localStorage.setItem("soketUserName",soketUserName);
                }
                WebSocketConnect();
            }
        }
    });
}

/**
 * 发起链接请求
 * @constructor
 */
function WebSocketConnect() {
    soketFalg = false;
    // 处理域名
    // domainNameProcessingf();
    // console.log(platNo);

    if (!soketUserName || 'undefined' == soketUserName) {
        console.log("未获取到用户名，5秒后重试");
        setTimeout(function () {
            WebSocketConnect()
        },5000)
        return;
    }
    if ("WebSocket" in window)
    {
        console.log("您的浏览器支持 WebSocket!");
        soket = new WebSocket(soketBaseUrl + soketUserName);
        soket.onopen = function()
        {
            console.log("链接初始化成功...");
            soketFalg = true;
            // 延迟1.5秒 发送消息 进行心跳检测
            setTimeout(function () {
                heartbeatDetection();
            },1500)
        };
        soket.onerror = function (e) {
            console.log("错误信息：",e.data)
        }
        soket.onmessage = function (e) {
            console.log("接受到消息："+e.data)
            processMessage(e.data)
        }
        soket.onclose = function (e) {
            soketFalg = false;
            console.log("链接关闭：",e.reason)
            console.log("10秒后重试链接：");
            setTimeout(function () {
                WebSocketConnect()
            },10000)
        }
    } else {
        console.log("您的浏览器不支持 WebSocket!");
    }
}

/**
 * 退出登录
 */
function loginOut() {
    $.ajax({
        type: "POST",
        url: "/admin/login/out",
        dataType: "json",
        success: function (result) {
            if (result && result.code == 200) {
                localStorage.removeItem("soketUserName");
                location.href = "/html/login.html"
            } else {
                layer.msg(result.message, {
                    icon: 2
                });
                return false;
            }
        }
    });
}

/**
 * 处理消息
 * @param message
 */
function processMessage(message) {
    if (!message){
        return
    }
    try {
        var parse = JSON.parse(message);
        var type = parse.type;
        if ("orderNew" == type) {
            // 新订单语音提醒
            newOrderAdvice();
        }
    }catch (e) {}
}

/**
 * 发送对象
 * @param to      发送对象
 * @param params  key,value 对象
 */
function sendMessageTo(params,to) {
    try {
        if (!to){
            to = 'MASTER_SERVER';
        }
        var json = JSON.stringify(params);
        json.To = to;
        soket.send(json);
    } catch (e) {
        console.log("发送失败："+to+": ",params)
    }
}

/**
 * 发送消息
 * @param message 消息字符串
 */
function sendMessage(message) {
    try {
        var params = {};
        params.message = message;
        params.To = 'MASTER_SERVER';
        console.log("发送消息："+JSON.stringify(params))
        soket.send(JSON.stringify(params));
    } catch (e) {
        console.log("发送失败：",message)
    }
}

/**
 * 新订单语音提醒
 */
function newOrderAdvice() {
    $('#chatAudio')[0].play();
}

/**
 * 解析域名
 */
function domainNameProcessingf() {
    platNo = window.location.host;
    if (platNo && platNo.indexOf('.psqweb.com') != -1) {
        platNo = platNo.split(".psqweb.com")[0];
    }else {
        platNo = "chat";
    }
}

/**
 * 心跳检测
 * 每15 秒发送消息
 */
function heartbeatDetection() {
    if (!soketFalg){
        return;
    }
    var interval = setInterval(function () {
        if (!soketFalg){
            clearInterval(interval);
            return;
        }
        sendMessage('heart');
    },14000);
}

