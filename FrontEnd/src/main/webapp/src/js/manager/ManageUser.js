class ManageUser {
    constructor(endPoint) {
        this.getUsersEndpoint = endPoint + "/get-users";
        this.toggleRoleEndPoint = endPoint + "/toggle-role-user";
        this.toggleBanEndPoint = endPoint + "/toggle-ban-user";
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getUsersEndpoint, function (data) {
            controller.renderGUI(data);
        })
            .done(function () {
                $("#insert-button").prop("disabled", false);
            })
            .fail(function () {
                controller.renderAlert(
                    "Error while charging data!! Retry in a few second.",
                    false
                );
            });
    }

    renderGUI(data) {
        $("#table td").remove();
        let staticHtml = $("#table-template").html();
        $.each(data, function (index, obj) {
            let row = staticHtml;
            if (obj.id_user !== getCookieID()) {
                row = row.replace(/{USERNAME}/gi, obj.username);
                row = row.replace(/{ROLE}/gi, obj.role);
                if (obj.role === "manager") {
                    row = row.replace(/{ROLE_INT}/gi, 0);
                } else {
                    row = row.replace(/{ROLE_INT}/gi, 1);
                }
                row = row.replace(/{BAN_INT}/, obj.ban);
                if (obj.ban === 1) {
                    row = row.replace(/{BAN}/gi, "Banned");
                } else {
                    row = row.replace(/{BAN}/gi, "Regular");
                }
                row = row.replace(/{ID_USER_B}/gi, obj.id_user);
                row = row.replace(/{ID_USER_R}/gi, obj.id_user);
                $("#table-rows").append(row);
            }
        });
        if (data.length === 0) {
            $("tfoot").html('<tr><th colspan="3">No records</th></tr>');
        } else {
            $("tfoot tr:first").fadeOut(100, function () {
                $("tfoot tr:first").remove();
            });
        }
    }

    toggleBan(ban, id_user) {
        $.post(
            this.toggleBanEndPoint,
            {id_user: id_user, ban: ban},
            function () {
            }
        )
            .done(function () {
            })
            .fail(function () {
            })
            .always(function () {
                controller.fillTable();
            });
    }

    toggleRole(id, role_int) {
        let role = role_int === 0 ? "manager" : "client";
        $.post(this.toggleRoleEndPoint, {id_user: id, role: role}, function () {
        })
            .done(function () {
            })
            .fail(function () {
            })
            .always(function () {
                controller.fillTable();
            });
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
