'use strict'
let number = 0;
//var playid = 0;
//显示所有信息
function get_schedule(){
    let each_nums=document.getElementById("each_nums").value;
    let now_page=document.getElementById("now_page").innerText;
    // alert("qqqqq");
    let play_id=document.getElementById('play_id').value;
    // alert("wwwww");
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange=function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let res = JSON.parse(xhr.responseText);
            if (res.status == false) {
                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                alert("已到最后一页！");
                return;
            }
            let tbody = document.getElementById('tbody');
            tbody.innerText = '';
            let json = res.object;
            for (let i = 0; i < json.length; i++) {
                let tr = document.createElement('tr');
                let td0 = document.createElement('td');
                td0.innerText = json[i][0];
                //进行转换
                /*playid = json[i][0];
                let xhr1 = new XMLHttpRequest();
                xhr1.onreadystatechange = function () {
                    if (xhr1.readyState == 4 && xhr1.status == 200){
                        let res1 = JSON.parse(xhr1.responseText);
                        let json1 = res1.object;

                    }
                }
                xhr.open('GET','/api/schedule?id='+play_id);
                xhr.send();*/


                let td1 = document.createElement('td');
                td1.innerText = json[i][1];
                let td2 = document.createElement('td');
                td2.innerText = json[i][2];
                let td3 = document.createElement('td');
                td3.innerText = json[i][3];
                let td4 = document.createElement('td');
                td4.innerHTML="<button id=\"select_seat\" class=\"btn btn-success\" onclick=\"window.open('sale.jsp')\">选座购票</button>";
                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);

                tbody.appendChild(tr);
            }

        }
    };
    xhr.open('GET','/api/schedule?page='+now_page+'&nums='+each_nums+'&name='+play_id);
    xhr.send();

}
function other_page(offset) {
    let now_page = document.getElementById('now_page');
    if(offset < 0 && parseInt(now_page.innerText) == 1){
        //
        alert("已到第一页！");
    }else {
        now_page.innerText = parseInt(now_page.innerText) + offset;
        get_schedule();
    }
}

//添加演出计划
function addSchedule() {
    //alert("34234");
    let scheduleAdd=document.getElementById('schedule');
    let playname = document.getElementById('play_name');
    let indexPlay = playname.selectedIndex;
    let play_name = playname.options[indexPlay].value;
    let play_id=4;
    //进行转换
    let studioname = document.getElementById('studio_name');
    let indexStudio = studioname.selectedIndex;
    let studio_name = studioname.options[indexStudio].value;
    let studio_id=4;
    //进行转换
    let sched_time = document.getElementById('sched_time').value;
    let sched_ticket_price= document.getElementById('sched_ticket_price').value;
    /*if(checktime()){

    }else {
        alert("输入时间有误！");
        return false;
    }*/

    let method = 'POST';
    let data = 'play_id='+play_id+'&studio_id='+studio_id+'&sched_time='+sched_time+'&sched_ticket_price='+sched_ticket_price;

    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        // alert("aaaa");
        if (xml.readyState == 4 && xml.status == 200) {
            let json = JSON.parse(xml.responseText);
            if (json.state) {
                get_schedule();
            } else {
                alert("失败，请重试！");
            }
        }
    };
    let sss = window.location.search;


    xml.open(method,'/api/schedule');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}

function reset_get() {
    document.getElementById('now_page').innerText = '1';
    get_schedule();
}

//鼠标点击事件
function scheduleRow(obj) {
    let table = document.getElementById('schedule');
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
            console.log(num);
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
function removeSchedule() {
    let row = number;
    if(row == 0){
        // alert("请选择需要删除的地方")
        // alert(row);
        console.log('aaa');
        let changeButton = document.getElementById('deleteSchedule');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！';
    }
    else{
        let schedule = [];
        schedule = changeRow(row);
        let sched_id = user[0];
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
        xhr.open('DELETE','/api/schedule');
        xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xhr.send('id='+sched_id);
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
        let changeButton = document.getElementById('changeSchedule');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#myModals');
        let change = [];
        let array = [];
        array = changeRow(number);
        change[0] = document.getElementById('change_play').value;
        change[2] = document.getElementById('change_studio').value;
        change[3] = document.getElementById('change_time').value;
        change[4] = document.getElementById('change_price').value;
        console.log(array[1]);

        /*document.getElementById('changeName').value = array[0];
        document.getElementById('user_pass').value = array[1];
        document.getElementById('user_passA').value = array[1];*/
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
        let changeButton = document.getElementById('changeSchedule');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！'
    }

}

//获取修改框中的信息
function putSchedule() {
    let play_id = document.getElementById('changeplay').value;
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


