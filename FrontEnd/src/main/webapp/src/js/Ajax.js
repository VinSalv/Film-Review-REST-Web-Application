$.ajaxSetup({cache: false});

$(document).ajaxStart(function () {
    $("#ajaxLoader").removeClass("hide");
    $("#ajaxLoader").addClass("show");
});

$(document).ajaxComplete(function () {
    $("#ajaxLoader").removeClass("show");
    $("#ajaxLoader").addClass("hide");
});

$(document).ajaxError(function (request, response) {
    $("#ajaxLoader").removeClass("show");
    $("#ajaxLoader").addClass("hide");
    $(".modal-header").addClass("bg-danger");
    $(".modal-title").html("Error");
    $("#redirect-to-home").val(0);
    $("#ajax-response").html("Erore ajax: <br>" + response.statusText);
    $("#modal-ajax-response").modal();
});

$(document).ajaxStop(function () {
    $("#ajaxLoader").removeClass("show");
    $("#ajaxLoader").addClass("hide");
});

$("a")
    .not(".no-ajax")
    .click(function (e) {
        $("#ajaxLoader").removeClass("hide");
        $("#ajaxLoader").addClass("show");
        setTimeout(function () {
            $("#ajaxLoader").removeClass("show");
        }, 3000);
        return true;
    });

$(".btn")
    .not(".no-ajax")
    .click(function (e) {
        $("#ajaxLoader").removeClass("hide");
        $("#ajaxLoader").addClass("show");
        setTimeout(function () {
            $("#ajaxLoader").removeClass("show");
        }, 3000);
        return true;
    });

$("table tbody tr td a, table tfoot tr td a")
    .not(".no-ajax")
    .click(function (e) {
        $("#ajaxLoader").removeClass("hide");
        $("#ajaxLoader").addClass("show");
        setTimeout(function () {
            $("#ajaxLoader").removeClass("hide");
            $("#ajaxLoader").addClass("show");
        }, 3000);
        return true;
    });

$(".loading").click(function (e) {
    $("#ajaxLoader").removeClass("hide");
    $("#ajaxLoader").addClass("show");
    return true;
});

$("form").submit(function (e) {
    $("#ajaxLoader").removeClass("hide");
    $("#ajaxLoader").addClass("show");
    return true;
});
