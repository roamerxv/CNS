var event_table;

$().ready(function () {
    // 设置 datatables 的错误，不做抛出。以便接管错误信息
    $.fn.dataTable.ext.errMode = 'none';

    event_table = $("#event_table").DataTable({
        "ajax": contextPath + "/event/getDataWithoutPaged.json",
        "language": {
            "url": contextPath + "assets/js/lib//DataTables-1.10.16/chinese.lang.json"
        },
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
                "render": function (data, type, row) {
                    // return '<button type="button" class="btn btn-outline-primary"  onclick="fun_edit(\'' + row.id + '\')">编辑</button>&nbsp;&nbsp;<button class="btn btn-outline-danger btn-sm" type="button"  onclick="fun_delete(\'' + row.id + '\')">删除</button>'
                    Logger.debug(row);
                    return  '<a href="javascript:fun_edit(\''+row.id+'\')" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑"><i class="la la-edit"></i></a>' +
                            '<a href="#" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title=" 删除"><i class="la la-trash"></i></a>' +
                            '<a href="#" class="m-portlet__nav-link btn m-btn m-btn--hover-success m-btn--icon m-btn--icon-only m-btn--pill" title="测试提醒"><i class="la la-send"></i></a>'
                }
            },
            {
                "orderable": false,
                "targets": [5],
                "render": function (data, type, row) {
                    if (row.notice){
                        return '<span class="m-badge m-badge--success" title="正在进行提醒"></span>'
                    }else{
                        return '<a class="m-badge m-badge--danger" alt="不再进行提醒"></a>'
                    }

                }
            },
            {
                "orderable": true,
                "targets": [4],
                "render": function (data, type, row) {
                    if (row.repeatType == 0){
                        return '<span>当天提醒一次</span>'
                    }else if(row.repeatType == 1) {
                        return '<span>每月当日提醒</span>'
                    }else if (row.repeatType == 2) {
                        return '<span>每周提醒</span>'
                    }

                }
            }
        ]
    });
});

function fun_edit(id){
    window.location = contextPath + "event/"+id;
}
