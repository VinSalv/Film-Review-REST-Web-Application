class UserEdit {
    constructor(endPoint) {
        this.editEndPoint = endPoint + "/edit-user";
        this.deleteEndPoint = endPoint + "/delete-user";
        this.getEndPoint = endPoint + "/get-user";
    }

    fillTable() {
        $.getJSON(this.getEndPoint, {id_user: getCookieID()}, function (data) {
            $("#id_user").val(getCookieID());
            $("#role").val(getCookieRole());
            $("#password").val(data.password);
            $("#email").val(data.email);
            $("#username").val(data.username);
        });
        if (getCookieRole() === "admin") {
            $("#delete-button").prop("hidden", true);
        }
    }

    editUser() {
        let controller = this;
        let newUsername = $('#username').val();
        let data = $("#entry-form").serialize();
        $.post(controller.editEndPoint, data, function () {
        })
            .done(function () {
                updateCookieUsername(newUsername);
                controller.renderAlert("Account successfully edit");
                window.location.reload(true);
            })
            .fail(function () {
                controller.renderAlert("Error while editing this account!! Try again.");
            });
    }

    deleteUser() {
        $.get(this.deleteEndPoint, {id_user: getCookieID()}, function () {
        });
        logout();
    }

    renderAlert(message, success) {
        let alert;
        if (success) {
            alert = $("#success-alert-template");
        } else {
            alert = $("#fail-alert-template");
        }
        const html = alert.html().replace(/{message}/gi, message);
        $(html)
            .prependTo("#response-alert-section")
            .delay(5000)
            .queue(function () {
                $(this).remove();
            });
    }
}
