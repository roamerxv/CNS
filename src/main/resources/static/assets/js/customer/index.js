var customer_table;
var contract_table;
var gatherInfo_table;

var loading_message = "正在重新获取数据，请稍等...";

$().ready(function () {
    customer_table = $("#customer_table").DataTable({
        layout: {theme: "default", class: "", scroll: !1, height: 450, footer: !1},
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
                    return '<a href="javascript:fun_show_contract_modal(\'' + row.id + '\')"  class="m-portlet__nav-link btn m-btn m-btn--hover-info m-btn--icon m-btn--icon-only m-btn--pill" title=" 合同"><i class="la flaticon-list"></i></a>' +
                        '<a href="javascript:fun_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'

                }
            }],
    });

    contract_table = $("#contract_table").DataTable({
        "width": "100%",
        "autoWidth": true,
        "ajax": {
            //url: contextPath + "contracts/" + customer_id + "/getDataWithoutPaged.json",
            beforeSend: function () {
                mApp.block((".modal-content"), {
                    overlayColor: "#000000",
                    type: "loader",
                    state: "success",
                    message: loading_message
                });
            },
            function ( jqXHR, textStatus, errorThrown ){
                Logger.debug(data);
                //showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
            },
            complete: function () {
                mApp.unblock(".modal-content");
            }
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
                    return '<a href="javascript:fun_show_gather_modal(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-info m-btn--icon m-btn--icon-only m-btn--pill" title="收款计划列表"><i class="la flaticon-calendar"></i></a>' +
                        '<a href="javascript:fun_contract_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_contract_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'
                }
            }],
    });

    gatherInfo_table = $("#gatherInfo_table").DataTable({
        "width": "100%",
        "autoWidth": true,
        "ajax": {
            // url: contextPath + "gatherInfos/" + contract_id + "/getDataWithoutPaged.json",
            beforeSend: function () {
                mApp.block((".modal-content"), {
                    overlayColor: "#000000",
                    type: "loader",
                    state: "success",
                    message: loading_message
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
            },
            complete: function () {
                mApp.unblock(".modal-content");
            }

        },
        "language": {
            "url": contextPath + "assets/js/lib/DataTables-1.10.16/chinese.lang.json"
        },
        "columns": [{
            "data": "name"
        }, {
            "data": "amount"
        }, {
            "data": "gatherDate"
        }, {
            "data": "notice"
        }, {
            "data": "noticeDate"
        }, {
            "data": "gathered"
        }],
        "order": [[2, "asc"]],
        "columnDefs": [
            {
                "orderable": false,
                "targets": [6],
                "render": function (data, type, row, meta) {
                    // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                    return '<a href="javascript:fun_gatherInfo_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_gatherInfo_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>'
                }
            }, {
                "orderable": false,
                "targets": [3],
                "render": function (data, type, row, meta) {
                    if (row.notice) {
                        return '<span class="m-switch m-switch--sm m-switch--icon">\n' +
                            '<label>\n' +
                            '<input type="checkbox" checked="checked"  disabled name="" style="margin-left: 0px">\n' +
                            '<span></sp\\an>\n' +
                            '</label>\n' +
                            '</span>'
                    } else {
                        return '<span class="m-switch m-switch--sm m-switch--icon">\n' +
                            '<label>\n' +
                            '<input type="checkbox" name=""  disabled style="margin-left: 0px">\n' +
                            '<span></sp\\an>\n' +
                            '</label>\n' +
                            '</span>'
                    }

                }
            }, {
                "orderable": false,
                "targets": [5],
                "render": function (data, type, row, meta) {
                    if (row.gathered) {
                        return '<span class="m-switch m-switch--sm m-switch--icon">\n' +
                            '<label>\n' +
                            '<input type="checkbox" checked="checked"  disabled name="" style="margin-left: 0px">\n' +
                            '<span></sp\\an>\n' +
                            '</label>\n' +
                            '</span>'
                    } else {
                        return '<span class="m-switch m-switch--sm m-switch--icon">\n' +
                            '<label>\n' +
                            '<input type="checkbox" name=""  disabled style="margin-left: 0px">\n' +
                            '<span></sp\\an>\n' +
                            '</label>\n' +
                            '</span>'
                    }

                }
            },
        ],
    });



    $("#addCustomerLink").on("click", function (e) {
        $.post(contextPath + "events", function (data) {
            Logger.debug(data);
        });
    })

    $("#m_modal_contracts").on('shown.bs.modal', function () {
        // 显示此客户对应的合同列表
        contract_table.clear();
        contract_table.ajax.url(contextPath + "contracts/" + customer_id + "/getDataWithoutPaged.json").load();
    });

    $("#m_modal_gatherInfos").on('shown.bs.modal', function () {
        gatherInfo_table.clear();
        gatherInfo_table.ajax.url(contextPath + "gatherInfos/" + contract_id + "/getDataWithoutPaged.json").load();
    });

    if (getUrlParam("showContractModal")!= null){
        fun_show_contract_modal(getUrlParam("showContractModal"));
    };

    if (getUrlParam("showGahterInfoModal")!= null){
        fun_show_gather_modal(getUrlParam("showGahterInfoModal"));
    };
});


function fun_edit(id) {
    window.location = contextPath + "customers/" + id;
}

/**
 * 打开合同列表的模式框
 * @param id
 */
function fun_show_contract_modal(id) {
    customer_id = id;
    $('#m_modal_contracts').modal('show');
}

/**
 * 跳转到客户新建合同的界面
 */
function fun_create_contract() {
    window.location = contextPath + "/customers/" + customer_id + "/contracts";
}

/**
 * 打开收款信息列表的模式框
 * @param id
 */
function fun_show_gather_modal(id) {
    contract_id = id;
    // 显示此合同对应的收款信息列表
    $('#m_modal_gatherInfos').modal('show');
}

/**
 *  显示收款信息的编辑界面
 */

function fun_gatherInfo_edit(id) {
    window.location = contextPath + "gatherInfos/" + id;
}


/**
 * 跳转到新建收款记录的界面
 */
function fun_create_gatherinfo() {
    window.location = contextPath + "/contracts/" + contract_id + "/gatherInfos";
}
