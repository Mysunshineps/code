$(function() {
    $("#register").click(function () {
        userRegister();
    });
});

//用户登陆
function userRegister() {
    var userName = $.trim(document.getElementById("username").value);
    var email = $.trim(document.getElementById("email").value);
    var passWord = $.trim(document.getElementById("password").value);
    var params = {
        username: userName,
        password: passWord,
        email:email,
    };
    $.ajax({
        "type": "POST",
        "dataType": "json",
        "url": "/admin/user/register",
        "data": params,
        "success": function (res) {
            if (res && res.code == "200") {
                $("#register").text("注册成功")
                setTimeout(function () {
                    location.href = "/html/login.html"
                },800);
            } else {
                layer.msg(res.message, {icon: 2});
            }
        }
    });
}
