'user strict'

let number=0;
let id = 0;
var each_nums = 0;

function getPlayCount() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse((xhr.responseText));
            if (res.status == false){
                alert("获取剧目失败");
            }else {
                var playCount = res.object.length;
                //alert(playCount+"ww");
                var pagesCount = Math.ceil(playCount/each_nums);
                var pages = document.getElementById("pages");
                pages.innerText = "共"+pagesCount+"页";
                console.log("剧目的总页数："+pagesCount);
                //alert("pagesCount"+pagesCount);
            }
        }
    };
    xhr.open('GET','/api/play');
    xhr.send();
}
function get_play() {
    each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let play_name = document.getElementById('play_name').value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse(xhr.responseText);
            if(res.status == false) {
                document.getElementById('now_page').innerText =parseInt(now_page)-1;
                alert("已到最后一页!");
                return ;
            }
            json = res.object;
            // console.log(json);
            for (let i in json){
                var play = document.getElementById('play');

                var div = document.createElement("div");
                aaa = json[i][0];
                div.setAttribute("id",aaa);
                div.setAttribute('class','aaa');
                play.appendChild(div);

                //添加剧目图片
                var div_img = document.createElement('div');
                div_img.style.cssFloat = 'left';
                var play_img = document.createElement('img');
                play_img.setAttribute('height','220px');
                play_img.setAttribute('width','160px');
                var path = json[i][5];
                if (path == 'null'){
                    play_img.setAttribute('src','/static/image/default.jpg');

                }else {
                    //request.getContextPath()+"/"+imgUrl;
                    play_img.setAttribute('src',path);
                    //play_img.setAttribute('alt','img');

                }
                // play_img.style.width = '200px';
                div.appendChild(div_img);
                div_img.appendChild(play_img);

                //添加演出信息
                var div_mess = document.createElement('div');
                div_mess.setAttribute('id',json[i][0]+json[i][0]);
                div_mess.style.width = '600px';
                div_mess.style.cssFloat = 'left';
                div.appendChild(div_mess);
                var ul = document.createElement('ul');
                var li1 = document.createElement('li');
                li1.innerText = "剧目名称："+json[i][3];

                var li2 = document.createElement('li');
                if(json[i][1]=='5'){
                    li2.innerText="剧目类型：古装剧";
                }
                else if(json[i][1]=='6'){
                    li2.innerText="剧目类型：动漫片";
                }
                else if(json[i][1]=='7'){
                    li2.innerText="剧目类型：生活剧";
                }
                else if(json[i][1]=='8'){
                    li2.innerText="剧目类型：恐怖片";
                }
                else if(json[i][1]=='9'){
                    li2.innerText="剧目类型：战争片";
                }
                else if(json[i][1]=='10'){
                    li2.innerText="剧目类型：科幻片";
                }

                var li3 = document.createElement('li');
                if (json[i][2] == '11'){
                    li3.innerText = '语言类型：国语';
                }else if (json[i][2] == '12'){
                    li3.innerText = '语言类型：英语';
                }

                var li5 = document.createElement('li');
                li5.innerText = "剧目简介："+json[i][4];

                var li6 = document.createElement('li');
                li6.innerText = "演出时间："+json[i][6];

                var li7 = document.createElement('li');
                li7.innerText = '票价：'+json[i][7];

                var li8 = document.createElement('li');
                if(json[i][8]=='0')
                   li8.innerText = '剧目状态：待安排演出';
                else if(json[i][8]=='1')
                    li8.innerText = '剧目状态：已安排演出';
                else
                    li8.innerText = '剧目状态：下线';

                div_mess.appendChild(ul);
                ul.appendChild(li1);
                ul.appendChild(li2);
                ul.appendChild(li3);
                ul.appendChild(li5);
                ul.appendChild(li6);
                ul.appendChild(li7);
                ul.appendChild(li8);

                //创建删除按钮
                var btn_del = document.createElement('button');
                btn_del.innerHTML = '<i class="fa fa-minus" aria-hidden="true"></i>'+' 删除剧目';

                btn_del.setAttribute('class','btn btn-default navbar-btn');
                btn_del.setAttribute('id','deletePlay');
                btn_del.style.cssFloat = 'left';
                btn_del.onclick = function () {
                    var remove_id = json[i][0];
                    removePlay(remove_id);
                };
                // btn_del.bottom = '20px';
                // <button type="button" class="btn btn-default navbar-btn" id = 'deletePlay' onclick="removePlay()"><i class="fa fa-minus" aria-hidden="true"></i> 删除剧目</button>
                // <button type="button" class="btn btn-default navbar-btn" id="changePlay" onclick="change()" data-toggle="modal" data-target="#myModals" ><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 修改剧目</button>

                //创建修改按钮
                var btn_change = document.createElement('button');
                btn_change.innerHTML = '<i class="fa fa-pencil-square-o" aria-hidden="true"></i>'+' 修改剧目信息';
                btn_change.setAttribute('class','btn btn-default navbar-btn');
                btn_change.setAttribute('id','changePlay');
                btn_change.setAttribute('data-toggle','modal');
                btn_change.setAttribute('data-target','#myModals');
                btn_change.onclick = function () {
                    id = json[i][0];
                    change(id);
                }

                //创建修改海报按钮
                var btn_img = document.createElement('button');
                btn_img.innerHTML ='<i class="fa fa-pencil-square-o" aria-hidden="true"></i>'+ "修改剧目海报";
                btn_img.setAttribute('class','btn btn-default navbar-btn');
                btn_img.setAttribute('id','changeplayImage');
                btn_img.setAttribute('data-toggle','modal');
                btn_img.setAttribute('data-target','#play_image');
                ///btn_img.setAttribute('onchange','upload(id)');
                //btn_img.setAttribute('onsubmit',"return upload(id)");
                //onsubmit="return upload()"
                //onchange="upload()
                btn_img.onclick = function () {
                    id = json[i][0];
                    findId(id);
                    upload();

                }
                div.appendChild(btn_del);
                div.appendChild(btn_change);
                div.appendChild(btn_img);


                var hr = document.createElement('hr');
                play.appendChild(hr);

            }
        }
    }
    xhr.open('GET','/api/play?page='+now_page+'&nums='+each_nums+'&name='+play_name);
    xhr.send();
}

