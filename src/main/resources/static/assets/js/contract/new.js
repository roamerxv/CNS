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
    //这里使用的是 bootstrap 的 daterangepicker 插件
    $('#liveDate').daterangepicker({
        locale:{
            format: 'YYYY-MM-DD',
            applyLabel: '确认',
            cancelLabel: '取消',
            fromLabel: '从',
            toLabel: '到',
            weekLabel: 'W',
            customRangeLabel: 'Custom Range',
            daysOfWeek:["日","一","二","三","四","五","六"],
            monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
        }
    });

    jQuery.datetimepicker.setLocale('zh');
    $('#firstGatherDate').datetimepicker({
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
        message: "正在保存和生成收款计划表，请稍等..."
    });

    var contractEntity = getUIValue2Json();
    var id = contractEntity.id;
    $.ajax({
        type: 'post',
        data: contractEntity.toString(),
        url: contextPath + 'contracts/' + id + ".json",
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data, textStatus, jqXHR) {
            window.location = contextPath + "customers/index?showContractModal=" + $("#customerId").val();
        },
        error: function ( jqXHR, textStatus, errorThrown ){
            Logger.debug(jqXHR);
            mApp.unblock();
            showMessage("danger", "错误", jqXHR.responseJSON.data[0].errorMessage);
        }
    });

};



function fun_back() {
    window.location = contextPath + "customers/index?showContractModal=" + $("#customerId").val();
}


