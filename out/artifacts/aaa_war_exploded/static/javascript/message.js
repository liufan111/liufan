function message(url) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
            let json = JSON.parse(xmlhttp.responseText);
            let top = document.getElementById("img");
            if(json.login == true){
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
        }
    };
    xmlhttp.open('GET','/MyInfo');
    xmlhttp.send();
}
