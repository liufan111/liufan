/**
 * Created by zxw on 17-12-18.
 */
'use strict';
//个人信息显示
function check1() {
    var ssss = document.getElementById('emp_email');
    var reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if(! reg.test(ssss.value)) {
        // document.getElementById('email_err').innerHTML = '邮箱格式不正确';
        return false;
    }else {
        return true;
    }
}

function check2(){
    var sss = document.getElementById('emp_tel');

    var reg= /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
    if(! reg.test(sss.value)) {
        // document.getElementById('phone_err').innerHTML = '手机号格式不正确';
        return false;
    }else {
        return true;
    }
}

function account() {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
            let json = JSON.parse(xmlhttp.responseText);
            if(json.login == true){
                let table = document.getElementById('table');
                let length = table.rows.length;
                // let lenCell = table.rows.item(0).cell.length;
                // alert(lenCell);
                // alert(table.rows.item(0).cells.length);
                let lenCell = table.rows.item(0).cells.length;
                while (lenCell>1){
                    for(let i = 0;i<length;i++){
                        table.rows[i].deleteCell(lenCell-1);
                    }
                    lenCell--;
                }
                json = json.mess;
                let rows = 0;
                let i;
                for(i in json){

                        let cell = table.rows[rows].insertCell(1);
                        cell.innerText = json[i];
                        rows ++;
                }
            }
        }
    };
    xmlhttp.open('GET','/MyInfo');
    xmlhttp.send();
}

//获取界面中的信息
function info() {
    let array = [];
    let table = document.getElementById('table');
    let length = table.rows.length;
    for(let i = 0;i<length;i++){
        // alert(table.rows[i].cells[1].innerHTML);
        array[i] = table.rows[i].cells[1].innerHTML;
    }
    return array
}

//点击事件 “修改员工信息”
function butInfo() {
    let mesInfo = [];
    mesInfo = info();
    // alert(mesInfo);
    let emp = [];
    document.getElementById('emp_no').value =  mesInfo[0];
    document.getElementById('emp_name').value = mesInfo[1];
    document.getElementById('emp_addr').value = mesInfo[2];
    document.getElementById('emp_email').value = mesInfo[3];
    document.getElementById('emp_tel').value = mesInfo[4];
    // alert(document.getElementById('emp_tel').value);
    // let len = mesInfo.length;
    // for(let i = 0;i<len;i++){
    //     emp[i] = mesInfo[i];
    // }
    // emp_no =mesInfo[0];
    // emp_name = mesInfo[1];
    // emp_addr= mesInfo[2];
    // emp_email= mesInfo[3];
    // emp_tel = mesInfo[4];
    // emp_no.setAttribute('placeholder',mesInfo[0]);
    // emp_name.setAttribute('placeholder',mesInfo[1]);
    // emp_addr.setAttribute('placeholder',mesInfo[2]);
    // emp_email.setAttribute('placeholder',mesInfo[3]);
    // emp_tel.setAttribute('placeholder',mesInfo[4]);
}

function changeInfo() {
//    let mesInfo = butInfo();
    let emp_no = document.getElementById('emp_no').value;
    let emp_name = document.getElementById('emp_name').value;
    let emp_addr = document.getElementById('emp_addr').value;
    let emp_email = document.getElementById('emp_email').value;
    let emp_tel = document.getElementById('emp_tel').value;
    // getMess(emp_no);


    if(check1() && check2()){

    }else {
        alert("格式输入有误！");
        return false;
    }


    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if(json.state){
                account();
            }else{
                alert("失败，请重试！");
            }
        }
    };
    let emp_id = '';
    let sss = window.location.search;
    let method = 'PUT';
    let data = 'emp_no='+emp_no+'&emp_name='+emp_name+'&emp_tel_num='+emp_tel+'&emp_addr='+emp_addr+'&emp_email='+emp_email+'&emp_id'+emp_id;
    // alert(data);
    xml.open(method,'/api/employee');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}


//更改头像

