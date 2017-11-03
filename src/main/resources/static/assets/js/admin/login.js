var SnippetLogin = function () {
    var e = $("#m_login"), i = function (e, i, a) {
        var t = $('<div class="m-alert m-alert--outline alert alert-' + i + ' alert-dismissible" role="alert">\t\t\t<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>\t\t\t<span></span>\t\t</div>');
        e.find(".alert").remove(), t.prependTo(e), t.animateClass("fadeIn animated"), t.find("span").html(a)
    }, a = function () {
        e.removeClass("m-login--forget-password"), e.removeClass("m-login--signin"), e.addClass("m-login--signup"), e.find(".m-login__signup").animateClass("flipInX animated")
    }, t = function () {
        e.removeClass("m-login--forget-password"), e.removeClass("m-login--signup"), e.addClass("m-login--signin"), e.find(".m-login__signin").animateClass("flipInX animated")
    }, r = function () {
        e.removeClass("m-login--signin"), e.removeClass("m-login--signup"), e.addClass("m-login--forget-password"), e.find(".m-login__forget-password").animateClass("flipInX animated")
    }, n = function () {
        $("#m_login_forget_password").click(function (e) {
            e.preventDefault(), r()
        }), $("#m_login_forget_password_cancel").click(function (e) {
            e.preventDefault(), t()
        }), $("#m_login_signup").click(function (e) {
            e.preventDefault(), a()
        }), $("#m_login_signup_cancel").click(function (e) {
            e.preventDefault(), t()
        })
    }, l = function () {
        $("#m_login_signin_submit").click(function (e) {

            var send_data = {};
            send_data.name = $("#Username").val();
            send_data.passwd = $("#Password").val();

            e.preventDefault();
            var a = $(this), t = $(this).closest("form");

            t.validate({
                rules: {
                    email: {required: !0},
                    password: {required: !0}
                }
            }), t.valid() && (a.addClass("m-loader m-loader--right m-loader--light").attr("disabled", !0), t.submit(
                $.ajax({
                    type: 'post',
                    data: JSON.stringify(send_data),
                    url: contextPath + 'signIn.json',
                    async: false,//默认为true
                    contentType: "application/json",
                    dataType: 'json',//默认为预期服务器返回的数据类型
                    beforeSend: function (data) {
                        cleanAllExceptionTip();
                    },
                    success: function (e, r, n, l) {
                        window.location = contextPath ;
                    },
                    error: function (data) {
                        Logger.debug("出现错误！");
                        setTimeout(function () {
                            a.removeClass("m-loader m-loader--right m-loader--light").attr("disabled", !1), i(t, "danger", "错误的用户名或密码！请重试")
                        }, 1e3)
                        //showExceptionTip(data);
                    }
                })
            ))
        })
    }, s = function () {
        $("#m_login_signup_submit").click(function (a) {
            a.preventDefault();
            var r = $(this), n = $(this).closest("form");
            n.validate({
                rules: {
                    fullname: {required: !0},
                    email: {required: !0, email: !0},
                    password: {required: !0},
                    rpassword: {required: !0},
                    agree: {required: !0}
                }
            }), n.valid() && (r.addClass("m-loader m-loader--right m-loader--light").attr("disabled", !0), n.ajaxSubmit({
                url: contextPath + "",
                success: function (a, l, s, o) {
                    setTimeout(function () {
                        r.removeClass("m-loader m-loader--right m-loader--light").attr("disabled", !1), n.clearForm(), n.validate().resetForm(), t();
                        var a = e.find(".m-login__signin form");
                        a.clearForm(), a.validate().resetForm(), i(a, "success", "Thank you. To complete your registration please check your email.")
                    }, 1e3)
                }
            }))
        })
    }, o = function () {
        $("#m_login_forget_password_submit").click(function (a) {
            a.preventDefault();
            var r = $(this), n = $(this).closest("form");
            n.validate({
                rules: {
                    email: {
                        required: !0,
                        email: !0
                    }
                }
            }), n.valid() && (r.addClass("m-loader m-loader--right m-loader--light").attr("disabled", !0), n.ajaxSubmit({
                url: "",
                success: function (a, l, s, o) {
                    setTimeout(function () {
                        r.removeClass("m-loader m-loader--right m-loader--light").attr("disabled", !1), n.clearForm(), n.validate().resetForm(), t();
                        var a = e.find(".m-login__signin form");
                        a.clearForm(), a.validate().resetForm(), i(a, "success", "Cool! Password recovery instruction has been sent to your email.")
                    }, 2e3)
                }
            }))
        })
    };
    return {
        init: function () {
            n(), l(), s(), o()
        }
    }
}();
jQuery(document).ready(function () {
    SnippetLogin.init()
});
