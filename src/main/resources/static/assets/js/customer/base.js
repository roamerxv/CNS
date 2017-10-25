var contract_table ;
var customer_table ;

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
    customerEntity.contacts = $("#contacts").val();
    customerEntity.mobile = $("#mobile").val();
    customerEntity.tel = $("#tel").val();
    customerEntity.address = $("#address").val();

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
                    url: contextPath + 'customers/' + id + ".json",
                    async: false,//默认为true
                    contentType: "application/json",
                    dataType: 'json',//默认为预期服务器返回的数据类型
                    success: function (data, textStatus, jqXHR) {
                        if (typeof customer_table == 'undefined') {
                            window.location = contextPath;
                        } else {
                            customer_table.ajax.reload();
                            mApp.unblock();
                        }
                    },
                    error: function (data, textStatus, jqXHR) {
                        var responseText = data.responseJSON.data[0].errorMessage;
                        mApp.unblock();
                        showMessage("error", "错误", responseText);
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
