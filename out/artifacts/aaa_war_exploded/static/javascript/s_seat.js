'use strict';
//修改票的状态

var row = 0;
var col = 0;
var changRow = 0;
var changeCol = 0;

let arr = new Array();
let array = [];
let n=0;
var ticket_status = 1;
var sale_type;
var sale_status;

var emp_id;
var sale_time;
var sale_payment;
var sale_change;
var saletype;
var salestatus;

var timeout = 5;
var date = new Date();
var nowdate = new Date();

var seat_id;
var ticket_id;
var sched_id;
var studio_id
var sale_id;
var ticketstatus;
var count=0;

function aa() {
    //console.log('sched_id:'+localStorage.getItem('sched_id'));
    studio_id = localStorage.getItem('studio_id');
    console.log('studio_id:'+localStorage.getItem('studio_id'));
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function(){
        if (xml.readyState == 4 && xml.status == 200){
            let res = JSON.parse(xml.responseText);
            //let json = res.object;
            //getstudio();
            //console.log("json:"+res);
            row = res.object[2];
            //alert(row);
            col = res.object[3];
            //alert(col);
            getTicketStatus();
        }
    }
    xml.open('GET','/api/studio?id='+studio_id);
    xml.send();
}

//获取票的状态
function getTicketStatus(){
    let sched_id = localStorage.getItem('sched_id');
    let each_nums = 100;
    let now_page = 1;
    let name='';
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            //alert(xhr.responseText);
            let res = JSON.parse(xhr.responseText);
            if(res.status == false) {
                alert("失败!");
                return ;
            }
            // console.log(res);
            let aa = res.object;
            // console.log('aa'+aa);
            let ticketCount = res.object.length;
            for(let i=0;i<ticketCount;i++){
                if(aa[i][2] == sched_id){
                    if(aa[i][4]==1 && aa[i][5]!=null){
                        date = new Date(aa[i][5].replace(/-/,"/"));
                        nowdate = new Date(getNowFormatDate().replace(/-/,"/"));
                        if((nowdate.getTime()-date.getTime())/1000>60){
                            //aa[i][4] = 0;
                            let xml0 = new XMLHttpRequest();
                            xml0.onreadystatechange = function () {
                                if (xml0.readyState == 4 && xml0.status == 200) {
                                    let json0 = JSON.parse(xml0.responseText);
                                    if (json0.state) {
                                        var seat = document.getElementById("seat");
                                    }
                                }
                            };
                            let data = 'seat_id='+aa[i][1]+'&ticket_status='+0+'&sched_id='+aa[i][2];
                            xml0.open('PUT','/api/lock');
                            xml0.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                            xml0.send(data);
                        }
                    }
                    arr[n++] = aa[i][4];
                    // console.log(arr);
                }
            }
            createState();
        }
    };
    xhr.open('GET','/api/jing?page='+now_page+'&nums='+each_nums+'&name='+name);
    xhr.send();
}
function createState(){
    let studio_id = localStorage.getItem('studio_id');
    for (let i = 1;i<=row;i++) {
        var div = document.createElement("div");
        div.setAttribute("class","div1");

        for (let j = 1; j <= col; j++) {
            var oneSeat = document.createElement('div');
            oneSeat.setAttribute('class', 'oneSeat');
            oneSeat.style.paddingLeft = '60px';
            oneSeat.style.paddingTop = '28px';
            let seatImage = document.createElement('img');
            let num = document.createElement('span');
            num.setAttribute('class', 'number');
            num.innerText = i + ',' + j;
            seatImage.setAttribute('class', 'seatImg');
            oneSeat.style.paddingLeft = '50px';
            seatImage.style.height = "42px";
            seatImage.style.width = "42px";
            seatImage.setAttribute("id", "img" + j + i);
            // seatImage.setAttribute('value',json[i][1]);

            if (arr[((i-1)*col)+(j-1)] == 9) {
                seatImage.src = '/static/image/white.png';
            } else if (arr[((i-1)*col)+(j-1)] == 0) {
                seatImage.src = '/static/image/blue.png';
            } else if (arr[((i-1)*col)+(j-1)] == 1) {
                seatImage.src = '/static/image/yellow.png';
            }else if(arr[((i-1)*col)+(j-1)]==-1){                    //此票拒售
                seatImage.src = '/static/image/bad.png';
            }

            oneSeat.appendChild(seatImage);
            oneSeat.appendChild(num);
            div.appendChild(oneSeat);
            document.getElementById("seat").appendChild(div);

            seatImage.addEventListener('click', function changeSate() {
                changRow = i;
                changeCol = j;
                let xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        let res = JSON.parse(xhr.responseText);
                        //console.log(res);
                        let json = res.object;
                        //console.log(json);
                        //alert(json);
                        seat_id=json[0][1];
                        localStorage.setItem('seat_id',seat_id);
                    }
                }
                xhr.open('GET','/api/last?id='+studio_id+'&seat_row='+changRow+'&seat_column='+changeCol);
                xhr.send();
                let where = document.getElementById('title');
                where.innerHTML = '已选择第' + i + '行第' + j + '列的票：';
            });

            seatImage.setAttribute('data-toggle', 'modal');
            seatImage.setAttribute('data-target', '#myModal');

            let changeBtn = document.getElementById('sure');
                changeBtn.onclick = function changeState() {
                    emp_id = document.getElementById('emp_id').value;
                    console.log(emp_id);
                    sale_time = document.getElementById('d421').value;
                    console.log(sale_time);
                    sale_payment = document.getElementById('sale_payment').value;
                    console.log(sale_payment);
                    sale_change = document.getElementById('sale_change').value;
                    console.log(sale_change);
                    saletype = document.getElementById('sale_type').value;
                    if (saletype == 'type1') {
                        sale_type = 1;
                    } else {
                        sale_type = -1;
                    }
                    console.log(sale_type);
                    salestatus = document.getElementById('sale_status').value;
                    if (salestatus == 'status2') {
                        sale_status = 0;
                    } else {
                        sale_status = 1;
                    }
                    console.log(sale_status);

                    let seatState = document.getElementById('ticketState');
                    let newTcket = document.getElementById("img" + changeCol + changRow);
                    let index = seatState.selectedIndex;
                    let value = seatState.options[index].value;
                    if (value == 'active') {
                        ticket_status = 0;
                        newTcket.src = '/static/image/blue.png';
                        console.log("active");
                    } else if (value == 'nactive') {
                        ticket_status = 9;
                        newTcket.src = '/static/image/white.png';
                        console.log("nactive");
                    }
                    if (value == 'lock') {
                        ticket_status = 1;
                        newTcket.src = '/static/image/yellow.png';
                        console.log("lock");
                    }
                    getticketid();
                    changeTicket();
                    getSaleid();
                    InsertSale();
                }
        }
    }
    var seatWidth = document.getElementById('seat');
    if (col < 5 || row < 5){
        seatWidth.style.width = (80+42) * col +80;
    }else {
        seatWidth.style.width = (32+60)* col +60;
    }
}

