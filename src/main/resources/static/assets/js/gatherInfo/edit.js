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

function fun_back() {
    window.history.back();
}