function findId(id) {
    document.getElementById("play_id").value = id;

}
function upload() {

    let file = document.getElementById("file").value;
    //alert("file="+file+"id="+id);
    if(/\.(jpg|png|gif|jpeg|png)$/.test(file)){
        document.getElementById('file_error').innerText = "";
        //alert("上传完成");
        return true;
    }
    //alert("file="+file+"id="+id);
    document.getElementById('file_error').innerText = "只能上传图片！";
    return false;
}

function other_page(offset) {
    let now_page = document.getElementById('now_page');
    if(offset < 0 && parseInt(now_page.innerText) == 1){
        //
        alert("已到第一页！");
    }else {
        now_page.innerText = parseInt(now_page.innerText) + offset;
        document.getElementById('play').innerHTML = '';
        get_play();
    }
}

function reset_get() {
    document.getElementById('now_page').innerText = '1';
    document.getElementById('play').innerHTML = '';
    get_play();
    getPlayCount();
}




function del_play() {

    let id = document.getElementById('delete_now').innerText;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            if(JSON.parse(xhr.responseText).status){
                get_play();
            }else{
                alert("删除失败！");
            }
        }
    };
    xhr.open('DELETE','/api/play');
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.send('id='+id);
}

//添加剧目
function addPlay() {
    let playAdd = document.getElementById('play');
    let flag,ty,la;
    let type=document.getElementById('type').value;
    alert(type);
    if(type=='古装剧'){
        ty=5;
    }
    else if(type=='动漫片'){
        ty=6;
    }
    else if(type=='生活剧'){
        ty=7;
    }
    else if(type=='恐怖片'){
        ty=8;
    }
    else if(type=='战争片'){
        ty=9;
    }
    else if(type=='科幻片'){
        ty=10;
    }
    let lang=document.getElementById('lang').value;
    if(lang=='国语')
        la=11;
    else if(lang=='英语')
        la=12;
    let name = document.getElementById('playName').value;
    let intro= document.getElementById('playIntro').value;
    let image = document.getElementById('playImage').value;
    let length = document.getElementById('playLength').value;
    let price = document.getElementById('playPrice').value;
    let state=document.getElementById("playState").value;
    if(state=='已安排演出')
        flag=1;
    else if(state=='待安排演出')
        flag=0;
    else
        flag=-1;
    if(check1() && check3()){

    }else {
        alert("格式输入有误！");
        return false;
    }

    let method = 'POST';
    let data = 'play_type_id='+ty+'&play_lang_id='+la+'&play_name='+name+'&play_introduction='+intro+'&play_image='+image+'&play_length='+length+'&play_ticket_price='+price+'&play_status='+flag;
    //alert(data);

    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                get_play();
                getPlayCount();
            }else{
                alert("失败，请重试！");
            }
        }
    };

    let sss = window.location.search;

    xml.open(method,'/api/play');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);

}



