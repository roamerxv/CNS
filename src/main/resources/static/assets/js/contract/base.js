var contract_table ;
var gatherInfo_table;

// 合同信息
var ContractEntity = {
    createNew: function () {
        var contractEntity = BaseObj.createNew();
        return contractEntity;
    }
};


function getUIValue2Json() {
    var contractEntity = ContractEntity.createNew();

    contractEntity.id = $("#id").val();
    contractEntity.name = $("#name").val();
    contractEntity.description = $("#description").val();
    contractEntity.beginDate = $("#beginDate").val();
    contractEntity.endDate = $("#endDate").val();
    contractEntity.customerId = $("#customerId").val();
    contractEntity.amount = $("#amount").val();


    return contractEntity;
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

$().ready(function () {

});
