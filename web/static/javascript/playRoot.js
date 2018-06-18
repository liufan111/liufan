'use strict'
let number = 0;
var each_nums;
var playid = 0;
var studioid=0;
let sid;
let pid;
var list=[];
var list1=[];
var sname=null;
var csid=0;
var flag=false;
var pname=[];
var sname=[];
var time=[];
var len=[];
var play=[];
var studio=[];
let T=false;
let TT=false;
//显示所有信息
function get_schedule(){
    let each_nums=document.getElementById("each_nums").value;

    let now_page=document.getElementById("now_page").innerText;
    let play_id=document.getElementById('play_id').value;
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
                let td1 = document.createElement('td');
                studioid=json[i][1];
                //进行转换
                if(studioid!=0) {
                    //console.log("id是："+playid);
                    let xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            let res = JSON.parse(xhr.responseText);
                            let json = res.object;
                            td1.innerText = json[1];
                        }
                    }
                    xhr.open('GET', '/api/studio?id=' + studioid);
                    xhr.send();
                }


                let td2 = document.createElement('td');
                let td4 = document.createElement('td');
                playid=json[i][2];
                //进行转换
                if(playid!=0) {
                    //console.log("id是："+playid);
                    let xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            let res = JSON.parse(xhr.responseText);
                            let json = res.object;
                            //alert(json.length);
                            td2.innerText = json[3];
                            td4.innerText=json[7];
                            //console.log("剧目名称是什么" + pname);

                        }
                    }
                    xhr.open('GET', '/api/play?id=' + playid);
                    xhr.send();
                }



                let td3 = document.createElement('td');
                td3.innerText = json[i][3];

                //td4.innerText = json[i][4];
                let td5 = document.createElement('td');

                // td5.innerHTML="<button class=\"btn btn-success\" onclick=Studio_Play()>选座购票</button>";
                let button = document.createElement('button');
                button.innerText = '选座购票';
                button.setAttribute('class','btn btn-success');
                td5.appendChild(button);
                button.addEventListener('click',function aaa() {
                    //alert("aaaaaaaa");
                    var sched_id = json[i][0];
                    var studio_id = json[i][1];
                    //alert(sched_id+","+studio_id);
                    //console.log("sched_id:"+sched_id);
                    //console.log("studio_id:"+studio_id);
                    //定义对象object
                    //给object添加键值对
                    localStorage.setItem('sched_id',sched_id);
                    localStorage.setItem("studio_id",studio_id);
                    window.location.href = 'http://localhost:9999/seller/sellTicket.jsp';
                });
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
    xhr.open('GET','/api/schedule?page='+now_page+'&nums='+each_nums+'&name='+play_id);
    xhr.send();

}

function chazhao() {
    let ppid;
    let each_nums=document.getElementById("each_nums").value;
    let now_page=document.getElementById("now_page").innerText;
    let play_name=document.getElementById('play_id').value;
    for(let i=0;i<pname.length;i++){
        if(pname[i]==play_name){
            ppid=i;
            console.log("id"+ppid);
            break;
        }
    }
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
                let td1 = document.createElement('td');
                studioid=json[i][1];
                //进行转换
                if(studioid!=0) {
                    let xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            let res = JSON.parse(xhr.responseText);
                            let json = res.object;
                            td1.innerText = json[1];
                        }
                    }
                    xhr.open('GET', '/api/studio?id=' + studioid);
                    xhr.send();
                }
                let td2 = document.createElement('td');
                let td4 = document.createElement('td');
                playid=json[i][2];
                //进行转换
                if(playid!=0) {
                    let xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            let res = JSON.parse(xhr.responseText);
                            let json = res.object;
                            td2.innerText = json[3];
                            td4.innerText=json[7];

                        }
                    }
                    xhr.open('GET', '/api/play?id=' + playid);
                    xhr.send();
                }
                let td3 = document.createElement('td');
                td3.innerText = json[i][3];
                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tbody.appendChild(tr);
            }
        }
    };
    xhr.open('GET','/api/schedule?page='+now_page+'&nums='+each_nums+'&name='+ppid);
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