//鼠标点击事件
// function playRow(obj) {
//     let table = document.getElementById('play');
//     let length = table.rows.length;
//
//     for(let i = 0;i<length;i++){
//         if(i == 0){
//             let row = table.rows[i];
//             row.setAttribute('class','warning');
//         }
//         else if(i%2 != 0 && i != 0){
//             let row = table.rows[i];
//             row.setAttribute('class','active');
//         }else{
//             let row = table.rows[i];
//             row.setAttribute('class','default');
//         }
//     }
//
//     if(event.srcElement.tagName == 'TD'){
//         let num = 0;
//         let curRow = event.srcElement.parentElement;
//         //let rowContainer = curRow.innerHTML;
//
//         if(curRow.rowIndex == 0){
//             curRow.setAttribute('class','warning');
//             return 0;
//         }
//         else{
//             curRow.setAttribute('class','info');
//             num = curRow.rowIndex ;
//             console.log(num);
//             number = num;
//         }
//     }
//
// }

//删除某一行
function deleteRow(row) {
    let deleteRow = document.getElementById('play');
    deleteRow.deleteRow(row);
}

//删除剧目
function removePlay(id) {
    let row = id;
    if(row > 0){
        // console.log(row);
        // let id = document.getElementById('delete_now').innerText;
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if(xhr.readyState == 4 && xhr.status == 200){
                if(JSON.parse(xhr.responseText).status){
                    get_play();
                    getPlayCount();
                }else{
                    alert("删除失败！");
                }
            }
        };
        xhr.open('DELETE','/api/play');
        xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        alert(row);
        xhr.send('id='+row);

    }else{
        let changeButton = document.getElementById('deletePlay');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要删除的地方！'
    }
}


//获取选中行的信息
// function changeRow(number) {
//     let array = [];
//     let table = document.getElementById('play');
//     let length = table.rows.length;
//     console.log(length);
//     for(let i = 0;i<length;i++){
//         array[i] = table.rows[number].cells[i].innerHTML;
//         console.log(array[i]);
//     }
//     return array
// }

//将信息添加在修改信息的相应位置
function change(id) {
    if(id > 0){
        let change = [];
        let array = [];
        var mess = document.getElementById(id+id);
        var aa = mess.querySelectorAll('li');
        var len = aa.length;
        for (var i = 0;i<len;i++){
            var aaa = aa[i].innerText.split('：');
            array[i] = aaa[1];
            console.log(array[i]);

        }

        //alert(array);
        change[0] = document.getElementById('changeplayName');
        change[1] = document.getElementById('changetype');
        change[2] = document.getElementById('changelang');
        change[3] = document.getElementById('changeplayIntro');
        change[4] = document.getElementById('changeplayLength');
        change[5] = document.getElementById('changeplayPrice');
        change[6] = document.getElementById('changeplayState');
        for(let i = 0;i<len;i++){
            change[i].value = array[i];
            // alert(change[i]);
        }
    }
    else{
        console.log('aaa');
        let changeButton = document.getElementById('changePlay');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要修改的地方！'
    }

}


