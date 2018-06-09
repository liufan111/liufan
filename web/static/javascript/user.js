'use strict'
let number = 0;
function check(){
    let pass = document.getElementById('pass').value;
    let passAgain = document.getElementById('passAgain').value;
    let a = /^[0-9]{6,20}$/.test(pass);
    //let b = /^[a-zA-Z0-9]{6,20}$/.test(passAgain);
    if(pass == passAgain){
        if(!a){
            // document.getElementById('err').innerHTML = '密码需要6～20位的任意字母和数字组合'
            return false;
        }else{
            // document.getElementById('err').innerHTML = '';
            return true;
        }
    }else{
        // document.getElementById('err').innerHTML = '两次输入的密码不一致';
        return false;
    }
}
//
// function check1() {
//     var sss = document.getElementById('studioState');
//     var reg=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
//     if(! reg.test(sss.value)) {
//         document.getElementById('email_err').innerHTML = '邮箱格式不正确';
//         return false;
//     }else {
//         return true;
//     }
// }
//
// function check2(){
//     var sss = document.getElementById('studioIntroduction');
//     var reg= /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;
//     if(! reg.test(sss.value)) {
//         document.getElementById('phone_err').innerHTML = '手机号格式不正确';
//         return false;
//     }else {
//         return true;
//     }
// }
//显示所有信息
function get_user() {
    let each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let user_no = document.getElementById('user_no').value;
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
                // td0.setAttribute('style','display: none;');
                let td1 = document.createElement('td');
                td1.innerText = json[i][1];
                let td2 = document.createElement('td');
                if(json[i][2] == 1){
                    td2.innerText = "管理员";
                }else if(json[i][2] == 0){
                    td2.innerText = "经理";
                }else {
                    td2.innerHTML = "售票员";
                }

                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);

                tbody.appendChild(tr);
            }


        }
    };
    xhr.open('GET','/api/user?page='+now_page+'&nums='+each_nums+'&name='+user_no);
    xhr.send();
}


function other_page(offset) {
    let now_page = document.getElementById('now_page');
    if(offset < 0 && parseInt(now_page.innerText) == 1){
        //
        alert("已到第一页！");
    }else {
        now_page.innerText = parseInt(now_page.innerText) + offset;
        get_user();
    }
}

//添加登录用户
function addUser() {
    //alert("34234");
    let user_no = document.getElementById('user_name').value;
    let job = document.getElementById('Job');
    let indexJob = job.selectedIndex;
    let type = job.options[indexJob].value;
    if (type == 'manager'){
        type =1;
    }else {
        type = 0;
    }
    let user_pass = document.getElementById('pass').value;
    // alert(user_pass);
    let passAgain = document.getElementById('passAgain').value;
    let head_path = '';
    // alert("user_no: "+user_no+"type:"+type+"user_pass"+user_pass+"head_path:"+head_path+"ddfd");
    if(check()){

    }else {
        alert("格式输入有误！");
        return false;
    }
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        // alert("aaaa");
        if (xml.readyState == 4 && xml.status == 200) {
            let json = JSON.parse(xml.responseText);
            if (json.state) {
                get_user();
            } else {
                alert("失败，请重试！");
            }
        }
    };
        let sss = window.location.search;
        let method = 'POST';
        let data = 'emp_no='+user_no+'&emp_pass='+user_pass+'&type='+type+'&head_path='+head_path;
        console.log(data);
        xml.open(method,'/api/user');
        xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xml.send(data);
}


//鼠标点击事件
function studioRow(obj) {
    let table = document.getElementById('loginUser');
    let length = table.rows.length;

    for(let i = 0;i<length;i++){
        if(i == 0){
            let row = table.rows[i];
            row.setAttribute('class','warning');
        }
        else if(i%2 != 0 && i != 0){
            let row = table.rows[i];
            row.setAttribute('class','active');
        }else{
            let row = table.rows[i];
            row.setAttribute('class','default');
        }
    }

    if(event.srcElement.tagName == 'TD'){
        let num = 0;
        let curRow = event.srcElement.parentElement;
        //let rowContainer = curRow.innerHTML;

        if(curRow.rowIndex == 0){
            curRow.setAttribute('class','warning');
            return 0;
        }
        else{
            curRow.setAttribute('class','info');
            num = curRow.rowIndex ;
            //console.log(num);
            number = num;
        }
    }

}

