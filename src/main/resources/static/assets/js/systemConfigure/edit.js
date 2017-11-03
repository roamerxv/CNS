$().ready(function () {
    activeMenu("menu2")
});


function fun_update(name) {
    var jsonObject = {};
    jsonObject.name = name;
    jsonObject.value = $("#" + name + "").val();
    $.ajax({
        type: 'post',
        data: JSON.stringify(jsonObject),
        url: contextPath + 'systemConfigs/' + jsonObject.name + ".json",
        async: false,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus) {
            showMessage("success", "成功", "更新完成！");
        },
        error: function ( jqXHR, textStatus, errorThrown ) {
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
        }
    });
}
