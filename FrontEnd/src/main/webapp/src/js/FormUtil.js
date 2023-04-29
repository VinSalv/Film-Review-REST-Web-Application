function validate(idForm) {
    return true;
    $(idForm).addClass("needs-validation");
    let flag = true;
    let forms = document.getElementsByClassName("needs-validation");
    Array.prototype.filter.call(
        forms,
        function (form) {
            if (form.checkValidity() === false) {
                flag = false;
            }
        },
        false
    );
    $(idForm).removeClass("needs-validation");
    return flag;
}

const Genres = {
    ACTION: "Action",
    ADVENTURE: "Adventure",
    COMEDY: "Comedy",
    HORROR: "Horror",
    ROMANCE: "Romance",
};
Object.freeze(Genres);
