'use strict'
let number = 0;

//添加演出厅
function addStudio() {
    let studioAdd = document.getElementById('studio');
    let flag;
    let name = document.getElementById('studioName').value;
    let row = document.getElementById('sateRow').value;
    let col = document.getElementById('sateCol').value;
    let introduction = document.getElementById('studioIntroduction').value;
    let state = document.getElementById('studioState').value;
    if(state == '可用'){
        flag = 1;
    }else {
        flag = 0;
    }
    if(check() && check1() && check2() && check3() ){

    }else {
        alert("格式输入有误！");
        return false;
    }

    let method = 'POST';
    let data = 'studio_name='+name+'&studio_rows='+row+'&studio_cols='+col+'&stuio_detial='+introduction+'&flag='+flag;



    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                alert("刷新");
                get_studio();
            }else{
                alert("失败，请重试！");
            }
        }
    };

    let sss = window.location.search;


    xml.open(method,'/api/studio');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);


    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            let json = JSON.parse(xhr.responseText);
            if (json.state){
                alert("座位插入成功！");
                // get_studio();
            }else{
                alert("座位插入失败，请重试！");
            }
        }
    };


    xhr.open(method,'/api/seat');
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.send(data);

}


//鼠标点击事件
function studioRow(obj) {
    let table = document.getElementById('studio');
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
    let deleteRow = document.getElementById('studio');
    deleteRow.deleteRow(row);
}

//删除演出厅
function removeStudio() {
    let row = number;
    if(row > 0){
        // console.log(row);
        // let id = document.getElementById('delete_now').innerText;
        let mess = [];
        mess = changeRow(row);
        let id = mess[0];
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
    }else{
        let changeButton = document.getElementById('deleteStudio');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要删除的地方！'
    }
}


//获取选中行的信息
function changeRow(number) {
    let array = [];
    let table = document.getElementById('studio');
    let length = table.rows.length;
    console.log(length);
    for(let i = 0;i<length;i++){
        array[i] = table.rows[number].cells[i].innerHTML;
        console.log(array[i]);
    }
    return array
}

//将信息添加在修改信息的相应位置
function change() {
    if(number > 0){
        let change = [];
        let array = changeRow(number);
        change[0] = document.getElementById('studio_no');
        change[1] = document.getElementById('changeName');
        change[2] = document.getElementById('changeRow');
        change[3] = document.getElementById('changeCol');
        change[4] = document.getElementById('changeState');
        change[5] = document.getElementById('changeInt');
        let len = array.length;
        for(let i = 0;i<len;i++){
            change[i].value = array[i];
        }
    }
    else{
        console.log('aaa');
        let changeButton = document.getElementById('changeStudio');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！'
    }

}

//记录选择的是什么演出厅
function seat() {
    let row = number;
    var seat = [];
    seat = changeRow(row);
    // console.log(seat[0]);
    localStorage.setItem("studio_id",seat[0]);
    window.location.href = 'http://localhost:9999/mana/seat.jsp';
}
function putStudio() {
    let studio_no = document.getElementById('studio_no').value;
    let studio_name = document.getElementById('changeName').value;
    let studio_row = document.getElementById('changeRow').value;
    let studio_col = document.getElementById('changeCol').value;
    let studio_state = document.getElementById('changeState').value;
    let studio_int = document.getElementById('changeInt').value;

    if(studio_state == "可用"){
        studio_state = 1;
    }else{
        studio_state = 0;
    }
    if(check() && check1() && check2() && check3() && check4() && check5() && check6() && check7() ){

    }else {
        alert("格式输入有误！");
        return false;
    }
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                // alert("刷新");
                get_studio();
            }else{
                alert("失败，请重试！");
            }
        }
    };
    let sss = window.location.search;
    let method = 'PUT';
    let data = 'studio_name='+studio_name+'&studio_rows='+studio_row+'&studio_cols='+studio_col+'&stuio_detial='+studio_int+'&flag='+studio_state+'&id='+studio_no;
    xml.open(method,'/api/studio');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}


//添加演出厅名称
function check() {
    let aaa = document.getElementById('studioName').value;
    console.log(aaa);
    if(/^.{2,20}$/.test(aaa)){
        document.getElementById('err').innerHTML = '';
        // alert("正确");
        return true;
    }
    else{
        // alert("格式错误");
        document.getElementById('err').innerHTML = 'err:演出厅名称为2～20个字符！';
        return false;
    }
    // alert(aaa);
}

//添加演出厅行数
function check1() {
    let aaa = document.getElementById('sateRow').value;
    console.log(aaa);
    if(/^([1-9])|(1[0-5])$/.test(aaa)){
        document.getElementById('err1').innerHTML = '';
        // alert("正确");
        return true;
    }else{
        // alert("格式错误");

        document.getElementById('err1').innerHTML = 'err:演出厅中座位的行数最多为15！';
        return false;
    }
    // alert(aaa);
}

//添加演出厅列数
function check2() {
    let aaa = document.getElementById('sateCol').value;
    console.log(aaa);
    if(/^([1-9])|(1[0-5])$/.test(aaa)){
        document.getElementById('err2').innerHTML = '';
        return true;
    }else{
        document.getElementById('err2').innerHTML = 'err:演出厅列数最多为15！';
        return false;
    }
}

//添加演出厅状态
function check3() {
    let aaa = document.getElementById('studioState').value;
    console.log(aaa);
    if(/^.{2,10}$/.test(aaa)){
        document.getElementById('err3').innerHTML = '';
        return true;
    }else{
        document.getElementById('err3').innerHTML = 'err:演出厅状态最多为10个字符！';
        return false;

    }
}

function check4() {
    let aaa = document.getElementById('changeName').value;
    console.log(aaa);
    if(/^.{2,20}$/.test(aaa)){
        document.getElementById('err4').innerHTML = '';
        return true;
    }else{
        document.getElementById('err4').innerHTML = 'err:演出厅名称为2～20个字符！';
        return false;
    }
}


function check5() {
    let aaa = document.getElementById('changeRow').value;
    console.log(aaa);
    if(/^([1-9])|(1[0-5])$/.test(aaa)){
        document.getElementById('err5').innerHTML = '';
        return true;
    }else{
        document.getElementById('err5').innerHTML = 'err:演出厅中座位的行数最多为15！';
        return false;
    }
}

function check6() {
    let aaa = document.getElementById('changeCol').value;
    console.log(aaa);
    if(/^([1-9])|(1[0-5])$/.test(aaa)){
        document.getElementById('err6').innerHTML = '';
        return true;
    }else{
        document.getElementById('err6').innerHTML = 'err:演出厅列数最多15！';
        return false;
    }
}

function check7() {
    let aaa = document.getElementById('changeState').value;
    console.log(aaa);
    if(/^.{2,10}$/.test(aaa)){
        document.getElementById('err3').innerHTML = '';
        return true;
    }else{
        document.getElementById('err3').innerHTML = 'err:演出厅状态最多为10个字符！';
        return false;
    }
}

