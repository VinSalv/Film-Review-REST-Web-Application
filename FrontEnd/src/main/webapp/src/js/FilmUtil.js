PLOT_LENGTH = 50;

function addActors(actorList) {
    let actors = "";
    $.each(actorList, function (index, obj) {
        actors += " " + obj.name + " " + obj.surname;
        if (actors.length > PLOT_LENGTH) {
            actors += "... ";
            return false;
        } else {
            actors += ",";
        }
    });
    actors = actors.substring(0, actors.length - 1);
    return '<h6 class="filmText" style="color: white">' + actors + "</h6>";
}
