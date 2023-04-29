class LoginPage {
    constructor(endpoint) {
        this.addUserEndpoint = endpoint + "/add-user";
        this.loginUserEndpoint = endpoint + "/login-user";
    }

    registration() {
        let controller = this;
        let data = $("#register-form").serialize();
        $.ajax({
            type: "post",
            url: controller.addUserEndpoint,
            data: data,
        })
            .done(function () {
                controller.reload();
            })
            .fail(function () {
                controller.reload();
            });
    }

    access() {
        let controller = this;
        let data = $("#login-form").serialize();
        $.ajax({
            type: "post",
            url: controller.loginUserEndpoint,
            data: data,
            traditional: true,
        })
            .done(function (data) {
                data = JSON.parse(data);
                setCookie(data, 1);
                controller.redirect();
            })
            .fail(function () {
                controller.reload();
            });
    }

    redirect() {
        let role = getCookieRole();
        if (role === "client") {
            $(location).attr("href", "client/MainPageClient.html");
        } else {
            $(location).attr("href", "manager/MainPageManager.html");
        }
    }

    reload() {
        $(location).attr("href", "index.html");
    }
}
