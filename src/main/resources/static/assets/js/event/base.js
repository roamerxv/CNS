// 事件信息
var EventInfoEntity = {
    createNew: function () {
        var eventInfoEntity = BaseObj.createNew();
        return eventInfoEntity;
    }
};


function getUIValue2Json() {
    var eventInfoEntity = EventInfoEntity.createNew();


    eventInfoEntity.id = $("#id").val();
    eventInfoEntity.name = $("#name").val();
    eventInfoEntity.memo = $("#memo").val();
    eventInfoEntity.actDate = $("#actDate").val();
    eventInfoEntity.noticeContent = $("#noticeContent").val();
    eventInfoEntity.repeatType = $("#repeatType").val();
    eventInfoEntity.notice = $("#notice").is(":checked");
    eventInfoEntity.noticeMail = $("#noticeMail").val();

    return eventInfoEntity;
};


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
                    url: contextPath + 'events/' + id + ".json",
                    async: false,//默认为true
                    contentType: "application/json",
                    dataType: 'json',//默认为预期服务器返回的数据类型
                    success: function (data) {
                        if ( typeof event_table == 'undefined' ){
                            window.location = contextPath ;
                        }else{
                            event_table.ajax.reload();
                            mApp.unblock();
                        }
                    },
                    error: function (data) {
                        var responseText = JSON.parse(jqXHR.responseText);
                        mApp.unblock();
                        showMessage("error", responseText.data[0].errorMessage);
                    }
                });
            }
        }
    });
}

$().ready(function () {
    //显示左侧菜单的对应菜单项激活效果
    $("li.m-menu__item").removeClass("m-menu__item--active");
    $("li[name='menu1']").addClass("m-menu__item--active");
    //end
});