function putPlay() {

    let flag,ty,la;
    let type=document.getElementById("changetype").value;
    if(type=="古装剧"){
        ty=5;
    }
    else if(type=="动漫片"){
        ty=6;
    }
    else if(type=="生活剧"){
        ty=7;
    }
    else if(type="恐怖片"){
        ty=8;
    }
    else if(type=="战争片"){
        ty=9;
    }
    else if(type=="科幻片"){
        ty=10;
    }
    let lang=document.getElementById('changelang').value;
    if(lang=="国语")
        la=11;
    else if(lang=="英语")
        la=12;
    let name = document.getElementById('changeplayName').value;
    let intro= document.getElementById('changeplayIntro').value;
    let image = document.getElementById('changeplayImage').value;
    let length = document.getElementById('changeplayLength').value;
    let price = document.getElementById('changeplayPrice').value;
    let state=document.getElementById("changeplayState").value;
    if(state=="已安排演出")
        flag=1;
    else if(state=="待安排演出")
        flag=0;
    else
        flag=-1;
    if(check5()  ){

    }else {
        alert("格式输入有误！");
        return false;
    }
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                document.getElementById('play').innerHTML = '';
                get_play();
            }else{
                alert("失败，请重试！");
            }
        }
    };
    let sss = window.location.search;
    let method = 'PUT';
    //let data = 'studio_name='+studio_name+'&studio_rows='+studio_row+'&studio_cols='+studio_col+'&stuio_detial='+studio_int+'&flag='+studio_state+'&id='+studio_no;
    let data = 'play_type_id='+ty+'&play_lang_id='+la+'&play_name='+name+'&play_introduction='+intro+'&play_image='+image+'&play_length='+length+'&play_ticket_price='+price+'&play_status='+flag+'&play_id='+id;


    xml.open(method,'/api/play');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}


//添加剧目名称
function check1() {
    let aaa = document.getElementById('playName').value;
    console.log(aaa);
    if(/^.{2,20}$/.test(aaa)){
        document.getElementById('err1').innerHTML = '';
        // alert("正确");
        return true;
    }
    else{
        // alert("格式错误");
        document.getElementById('err1').innerHTML = 'err:剧目名称为2～20个字符！';
        return false;
    }
    // alert(aaa);
}

function check2() {
    let aaa = document.getElementById('playLength').value;
    if(/^(?:0|[1-9][0-9]?|300)$/.test(aaa)){
        document.getElementById('err2').innerHTML = '';
        return true;
    }else{
        document.getElementById('err2').innerHTML = 'err:演出时长不符合规范（只能为1～300）！';
        return false;
    }
}

function check3() {
    let aaa = document.getElementById('playPrice').value;
    if(/^(?:(?!0\d)\d{1,2}(?:\.\d{1,2})?|100(?:\.0{1,2})?)$/.test(aaa)){
        document.getElementById('err3').innerHTML = '';
        return true;
    }else{
        document.getElementById('err3').innerHTML = 'err:票价不合法（只能为0.00~100.00）！';
        return false;
    }
}

function check4() {
    let aaa = document.getElementById('changeplayLength').value;
    if(/^(?:0|[1-9][0-9]?|300)$/.test(aaa)){
        document.getElementById('err4').innerHTML = '';
        return true;
    }else{
        document.getElementById('err4').innerHTML = 'err:演出时长不符合规范（只能为1～300）！';
        return false;
    }
}

function check5() {
    let aaa = document.getElementById('changeplayPrice').value;
    if(/^(?:(?!0\d)\d{1,2}(?:\.\d{1,2})?|100(?:\.0{1,2})?)$/.test(aaa)){
        document.getElementById('err5').innerHTML = '';
        return true;
    }else{
        document.getElementById('err5').innerHTML = 'err:票价不合法（0.00~100.00）！';
        return false;
    }
}





