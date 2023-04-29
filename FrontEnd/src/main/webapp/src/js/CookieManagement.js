function setCookie(data, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
    let expires = d.toUTCString();
    document.cookie =
        "data=" + JSON.stringify(data) + ";expires=" + expires + ";path=/";
}

function getCookie() {
    if (document.cookie === "") return null;
    return JSON.parse(document.cookie.split(";")[0].split("=")[1]);
}

function getCookieID() {
    let cookie = getCookie();
    return cookie === null ? null : cookie.id_user;
}

function getCookieRole() {
    let cookie = getCookie();
    return cookie === null ? null : cookie.role;
}

function getCookieUsername() {
    let cookie = getCookie();
    return cookie === null ? null : cookie.username;
}

function getCookieBan() {
    let cookie = getCookie();
    return cookie === null ? null : cookie.ban;
}

function updateCookieUsername(newUsername) {
    let cookie = getCookie();
    cookie.username = newUsername;
    setCookie(cookie);
}

function checkCookie(clientAllowed) {
    if (getCookieID() !== null) {
        if (getCookieBan() !== 1) {
            if (clientAllowed || getCookieRole() !== "client") {
            } else {
                alert("You are not allowed to be on this page!");
                logout();
            }
        } else {
            alert("NOT ALLOWED!! Contact and administrator for instructions");
            logout();
        }
    } else {
        alert("Login or Password wrong. Try again");
        logout();
    }
}

function logout() {
    $(location).attr("href", "../index.html");
    document.cookie = "data=" + null + ";expires=" + 0 + ";path=/";
}
