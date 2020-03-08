var wpt_serverName = "/wpt/";
function showImg(who) {
    var myArray = new Array()
    myArray[0] = "工程位于抚松县县城西南侧，紧临抚松县主城区，头道松花江中下游河段右岸(松江河入头道松花江以上河段)，" +
    "保护区域为规划的抚松县城南新区。新建（拆除重建）堤防总长3434米，新建排水涵洞4座，人行下河台阶4处，人行步道至堤顶台阶9处，背水坡上堤台阶3处，新建防汛连接道路50米。";

    myArray[0] = "2018年扶余市中央财政水利发展资金高效节水灌溉项目六标段位于吉林省扶余市境内，工程内容主要包括建筑工程、设备及安装工程、金属结构设备及安装工程、临时工程。";

    myArray[1] = "东辽县隶属吉林省辽源市，位于吉林省中南部，地处长白山余脉和松辽平原的过渡地带，属低山丘陵区，东辽河发源于境内。" +
    "1.东辽县辽河源镇安乐村新建挡土墙扶贫项目，本工程项目区分布在吉林省东辽县。" +
    "2.主要建设内容主要施工内容为：新建挡土墙总长度为1600m。";
    myArray[3] = "";
    myArray[4] = "";
    myArray[5] = "";
//示范一个公告层
    layer.open({
        type: 1,
        title: false, //不显示标题栏
        closeBtn: false,
        area: '300px;',
        shade: 0.8,
        id: 'LAY_layuipro', //设定一个id，防止重复弹出
        resize: false,
        btn: ['关闭'],
        btnAlign: 'c',
        moveType: 1, //拖拽模式，0或者1
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;"><p class="lyp">' + myArray[who] + '</p></div>'
    });
}