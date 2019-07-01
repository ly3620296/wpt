$(function () {
    var loadIndex;
    $.ajax({
        url: wpt_serverName + "jwl/xl",
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        beforeSend: function () {
            layer.ready(function () {
                loadIndex = layer.load(0, {shade: [0.2, '#393D49']})
            })
        },
        success: function (data) {
            if (data) {
                var code = data.returnInfo.return_code;
                var msg = data.returnInfo.return_msg;
                if (code == "0") {
                    var xlList = data.xlList
                    var currDate = data.currDate;
                    var xlHtml = "";
                    var count = 0;
                    for (var index in xlList) {
                        var rq = xlList[index].RQ;
                        var rqS = rq.split("-");
                        var rqDay = rqS[2];

                        if (rq == currDate) {
                            if (rqDay == "01") {
                                rqDay = '<p style="background-color: #5FB878;line-height: 30px;width: 90%;color: #000000">' + getMyMonth(rqS[1]) + '</p>';
                            } else {
                                rqDay = '<p style="background-color: #5FB878;line-height: 30px;width: 90%;color: #000000">' + rqDay + '</p>';
                            }

                        } else if (rqDay == "01") {
                            rqDay = '<p style="color:#1E9FFF">' + getMyMonth(rqS[1]) + '</p>';
                        }

                        var zc = xlList[index].ZC;
                        var xq = xlList[index].XQ;

                        if (index == 0 && xq != 1) {
                            xlHtml += '<tr>' +
                            '<td style="font-weight:bold;border-right: 1px solid #f3f3f3;">' + zc + '</td>';
                            for (var i = 1; i < xq; i++) {
                                xlHtml += '<td></td>'
                                count++;
                            }
                        } else {
                            if (xq == 1) {
                                xlHtml += '<tr>' +
                                '<td style="font-weight:bold;border-right: 1px solid #f3f3f3;">' + zc + '</td>';
                            }
                        }
                        if (xq > 1 || xq < 7) {
                            if (parseInt(count + 1) % xq == 0) {
                                xlHtml += '<td style="color:#b3a7a7">' + rqDay + '</td>';
                            } else {
                                xlHtml += '<td></td>';
                            }
                        }
                        if (xq == 7) {
                            xlHtml += '</tr>';
                            count = -1;
                        }
                        count++;
                    }
                    $("#xl_by").html(xlHtml);
                    var currXnxqS = data.currXnxq.split("-");
                    $("#xnxq").html(currXnxqS[0] + "-" + currXnxqS[1] + "学年 第" + currXnxqS[2] + "学期");
                } else {
                    layer.msg(msg, {anim: 6, time: 2000});
                }
            }
        }
        ,
        complete: function () {
            layer.close(loadIndex);
        }
    })
})

function getMyMonth(mon) {
    var myMon;
    if (mon == "01") {
        myMon = "一月";
    } else if (mon == "02") {
        myMon = "二月";
    } else if (mon == "03") {
        myMon = "三月";
    } else if (mon == "04") {
        myMon = "四月";
    } else if (mon == "05") {
        myMon = "五月";
    } else if (mon == "06") {
        myMon = "六月";
    } else if (mon == "07") {
        myMon = "七月";
    } else if (mon == "08") {
        myMon = "八月";
    } else if (mon == "09") {
        myMon = "九月";
    } else if (mon == "10") {
        myMon = "十月";
    } else if (mon == "11") {
        myMon = "十一月";
    } else if (mon == "12") {
        myMon = "十二月";
    }
    return myMon;
}