'use strict'
let number = 0;
var each_nums = 0;
// function check1() {
//     let pass = document.getElementById('pass').value;
//     let a = /^[0-9a-zA-Z]{6,20}$/.test(pass);
//     if(!a){
//         document.getElementById('err1').innerHTML = '密码需要6～20位的任意字母和数字组合'
//         return false;
//     }else{
//         document.getElementById('err').innerHTML = '';
//         return true;
//     }
// }
function check(){
    let pass = document.getElementById('pass').value;
    let passAgain = document.getElementById('passAgain').value;
    let a = /^[0-9a-zA-Z]{6,20}$/.test(pass);
    //let b = /^[a-zA-Z0-9]{6,20}$/.test(passAgain);
    if(pass == passAgain){
        if(!a){
            document.getElementById('err').innerHTML = '密码需要6～20位的任意字母和数字组合'
            return false;
        }else{
            document.getElementById('err').innerHTML = '';
            return true;
        }
    }else{
        document.getElementById('err').innerHTML = '两次输入的密码不一致';
        return false;
    }
}
function check1() {
    var pass_a = document.getElementById('user_pass').value;
    var pass_b = document.getElementById('user_passA').value;
    var aa = /^[0-9a-zA-Z]{6,20}$/.test(pass_a);
    if (pass_a!=pass_b){
        document.getElementById('err1').innerHTML = '两次输入的密码不一致';
        return false

    }else {
        if (!aa){
            document.getElementById('err1').innerHTML = '密码需要6～20位的任意字母和数字组合'
            return false;
        }else {
            document.getElementById('err1').innerHTML = '';
            return true;
        }
    }
}

function getUserCount() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse((xhr.responseText));
            if (res.status == false){
                alert("获取登录用户失败");
            }else {
                var userCount = res.object.length;
                console.log("登录用户总数:"+userCount);
                var pagesCount = Math.ceil(userCount/each_nums);
                var pages = document.getElementById("pages");
                pages.innerText = "共"+pagesCount+"页";
                console.log("登录用户的总页数："+pagesCount);
            }
        }
    };
    xhr.open('GET','/api/user');
    xhr.send();
}
//显示所有信息
function get_user() {
    each_nums = document.getElementById('each_nums').value;
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
                td0.innerText = json[i][3];
                // td0.setAttribute('style','display: none;');
                let td1 = document.createElement('td');
                td1.innerText = json[i][0];
                let td2 = document.createElement('td');
                td2.innerText = json[i][1];
                let td3 = document.createElement('td');
                if(json[i][2] == 1){
                    td3.innerText = "管理员";
                }else if(json[i][2] == 0){
                    td3.innerText = "经理";
                }else {
                    td3.innerHTML = "售票员";
                }

                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
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
    // let user_no = document.getElementById('user_name').value;
    let empName = document.getElementById('empName');
    let indexeN = empName.selectedIndex;
    let emp_no = empName.options[indexeN].value;
    let job = document.getElementById('Job');
    let indexJob = job.selectedIndex;
    let type = job.options[indexJob].value;
    if (type == 'manager'){
        type =1;
    }else if (type == 'saler'){
        type = -1;
    }else
        type = 0;
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
                getUserCount();
                window.location.href = 'http://localhost:9999/admin/user.jsp';

            } else {
                alert("失败，请重试！");
            }
        }
    };
    let sss = window.location.search;
    let method = 'POST';
    let data = 'emp_no='+emp_no+'&emp_pass='+user_pass+'&type='+type+'&head_path='+head_path;
    xml.open(method,'/api/user');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}

function reset_get() {
    document.getElementById('now_page').innerText = '1';
    get_user();
    getUserCount();
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
        let changeButton = document.getElementById('deleteStudio');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！';
    }
    else{
        let user = [];
        user = changeRow(row);
        let emp_no = user[1];
        console.log(user);
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if(xhr.readyState == 4 && xhr.status == 200){
                if (JSON.parse(xhr.responseText).status){
                    get_user();
                    getUserCount();
                    window.location.href = 'http://localhost:9999/admin/user.jsp';
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

        console.log(array);
        //设置所选中的性别为默认
        if(array[3] == '经理'){
            document.getElementById('changeJob').value = 'boss';
        }else if(array[3] == '管理员'){
            document.getElementById('changeJob').value = 'manager';
        }else {
            document.getElementById('changeJob').value = 'sealer';
        }
        document.getElementById('changeName').value = array[0];
        document.getElementById('user_pass').value = array[2];
        document.getElementById('user_passA').value = array[2];
        // alert("array[0]:"+array[0]+"array[1]:"+array[1]+"array[2]:"+array[2] +"array[3]:"+array[3]);
        // alert(array[3])
        change[0] = array[0];
        // array[2]为密码
        change[2] = array[2];
        change[3] = array[2];
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
    let array = [];
    array = changeRow(number);
    console.log(array);
    let emp_no =array[1];
    let emp_pass = document.getElementById("user_pass").value;
    let job = document.getElementById("changeJob");
    let index = job.selectedIndex;
    let type = job.options[index].value;
    // alert(type);
    if(type == "manager"){
        type = 1;
    }else if (type == 'boss'){
        type = 0;
    }else {
        type = -1;
    }
    // alert(type);
    let head_path = "";
    if(check1()){

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
                getUserCount();
                // window.location.href = 'http://localhost:9999/admin/user.jsp';

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

