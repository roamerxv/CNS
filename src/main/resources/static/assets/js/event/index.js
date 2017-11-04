var event_table;

$().ready(function () {
    // 设置 datatables 的错误，不做抛出。以便接管错误信息
    $.fn.dataTable.ext.errMode = 'none';

    event_table = $("#event_table").DataTable({
        "width": "100%",
        "autoWidth": true ,
        "ajax": {
            url: contextPath + "events/getDataWithoutPaged.json",
            error: function (data, textStatus, jqXHR) {
                showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
            },
        },
        "language": {
            "url": contextPath + "assets/js/lib//DataTables-1.10.16/chinese.lang.json"
        },
        "order": [[3, "desc"]],
        "columns": [{
            "data": "name"
        }, {
            "data": "memo"
        }, {
            "data": "noticeContent"
        }, {
            "data": "actDate"
        }, {
            "data": "repeatType"
        }, {
            "data": "notice"
        }, {
            "data": "noticeMail"
        }],
        "columnDefs": [
            {
                "orderable": false,
                "targets": [7],
                "render": function (data, type, row, meta) {
                    // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                    return '<a href="javascript:fun_edit(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                        '<a href="javascript:fun_delete(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>' +
                        '<a href="javascript:fun_notice(\'' + row.id + '\')" class="m-portlet__nav-link btn m-btn m-btn--hover-success m-btn--icon m-btn--icon-only m-btn--pill" title="测试提醒"><i class="la la-send"></i></a>'
                }
            },
            {
                "orderable": false,
                "targets": [5],
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
            },
            {
                "orderable": true,
                "targets": [4],
                "render": function (data, type, row) {
                    if (row.repeatType == 0) {
                        return '<span>当天提醒一次</span>'
                    } else if (row.repeatType == 1) {
                        return '<span>每月当日提醒</span>'
                    } else if (row.repeatType == 2) {
                        return '<span>每周提醒</span>'
                    }

                }
            }
        ]
    });
});


function fun_edit(id) {
    window.location = contextPath + "events/" + id;
}

function fun_notice(id) {
    //关闭页面响应
    mApp.blockPage({
        overlayColor: "#000000",
        type: "loader",
        state: "success",
        message: "正在发送提醒,请稍等..."
    });
    $.ajax({
        type: 'post',
        url: contextPath + 'notice/test/' + id + ".json",
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            showMessage("success", "成功", data.data.localMessage);
        },
        error: function (data, textStatus, jqXHR) {
            showMessage("danger", "错误", data.responseJSON.data[0].errorMessage);
        },
    }).always(function () {
        //关闭页面响应
        mApp.unblock();
    });
}

