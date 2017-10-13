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
