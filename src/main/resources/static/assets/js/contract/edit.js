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
            fun_back();
        },
        error: function (data, textStatus, jqXHR) {
            mApp.unblock();
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
        }
    });

};


function fun_back() {
    window.location = contextPath + "customers/index?showContractModal=" + $("#customerId").val();
}

function fun_delete(id) {
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
                    error: function ( jqXHR, textStatus, errorThrown ) {
                        mApp.unblock();
                        showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
                    }
                });
            }
        }
    });
}

