/**
 * Created by zxw on 17-12-19.
 */
'use strict';

function get_emp() {
    let each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let emp_name = document.getElementById('employee_name').value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse(xhr.responseText);
            if(res.status == false) {
                document.getElementById('now_page').innerText =parseInt(now_page)-1;
                alert("已到最后一页!");
                return ;
            }

            let tbody = document.getElementById('tbody');
            tbody.innerText = '';
            let json = res.object;
            for(let i = 0;i<json.length;i++){
                let tr = document.createElement('tr');
                let td0 = document.createElement('td');
                td0.innerText = json[i][0];
                td0.setAttribute('style','display: none;');
                let td1 = document.createElement('td');
                td1.innerText = json[i][1];
                let td2 = document.createElement('td');
                td2.innerText = json[i][2];
                let td3 = document.createElement('td');
                td3.innerText = json[i][3];
                let td4 = document.createElement('td');
                td4.innerText = json[i][4];

                let td5 = document.createElement('td');
                td5.innerText = json[i][5];

                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tbody.appendChild(tr);
            }


        }
    };
    xhr.open('GET','/api/employee?page='+now_page+'&nums='+each_nums+'&name='+emp_name);
    xhr.send();
}

function other_page(offset) {
    let now_page = document.getElementById('now_page');
    if(offset < 0 && parseInt(now_page.innerText) == 1){
        //
        alert("已到第一页！");
    }else {
        now_page.innerText = parseInt(now_page.innerText) + offset;
        get_emp()
    }
}

function reset_get() {
    document.getElementById('now_page').innerText = '1';
    get_emp();
}




function del_studio() {

    let id = document.getElementById('delete_now').innerText;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            if(JSON.parse(xhr.responseText).status){
                get_studio();
            }else{
                alert("删除失败！");
            }
        }
    };
    xhr.open('DELETE','/api/studio');
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.send('id='+id);
}