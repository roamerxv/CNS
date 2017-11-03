var customer_table;

$().ready(function () {
    // 设置 datatables 的错误，不做抛出。以便接管错误信息
    $.fn.dataTable.ext.errMode = 'none';

    customer_table = $("#customer_table").DataTable({
        "width": "100%",
        "autoWidth": true,
        "ajax": {
            url: contextPath + "customers/getDataWithoutPaged.json",
            error: function ( jqXHR, textStatus, errorThrown ) {
                showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
                mApp.unblock();
            },
        },
        "language": {
            "url": contextPath + "assets/js/lib/DataTables-1.10.16/chinese.lang.json"
        },
        "order": [[3, "desc"]],
        "columns": [{
            "data": "name"
        }, {
            "data": "contacts"
        }, {
            "data": "mobile"
        }, {
            "data": "tel"
        }, {
            "data": "address"
        }],
        "columnDefs": [
            {
                "orderable": false,
                "targets": [5],
                "render": function (data, type, row, meta) {
                    // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                    return '<a href="javascript:fun_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'
                }
            }],
    });

    $("#addCustomerLink").on("click", function (e) {
            $.post(contextPath + "events", function (data) {
                Logger.debug(data);
        });
    })
});


function fun_edit(id) {
    window.location = contextPath + "customers/" + id;
}




