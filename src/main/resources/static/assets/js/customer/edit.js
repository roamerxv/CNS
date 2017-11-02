$().ready(function () {
    // 显示此客户对应的合同列表
    if ($("#contract_table").length > 0) {
        contract_table = $("#contract_table").DataTable({
            "width": "100%",
            "autoWidth": true,
            "ajax": {
                url: contextPath + "contracts/" + $("#id").val() + "/getDataWithoutPaged.json",
                error: function (jqXHR, textStatus, errorThrown) {
                    var responseText = JSON.parse(jqXHR.responseText);
                    showMessage("error", "错误", responseText.data[0].errorMessage);
                },
            },
            "language": {
                "url": contextPath + "assets/js/lib/DataTables-1.10.16/chinese.lang.json"
            },
            "columns": [{
                "data": "name"
            }, {
                "data": "description"
            }, {
                "data": "amount"
            }, {
                "data": "beginDate"
            }, {
                "data": "endDate"
            }],
            "columnDefs": [
                {
                    "orderable": false,
                    "targets": [5],
                    "render": function (data, type, row, meta) {
                        // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                        return '<a href="javascript:fun_contract_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                            '<a href="javascript:fun_contract_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'
                    }
                }],
        });
    }
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
    var id = eventInfoEntity.id;
    $.ajax({
        type: 'post',
        data: eventInfoEntity.toString(),
        url: contextPath + 'customers/' + id + ".json",
        async: false,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            window.location = contextPath + "customers/index";
        },
        error: function (data, textStatus, jqXHR) {
            Logger.debug(jqXHR);
            var responseText = JSON.parse(jqXHR.responseText);
            mApp.unblock();
            showMessage("error", "错误", responseText.data[0].errorMessage);
        }
    });

};

function fun_back() {
    window.location = contextPath;
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
                    error: function (data, textStatus, jqXHR) {
                        var responseText = JSON.parse(jqXHR.responseText);
                        mApp.unblock();
                        showMessage("error", "错误", responseText.data[0].errorMessage);
                    }
                });
            }
        }
    });
}