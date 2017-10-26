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
