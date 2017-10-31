// 客户分类
var CustomerTypeEntity = {
    createNew: function () {
        var customerTypeEntity = BaseObj.createNew();
        return customerTypeEntity;
    }
};

function getUIValue2Json() {
    var customerTypeEntity = CustomerTypeEntity.createNew();


    customerTypeEntity.id = "";
    customerTypeEntity.name = $("#type_name").val();

    return customerTypeEntity;
};


$.validator.setDefaults({
    submitHandler: function () {
        alert("提交事件!");
    }
});


var customer_type_select2;

$().ready(function () {
    //显示左侧菜单的对应菜单项激活效果
    activeMenu("menu_customer_type");
    activeMenu("menu_type");
    //end

    fun_block();

    fun_renderSelect2();

    $("#customer_type_form").validate({
        rules: {
            type_name: "required"
        },
        messages: {
            type_name: "请输入分类名称"
        }
    });


    $("button").click(function () {
        var value = $(this).attr("name"); // $(this)表示获取当前被点击元素的name值
        fun_block();
        if ("add_top_type_btn" == value) {
            if (!$("#customer_type_form").valid()) {
                fun_unblock();
                return false;
            }
            ;
            var customerTypeEntity = getUIValue2Json();
            customerTypeEntity.pId = "0";
            //增加一个顶级的分类
            $.ajax({
                type: 'post',
                data: customerTypeEntity.toString(),
                url: contextPath + "customer_type/create.json",
                async: true,//默认为true
                contentType: "application/json",
                dataType: 'json',//默认为预期服务器返回的数据类型
                success: function (data, textStatus, jqXHR) {
                    fun_renderSelect2();
                    $("#type_name").val("");
                    showMessage("success", "成功", "增加一个顶级分类成功");
                },
                error: function (data, textStatus, jqXHR) {
                    Logger.debug(jqXHR);
                    var responseText = JSON.parse(jqXHR.responseText);
                    showMessage("error", "错误", responseText.data[0].errorMessage);
                }
            }).done(function (data) {

            });
        } else if ("add_sub_type_btn" == value) {
            Logger.debug(customer_type_select2.val());
            fun_unblock();
        }
    });
});


function fun_renderSelect2() {
    $.ajax({
        type: "get",
        async: true,
        url: contextPath + "customer_type/list.json",
        dataType: "json",
        contentType: "application/json",
        beforeSend: function () {
            $("#customer_type").html("");
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
        error: function (data) {

        }
    }).done(function (data) {
        fun_unblock();
        $("#type_id").val($("#customer_type_select2").val());
    });
}

function fun_block(){
    mApp.block(".content_container", {
        overlayColor: "#000000",
        type: "loader",
        state: "success",
        message: "正在处理分类信息..."
    });
}

function fun_unblock() {
    mApp.unblock(".content_container");
}
