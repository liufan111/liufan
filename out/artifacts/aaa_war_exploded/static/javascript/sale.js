'use strict';
let number=0;
var each_nums = 0;
function getSaleCount(){
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse((xhr.responseText));
            if (res.status == false){
                alert("获取销售数据失败");
            }else {
                var saleCount = res.object.length;
                console.log("销售数据总数:"+saleCount);
                var pagesCount = Math.ceil(saleCount/each_nums);
                var pages = document.getElementById("pages");
                pages.innerText = "共"+pagesCount+"页";
                console.log("销售数据的总页数："+pagesCount);
            }
        }
    };
    xhr.open('GET','/api/sale');
    xhr.send();
}
function get_sale(){
    each_nums = document.getElementById('each_nums').value;
    let now_page = document.getElementById('now_page').innerText;
    let sale_no = document.getElementById('sale_no').value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            //alert(xhr.responseText);
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
                //td0.setAttribute('style','display:none;');
                let td1 = document.createElement('td');
                td1.innerText = json[i][1];
                let td2 = document.createElement('td');
                td2.innerText = json[i][2];
                let td3 = document.createElement('td');
                td3.innerText = json[i][3];
                let td4 = document.createElement('td');
                td4.innerText = json[i][4];
                let td5 = document.createElement('td');
                td5.innerText = json[i][5];
                if(json[i][5] == 1){
                    td5.innerText="销售单";
                }
                else{
                    td5.innerText="退票单"
                }
                let td6 = document.createElement('td');
                td6.innerText = json[i][6];
                if(json[i][6] == 0){
                    td6.innerText="待付款";
                }
                else{
                    td6.innerText="已付款"
                }

                tr.appendChild(td0);
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);

                tbody.appendChild(tr);
            }
        }
    };
    xhr.open('GET','/api/sale?page='+now_page+'&nums='+each_nums+'&sid='+sale_no);
    console.log(now_page+","+each_nums+","+sale_no);
    xhr.send();
}

function other_page(offset){
    let now_page = document.getElementById('now_page');
    if( offset < 0 && parseInt(now_page.innerText)==1){
        alert("已到第一页！");
    } else{
        now_page.innerText = parseInt(now_page.innerText)+ offset;
        document.getElementById('sale').innerText='';
        get_sale();
    }
}
function reset_get(){
    document.getElementById('now_page').innerText = '1';
    document.getElementById('sale').innerText='';
    get_sale();
    getSaleCount();
}

//鼠标点击事件
function saleRow(obj){
    let table = document.getElementById('sale');
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
            //console.log(num);
            number = num;
        }
    }
}
//获取选中行的信息
function changeRow(number){
    let array = [];
    let table = document.getElementById('sale');
    let length = table.rows.item(0).cells.length;
    for(let i=0;i<length;i++){
        array[i] = table.rows[number].cells[i].innerHTML;
    }
    return array;
}
//将信息添加在修改信息的相应位置
function change(){
    //当选中需要修改的内容时
    if(number>0){
        let changeButton = document.getElementById('changeSale');
        changeButton.setAttribute('data-toggle','modal');
        changeButton.setAttribute('data-target','#myModals');
        let change = [];
        let array = [];
        array = changeRow(number);
        //alert(array);
        change[0] = document.getElementById('a_sale_ID');
        change[1] = document.getElementById('a_emp_id');
        change[2] = document.getElementById('a_sale_time');
        change[3] = document.getElementById('a_sale_payment');
        change[4] = document.getElementById('a_sale_change')
        change[5] = document.getElementById('a_sale_type');
        change[6] = document.getElementById('a_sale_status');
        //alert(change[2]);
        //console.log(array);
        if(array[5] == '销售单'){
            document.getElementById('a_sale_type').value = 'type1';
        }else{
            document.getElementById('a_sale_type').value = 'type2';
        }
        //alert(document.getElementById('a_sale_type').value);
        if(array[6] == '待付款'){
            document.getElementById('a_sale_status').value = 'status1';
        }else{
            document.getElementById('a_sale_status').value = 'status2';
        }

        //console.log(change[2]);
        let len = array.length;
        for(let i=0;i<5;i++){
            change[i].value = array[i];
        }
        //alert(array[0]);
        // document.getElementById('sale_ID').value = array[0];
        // document.getElementById('emp_id').value = array[1];
        // document.getElementById('sale_time').value = array[2];
        // document.getElementById('sale_payment').value = array[3];
        // document.getElementById('sale_change').value = array[4];
        //
        // change[0].value = array[0];
        // change[1].value = array[1];
        // change[2].value = array[2];
        // change[3].value = array[3];
        // change[4] = array[4];
        // change[5] = array[5];
        // change[6] = array[6];

        return change;
    }
    //当没有选中内容时
    else{
        let changeButton = document.getElementById('changeSale');
        changeButton.setAttribute('data-toggle','modal');
        changeButton.setAttribute('data-target','#error');
        let text = document.getElementById('waring');
        text.innerHTML= '请选择需要修改的地方！';
    }
}

