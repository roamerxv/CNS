var customer_table;
var customer_id ;

$().ready(function () {
    // 设置 datatables 的错误，不做抛出。以便接管错误信息
    $.fn.dataTable.ext.errMode = 'none';

    customer_table = $("#customer_table").DataTable({
        "width": "100%",
        "autoWidth": true,
        "ajax": {
            url: contextPath + "customers/getDataWithoutPaged.json",
            error: function (jqXHR, textStatus, errorThrown) {
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
            "data": "type.name"
        }],
        "columnDefs": [
            {
                "orderable": false,
                "targets": [3],
                "render": function (data, type, row, meta) {
                    // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                    return '<a href="javascript:fun_show_modal(\'' + row.id + '\')"  class="m-portlet__nav-link btn m-btn m-btn--hover-info m-btn--icon m-btn--icon-only m-btn--pill" title=" 合同"><i class="la flaticon-list"></i></a>' +
                        '<a href="javascript:fun_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'

                }
            }],
    });

    $("#addCustomerLink").on("click", function (e) {
        $.post(contextPath + "events", function (data) {
            Logger.debug(data);
        });
    })

    $( "#m_modal_contracts" ).on('shown.bs.modal', function(){
        contract_table.clear();
        contract_table.ajax.reload();
    });
});


function fun_edit(id) {
    window.location = contextPath + "customers/" + id;
}


function fun_show_modal(id) {
    customer_id = id ;
    $('#m_modal_contracts').modal('show');
    // 显示此客户对应的合同列表

    contract_table = $("#contract_table").DataTable({
            "width": "100%",
            "autoWidth": true,
            "ajax": {
                url: contextPath + "contracts/" + customer_id + "/getDataWithoutPaged.json",
                error: function (data, textStatus, jqXHR) {
                    Logger.debug(jqXHR);
                    showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
                },
            },
            "language": {
                "url": contextPath + "assets/js/lib/DataTables-1.10.16/chinese.lang.json"
            },
            "columns": [{
                "data": "name"
            }, {
                "data": "amount"
            }, {
                "data": "beginDate"
            }, {
                "data": "endDate"
            }, {
                "data": "firstGatherDate"
            }],
            "order": [[2, "desc"]],
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

/**
 * 跳转到客户新建合同的界面
 */
function fun_create_contract() {
     window.location=contextPath+"/customers/"+customer_id+"/contracts";
}

