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
    contractEntity.firstGatherDate=$("#firstGatherDate").val();
    contractEntity.gatherInterval=$("#gatherInterval").val();
    contractEntity.gatherCount=$("#gatherCount").val();
    contractEntity.customerId = $("#customerId").val();
    contractEntity.amount = $("#amount").val();


    return contractEntity;
};


$().ready(function () {

});
