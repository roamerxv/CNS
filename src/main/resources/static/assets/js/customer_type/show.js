var customer_type_select2;

$().ready(function () {
    fun_renderSelect2();
});


function fun_renderSelect2() {
    $.ajax({
        type: "get",
        async: true,
        url: contextPath + "customer_type/list4Select2.json",
        dataType: "json",
        contentType: "application/json",
        beforeSend: function () {
            mApp.block(".select2_customer_type", {
                overlayColor: "#000000",
                type: "loader",
                state: "success",
                message: "正在获取分类信息..."
            });
        },
        success: function (data) {
            customer_type_select2 = $("#customer_type").select2({
                data: data,
                language: "zh-CN",//汉化
                placeholder: '请选择分类',//默认文字提示
                allowClear: false,//允许清空
            }).on("change", function (e) {

            })

        },
        error: function ( jqXHR, textStatus, errorThrown ) {
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
        },
    }).done(function (data) {
        //如果存在这个 dom 对象,则说明需要手动设置 选中的值。
        if (  $("#customer_type_org").length  > 0 ){
            customer_type_select2.val($("#customer_type_org").val()).trigger('change');
        }
        mApp.unblock(".select2_customer_type");
    });
}
