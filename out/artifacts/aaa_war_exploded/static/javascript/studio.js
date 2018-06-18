'use strict'
let number = 0;
let studio_id = 0;

//添加演出厅
function addStudio() {
    let studioAdd = document.getElementById('studio');
    let name = document.getElementById('studioName').value;
    let row = document.getElementById('sateRow').value;
    let col = document.getElementById('sateCol').value;
    let introduction = document.getElementById('studioIntroduction').value;
    let flag = 1;
    // if(state == '可用'){
    //     flag = 1;
    // }else {
    //     flag = 0;
    // }
    if(check() && check1() && check2()  ){

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
                get_studio();
                getStudioCount();
            }else{
                alert("失败，请重试！");
            }
        }
    };

    let sss = window.location.search;

    xml.open(method,'/api/studio');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
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
    console.log("number:"+number);

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
                    getStudioCount();
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
    let length =  table.rows[number].cells.length;
    console.log('信息的多少：'+length);
    for(let i = 0;i<length;i++){
        array[i] = table.rows[number].cells[i].innerText;
    }
    console.log('studio_mess:'+array);
    studio_id = array[0];
    return array
}

//将信息添加在修改信息的相应位置
function change() {
    if(number > 0){
        let change = [];
        let array = changeRow(number);
        // console.log("演出厅信息"+array);
        // change[0] = document.getElementById('studio_no').value = array[0];
        change[1] = document.getElementById('changeName').value = array[1];
        change[2] = document.getElementById('changeRow').value = array[2];
        change[3] = document.getElementById('changeCol').value = array[3];
        if(array[4] == '' || array[4] == null){
            change[4] = document.getElementById("changeState").value = '';
        }else {
            change[4] = document.getElementById("changeState").value = array[4];
        }
        if (array[5] == '已生成座位'){
            document.getElementById('normal').setAttribute('selected','selected');
        }else {
            document.getElementById('broken').setAttribute('selected','selected');
        }

    }
    else{
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
    if (row >= 1){
        var seat = [];
        seat = changeRow(row);
        localStorage.setItem("studio_id",seat[0]);
        window.location.href = 'http://localhost:9999/admin/seat.jsp';
    }else{
        let changeButton = document.getElementById('seat');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要管理座位的演出厅的地方！'
    }

}
function putStudio() {
    var aaa = [];
    // let studio_no = document.getElementById('studio_no').value;
    let studio_name = document.getElementById('changeName').value;
    let studio_row = document.getElementById('changeRow').value;
    let studio_col = document.getElementById('changeCol').value;
    let studio_state = document.getElementById('changeInt').value;
    let studio_int = document.getElementById('changeState').value;

    if(studio_state == "normal"){
        studio_state = 1;
    }else{
        studio_state = 0;
    }
    if( check4() && check5() && check6()  ){

    }else {
        alert("格式输入有误！");
        return false;
    }
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                get_studio();
            }else{
                alert("失败，请重试！");
            }
        }
    };

    let sss = window.location.search;
    let method = 'PUT';
    let data = 'studio_name='+studio_name+'&studio_rows='+studio_row+'&studio_cols='+studio_col+'&stuio_detial='+studio_int+'&flag='+studio_state+'&id='+studio_id;
    xml.open(method,'/api/studio');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}


//添加演出厅名称
function check() {
    let aaa = document.getElementById('studioName').value;
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
    if(/^([1-9])|(1[0-5])$/.test(aaa)){
        document.getElementById('err2').innerHTML = '';
        return true;
    }else{
        document.getElementById('err2').innerHTML = 'err:演出厅列数最多为15！';
        return false;
    }
}


function check4() {
    let aaa = document.getElementById('changeName').value;
    if(/^.{2,20}$/.test(aaa)){
        document.getElementById('err4').innerHTML = '';
        return true;
    }else{
        console.log("演出厅名称格式错误");
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
        console.log("演出厅行数格式错误");
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
        console.log("演出厅列数格式错误");
        document.getElementById('err6').innerHTML = 'err:演出厅列数最多15！';
        return false;
    }
}
