/**
 * Created by zxw on 17-11-19.
 */
'use strict';
function get_user_message(url) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
            let json = JSON.parse(xmlhttp.responseText);
            let top = document.getElementById("user");
            if(json.login == true){
                //常见一个img便签
                //document.createElement('')
                let head = document.createElement('img');
                if(json.head_path === ''){
                    head.setAttribute("src","/static/image/user.jpg");
                }else{
                    head.setAttribute('src',json.head_path);
                }
                top.appendChild(head);
            }else{
                window.location.href = '/login.html?url='+url;
            }
            let name = document.getElementById("name");
            let nameA = document.createElement("a");
            nameA.setAttribute("href","/me.jsp");
            nameA.innerText = json.emp_name;
            name.appendChild(nameA);
        }
    };
    xmlhttp.open('GET','/MyInfo');
    xmlhttp.send();
}