//删除某一行
function deleteRow(row) {
    let deleteRow = document.getElementById('loginUser');
    //let length = deleteRow.rows.length;
        deleteRow.deleteRow(row);

}

//删除可登录用户
function removeUser() {
    let row = number;
    if(row == 0){
        // alert("请选择需要删除的地方")
        // alert(row);
        console.log('aaa');
        let changeButton = document.getElementById('deleteStudio');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！';
    }
    else{
        let user = [];
        user = changeRow(row);
        let emp_no = user[0];
        // alert(emp_no);
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
          if(xhr.readyState == 4 && xhr.status == 200){
              if (JSON.parse(xhr.responseText).status){
                  get_user();
              }else {
                  alert("删除失败");
              }
          }
        };
        xhr.open('DELETE','/api/user');
        xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xhr.send('id='+emp_no);
    }


}

//获取选中行的信息
function changeRow(number) {
    let array = [];
    let table = document.getElementById('loginUser');
    // let length = table.rows.length;
    let length = table.rows.item(0).cells.length;
    console.log(length);
    if(number != 0){
        for(let i = 0;i<length;i++){
            array[i] = table.rows[number].cells[i].innerHTML;
            // alert(array[i]);
        }
        return array
    }
}

//将信息添加在修改信息的相应位置
function change() {

//当选中需要修改的内容时
    if(number >0){
        let changeButton = document.getElementById('changeStudio');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#myModals');
        let change = [];
        let array = [];
        array = changeRow(number);
        change[0] = document.getElementById('changeName').value;
        change[2] = document.getElementById('user_pass').value;
        change[3] = document.getElementById('user_passA').value;

        console.log(array[1]);
        //设置所选中的性别为默认
        if(array[2] == '超级管理员'){
            document.getElementById('changeJob').value = 'manager';
        }else if(array[2] == '普通管理员'){
            document.getElementById('changeJob').value = 'sealer';
        }
        document.getElementById('changeName').value = array[0];
        document.getElementById('user_pass').value = array[1];
        document.getElementById('user_passA').value = array[1];
        // alert("array[0]:"+array[0]+"array[1]:"+array[1]+"array[2]:"+array[2] +"array[3]:"+array[3]);
        // alert(array[3])
        change[0] = array[0];
        // arry[1]为密码
        change[2] = array[1];
        change[3] = array[1];
        // alert(change[2]);
        // console.log(change);
        return change;
    }
//当没有选中内容时
    else{
        let changeButton = document.getElementById('changeStudio');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！'
    }

}

//获取修改框中的信息
function putUser() {
    let emp_no = document.getElementById('changeName').value;
    let emp_pass = document.getElementById("user_pass").value;
    // alert(emp_pass);
    let job = document.getElementById("changeJob");
    let index = job.selectedIndex;
    let type = job.options[index].value;
    // alert(type);
    if(type == "manager"){
        type = 1;
    }else{
        type = 0;
    }
    // alert(type);
    let head_path = "";
    // alert("user_no: "+emp_no+"type:"+type+"user_pass"+emp_pass+"head_path:"+head_path+"ddfd");
    if(check() && check1() && check2()){

    }else {
        alert("格式输入有误！");
        return false;
    }
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
      if(xml.readyState == 4 && xml.status == 200){
          let json = JSON.parse(xml.responseText);
          if(json.state){
              get_user();
          }else{
              alert("失败，请重试！");
          }
      }
    };
    let sss = window.location.search;
    let method = 'PUT';
    let data = 'emp_no='+emp_no+'&emp_pass='+emp_pass+'&type='+type+'&head_path='+head_path;
    xml.open(method,'/api/user');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}