window.onload=function() {
    console.log("获取演出厅名称");
    let each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let res = JSON.parse(xhr.responseText);
            if (res.status == false) {
                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                alert("已到最后一页!");
                return;
            }
            let studio_name = document.getElementById("studio_name");
            studio_name.innerText = '';
            let json = res.object;
            for (let i = 0; i < json.length; i++) {
                sname[json[i][0]]=json[i][1];
                console.log("演出厅id"+json[i][0]+"演出厅名字"+sname[json[i][0]]);
                if(json[i][5]==1) {
                    let option = document.createElement("option");
                    option.innerText = json[i][1];
                    list[i] = option.value;
                    studio_name.appendChild(option);
                }
            }
        }
    };
    xhr.open('GET', '/api/studio');
    xhr.send();


    console.log("获取剧目名称");
    let xhr1 = new XMLHttpRequest();
    xhr1.onreadystatechange = function () {
        if (xhr1.readyState == 4 && xhr1.status == 200) {
            let res1 = JSON.parse(xhr1.responseText);
            if (res1.status == false) {
                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                alert("已到最后一页!");
                return;
            }
            let play_name = document.getElementById("play_name");
            play_name.innerText = '';
            let json = res1.object;
            for (let i = 0; i < json.length; i++) {
                let option = document.createElement("option");
                option.innerText = json[i][3];
                list1[i] = option.value;
                play_name.appendChild(option);
            }
        }
    };
    xhr1.open('GET', '/api/play');
    xhr1.send();


    console.log("获取");
    let xhr2 = new XMLHttpRequest();
    xhr2.onreadystatechange = function () {
        if (xhr2.readyState == 4 && xhr2.status == 200) {
            let res2 = JSON.parse(xhr2.responseText);
            if (res2.status == false) {
                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                alert("已到最后一页!");
                return;
            }
            let json2 = res2.object;
            for (let i = 0; i < json2.length; i++) {
                pname[json2[i][0]]=json2[i][3];
                len[json2[i][0]]=json2[i][6];
                console.log("id"+json2[i][0]+"剧目"+json2[i][3]+"时长"+json2[i][6]);
            }
        }
    };
    xhr2.open('GET', '/api/play');
    xhr2.send();


    console.log("获取时间");
    let xhr3 = new XMLHttpRequest();
    xhr3.onreadystatechange = function () {
        if (xhr3.readyState == 4 && xhr3.status == 200) {
            let res3 = JSON.parse(xhr3.responseText);
            if (res3.status == false) {
                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                alert("已到最后一页!");
                return;
            }
            let json3 = res3.object;
            for (let i = 0; i < json3.length; i++) {
                time[json3[i][0]]=json3[i][3];
                studio[json3[i][0]]=json3[i][1];
                play[json3[i][0]]=json3[i][2];
                console.log("获取的时间"+time[json3[i][0]]+"演出厅"+studio[json3[i][0]]+"剧目"+play[json3[i][0]]);
            }
        }
    };
    xhr3.open('GET', '/api/schedule');
    xhr3.send();
}

//添加演出计划
function addSchedule() {

    checkTime();
    if (T) {
        alert("无法添加");
    } else {
        let studioname = document.getElementById('studio_name');
        let indexStudio = studioname.selectedIndex;
        let studio_name = studioname.options[indexStudio].value;
        //进行转换
        let name = studio_name;
        let time = document.getElementById('sched_time').value;
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let res = JSON.parse(xhr.responseText);
                if (res.status == false) {
                    document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                    alert("已到最后一页!");
                    return;
                }
                let json = res.object;
                for (let i = 0; i < json.length; i++) {
                    sid = json[i][0];
                    console.log("演出厅id" + sid);
                    let playname = document.getElementById('play_name');
                    let indexPlay = playname.selectedIndex;
                    let play_name = playname.options[indexPlay].value;
                    let pname = play_name;
                    let xhr1 = new XMLHttpRequest();
                    xhr1.onreadystatechange = function () {
                        if (xhr1.readyState == 4 && xhr1.status == 200) {
                            let res1 = JSON.parse(xhr1.responseText);
                            if (res1.status == false) {
                                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                                alert("已到最后一页!");
                                return;
                            }
                            let json1 = res1.object;
                            for (let i = 0; i < json1.length; i++) {
                                pid = json1[i][0];
                                let price = json1[i][7]
                                console.log("票价" + price);
                                let method = 'POST';
                                let data = 'play_id=' + pid + '&studio_id=' + sid + '&sched_time=' + time + '&sched_ticket_price=' + price;
                                let xml = new XMLHttpRequest();
                                xml.onreadystatechange = function () {
                                    if (xml.readyState == 4 && xml.status == 200) {
                                        let json2 = JSON.parse(xml.responseText);
                                        if (json2.state) {
                                            get_schedule();
                                        } else {
                                            alert("失败，请重试！");
                                        }
                                    }
                                };
                                let sss = window.location.search;
                                xml.open(method, '/api/schedule');
                                xml.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                xml.send(data);
                            }
                        }
                    };
                    xhr1.open('GET', '/api/play?name=' + pname);
                    xhr1.send();
                }
            }
        };
        xhr.open('GET', '/api/studio?name=' + name);
        xhr.send();
    }
}

