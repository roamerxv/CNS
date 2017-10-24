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

})
