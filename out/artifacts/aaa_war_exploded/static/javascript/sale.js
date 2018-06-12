//在售票界面显示所有的剧目信息
    window.onload=function(){/*当页面加载的时候调用函数*/
        // waterfall(main, pin);
        window.onscroll=sscroll();
    };
function sscroll() {
    var pic;
    $.ajax({
        async: false,
        type: 'get',//get是获取数据，post是带数据的向服务器发送请求
        url:'/api/play',
        dataType: 'json',
        success: function (data) {
            alert(data);
            // pic = eval(data);//转换成js对象
        },
        error: function (data) {
            alert("JSON数据获取失败，请联系管理员！");
        }
    });
    var dataInt = {'data': [{'src': 'img1.jpg'}, {'src': 'img2.jpg'}, {'src': 'img3.jpg'}, {'src': 'img4.jpg'}, {'src': 'img5.jpg'}, {'src': 'img6.jpg'}]};

    if (checkscrollside()) {
        var oParent = document.getElementById('main');
        for (var i = 0; i < pic.length; i++) {
            var oPin = document.createElement('div');//添加元素节点
            oPin.className = 'pin';
            oParent.appendChild(oPin);
            var oBox = document.createElement('div');
            oBox.className = 'box';
            oPin.appendChild(oBox);
            var oImg = document.createElement('img');
            oImg.src = src = '/Login_ssm_mav_picbyte/user/toLookImage?id=' + pic[i].photoId;
            oBox.appendChild(oImg)
        }
        waterfall('main', 'pin');
    }

    function checkscrollside() {
        var oParent = document.getElementById('main');
        var aPin = getClassObj(oParent, 'pin');
        var lastPinH = aPin[aPin.length - 1].offsetTop + Math.floor(aPin[aPin.length - 1].offsetHeight / 2);
        //创建【触发添加块框的的函数waterfall()】的高度:最后一个块框的与网页顶部的距离+自身高的一半(未滚到底便开始加载)

        //网页中获取滚动条卷去部分的高度
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
        var documentH = document.documentElement.clientHeight;//页面高度
        return (lastPinH < scrollTop + documentH) ? true : false;//到达指定高度后返回true,触发waterfall（）函数
    }

    function waterfall(parent, pin) {
        var oParent = document.getElementById(parent);//父级对象
        var aPin = getClassObj(oParent, pin);//获取存储pin的数组的pin
        var iPinW = aPin[0].offsetWidth;//一个块框Pin的宽
        var num = Math.floor(document.documentElement.clientWidth / iPinW);//每行中能容纳的pin的个数，
        oParent.style.cssText = 'width' + iPinW * num + 'px;margin:0 auto;';//设置父级居中样式：定宽+自动水平外边距

        var pinHArr = [];//用于存储没列中的所有块框相加的高度
        for (var i = 0; i < aPin.length; i++) {
            var pinH = aPin[i].offsetHeight;
            if (i < num) {
                pinHArr[i] = pinH;//第一行中的num个块框pin先添加进数组pinHArr
            }
            else {
                var minH = Math.min.apply(null, pinHArr);//数组pinHArr中的最小值
                var minHIndex = getminHIndex(pinHArr, minH);//获取高度最小的pin的index
                aPin[i].style.position = 'absolute';//设置绝对偏移
                aPin[i].style.top = minH + 'px';
                aPin[i].style.left = aPin[minHIndex].offsetLeft + 'px';
                pinHArr[minHIndex] += aPin[i].offsetHeight;//更新添加了块框后的列高

            }
        }
    }

    //获取高度最小的pin 的index
    function getminHIndex(arr, minH) {
        for (var i in arr) {
            if (arr[i] == minH) {
                return i;
            }
        }
    }

    /*通过父级和子元素的class名称获取该同类子元素的数组*/
    function getClassObj(parent, className) {
        var obj = parent.getElementsByTagName('*');//获取父类元素的所有子元素
        var pinS = [];//创建一个数组，用于收集子元素
        for (var i = 0; i < obj.length; i++) {//遍历子元素,判断类别，压入数组
            if (obj[i].className == className) {
                pinS.push(obj[i]);
            }
        }
        return pinS;
    }

}