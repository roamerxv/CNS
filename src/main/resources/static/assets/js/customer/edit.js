$().ready(function () {

    // 显示此客户对应的合同列表
    contract_table = $("#contract_table").DataTable({
        "width": "100%",
        "autoWidth": true,
        "ajax": {
            url: contextPath + "contracts/"+ "21a12da0-55c4-4ab8-a7f2-3e3f1600ee0c"+"/getDataWithoutPaged.json",
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
            "data": "beginDate"
        }, {
            "data": "endDate"
        }],
        "columnDefs": [
            {
                "orderable": false,
                "targets": [4],
                "render": function (data, type, row, meta) {
                    // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                    return '<a href="javascript:fun_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'
                }
            }],
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
    var id = eventInfoEntity.id;
    Logger.debug(eventInfoEntity);
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
            Logger.debug(data);
            var responseText = JSON.parse(jqXHR.responseText);
            mApp.unblock();
            showMessage("error", "错误", responseText.data[0].errorMessage);
        }
    });

};

function fun_back() {
    window.location = contextPath;
}
