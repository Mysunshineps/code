$(function() {
    $("#login").click(function () {
        userLogin();
    });
});

//用户登陆
function userLogin() {
    var userName = $.trim(document.getElementById("username").value);
    var passWord = $.trim(document.getElementById("password").value);
    //    var token = $('input[name=csrfmiddlewaretoken]').val();  //非csrf验证就使用
    var params = {
        username: userName,
        password: passWord,
        //    csrfmiddlewaretoken: token
    };
    // post 你制作的JWT的API接口
    $.ajax({
        "type": "POST",
        "dataType": "json",
        "url": "/admin/user/userLogin",
        "data": params,
        "success": function (res) {
            if (res && res.code == "200") {
                var data = res.data;
                window.localStorage.clear('X-Token') // 每次登陆清理token
                window.localStorage.setItem('userName', data.userName) // 登陆成功重新设置username保存到浏览器本地
                window.localStorage.setItem('X-Token', data.token) // 登陆成功重新设置token保存到浏览器本地
                document.cookie = data.username + '|' + data.token;  // 登陆成功信息保存到cookie中
                $("#login").text("登陆成功")
                location.href = "/html/index.html"
            } else {
                layer.msg('登录失败!', {icon: 2});
            }
        }
    });
}
