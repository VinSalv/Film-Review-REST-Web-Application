class ReviewFilm {
    constructor(endPoint) {
        this.getFilmsHomePageEndPoint = endPoint + "/get-films-home-page";
        this.getFilmsHomePagePerGenreEndPoint = endPoint + "/get-films-home-page-per-genre";
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getFilmsHomePageEndPoint, function (data) {
            controller.renderGUI(data);
        })
            .done(function () {
            })
            .fail(function () {
                controller.renderAlert(
                    "Error while charging data!! Retry in a few second.",
                    false
                );
            });
    }

    fillTablePerGenre(genre) {
        let controller = this;
        $.getJSON(
            this.getFilmsHomePagePerGenreEndPoint + "?genre=" + genre,
            function (data) {
                controller.renderGUI(data);
            }
        )
            .done(function () {
            })
            .fail(function () {
                controller.renderAlert(
                    "Error while charging data!! Retry in a few second.",
                    false
                );
            });
    }

    getGenre() {
        if (document.getElementById("genres").value === "all") {
            controller.fillTable();
        } else {
            controller.fillTablePerGenre(document.getElementById("genres").value);
        }
    }

    renderGUI(data) {
        $("#view .our_2").remove();
        var array = [];
        let static_film = $("#film-template").html()
        $.each(data, function (index, obj) {
            let film_view = static_film
            film_view = film_view.replace(/{ID}/gi, obj.id);
            film_view = film_view.replace(/{POSTER}/gi, obj.poster)
            film_view = film_view.replace(/{TITLE}/gi, obj.title)
            film_view = film_view.replace(/{ACTORS}/gi, addActors(obj.actors))
            let avg = ((Number)(obj.avgScore)).toFixed(2);
            let cutPlot
            if (obj.plot.length > PLOT_LENGTH) {
                cutPlot = obj.plot.substr(0, PLOT_LENGTH) + "...";
            } else {
                cutPlot = obj.plot;
            }
            film_view = film_view.replace(/{PLOT}/gi, cutPlot)
            if (avg == 5) {
                film_view = film_view.replace(/{SCORE5}/gi, 'checked')
            }
            if (avg >= 4) {
                film_view = film_view.replace(/{SCORE4}/gi, 'checked')
            }
            if (avg >= 3) {
                film_view = film_view.replace(/{SCORE3}/gi, 'checked')
            }
            if (avg >= 2) {
                film_view = film_view.replace(/{SCORE2}/gi, 'checked')
            }
            if (avg >= 1) {
                film_view = film_view.replace(/{SCORE1}/gi, 'checked')
            } else {
                film_view = film_view.replace(/{SCORE}/gi, "Be the first to comment");
            }
            film_view = film_view.replace(/{SCORE}/gi, (avg == 0 ? "Be the first to comment" : "Average Score: " + avg));
            if (controller.count === 0) array.push('<div class="col-sm-12 row">');
            array.push(film_view);
            if (controller.count === 2) array.push("</div>");
            controller.count = (controller.count + 1) % 3;
        });
        $("#view").append(array.join(""));
        controller.count = 0;
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
