$().ready(function () {
    jQuery.datetimepicker.setLocale('zh');
    $('#actDate').datetimepicker({
        format: "Y-m-d",
        timepicker:false,    //关闭时间选项
    });
});


function fun_submit() {
    //关闭页面响应
    mApp.blockPage({
        overlayColor: "#000000",
        type: "loader",
        state: "success",
        message: "正在保存，请稍等..."
    });

    var eventInfoEntity = getUIValue2Json();
    var id = eventInfoEntity.id ;
    $.ajax({
        type: 'post',
        data: eventInfoEntity.toString(),
        url: contextPath + 'events/' + id+".json",
        async: false,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data) {
            window.location = contextPath ;
        },
        error: function (data) {
            var responseText = JSON.parse(jqXHR.responseText);
            mApp.unblock();
            showMessage("error", responseText.data[0].errorMessage);
        }
    });
};

function fun_back() {
    window.location = contextPath ;
}
