class FeedBackFilm {
    constructor(endPoint, id) {
        this.addFeedbackEndPoint = endPoint + "/add-feedback";
        this.editFeedbackEndpoint = endPoint + "/edit-feedback";
        this.deleteFeedbackEndpoint = endPoint + "/delete-feedback";
        this.getFilmReviewPageEndPoint = endPoint + "/get-film-review-page";
        this.id = id;
        this.countFeedback = 0;
    }

    showFilm() {
        let controller = this;
        $.getJSON(this.getFilmReviewPageEndPoint, {id: this.id}, function (obj) {
            $("#title").text(obj.title);
            $("#genre").text(obj.genre);
            $("#plot").text(obj.plot);
            $("#filmPoster").attr("src", "data:image/jpeg;base64," + obj.poster);
            $("#trailerFilm").attr(
                "src",
                "https://www.youtube.com/embed/" + obj.trailer
            );
            console.log(obj.trailer)
            $("#cast").text(controller.getCast(obj.actors));
            $("#average").text(((Number)(obj.avgScore)).toFixed(2));
            let mine = false;
            $.each(obj.feedbacks, function (index, obj) {
                controller.countFeedback += 1;
                if (obj.username === getCookieUsername()) {
                    mine = true;
                    let buttons = $("#buttons-template").html();
                    buttons = buttons.replace(/{SCORE}/gi, obj.score);
                    $("#buttons").append(buttons);
                    $("#insert-button").prop("hidden", true);
                    controller.constructFeedbackView(obj, true);
                } else controller.constructFeedbackView(obj, false);
            });
            $(".rating-upper").css({
                width: obj.avgScore * 20 + "%",
            });
            $("#number_feedback").text("(" + controller.countFeedback + ")");
            $("#all-reviews").text(controller.countFeedback);
        });
    }

    constructFeedbackView(obj, mine) {
        let templ = $("#feedback-template").html();
        templ = templ.replace(/{NAME}/gi, obj.username);
        templ = templ.replace(/{COMMENT}/gi, obj.comment);
        templ = templ.replace(/{DATE}/gi, obj.date);
        switch (obj.score) {
            case 5:
                templ = templ.replace(/{SCORE5}/gi, "green-dot");
            case 4:
                templ = templ.replace(/{SCORE4}/gi, "green-dot");
            case 3:
                templ = templ.replace(/{SCORE3}/gi, "green-dot");
            case 2:
                templ = templ.replace(/{SCORE2}/gi, "green-dot");
            case 1:
                templ = templ.replace(/{SCORE1}/gi, "green-dot");
        }
        if (mine) {
            $("#my-feedback-area").append(templ);
            $("#my-area").prop("hidden", false);
        } else {
            $("#feedbackList").append(templ);
        }
    }

    getCast(actors) {
        var allActors = [];
        $.each(actors, function (index, actor) {
            allActors.push(actor.name + " " + actor.surname);
        });
        return allActors.join(", ");
    }

    viewEditFeedback(score) {
        $("#edit-idUser").val(getCookieID());
        $("#edit-idFilm").val(controller.id);
        $("#edit-feedback").val($("#my-area p.comment").text());
        $("#edit-star" + score).prop("checked", "true");
    }

    insertFeedback() {
        let controller = this;
        $("#add-idFilm").val(this.id);
        $("#add-idUser").val(getCookieID());
        let data = $("#insert-form").serialize();
        $.post(this.addFeedbackEndPoint, data, function () {
        })
            .done(function () {
                controller.renderAlert("Feedback successfully inserted.", true);
                $("#add-feedback").val("");
                window.location.reload();
            })
            .fail(function () {
                controller.renderAlert("Error while inserting!! Try again.", false);
            });
    }

    editFeedback() {
        let controller = this;
        //
        let data = $("#edit-form").serialize();
        $.post(this.editFeedbackEndpoint, data, function () {
        })
            .done(function () {
                controller.renderAlert("Feedback successfully edited.", true);
                window.location.reload(true);
            })
            .fail(function () {
                controller.renderAlert("Error while editing!! Try again.", false);
            });
    }

    deleteFeedback() {
        let controller = this;
        $.get(
            this.deleteFeedbackEndpoint,
            {id_film: controller.id, id_user: getCookieID()},
            function () {
            }
        );
        window.location.reload(true);
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