function changeTicket(){
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function (){
        if (xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.status){
                var ticket = document.getElementById("seat");
            }else {
                //alert("失败刷新重试");
            }
        }
    };
    let sched_id = localStorage.getItem("sched_id");
    let ticket_id = localStorage.getItem('ticket_id');
    let method = "PUT";
    let data = "sched_id="+sched_id+'&ticket_status='+ticket_status+'&seat_id='+seat_id
        +"&emp_id="+emp_id+'&sale_time=' +sale_time+'&sale_payment='+sale_payment
        +'&sale_change='+sale_change +'&sale_type='+sale_type+'&sale_status='+sale_status;
    //'&ticket_id='+ticket_id+'&sale_id='+sale_id;
    console.log(data);
    xml.open(method,'/api/jing');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}
function getticketid() {
    let seat_id = localStorage.getItem('seat_id');
    let sched_id = localStorage.getItem("sched_id");
    //alert("seatid:"+seat_id);
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function(){
        if (xml.readyState == 4 && xml.status == 200){
            let res = JSON.parse(xml.responseText);
            let json = res.object;
            //alert(json);
            ticket_id = json[0][0];
            //alert("ticketid:"+ticket_id);
            localStorage.setItem('ticket_id',ticket_id)
        }
    }
    xml.open('GET','/api/fight?id='+seat_id+'&sched_id='+sched_id);
    xml.send();
}
function InsertSale() {
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function (){
        if (xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.status){
                var ticket = document.getElementById("seat");
            }else {
                //alert("失败刷新重试");
            }
        }
    };
    let ticket_id = localStorage.getItem('ticket_id');
    let sale_id = localStorage.getItem('sale_id');
    //alert("sale_id:"+sale_id);
    let method = "PUT";
    let data = "ticket_id="+ticket_id+'&sale_id='+sale_id+'&sale_payment='+sale_payment;
    console.log(data);
    xml.open(method,'/api/first');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}
function getSaleid(){
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function(){
        if (xml.readyState == 4 && xml.status == 200){
            let res = JSON.parse(xml.responseText);
            let json = res.object;
            //alert(json);
            sale_id = json[json.length-1][0];
            alert("saleid:"+sale_id);
            localStorage.setItem('sale_id',sale_id);
        }
    }
    xml.open('GET','/api/first?emp_id='+emp_id+'&sale_time='
        +sale_time+'&sale_payment='+sale_payment+'&sale_change='+sale_change
        +'&sale_type='+sale_type+'&sale_status='+sale_status);
    xml.send();
}