'use strict';
let number=0;
// function addTicket(){
//     let ticket_id = document.getElementById('ticket_id');
//     let seat_id = document.getElementById('seat_id');
//     let sched_id = document.getElementById('sched_id');
//     let ticket_price = document.getElementById('ticket_price');
//     let ticket_status = document.getElementById('')
// }
function get_ticket(){
    let each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let ticket_no = document.getElementById('ticket_no').value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse(xhr.responseText);
            if(res.status == false) {
                document.getElementById('now_page').innerText =parseInt(now_page)-1;
                alert("已到最后一页!");
                return ;
            }

            let tbody = document.getElementById('tbody');
            tbody.innerText='';
            let json = res.object;
            for(let i=0;i<json.length;i++){
                let tr = document.createElement('tr');
                let td0 = document.createElement('td');
                td0.innerText = json[i][0];
                let td1 = document.createElement('td');
                td1.innerText = json[i][1];
                let td2 = document.createElement('td');
                td2.innerText = json[i][2];
                let td3 = document.createElement('td');
                td3.innerText = json[i][3];
                let td4 = document.createElement('td');
                td4.innerText = json[i][4];
                if(json[i][4] == 9){
                    td4.innerText="已售出"
                }
                else if(json[i][4] == 1){
                    td4.innerText="待售"
                }
                else{
                    td4.innerText="锁定"
                }
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
    xhr.open('GET','/api/ticket?page='+now_page+'&nums='+each_nums+'&name='+ticket_no);
    xhr.send();
}
function reset_get() {
    document.getElementById('now_page').innerText = '1';
    get_ticket();
}
function ticketRow(obj){
    let table = document.getElementById('ticket');
    let length = table.rows.length;

    for(let i=0;i<length;i++){
        if(i==0){
            let row = table.rows[i];
            row.setAttribute('class','warning');
        }
        else if(i%2!=0 && i!=0){
            let row = table.rows[i];
            row.setAttribute('class','active');
        }
        else{
            let row = table.rows[i];
            row.setAttribute('class','default');
        }
    }
    if(event.srcElement.tagName =='TD'){
        let num = 0;
        let curRow = event.srcElement.parentElement;

        if(curRow.rowIndex==0){
            curRow.setAttribute('class','warning');
        }
        else{
            curRow.setAttribute('class','info');
            num = curRow.rowIndex ;
            console.log(num);
            number = num;
        }
    }
}
function other_page(offset){
    let now_page = document.getElementById('now_page');
    if(offset<0 && parseInt(now_page.innerText)==1){
        alert("已到第一页！");
    }
    else{
        now_page.innerText = parseInt(now_page.innerText);
        get_ticket();
    }
}
//获取选中行信息
function changeRow(number){
    let array = [];
    let table = document.getElementById('loginSeller');
    let length = table.rows.item(0).cells.length;
    console.log(length);
    if(number!=0){
        for(let i=0;i<length;i++){
            array[i]=table.rows[number].cells[i].innerHTML;
        }
        return array;
    }
}
//删除票
function removeTicket(){
    let row = number;
    if(row == 0 ){
        console.log('aaa');
        let changeButton = document.getElementById('deleteTicket');
        changeButton.setAttribute('data-toggle','modal');
        changeButton.setAttribute('data-target','#error');
        let text = document.getElementById('warning');
        text.innerHTML = '请选择需要修改的地方！';
    }
    else{
        let ticket = [];
        ticket = changeRow(row);
        let ticket_id = ticket[0];
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if(xhr.readyState == 4 && xhr.status == 200){
                if (JSON.parse(xhr.responseText).status){
                    get_ticket();
                }else {
                    alert("删除失败");
                }
            }
        };
        xhr.open('DELETE','/api/ticket');
        xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded")
        xhr.send('id='+ticket_id);
    }
}
function change(){
    if(number >0){
        let changeButton = document.getElementById('changeTicket');
        changeButton.setAttribute('data-toggle','modal');
        changeButton.setAttribute('data-target','#myModals');
        let change = [];
        let array = [];
        array = changeRow(number);
        change[0] = document.getElementById('changeName').value;
        change[1] = document.getElementById('').value;
        change[2] = document.getElementById('').value;
        change[3] = document.getElementById('').value;
        change[4] = document.getElementById('').value;
        change[5] = document.getElementById('').value;

        console.log(array[1]);
    }
    //当选中框没有内容时
    else{
        let changeButton = document.getElementById('changeButton');
        changeButton.setAttribute('data-toggle','modal');
        changeButton.setAttribute('data-target','#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！';
    }
}
//获取修改框中的信息
function putTicket(){
    let ticket_id = document.getElementById('changeTicketName').value;
    let seat_id = document.getElementById('changeSeatName').value;
    let sched_id = document.getElementById('changeSchedName').value;
    let ticket_price = document.getElementById('ticket_price').value;
    let ticket_status = document.getElementById('ticket_status').value;
    let ticket_locket_time = document.getElementById('ticket_locked_time').value;


}