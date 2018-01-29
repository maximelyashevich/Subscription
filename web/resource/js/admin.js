function viewDiv(idMain, idClose) {
    var mainForm = document.getElementsByClassName("form")[0];
    var signupForm = document.getElementById("signupForm");
    var loginForm = document.getElementById("loginForm");
    var first_name = document.getElementById("first_name");
    var last_name = document.getElementById("last_name");
    var login = document.getElementById("loginID");
    var dob = document.getElementById("dob");
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var login2 = document.getElementById("loginID2");
    var password2 = document.getElementById("password2");
    var componentEl = document.getElementById(idMain);
    var componentClose = document.getElementById(idClose);
    if (idMain === 'signup') {
        first_name.setAttribute("required", "");
        last_name.setAttribute("required", "");
        login.setAttribute("required", "");
        dob.setAttribute("required", "");
        email.setAttribute("required", "");
        password.setAttribute("required", "");
    } else {
        login2.setAttribute("required", "");
        password2.setAttribute("required", "");
    }
    signupForm.reset();
    loginForm.reset();
    mainForm.style.display = "block";
    componentClose.style.display = "none";
    componentEl.style.display = "block";
}

function closeDiv(idMain) {
    var mainForm = document.getElementsByClassName("form")[0];
    var first_name = document.getElementById("first_name");
    var last_name = document.getElementById("last_name");
    var login = document.getElementById("loginID");
    var email = document.getElementById("email");
    var dob = document.getElementById("dob");
    var password = document.getElementById("password");
    var login2 = document.getElementById("loginID2");
    var password2 = document.getElementById("password2");
    var componentEl = document.getElementById(idMain);
    first_name.removeAttribute("required");
    last_name.removeAttribute("required");
    login.removeAttribute("required");
    email.removeAttribute("required");
    dob.removeAttribute("required");
    password.removeAttribute("required");
    login2.removeAttribute("required");
    password2.removeAttribute("required");
    mainForm.style.display = "none";
    componentEl.style.display = "none";
}
function showDivBlock(id, idPagination, id1, id2) {
    var block = document.getElementById(id);
    var pagination = document.getElementById(idPagination);
    clearDivBlock(id1, id2);
    block.style.display="block";
    pagination.style.display="block";
}
function clearDivBlock(id1, id2) {
    document.getElementById(id1).style.display="none";
    document.getElementById(id2).style.display="none";
}