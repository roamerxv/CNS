// 收款信息
var GatherInfoEntity = {
    createNew: function () {
        var gatherInfoEntity = BaseObj.createNew();
        return gatherInfoEntity;
    }
};


function getUIValue2Json() {
    var gatherInfoEntity = GatherInfoEntity.createNew();

    gatherInfoEntity.id = $("#id").val();
    gatherInfoEntity.contractId = $("#contractId").val();
    gatherInfoEntity.name = $("#name").val();
    gatherInfoEntity.description = $("#description").val();
    gatherInfoEntity.amount = $("#amount").val();
    gatherInfoEntity.gatherDate = $("#gatherDate").val();
    gatherInfoEntity.noticeDate = $("#noticeDate").val();
    gatherInfoEntity.notice = $("#notice").is(":checked");
    gatherInfoEntity.gathered = $("#gathered").is(":checked");
    gatherInfoEntity.gatheredDate = $("#gatheredDate").val();
    gatherInfoEntity.noticeContent = $("#noticeContent").val();
    gatherInfoEntity.noticeTo = $("#noticeTo").val();

    return gatherInfoEntity;
};

function fun_genContent(){
    var id = $("#id").val();
    $.ajax({
        type: 'get',
        url: contextPath + 'gatherInfos/gen_notic_ontent/' + id + ".json",
        data: {},
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        beforeSend: function () {
            mApp.blockPage({
                overlayColor: "#000000",
                type: "loader",
                state: "success",
                message: "正在生成提醒内容，请稍等..."
            });
        },
        success: function (data, textStatus, jqXHR) {
            $("#noticeContent").val(data.content);
        },
        error: function ( jqXHR, textStatus, errorThrown ) {
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
        },
        complete: function (data) {
            mApp.unblock();
        }
    });

}


$().ready(function () {
    jQuery.datetimepicker.setLocale('zh');
    $('#noticeDate').datetimepicker({
        format: "Y-m-d",
        timepicker: false,    //关闭时间选项
        scrollMonth : false,
        scrollInput : false
    });
    $('#gatherDate').datetimepicker({
        format: "Y-m-d",
        timepicker: false,    //关闭时间选项
        scrollMonth : false,
        scrollInput : false
    });
    $('#gatheredDate').datetimepicker({
        format: "Y-m-d",
        timepicker: false,    //关闭时间选项
        scrollMonth : false,
        scrollInput : false
    });
});



