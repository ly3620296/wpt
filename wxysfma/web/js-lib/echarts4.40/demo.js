$(function () {
    demo1();
    demo2();
    demo3();
    demo4();
})

function demo1() {
    var myChart = echarts.init(document.getElementById('zxt'));
    var option = {
        title: {
            text: '折线图堆叠'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['邮件营销', '联盟广告', '视频广告', '直接访问', '搜索引擎']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '邮件营销',
                type: 'line',
                stack: '总量',
                data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
                name: '联盟广告',
                type: 'line',
                stack: '总量',
                data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
                name: '视频广告',
                type: 'line',
                stack: '总量',
                data: [150, 232, 201, 154, 190, 330, 410]
            },
            {
                name: '直接访问',
                type: 'line',
                stack: '总量',
                data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
                name: '搜索引擎',
                type: 'line',
                stack: '总量',
                data: [820, 932, 901, 934, 1290, 1330, 1320]
            }
        ]
    };
    myChart.setOption(option);
}
var zztMyChart = echarts.init(document.getElementById('zzt'));
function demo2() {


    var xAxisData = [];
    var data1 = [];
    var data2 = [];
    var data3 = [];
    var data4 = [];

    for (var i = 0; i < 10; i++) {
        xAxisData.push('Class' + i);
        data1.push((Math.random() * 2).toFixed(2));
        data2.push(-Math.random().toFixed(2));
        data3.push((Math.random() * 5).toFixed(2));
        data4.push((Math.random() + 0.3).toFixed(2));
    }

    var itemStyle = {
        normal: {},
        emphasis: {
            barBorderWidth: 1,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0,0,0,0.5)'
        }
    };

    var option = {
        backgroundColor: '#eee',
        legend: {
            data: ['bar', 'bar2', 'bar3', 'bar4'],
            align: 'left',
            left: 10
        },
        brush: {
            toolbox: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear'],
            xAxisIndex: 0
        },
        toolbox: {
            feature: {
                magicType: {
                    type: ['stack', 'tiled']
                },
                dataView: {}
            }
        },
        tooltip: {},
        xAxis: {
            data: xAxisData,
            name: 'X Axis',
            silent: false,
            axisLine: {onZero: true},
            splitLine: {show: false},
            splitArea: {show: false}
        },
        yAxis: {
            inverse: true,
            splitArea: {show: false}
        },
        grid: {
            left: 100
        },
        visualMap: {
            type: 'continuous',
            dimension: 1,
            text: ['High', 'Low'],
            inverse: true,
            itemHeight: 200,
            calculable: true,
            min: -2,
            max: 6,
            top: 60,
            left: 10,
            inRange: {
                colorLightness: [0.4, 0.8]
            },
            outOfRange: {
                color: '#bbb'
            },
            controller: {
                inRange: {
                    color: '#2f4554'
                }
            }
        },
        series: [
            {
                name: 'bar',
                type: 'bar',
                stack: 'one',
                itemStyle: itemStyle,
                data: data1
            },
            {
                name: 'bar2',
                type: 'bar',
                stack: 'one',
                itemStyle: itemStyle,
                data: data2
            },
            {
                name: 'bar3',
                type: 'bar',
                stack: 'two',
                itemStyle: itemStyle,
                data: data3
            },
            {
                name: 'bar4',
                type: 'bar',
                stack: 'two',
                itemStyle: itemStyle,
                data: data4
            }
        ]
    };

    zztMyChart.on('brushSelected', renderBrushed);
    zztMyChart.setOption(option);

}

function renderBrushed(params) {
    var brushed = [];
    var brushComponent = params.batch[0];

    for (var sIdx = 0; sIdx < brushComponent.selected.length; sIdx++) {
        var rawIndices = brushComponent.selected[sIdx].dataIndex;
        brushed.push('[Series ' + sIdx + '] ' + rawIndices.join(', '));
    }

    zztMyChart.setOption({
        title: {
            backgroundColor: '#333',
            text: 'SELECTED DATA INDICES: \n' + brushed.join('\n'),
            bottom: 0,
            right: 0,
            width: 100,
            textStyle: {
                fontSize: 12,
                color: '#fff'
            }
        }
    });
}

