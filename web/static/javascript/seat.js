'use strict';
//

//添加座位
// array[i][j] = 0 座位正常
// array[i][j] = -1 座位坏掉
// array[i][j] = 1 改为过道

var changRow = 0;
var changeCol = 0;

var row;
var col;
var array = new Array();
var flag_aa = 0;
var flag_bb = 0;
var seat_status = 0;
var seat_id = 0;


//获取座位行列信息：
function aa() {
    let studio_id = localStorage.getItem("studio_id");
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function(){
        if (xml.readyState == 4 && xml.status == 200){
            let res = JSON.parse(xml.responseText);
            row = res.object[2];
            col = res.object[3];
            flag_aa = 1;

            if (flag_bb == 1){
                createState()
            }
        }

    }
    xml.open('GET','/api/studio?id='+studio_id);
    xml.send();
}


//获取座位状态
function bb() {
    let studio_id = localStorage.getItem("studio_id");
    // console.log(studio_id);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse(xhr.responseText);
            // console.log(res);
            let json = res.object;
            flag_bb = 1;
            for(let i = 0;i<row;i++){
                array[i] = new Array();
                for(let j = 0;j<col;j++){
                    array[i][j] = 1;
                }
            }

            for(let i of json){
                array[i[2]-1][i[3]-1] = i[4];

            }
            if (flag_aa == 1){
                createState();
            }
            console.log('json:'+json[0]);
            console.log(array[0][0]);
        }
    }
    xhr.open('GET','/api/seat?id='+studio_id);
    xhr.send();
}

function createState(){
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
            if (col < 5 || row < 5){
                oneSeat.style.paddingLeft = '80px';
                seatImage.style.height = "42px";
                seatImage.style.width = "42px";
            }
            seatImage.setAttribute("id", "img" + j + i);
            if (array[i - 1][j - 1] == 0) {
                seatImage.src = '/static/image/seat.png';
            } else if (array[i - 1][j - 1] == 1) {
                seatImage.src = '/static/image/aisle.png';
            } else if (array[i - 1][j - 1] == -1) {
                seatImage.src = '/static/image/broken.png';
            }
            oneSeat.appendChild(seatImage);
            oneSeat.appendChild(num);
            div.appendChild(oneSeat);
            seatImage.setAttribute('data-toggle', 'modal');
            seatImage.setAttribute('data-target', '#myModal');
            seatImage.addEventListener('click', function changeSate() {
                changRow = i;
                changeCol = j;
                let where = document.getElementById('title');
                where.innerHTML = '请选择第' + i + '行第' + j + '列状态：';
            });

            let changeBtn = document.getElementById('update');
            changeBtn.onclick = function changeState() {
                let seatState = document.getElementById('seatState');
                let newSeat = document.getElementById("img"+changeCol+changRow);
                let index = seatState.selectedIndex;
                let value = seatState.options[index].value;
                if (value == 'active'){
                    seat_status = 0;
                    newSeat.src = '/static/image/seat.png';
                    console.log("active");
                }else if (value == 'broken'){
                    seat_status = -1;
                    newSeat.src = '/static/image/broken.png';
                    console.log("broken");
                }else if (value == 'aisle'){
                    seat_status = 1;
                    newSeat.src = '/static/image/aisle.png';
                    console.log("aisle");
                }
                change();
            }

            document.getElementById("seat").appendChild(div);
        }
    }
    var seatWidth = document.getElementById('seat');
    if (col < 5 || row < 5){
        seatWidth.style.width = (80+42) * col +80;
    }else {
        seatWidth.style.width = (32+60)* col +60;
    }
}

function change()   {
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if (xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                var seat = document.getElementById("seat");
                // seat.empty();
                // aa();
                // bb();
            }else {
                alert("失败刷新重试");
            }
        }
    };
    let studio_id = localStorage.getItem("studio_id");
    let method = 'PUT';
    let data = 'studio_id='+studio_id+'&seat_row='+changRow+'&seat_column='+changeCol+'&seat_status='+seat_status;
    xml.open(method,'/api/seat');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);

}






