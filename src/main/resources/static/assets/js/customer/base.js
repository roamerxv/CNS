// 事件信息
var CustomerEntity = {
    createNew: function () {
        var customerEntity = BaseObj.createNew();
        return customerEntity;
    }
};


function getUIValue2Json() {
    var customerEntity = CustomerEntity.createNew();


    customerEntity.id = $("#id").val();
    customerEntity.name = $("#name").val();
    customerEntity.memo = $("#memo").val();


    return customerEntity;
};


function fun_delete(id) {
    bootbox.confirm({
        message: "确认要删除这条记录吗?一经删除，就无法恢复！",
        buttons: {
            confirm: {
                label: '删除',
                className: 'btn-success'
            },
            cancel: {
                label: '不删了',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                mApp.blockPage({
                    overlayColor: "#000000",
                    type: "loader",
                    state: "success",
                    message: "正在删除，请稍等..."
                });
                $.ajax({
                    type: 'delete',
                    url: contextPath + 'events/' + id + ".json",
                    async: false,//默认为true
                    contentType: "application/json",
                    dataType: 'json',//默认为预期服务器返回的数据类型
                    success: function (data) {
                        if (typeof event_table == 'undefined') {
                            window.location = contextPath;
                        } else {
                            event_table.ajax.reload();
                            mApp.unblock();
                        }
                    },
                    error: function (data) {
                        var responseText = JSON.parse(jqXHR.responseText);
                        mApp.unblock();
                        showMessage("error", "错误", responseText.data[0].errorMessage);
                    }
                });
            }
        }
    });
}

$().ready(function () {
    //显示左侧菜单的对应菜单项激活效果
    activeMenu("menu_customer");
    //end
});
