var photoFunctionsAndParams = {};

var photo;
var isEditModal = 0;

photoFunctionsAndParams.PHOTO_MODAL = document.getElementById('photoModal');
photoFunctionsAndParams.PHOTO_INPUT_ID = "photo";

photoFunctionsAndParams.showPhotoModal = function(){
    photoFunctionsAndParams.PHOTO_MODAL.style.display = "block";
};

var flag = false;

photoFunctionsAndParams.closePhotoModal = function() {
    photoFunctionsAndParams.PHOTO_MODAL.style.display = "none";
};

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
            photoFunctionsAndParams.closePhotoModal();
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
    inputPhoto.setAttribute("accept", "image/png, image/jpeg");

    form.appendChild(inputPhoto);
    inputPhoto.click();
};
