$().ready(function () {
    // $('#m_modal_gatherInfos').modal('show').css({
    //     // width: '3000000 px',
    //     // 'margin-left': function () {
    //     //     return -($(this).width() / 2);
    //     // }
    // });

    $("#m_modal_gatherInfos").on("show.bs.modal", function () {
        $(this).find(".modal-content").css("width", "110%");
    });

    jQuery.datetimepicker.setLocale('zh');
    $('#beginDate').datetimepicker({
        format: "Y-m-d",
        timepicker: false,    //关闭时间选项
        scrollMonth: false,
        scrollInput: false
    });
    $('#endDate').datetimepicker({
        format: "Y-m-d",
        timepicker: false,    //关闭时间选项
        scrollMonth: false,
        scrollInput: false
    });


    // 显示此合同对应的收款信息列表
    if ($("#gatherInfo_table").length > 0) {
        gatherInfo_table = $("#gatherInfo_table").DataTable({
            "width": "100%",
            "autoWidth": true,
            "ajax": {
                url: contextPath + "gatherInfos/" + $("#id").val() + "/getDataWithoutPaged.json",
                error: function (jqXHR, textStatus, errorThrown) {
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
                "data": "gatherDate"
            }, {
                "data": "notice"
            }, {
                "data": "noticeDate"
            }, {
                "data": "gathered"
            }, {
                "data": "gatheredDate"
            }],
            "columnDefs": [
                {
                    "orderable": false,
                    "targets": [7],
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
        url: contextPath + 'contracts/' + id + ".json",
        async: false,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            window.location = contextPath + "customers/" + $("#customerId").val();
        },
        error: function (data, textStatus, jqXHR) {
            var responseText = JSON.parse(jqXHR.responseText);
            mApp.unblock();
            showMessage("error", "错误", responseText.data[0].errorMessage);
        }
    });

};


function fun_back() {
    window.history.back();
}


// 显示收款信息的编辑界面
function fun_gatherInfo_edit(id) {
    window.location = contextPath + "gatherInfos/" + id;
}


// 删除收款信息
function fun_gatherInfo_delete(id) {
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
                    url: contextPath + 'gatherInfos/' + id + ".json",
                    async: false,//默认为true
                    contentType: "application/json",
                    dataType: 'json',//默认为预期服务器返回的数据类型
                    success: function (data, textStatus, jqXHR) {
                        if (typeof gatherInfo_table == 'undefined') {
                            window.location = contextPath;
                        } else {
                            gatherInfo_table.ajax.reload();
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


