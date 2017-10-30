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
    submitHandler: function() {
        alert("提交事件!");
    }
});


$().ready(function () {
    //显示左侧菜单的对应菜单项激活效果
    activeMenu("menu_customer_type");
    activeMenu("menu_type");
    //end

    $("#customer_type").select2(
        {
            language: "zh-CN",
            placeholder: '选择一个分类'
        }
    );


    $("#customer_type_form").validate({
        rules: {
            type_name: "required"
        },
        messages: {
            type_name: "请输入分类名称"
        }
    })


    $("button").click(function () {
        var value = $(this).attr("name"); // $(this)表示获取当前被点击元素的name值
        if ("add_top_type_btn" == value) {
            if ( !$("#customer_type_form").valid()){
                return false;
            };

            var customerTypeEntity = getUIValue2Json();
            customerTypeEntity.pId = "0";
            //增加一个顶级的分类
            $.ajax({
                type: 'post',
                data: customerTypeEntity.toString(),
                url: contextPath + "customer_type/create.json",
                async: false,//默认为true
                contentType: "application/json",
                dataType: 'json',//默认为预期服务器返回的数据类型
                success: function (data, textStatus, jqXHR) {
                    showMessage("success", "成功", "增加一个顶级分类成功");
                },
                error: function (data, textStatus, jqXHR) {
                    // Logger.debug(jqXHR);
                    // var responseText = JSON.parse(jqXHR.responseText);
                    // mApp.unblock();
                    // showMessage("error", "错误", responseText.data[0].errorMessage);
                }
            });
        } else if ("add_sub_type_btn" == value) {
            Logger.debug("1111");
        }
    });
});

