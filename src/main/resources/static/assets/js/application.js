var contextPath = $("meta[name='ctx']").attr("content");

Logger.useDefaults();

var BaseObj = {
    createNew: function () {
        var baseObj = {};
        baseObj.toString = function () {
            return JSON.stringify(baseObj);
        }
        return baseObj;
    }
}

function activeMenu(menuName) {
    //显示左侧菜单的对应菜单项激活效果
    // $("li.m-menu__item").removeClass("m-menu__item--active");
    $("li[name='"+menuName+"']").addClass("m-menu__item--active");
    //end
}

$().ready(function () {
    $.ajax({
        type: 'get',
        url: contextPath + 'systemConfigs/banner_message.json',
        async: true,//默认为true
        contentType: "application/json",
        dataType: 'json',//默认为预期服务器返回的数据类型
        success: function (data,  textStatus, jqXHR) {
            $("#banner_text").html(data.value);
        },
        error: function (data) {

        }
    });

})