function reset_get() {
    document.getElementById('now_page').innerText = '1';

    chazhao();
    getScheduleCount();
}

function getScheduleCount() {
    let each_nums=document.getElementById("each_nums").value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let res = JSON.parse((xhr.responseText));
            if (res.status == false) {
                alert("获取演出计划失败");
            } else {
                var scheduleCount = res.object.length;
                //alert(scheduleCount+"ttt");
                var pagesCount = Math.ceil(scheduleCount / each_nums);
                var pages = document.getElementById("pages");
                pages.innerText = "共" + pagesCount + "页";
                console.log("演出计划的总页数:" + pagesCount);
            }
        }
    };
    xhr.open('GET', '/api/schedule');
    xhr.send();
}

//鼠标点击事件
function scheduleRow(obj) {
    let table = document.getElementById('schedule');
    let length = table.rows.length;

    for (let i = 0; i < length; i++) {
        if (i == 0) {
            let row = table.rows[i];
            row.setAttribute('class', 'warning');
        }
        else if (i % 2 != 0 && i != 0) {
            let row = table.rows[i];
            row.setAttribute('class', 'active');
        } else {
            let row = table.rows[i];
            row.setAttribute('class', 'default');
        }
    }

    if (event.srcElement.tagName == 'TD') {
        let num = 0;
        let curRow = event.srcElement.parentElement;
        //let rowContainer = curRow.innerHTML;

        if (curRow.rowIndex == 0) {
            curRow.setAttribute('class', 'warning');
            return 0;
        }
        else {
            curRow.setAttribute('class', 'info');
            num = curRow.rowIndex;
            console.log(num);
            number = num;
        }
    }

}

//删除某一行
function deleteRow(row) {
    let deleteRow = document.getElementById('schedule');
    deleteRow.deleteRow(row);

}

function del_schedule() {

    let id = document.getElementById('delete_now').innerText;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            if (JSON.parse(xhr.responseText).status) {
                get_schedule();
            } else {
                alert("删除失败！");
            }
        }
    };
    xhr.open('DELETE', '/api/schedule');
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send('id=' + id);
}


//删除演出计划
function removeSchedule() {
    let row = number;
    if (row > 0) {
        let mess = [];
        mess = changeRow(row);
        let id = mess[0];
        //alert("演出计划id"+id);
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                if (JSON.parse(xhr.responseText).status) {
                    get_schedule();
                    //getScheduleCount();
                } else {
                    alert("删除失败！");
                }
            }
        };
        xhr.open('DELETE', '/api/schedule');
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send('id=' + id);
    } else {
        let changeButton = document.getElementById('deleteSchedule');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要删除的地方！'
    }
}


//获取选中行的信息
function changeRow(number) {
    let array = [];
    let table = document.getElementById('schedule');
    let length = table.rows.length;
    //alert(length);
    console.log(length);
    for (let i = 0; i < 5; i++) {
        array[i] = table.rows[number].cells[i].innerHTML;
    }
    console.log(array[0]);
    return array
}

//将信息添加在修改信息的相应位置
function change() {
    let studio = document.getElementById('change_studio');
    studio.innerText = '';
    for (let i = 0; i < list.length; i++) {
        let option = document.createElement('option');
        option.innerText = list[i];
        studio.appendChild(option);
        //alert(option.value);
    }
    let play = document.getElementById('change_play');
    play.innerText = '';
    for (let i = 0; i < list1.length; i++) {
        let option = document.createElement('option');
        option.innerText = list1[i];
        play.appendChild(option);
    }
    if (number > 0) {
        let change_id = document.getElementById('change_id');
        let change_studio = document.getElementById('change_studio');
        let change_play = document.getElementById('change_play');
        let change_time = document.getElementById('change_time');
        let change_price = document.getElementById('change_price');
        let array = changeRow(number);
        console.log(array[0]);
        change_id.value = array[0];
        change_studio.value = array[1];
        change_play.value= array[2];
        change_time.value = array[3];
        change_price.value = array[4];
    }
    else {
        console.log('aaa');
        let changeButton = document.getElementById('changeSchedule');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#myModals');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！'
    }

}

