$().ready(function () {
    // 统计本周的待收款笔数
    $.ajax({
        type: 'post',
        url: contextPath + 'gatherInfos/sum/thisMonth.json',
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            $("#gather_sum_this_month").html(data.data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
            mApp.unblock();
        }
    });
});
