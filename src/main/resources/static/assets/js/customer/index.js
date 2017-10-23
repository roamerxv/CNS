var customer_table;

$().ready(function () {
    // 设置 datatables 的错误，不做抛出。以便接管错误信息
    $.fn.dataTable.ext.errMode = 'none';

    customer_table = $("#customer_table").DataTable({
        "width": "100%",
        "autoWidth": true ,
        "ajax": {
            url: contextPath + "customers/getDataWithoutPaged.json",
            error: function (jqXHR, textStatus, errorThrown) {
                var responseText = JSON.parse(jqXHR.responseText);
                showMessage("error", "错误", responseText.data[0].errorMessage);
            },
        },
        "language": {
            "url": contextPath + "assets/js/lib/DataTables-1.10.16/chinese.lang.json"
        },
        "order": [[3, "desc"]],
        // "columns": [{
        //     "data": "name"
        // }, {
        //     "data": "contacts"
        // }, {
        //     "data": "mobile"
        // }, {
        //     "data": "tel"
        // }, {
        //     "data": "address"
        // }]
    });
});


function fun_edit(id) {
    window.location = contextPath + "customers/" + id;
}