function addSale(){
    let saleAdd = document.getElementById('sale');
    let flag1,flag2;
    let saleid = document.getElementById('sale_ID').value;
    let empid = document.getElementById('emp_id').value;
    let saletime = document.getElementById('sale_time').value;
    let salepayment = document.getElementById('sale_payment').value;
    let salechange = document.getElementById('sale_change').value;
    let saletype = document.getElementById('sale_type').value;
    let salestatus = document.getElementById('sale_status').value;
    if(saletype =='type1'){
        flag1=1;
    }else{
        flag1=-1;
    }
    if(salestatus=='status1'){
        flag2=0;
    }else{
        flag2=1;
    }
    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function(){
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                // alert("刷新");
                get_sale();
            }else{
                alert("失败,请重试！");
            }
        }
    };
    let sss = window.location.search;
    let method = 'POST';
    //alert(saleid+","+empid+","+saletime+","+salepayment+","+salechange+","+saletype+","+salestatus);
    let data = 'sale_ID='+saleid+'&emp_id='+empid+'&sale_time='+saletime+'&sale_payment='+
        salepayment+'&sale_change='+salechange+'&flag1='+flag1+'&flag2='+flag2;
    //alert(data);
    xml.open(method,'/api/sale');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
    //alert("添加成功！")
}

function updateSale(){
    let saleid = document.getElementById('a_sale_ID').value;
    let empid = document.getElementById('a_emp_id').value;
    let saletime = document.getElementById('a_sale_time').value;
    let salepayment = document.getElementById('a_sale_payment').value;
    let salechange = document.getElementById('a_sale_change').value;
    let saletype = document.getElementById('a_sale_type').value;
    let salestatus = document.getElementById('a_sale_status').value;
    if(saletype =='type1'){
        saletype=1;
    }else{
        saletype=-1;
    }
    if(salestatus=='status1'){
        salestatus=0;
    }else{
        salestatus=1;
    }

    let xml = new XMLHttpRequest();
    xml.onreadystatechange = function () {
        if(xml.readyState == 4 && xml.status == 200){
            let json = JSON.parse(xml.responseText);
            if (json.state){
                // alert("刷新");
                get_sale();
            }else{
                alert("失败，请重试！");
            }
        }
    };
    let sss = window.location.search;
    let method = 'PUT';
    let data = 'sale_ID='+saleid+'&emp_id='+empid+'&sale_time='+saletime+'&sale_payment='+
        salepayment+'&sale_change='+salechange+'&sale_type='+saletype+'&sale_status='+salestatus;
    xml.open(method,'/api/sale');
    xml.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xml.send(data);
}

function removeSale(){
    let row = number;
    if(row == 0) {
        let changeButton = document.getElementById('deleteSale');
        changeButton.setAttribute('data-toggle', 'modal');
        changeButton.setAttribute('data-target', '#error');
        let text = document.getElementById('waring');
        text.innerHTML = '请选择需要删除的地方！';
    }else {
        let mess = [];
        mess = changeRow(row);
        alert(mess);
        let id = mess[0];
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function(){
            if(xhr.readyState ==4 && xhr.status==200){
                if(JSON.parse(xhr.responseText).status){
                    get_sale();
                }else{
                    alert("删除失败！");
                }
            }
        };
        xhr.open('DELETE','/api/sale');
        xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xhr.send('id='+id);
    }
}

function deleteRow(row){
    let deleteRow = document.getElementById('sale');
    deleteRow.deleteRow(row);
}