function demo3() {
    var myChart = echarts.init(document.getElementById('bt'));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {value: 335, name: '直达', selected: true},
                    {value: 679, name: '营销广告'},
                    {value: 1548, name: '搜索引擎'}
                ]
            },
            {
                name: '访问来源',
                type: 'pie',
                radius: ['40%', '55%'],
                label: {
                    normal: {
                        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                        backgroundColor: '#eee',
                        borderColor: '#aaa',
                        borderWidth: 1,
                        borderRadius: 4,
                        // shadowBlur:3,
                        // shadowOffsetX: 2,
                        // shadowOffsetY: 2,
                        // shadowColor: '#999',
                        // padding: [0, 7],
                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                align: 'center'
                            },
                            // abg: {
                            //     backgroundColor: '#333',
                            //     width: '100%',
                            //     align: 'right',
                            //     height: 22,
                            //     borderRadius: [4, 4, 0, 0]
                            // },
                            hr: {
                                borderColor: '#aaa',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            b: {
                                fontSize: 16,
                                lineHeight: 33
                            },
                            per: {
                                color: '#eee',
                                backgroundColor: '#334455',
                                padding: [2, 4],
                                borderRadius: 2
                            }
                        }
                    }
                },
                data: [
                    {value: 335, name: '直达'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1048, name: '百度'},
                    {value: 251, name: '谷歌'},
                    {value: 147, name: '必应'},
                    {value: 102, name: '其他'}
                ]
            }
        ]
    };
    myChart.setOption(option);
}

function demo4() {
    var dataList = [
        {name: "南海诸岛", value: 0},
        {name: '北京', value: randomValue()},
        {name: '天津', value: randomValue()},
        {name: '上海', value: randomValue()},
        {name: '重庆', value: randomValue()},
        {name: '河北', value: randomValue()},
        {name: '河南', value: randomValue()},
        {name: '云南', value: randomValue()},
        {name: '辽宁', value: randomValue()},
        {name: '黑龙江', value: randomValue()},
        {name: '湖南', value: randomValue()},
        {name: '安徽', value: randomValue()},
        {name: '山东', value: randomValue()},
        {name: '新疆', value: randomValue()},
        {name: '江苏', value: randomValue()},
        {name: '浙江', value: randomValue()},
        {name: '江西', value: randomValue()},
        {name: '湖北', value: randomValue()},
        {name: '广西', value: randomValue()},
        {name: '甘肃', value: randomValue()},
        {name: '山西', value: randomValue()},
        {name: '内蒙古', value: randomValue()},
        {name: '陕西', value: randomValue()},
        {name: '吉林', value: randomValue()},
        {name: '福建', value: randomValue()},
        {name: '贵州', value: randomValue()},
        {name: '广东', value: randomValue()},
        {name: '青海', value: randomValue()},
        {name: '西藏', value: randomValue()},
        {name: '四川', value: randomValue()},
        {name: '宁夏', value: randomValue()},
        {name: '海南', value: randomValue()},
        {name: '台湾', value: randomValue()},
        {name: '香港', value: randomValue()},
        {name: '澳门', value: randomValue()}
    ]
    var myChart = echarts.init(document.getElementById('china'));

    function randomValue() {
        return Math.round(Math.random() * 1000);
    }

    var option = {
        tooltip: {
            formatter: function (params, ticket, callback) {
                return params.seriesName + '<br />' + params.name + '：' + params.value
            }//数据格式化
        },
        visualMap: {
            min: 0,
            max: 1500,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],//取值范围的文字
            inRange: {
                color: ['#e0ffff', '#006edd']//取值范围的颜色
            },
            show: true//图注
        },
        geo: {
            map: 'china',
            roam: false,//不开启缩放和平移
            zoom: 1.23,//视角缩放比例
            label: {
                normal: {
                    show: true,
                    fontSize: '10',
                    color: 'rgba(0,0,0,0.7)'
                }
            },
            itemStyle: {
                normal: {
                    borderColor: 'rgba(0, 0, 0, 0.2)'
                },
                emphasis: {
                    areaColor: '#F3B329',//鼠标选择区域颜色
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    shadowBlur: 20,
                    borderWidth: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        },
        series: [
            {
                name: '信息量',
                type: 'map',
                geoIndex: 0,
                data: dataList
            }
        ]
    };
    myChart.setOption(option);
    myChart.on('click', function (params) {
        alert(params.name);
    });

}