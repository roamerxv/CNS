// 客户分类
var CustomerTypeEntity = {
    createNew: function () {
        var customerTypeEntity = BaseObj.createNew();
        return customerTypeEntity;
    }
};

function getUIValue2Json() {
    var customerTypeEntity = CustomerTypeEntity.createNew();


    customerTypeEntity.pId = $("#type_id").val();
    customerTypeEntity.name = $("#type_name").val();

    return customerTypeEntity;
};

var jsTree_type ;

$().ready(function () {
    //显示左侧菜单的对应菜单项激活效果
    activeMenu("menu_customer_type");
    activeMenu("menu_type");
    //end


    $("#customer_type_form").validate({
        rules: {
            type_name: "required"
        },
        messages: {
            type_name: "请输入分类名称"
        }
    });


    fun_render_jsTree();

    $("button").click(function () {
        var value = $(this).attr("name"); // $(this)表示获取当前被点击元素的name值
        if ("add_top_type_btn" == value) {
            if (!$("#customer_type_form").valid()) {
                return false;
            }
            ;
            if ($("#type_id").val() === ""){
                showMessage("danger","错误","请先选中一个父分类");
                return false;
            }
            fun_block();
            var customerTypeEntity = getUIValue2Json();
            Logger.debug(customerTypeEntity);
            //增加一个的分类
            $.ajax({
                type: 'post',
                data: customerTypeEntity.toString(),
                url: contextPath + "customer_type/create.json",
                async: true,//默认为true
                contentType: "application/json",
                dataType: 'json',//默认为预期服务器返回的数据类型
                success: function (data, textStatus, jqXHR) {
                    showMessage("success", "成功", "增加一个分类成功");
                    window.location = contextPath + "/customer_type/edit";
                },
                error: function (data, textStatus, jqXHR) {
                    Logger.debug(jqXHR);
                    var responseText = JSON.parse(jqXHR.responseText);
                    showMessage("error", "错误", responseText.data[0].errorMessage);
                }
            }).done(function (data) {
                fun_unblock();
            });
        } else {

        }
    });

});


function fun_render_jsTree() {

    $.ajax({
        type: 'get',
        url: contextPath + "customer_type/list4jsTree.json",
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            //加上一个 jsTree 的 root 节点
            var new_data = {
                "data": [{
                    "id": "0",
                    "parent": "#",
                    "text": "客户分类"
                }]
            };
            for (var i = 0; i < data.data.length; i++) {

                new_data.data.splice(1 + i, 1, data.data[i]);
            }
            //处理完成
            jsTree_type = $('#jstree_customer_type_div').jstree({
                'core': new_data,
                "themes": {
                    "variant": "large"
                },
                "plugins": [
                    "search", "types", "state",
                ],
                'state': {
                    "opened": true,
                },
            }).bind('changed.jstree', function (e, data) {
                var i, j, r = [];
                for (i = 0, j = data.selected.length; i < j; i++) {
                    r.push(data.instance.get_node(data.selected[i]).id);
                }
                $("#type_id").val(r)
            });
        },
        error: function (data, textStatus, jqXHR) {
            var responseText = JSON.parse(jqXHR.responseText);
            showMessage("error", "错误", responseText.data[0].errorMessage);
        }
    }).done(function (data) {

    });
}


function fun_block() {
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
