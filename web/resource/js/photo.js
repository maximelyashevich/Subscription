var photoFunctionsAndParams = {};

var photo;

photoFunctionsAndParams.PHOTO_INPUT_ID = "photo";


var flag = false;


photoFunctionsAndParams.addPhoto = function() {
    var phInput = document.getElementById("photoIdInput");

    if(flag){
        phInput.click();
    } else
        photoFunctionsAndParams.createHiddenInputForPhoto();
    var ph = document.getElementById("photoIdInput");
    ph.onchange = function (evt) {
        flag = true;
        var tgt = evt.target || window.event.srcElement,
            files = tgt.files;
        if (FileReader && files && files.length) {
            var fr = new FileReader();
            fr.onload = function () {
                document.getElementById("contactImage").src = fr.result;
            };
            fr.readAsDataURL(files[0]);
        }
    }
};
photoFunctionsAndParams.createHiddenInputForPhoto = function() {
    var inputPhoto = document.createElement("input");
    var form = document.getElementById("editForm");
    inputPhoto.setAttribute("type", "file");
    inputPhoto.setAttribute("id", "photoIdInput");
    inputPhoto.setAttribute("class", "photo");
    inputPhoto.setAttribute("name", "photoInputName");
    inputPhoto.setAttribute("accept", "image/png, image/jpeg, image/jpg");

    form.appendChild(inputPhoto);
    inputPhoto.click();
};
