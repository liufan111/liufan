'use strict';

function confirm() {
    var user = 'zxw';
    var password = '123456';

    var name = document.getElementById('name').value;
    var pass = document.getElementById("password").value;

    if(name != user || pass != password){
        alert("This page says:请输入正确的账号密码");
        return false;
    }

}

