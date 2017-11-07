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
    var liveDate = $("#liveDate").val();
    contractEntity.beginDate = liveDate.slice(0,10);
    Logger.debug(contractEntity.beginDate);
    contractEntity.endDate = liveDate.slice(13,23);
    Logger.debug(contractEntity.endDate);
    contractEntity.firstGatherDate=$("#firstGatherDate").val();
    contractEntity.gatherInterval=$("#gatherInterval").val();
    contractEntity.gatherCount=$("#gatherCount").val();
    contractEntity.customerId = $("#customerId").val();
    contractEntity.amount = $("#amount").val();


    return contractEntity;
};


$().ready(function () {

});
