$().ready(function () {


});


function fun_submit() {
    //关闭页面响应
    mApp.blockPage({
        overlayColor: "#000000",
        type: "loader",
        state: "success",
        message: "正在保存，请稍等..."
    });

    var customerEntity = getUIValue2Json();
    var id = customerEntity.id;
    $.ajax({
        type: 'post',
        data: customerEntity.toString(),
        url: contextPath + 'customers/' + id + ".json",
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            window.location = contextPath + "customers/index";
        },
        error: function ( jqXHR, textStatus, errorThrown ){
            mApp.unblock();
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
        }
    });

};

function fun_back() {
    window.location = contextPath + "customers/index";
}

function fun_contract_edit(id) {
    window.location = contextPath + "contracts/" + id;
}


function fun_contract_delete(id) {
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
                    url: contextPath + 'contracts/' + id + ".json",
                    async: false,//默认为true
                    contentType: "application/json",
                    dataType: 'json',//默认为预期服务器返回的数据类型
                    success: function (data, textStatus, jqXHR) {
                        if (typeof contract_table == 'undefined') {
                            window.location = contextPath;
                        } else {
                            contract_table.ajax.reload();
                            mApp.unblock();
                        }
                    },
                    error: function ( jqXHR, textStatus, errorThrown ) {
                        showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
                        mApp.unblock();
                    }
                });
            }
        }
    });
}