//获取修改框中的信息
function putSchedule() {
    checkTime1();
    console.log(TT);
    if(TT){
        alert("时间冲突，无法修改");
    }else {

        let sched_id = document.getElementById('change_id').value;
        console.log("没有id吗" + sched_id);

        let studioname = document.getElementById('change_studio');
        let indexStudio = studioname.selectedIndex;
        sname = studioname.options[indexStudio].value;
        //alert(sname);
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                let res = JSON.parse(xhr.responseText);
                if (res.status == false) {
                    document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                    alert("已到最后一页!");
                    return;
                }
                let json = res.object;
                //alert("长度" + json.length);

                for (let i = 0; i < json.length; i++) {
                    csid = json[i][0];
                    console.log("演出厅id" + csid);
                    let playname = document.getElementById('change_play');
                    let indexPlay = playname.selectedIndex;
                    let pname = playname.options[indexStudio].value;
                    console.log("剧目名称" + pname);
                    let xhr1 = new XMLHttpRequest();
                    xhr1.onreadystatechange = function () {
                        if (xhr1.readyState == 4 && xhr1.status == 200) {
                            let res1 = JSON.parse(xhr1.responseText);
                            if (res1.status == false) {
                                document.getElementById('now_page').innerText = parseInt(now_page) - 1;
                                alert("已到最后一页!");
                                return;
                            }
                            let json1 = res1.object;
                            /*alert("长度"+json.length);*/

                            for (let i = 0; i < json1.length; i++) {
                                let psid = json1[i][0];
                                console.log("剧目id" + psid);
                                let sched_time = document.getElementById("change_time").value;
                                console.log("修改后的时间" + sched_time);
                                let sched_ticket_price = document.getElementById("change_price").value;
                                console.log("修改后的票价" + sched_ticket_price);
                                let xml = new XMLHttpRequest();
                                xml.onreadystatechange = function () {
                                    if (xml.readyState == 4 && xml.status == 200) {
                                        let json3 = JSON.parse(xml.responseText);
                                        if (json3.state) {
                                            get_schedule();
                                        } else {
                                            alert("失败，请重试！");
                                        }
                                    }
                                };
                                let sss = window.location.search;
                                let method = 'PUT';
                                let data = 'sched_id=' + sched_id + '&studio_id=' + csid + '&play_id=' + psid + '&sched_time=' + sched_time + '&sched_ticket_price=' + sched_ticket_price;
                                xml.open(method, '/api/schedule');
                                xml.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                                xml.send(data);

                            }
                        }
                    };
                    xhr1.open('GET', '/api/play?name=' + pname);
                    xhr1.send();
                }
            }
        };
        xhr.open('GET', '/api/studio?name=' + sname);
        xhr.send();

    }
}

//时间判断
function checkTime() {
    let Time=document.getElementById('sched_time').value;
    let tt=new Date(Time).getTime();
    console.log("添加时间"+ new Date(Time).getTime());
    let studioname = document.getElementById('studio_name');
    let indexStudio = studioname.selectedIndex;
    let studio_name = studioname.options[indexStudio].value;
    let sid;
    let schid=[];
    let t=[];
    //查找演出厅id
    for(let i=0;i<sname.length;i++){
        if(sname[i]==studio_name){
            sid=i;
        }
    }
    let x=0;
    //查找演出计划id
    for (let i=0;i<studio.length;i++){
        if(studio[i]==sid){
            schid[x]=i;
            console.log("演出厅对应的演出计划："+schid[x]);
            x++;
        }
    }
    //存时间
    for(let i=0;i<schid.length;i++){
        let min=new Date(time[schid[i]]).getTime();
        let max=(60000*len[play[schid[i]]])+min;
        console.log("最小"+min+"最大"+max);
        if(tt>=min&&tt<=max){
            alert("时间冲突")
            T=true;
            break;
        }
    }

}


function checkTime1() {
    let Time = document.getElementById('change_time').value;
    let xid = document.getElementById('change_id').value;

    let tt = new Date(Time).getTime();
    console.log("修改时间" + new Date(Time).getTime()+"修改id"+xid);
    let studioname = document.getElementById('change_studio');
    let indexStudio = studioname.selectedIndex;
    let studio_name = studioname.options[indexStudio].value;
    let sid;
    let schid = [];
    let t = [];
//查找演出厅id
    for (let i = 0; i < sname.length; i++) {
        if (sname[i] == studio_name) {
            sid = i;
        }
    }
    let x = 0;
//查找演出计划id
    for (let i = 0; i < studio.length; i++) {
        if (studio[i] == sid&&(i!=xid)) {
            schid[x] = i;
            console.log("演出厅对应的演出计划：" + schid[x]);
            x++;
        }
    }
//存时间
    for (let i = 0; i < schid.length; i++) {
        let min = new Date(time[schid[i]]).getTime();
        let max = (60000 * len[play[schid[i]]]) + min;
        console.log("最小" + min + "最大" + max);
        if (tt >= min && tt <= max) {
            alert("时间冲突")
            TT = true;
            break;
        }
    }
}