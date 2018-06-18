
//通过name数组把id转为Name
function id2name(id,name) {
    for(let i of name){
        if(i[0] == id){
            return i[1];
        }
    }
}

//绘制饼状图
function picTab_init(json) {
    let bing1 = echarts.init(document.getElementById("bing1"));
    let bing2 = echarts.init(document.getElementById("bing2"));

    bing1.clear();
    bing2.clear();
    let mai = [];
    let tui = [];
    //提取卖和退
    for(let i of json){
        if(i[5] == 1){
            mai.push(i);
        }else{
            tui.push(i);
        }
    }

    //提取每个人总共卖的和退的
    let data_mai = new Array();
    let data_tui = new Array();
    for(let i of mai){
        let id = i[3];
        if(!data_mai[id]){
            data_mai[id] = i[1];
        }else{
            data_mai[id] = data_mai[id] + i[1];
        }
    }

    for(let i of tui){
        let id = i[3];
        if(!data_tui[id]){
            data_tui[id] = i[1];
        }else{
            data_tui[id] = data_tui[id] + i[1];
        }
    }

    //提取总卖出钱数及转成支持的类型
    let data_sale = new Array();
    let money_sale = 0;
    for(let key in data_mai){
        let name = id2name(key,Play_id);
        data_sale.push({value:data_mai[key],name:name});
        money_sale += data_mai[key];
        delete data_mai[key];
    }
    delete data_mai;

    //提取总退出钱数及转成支持的类型
    let data_ret = new Array();
    let money_ret = 0;
    for(let key in data_tui){
        let name = id2name(key,Play_id);
        data_ret.push({value:data_tui[key],name:name});
        money_ret += data_tui[key];
        delete data_tui[key];
    }
    delete data_tui;
    //绘制卖出饼状图
    bing1.setOption({
        title: {
            text: '售出',
            subtext:'总销售金额' + money_sale +'￥',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#000'
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series : [
            {
                name: '买票',

                type: 'pie',
                roseType: 'angle',
                radius: '55%',
                data:data_sale,
            }
        ]
    });
    //绘制退票饼状图
    bing2.setOption({
        title: {
            text: '退票',
            subtext:'总退票金额' + money_ret +'￥',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#000'
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series : [
            {
                name: '退票',
                type: 'pie',
                roseType: 'angle',
                radius: '55%',
                data:data_ret,
            }
        ]
    });

    let User_list = new Array();
    data_mai = new Array();
    data_tui = new Array();

    //获取所有售票员名字
    for(let i of json){
        let name = id2name(i[2],User_id);
        if(User_list.indexOf(name) == -1){
            User_list.push(name);
        }
    }
    //获取各个售票员卖的钱
    for(let i of mai){
        let name = id2name(i[2],User_id);
        if(!data_mai[name]){
            data_mai[name] = i[1];
        }else{
            data_mai[name] += i[1];
        }
    }
    //获取各个售票员退的钱
    for(let i of tui){
        let name = id2name(i[2],User_id);
        if(!data_tui[name]){
            data_tui[name] = i[1];
        }else{
            data_tui[name] += i[1];
        }
    }


    //获取卖票的数组
    // for(let key in data_tui){
    //     alert(key+";"+data_tui[key]);
    // }
    data_sale = new Array();
    for(let name of User_list){
        if(data_mai[name] == undefined){
            data_sale.push(0);
        }else{
            data_sale.push(data_mai[name]);
        }
    }

    //获取退票的数组
    data_ret = new Array();
    for(let name of User_list){
        if(data_tui[name] == undefined){
            data_ret.push(0);
        }else{
            data_ret.push(data_tui[name]);
        }
    }

    //图表控件
    // 基于准备好的dom，初始化echarts实例
    //document.getElementById('main').clear();
    var myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            show:true,
            text: '各售票员销量一览',
        },
        tooltip: {},
        legend: {
            data:['售票','退票']
        },
        xAxis: {
            data: User_list
        },
        yAxis: {},
        series: [{
            name: '售票',
            type: 'bar',
            data: data_sale
        },
            {
                name: '退票',
                type: 'bar',
                data: data_ret
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function check_time(val) {
    let start = document.getElementById("start");
    let end = document.getElementById("end");
    if(start.value == "" || end.value == ""){
        return true;
    }else{
        let s_time = Date.parse(start.value);
        let e_time = Date.parse(end.value);
        if(s_time >= e_time){
            alert("时间段不合法！");
            val.value = "";
            return false;
        }else{
            return true;
        }
    }
}

function getmovie() {
    let people = document.getElementById('movie');
    let xhr = new XMLHttpRequest();
    people.innerHTML = "<option value=\"\" selected>*</option>";
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let json = JSON.parse(xhr.responseText);
            if(json.status){
                Play_id = json.object;
                for(let x of json.object){
                    let item = document.createElement("option");
                    item.setAttribute("value",x[0]);
                    item.innerText = x[1];
                    people.appendChild(item);
                }
            }
        }
    };

    xhr.open('GET',"/api/xiaoshou/movie");
    xhr.send()
}

function getpeople() {
    let people = document.getElementById('people');
    let xhr = new XMLHttpRequest();
    people.innerHTML = "<option value=\"\" selected>*</option>";
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            let json = JSON.parse(xhr.responseText);
            if(json.status){
                User_id = json.object;
                for(let x of json.object){
                    let item = document.createElement("option");
                    item.setAttribute("value",x[0]);
                    item.innerText = x[1];
                    people.appendChild(item);
                }
            }
        }
    };

    xhr.open('GET',"/api/xiaoshou/shoupiaoyuan");
    xhr.send()
}

function search() {
    let movie = document.getElementById("movie");
    let people = document.getElementById("people");
    let start = document.getElementById("start");
    let end = document.getElementById("end");
    if(!check_time(end)){
        return;
    }
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if(xhr.readyState == 4 && xhr.status == 200){
            let res = JSON.parse(xhr.responseText);
            if(res.status){
                let json = res.object;
                picTab_init(json);
            }
        }
    };
    xhr.open("GET","/api/xiaoshou?movie="+movie.value+"&people="+people.value+"&start="+start.value+"&end="+end.value);
    xhr.send();

}