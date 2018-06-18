'use strict'
let number = 0;
var each_nums=0;
var playid = 0;
let sid;
var list=[];
var sname=null;
var csid=0;
var flag=false;
var sched_id;
var studio_id;

//显示所有信息
// function Studio_Play(){
//     let row = number;
//     if (row >= 1){
//         var seat = [];
//         seat = changeRow(row);
//         //console.log(seat[0]+","+seat[1]);
//         let studio_name = seat[1];
//         let xhr = new XMLHttpRequest();
//         xhr.onreadystatechange = function () {
//             if (xhr.readyState == 4 && xhr.status == 200) {
//                 let res = JSON.parse(xhr.responseText);
//                 if (res.status == true) {
//                     let json = res.object;
//                     console.log(json);
//                     studio_id = json[0][0];
//                     console.log(studio_id);
//                 }
//             }
//         };
//         xhr.open('GET', '/api/lock?name=' + studio_name);
//         xhr.send();
//         localStorage.setItem("sched_id",seat[0]);
//         localStorage.setItem("studio_id",studio_id);
//         window.location.href = '';
//     }
// }
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
            console.log(json);
            for (let i = 0; i < json.length; i++) {
                let tr = document.createElement('tr');
                let td0 = document.createElement('td');
                td0.innerText = json[i][0];
                let td1 = document.createElement('td');
                var studioid=json[i][1];
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
                // if(playid!=0) {
                //     //console.log("id是："+playid);
                //     let xhr = new XMLHttpRequest();
                //     xhr.onreadystatechange = function () {
                //         if (xhr.readyState == 4 && xhr.status == 200) {
                //             let res = JSON.parse(xhr.responseText);
                //             let json = res.object;
                //             console.log(json);
                //             alert(json.length);
                //             td2.innerText = json[3];
                //             td4.innerText=json[7];
                //             console.log("剧目名称是什么" + pname);
                //         }
                //     }
                //     xhr.open('GET', '/api/play?id=' + playid);
                //     xhr.send();
                // }
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
    //alert("san");
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
                let option = document.createElement("option");
                option.innerText = json[i][1];
                list[i] = option.value;
                //alert(list[i]);
                studio_name.appendChild(option);
            }
        }
    };
    xhr.open('GET', '/api/studio');
    xhr.send();
}

//查找
function chazhao() {
    let each_nums = document.getElementById("each_nums").value;
    let now_page = document.getElementById("now_page").innerText;
    let id = document.getElementById('play_id').value;
    console.log("要查找的剧目id是：" + id);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
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
                studioid = json[i][1];
                //进行转换
                if (studioid != 0) {
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
                playid = json[i][2];
                //进行转换
                if (playid != 0) {
                    //console.log("id是："+playid);
                    let xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            let res = JSON.parse(xhr.responseText);
                            let json = res.object;
                            //alert(json.length);
                            td2.innerText = json[3];
                            td4.innerText = json[7];
                            //console.log("剧目名称是什么" + pname);

                        }
                    }
                    xhr.open('GET', '/api/play?id=' + playid);
                    xhr.send();
                }


                let td3 = document.createElement('td');
                td3.innerText = json[i][3];

                //td4.innerText = json[i][4];

                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tbody.appendChild(tr);
            }

        }
    };
    xhr.open('GET', '/api/schedule?page=' + now_page + '&nums=' + each_nums + '&name=' + play_id);
    xhr.send();

}

//添加演出计划
function addSchedule() {
    //alert("34234");
    /*let scheduleAdd=document.getElementById('schedule');
    let playname = document.getElementById('play_name');
    let indexPlay = playname.selectedIndex;
    let play_name = playname.options[indexPlay].value;*/
    //进行转换
    let pid = 6;
    //进行转换
    let studioname = document.getElementById('studio_name');
    let indexStudio = studioname.selectedIndex;
    let studio_name = studioname.options[indexStudio].value;
    //进行转换
    let each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let name = studio_name;
    let time = document.getElementById('sched_time').value;
    let price = document.getElementById('sched_ticket_price').value;
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
            alert(json.length);
            for (let i = 0; i < json.length; i++) {
                sid = json[i][0];
                let method = 'POST';
                let data = 'play_id=' + pid + '&studio_id=' + sid + '&sched_time=' + time + '&sched_ticket_price=' + price;
                alert(data);
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
                xml.open(method, '/api/schedule');
                xml.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xml.send(data);
            }
        }


    };
    xhr.open('GET', '/api/studio?name=' + name);
    xhr.send();


}

function reset_get() {
    document.getElementById('now_page').innerText = '1';
    get_schedule();
    /* chazhao();*/
    getScheduleCount();
}

function getScheduleCount() {
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
// function scheduleRow(obj) {
//     let table = document.getElementById('schedule');
//     let length = table.rows.length;
//
//     for (let i = 0; i < length; i++) {
//         if (i == 0) {
//             let row = table.rows[i];
//             row.setAttribute('class', 'warning');
//         }
//         else if (i % 2 != 0 && i != 0) {
//             let row = table.rows[i];
//             row.setAttribute('class', 'active');
//         } else {
//             let row = table.rows[i];
//             row.setAttribute('class', 'default');
//         }
//     }
//
//     if (event.srcElement.tagName == 'TD') {
//         let num = 0;
//         let curRow = event.srcElement.parentElement;
//         //let rowContainer = curRow.innerHTML;
//
//         if (curRow.rowIndex == 0) {
//             curRow.setAttribute('class', 'warning');
//             return 0;
//         }
//         else {
//             curRow.setAttribute('class', 'info');
//             num = curRow.rowIndex;
//             console.log(num);
//             number = num;
//         }
//     }
//
// }

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
    //alert("数组长度："+array.length);
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
    //alert('aaaaaaa');
    if (number > 0) {
        /*let change = [];
        let array=changeRow(number);

        change[0] = document.getElementById('change_id');
        change[1] = document.getElementById('change_studio');
        change[2] = document.getElementById('change_play');
        change[3] = document.getElementById('change_time');
        change[4] = document.getElementById('change_price');*/
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


    let sched_id = document.getElementById('change_id').value;
    console.log("没有id吗" + sched_id);

    let studioname= document.getElementById('change_studio');
    let indexStudio = studioname.selectedIndex;
    sname = studioname.options[indexStudio].value;
    alert(sname);
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
            alert("长度"+json.length);
            flag=true;
            for (let i = 0; i < json.length; i++) {
                csid = json[i][0];
                console.log("这里有吗"+csid);

                alert(csid + "csid");
                // /let play_id = document.getElementById('change_play').value;*/
                let play_id = 6;
                let sched_time = document.getElementById("change_time").value;
                console.log("修改后的时间" + sched_time);
                let sched_ticket_price = document.getElementById("change_price").value;
                console.log("修改后的票价" + sched_ticket_price);
                let xml = new XMLHttpRequest();
                xml.onreadystatechange = function () {
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
                let method = 'PUT';
                let data = 'sched_id=' + sched_id + '&studio_id=' + csid + '&play_id=' + play_id + '&sched_time=' + sched_time + '&sched_ticket_price=' + sched_ticket_price;
                xml.open(method, '/api/schedule');
                xml.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xml.send(data);

            }
        }
    };
    xhr.open('GET', '/api/studio?name=' + sname);
    xhr.send();


}
