class ManageFilm {
    constructor(endPoint) {
        this.addFilmEndPoint = endPoint + "/add-film";
        this.editFilmEndPoint = endPoint + "/edit-film";
        this.deleteFilmEndPoint = endPoint + "/delete-film";
        this.getFilmEndPoint = endPoint + "/get-film";
        this.getFilmsHomePageEndPoint = endPoint + "/get-films-home-page";
        this.getFilmsHomePagePerGenreEndPoint =
            endPoint + "/get-films-home-page-per-genre";
        this.getActorsEndPoint = endPoint + "/get-actors";
        this.count = 0;
    }

    fillTable() {
        let controller = this;
        $.getJSON(this.getFilmsHomePageEndPoint, function (data) {
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

    selectElement(id, valueToSelect) {
        let element = document.getElementById(id);
        element.value = valueToSelect;
    }

    renderGUI(data) {
        $("#view .our_2").remove();
        var array = [];
        let controller = this;
        let static_film = $("#film-template").html()
        $.each(data, function (index, obj) {
            let film_view = static_film
            film_view = film_view.replace(/{ID}/gi, obj.id);
            film_view = film_view.replace(/{POSTER}/gi, obj.poster)
            film_view = film_view.replace(/{TITLE}/gi, obj.title)
            let cutPlot
            if (obj.plot.length > PLOT_LENGTH) {
                cutPlot = obj.plot.substr(0, PLOT_LENGTH) + "...";
            } else {
                cutPlot = obj.plot;
            }
            film_view = film_view.replace(/{PLOT}/gi, cutPlot)
            film_view = film_view.replace(/{ACTORS}/gi, addActors(obj.actors))
            let avg = ((Number)(obj.avgScore)).toFixed(2);
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

    insertViewFilm() {
        let genreSelect = document.getElementById("add-genre");
        $("#add-genre option:not(:first)").remove();
        $.each(Genres, function (index, obj) {
            genreSelect.add(new Option(obj, obj));
        });
        this.getActors($("#insert-table-actors"), false, null);
    }

    getActors(modal, editing, actorList) {
        modal.find("tr").remove();
        let controller = this;
        $.getJSON(this.getActorsEndPoint, function (data) {
            controller.addCheckBoxes(data, modal, editing, actorList);
        })
            .done(function () {
            })
            .fail(function () {
            });
    }

    addCheckBoxes(data, modal, editing, actorList) {
        let listID;
        if (editing) listID = actorList.map((a) => a.id);
        $.each(data, function (index, obj) {
            modal.append(
                '<tr class="list-group-item py-1">' +
                "<td>" +
                '<input type="checkbox" class="form-check-input checkbox" name="actors" value="' +
                obj.id +
                '" id="Cb' +
                obj.id +
                '"' +
                (editing && listID.includes(obj.id) ? "checked" : "") +
                ">  " +
                obj.name +
                "  " +
                obj.surname +
                "</td>" +
                "</tr>"
            );
        });
    }

    viewEditFilm(id) {
        let genreSelect = document.getElementById("edit-genre");
        $("#edit-genre option:not(:first)").remove();
        $.each(Genres, function (index, obj) {
            genreSelect.add(new Option(obj, obj));
        });
        $.getJSON(this.getFilmEndPoint, {id: id}, function (obj) {
            $("#edit-id").val(obj.id);
            $("#edit-title").val(obj.title);
            $("#edit-plot").val(obj.plot);
            $("#edit-genre").val(obj.genre);
            controller.getActors($("#edit-table-actors"), true, obj.actorList);
            $("#edit-trailer").val("www.youtube.com/" + obj.trailer);
        }).done(function (obj) {
            $("#edit-modal").modal("show");
        });
    }

    insertFilm() {
        let controller = this;
        //
        $.ajax({
            type: "POST",
            url: controller.addFilmEndPoint,
            data: new FormData($("#insert-form")[0]),
            processData: false,
            contentType: false,
            success: function () {
                controller.renderAlert("Film successfully inserted.", true);
                controller.selectElement("genres", "all");
                controller.fillTable();
            },
            error: function (e) {
                controller.renderAlert("Error while uploading!! Try again.", false);
            },
        });
    }

    editFilm() {
        let controller = this;
        if (validate("#edit-form") === false) {
            controller.renderAlert(
                "Error: The input fields cannot be left empty!! Edit rejected.",
                false
            );
            return;
        }
        $.ajax({
            type: "POST",
            url: controller.editFilmEndPoint,
            data: new FormData($("#edit-form")[0]),
            processData: false,
            contentType: false,
            success: function () {
                controller.renderAlert("Film successfully edited.", true);
                controller.selectElement("genres", "all");
                controller.fillTable();
            },
            error: function (e) {
                controller.renderAlert("Error while editing!! Try again.", false);
            },
        });
    }

    deleteFilm() {
        let controller = this;
        let id = $("#edit-id").val();
        $.get(this.deleteFilmEndPoint, {id: id}, function () {
        })
        window.location.reload(true)
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
