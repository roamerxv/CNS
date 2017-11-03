$().ready(function () {


});


function fun_submit() {
    // 关闭页面响应
    mApp.blockPage({
        overlayColor: "#000000",
        type: "loader",
        state: "success",
        message: "正在保存，请稍等..."
    });

    var gatherInfoEntity = getUIValue2Json();
    var id = gatherInfoEntity.id;
    $.ajax({
        type: 'post',
        data: gatherInfoEntity.toString(),
        url: contextPath + 'gatherInfos/' + id + ".json",
        async: false,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            window.location = contextPath + "contracts/"+ $("#contractId").val();
        },
        error: function ( jqXHR, textStatus, errorThrown ) {
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
            mApp.unblock();
        }
    });
};


function fun_notice_test(){
    Logger.debug($("#noticeContent").val().replace(/\s/g,""));
    if ( $("#noticeContent").val().replace(/\s/g,"")=="" ){
        $.notify({
            title: "<strong>错误</strong> ",
            message: "提醒内容必须设置！"
        }, {
            type: "danger",
            placement: {
                from: 'bottom',
                align: 'right'
            }
        });
        return false;
    };
    if ($("#noticeTo").val().replace(/\s/g,"")=="" ){
        $.notify({
            title: "<strong>错误</strong> ",
            message: "发送的地址，必须设置！"
        }, {
            type: "danger",
            placement: {
                from: 'bottom',
                align: 'right'
            }
        });
        return false;
    };
    // 调用测试功能
    //关闭页面响应
    mApp.blockPage({
        overlayColor: "#000000",
        type: "loader",
        state: "success",
        message: "正在发送提醒测试,请稍等..."
    });
    var id = $("#id").val();
    $.ajax({
        type: 'post',
        url: contextPath + 'gatherInfos/notice/' + id + ".json",
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            showMessage("success", "成功", data.data.localMessage);
        },
        error: function (data, textStatus, jqXHR) {
            showMessage("danger", "错误", data.responseJSON.data[0].errorMessage);
        },
    }).always(function () {
        //关闭页面响应
        mApp.unblock();
    });
}

function fun_back() {
    window.history.back();
}
