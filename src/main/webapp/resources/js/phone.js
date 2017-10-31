var phoneFunctionsAndParams = {};

phoneFunctionsAndParams.PHONE_MODAL = document.getElementById('phoneModal');
phoneFunctionsAndParams.COUNTRY_CODE_ID = "countryCode";
phoneFunctionsAndParams.OPERATOR_CODE_ID = "operatorCode";
phoneFunctionsAndParams.NUMBER_ID = "number";
phoneFunctionsAndParams.TYPE_ID = "type";
phoneFunctionsAndParams.PHONE_COMMENT_ID = "phoneComment";
phoneFunctionsAndParams.PHONE_TABLE_BODY_ID = "phone_table_body";

phoneFunctionsAndParams.cancelPhone = function (){
    document.getElementById(phoneFunctionsAndParams.COUNTRY_CODE_ID).value = "";
    document.getElementById(phoneFunctionsAndParams.OPERATOR_CODE_ID).value = "";
    document.getElementById(phoneFunctionsAndParams.NUMBER_ID).value = "";
    document.getElementById(phoneFunctionsAndParams.TYPE_ID).value = "";
    document.getElementById(phoneFunctionsAndParams.PHONE_COMMENT_ID).value = "";

    phoneFunctionsAndParams.PHONE_MODAL.style.display = "none";
};
phoneFunctionsAndParams.showPhoneModal = function(okOnclickFunction, clickedButton){

    if(okOnclickFunction === "editPhone"){
        // get values from attachment tr
        var id = clickedButton.id;
        id = id.substring(2, id.length);
        var phoneNumber = document.getElementById("N-"+id).value;
        var phoneCountryCode = phoneFunctionsAndParams.getCountryCode(phoneNumber);
        var phoneOperatorCode = phoneFunctionsAndParams.getOperatorCode(phoneNumber);
        var phoneType = document.getElementById("T-"+id).value;
        var comment = document.getElementById("C-"+id).value;

        phoneNumber = phoneFunctionsAndParams.getPhoneNumber(phoneNumber);


        document.getElementById(phoneFunctionsAndParams.COUNTRY_CODE_ID).value = phoneCountryCode;
        document.getElementById(phoneFunctionsAndParams.OPERATOR_CODE_ID).value = phoneOperatorCode;
        document.getElementById(phoneFunctionsAndParams.NUMBER_ID).value = phoneNumber;
        document.getElementById(phoneFunctionsAndParams.TYPE_ID).value = phoneType;
        document.getElementById(phoneFunctionsAndParams.PHONE_COMMENT_ID).value = comment;
        isEditModal =  id;
    }

    phoneFunctionsAndParams.PHONE_MODAL.style.display = "block";
};

phoneFunctionsAndParams.getCountryCode =function(fullPhoneNumber){
    var startIndexCountryCode = 0;
    var endIndexCountryCode = 3;
    return fullPhoneNumber.substring(startIndexCountryCode,endIndexCountryCode);
};

phoneFunctionsAndParams.getOperatorCode = function(fullPhoneNumber) {
    var startIndexOperatorCode = 3;
    var endIndexOperatorCode = 5;
    return fullPhoneNumber.substring(startIndexOperatorCode,endIndexOperatorCode);
};

phoneFunctionsAndParams.getPhoneNumber = function(fullPhoneNumber) {
    var startIndexPhoneNumber = 5;
    var endIndexPhoneNumber = fullPhoneNumber.length;
    return fullPhoneNumber.substring(startIndexPhoneNumber, endIndexPhoneNumber);
};

phoneFunctionsAndParams.addPhone = function(){

    var countryCode = document.getElementById(phoneFunctionsAndParams.COUNTRY_CODE_ID).value;
    var operatorCode = document.getElementById(phoneFunctionsAndParams.OPERATOR_CODE_ID).value;
    var phoneNumber = document.getElementById(phoneFunctionsAndParams.NUMBER_ID).value;
    var phoneType = document.getElementById(phoneFunctionsAndParams.TYPE_ID).value;
    var phoneComment = document.getElementById(phoneFunctionsAndParams.PHONE_COMMENT_ID).value;

    if(!countryCode.match(/^([\d]+)$/) || !operatorCode.match(/^([\d]+)$/) || !phoneNumber.match(/^([\d]+)$/)){
        alert("Incorrect data!");
        return false;
    }

    if(isEditModal !== 0){
        var idTr = isEditModal;
        var editPhoneNumber = document.getElementById("N-"+idTr);
        var editPhoneType = document.getElementById("T-"+idTr);
        var editComment = document.getElementById("C-"+idTr);
        var patentTr = editPhoneNumber.parentNode;
        var editIdPhone = document.createElement("input");


        editPhoneNumber.value = countryCode + operatorCode + phoneNumber;
        editPhoneType.value = phoneType;
        editComment.value = phoneComment;
        isEditModal = 0;
        if(idTr > 0) {
            editIdPhone.setAttribute("name", "editIdPhone");
            editIdPhone.setAttribute("value", "" + idTr);
            editIdPhone.setAttribute("class","phone");
            patentTr.appendChild(editIdPhone);
        }
        phoneFunctionsAndParams.cancelPhone();
    } else {
        var tableBody = document.getElementById(phoneFunctionsAndParams.PHONE_TABLE_BODY_ID);
        var tr = phoneFunctionsAndParams.createPhoneTr(
            countryCode,
            operatorCode,
            phoneNumber,
            phoneType,
            phoneComment
        );
        tableBody.appendChild(tr);
        phoneFunctionsAndParams.cancelPhone();
    }
};

var j = -1;
phoneFunctionsAndParams.createPhoneTr = function(countryCode, operatorCode, phoneNumber, phoneType, phoneComment){
    var tr = document.createElement("tr");

    var checkBoxTd = phoneFunctionsAndParams.createPhoneCheckBoxTd();
    var phoneNumberTd = phoneFunctionsAndParams.createPhoneNumberTd(countryCode, operatorCode, phoneNumber);
    var phoneTypeTd = phoneFunctionsAndParams.createPhoneTypeTd(phoneType);
    var commentTd = phoneFunctionsAndParams.createPhoneCommentTd(phoneComment);
    var editButtonTd = phoneFunctionsAndParams.createPhoneEditButtonTd();

    tr.appendChild(checkBoxTd);
    tr.appendChild(phoneNumberTd);
    tr.appendChild(phoneTypeTd);
    tr.appendChild(commentTd);
    tr.appendChild(editButtonTd);
    j--;

    return tr;
};

phoneFunctionsAndParams.createPhoneEditButtonTd = function() {
    var editButtonTd = document.createElement("td");
    var editButton = document.createElement("input");
    editButton.setAttribute("type", "button");
    editButton.setAttribute("id","E-"+j);
    editButton.setAttribute("name", "editPhoneButton");
    editButton.setAttribute("value", "Edit");
    editButton.setAttribute("class", "form-control");
    editButton.onclick = function () {
        phoneFunctionsAndParams.showPhoneModal('editPhone', this);
    };
    editButtonTd.appendChild(editButton);

    return editButtonTd;
};

phoneFunctionsAndParams.createPhoneCheckBoxTd = function(){
    var checkBoxTd = document.createElement("td");
    var checkBoxInput = document.createElement("input");
    checkBoxInput.setAttribute("type", "checkbox");
    checkBoxInput.setAttribute("name", "checkBoxesPhone");
    checkBoxInput.setAttribute("value", "0");
    checkBoxTd.appendChild(checkBoxInput);

    return checkBoxTd;
};

phoneFunctionsAndParams.createPhoneNumberTd = function(countryCode, operatorCode, phoneNumber){
    var phoneNumberTd = document.createElement("td");
    var inputForPhoneNumber = document.createElement("input");
    inputForPhoneNumber.setAttribute("type","text");
    inputForPhoneNumber.setAttribute("name","newPhoneNumber");
    inputForPhoneNumber.setAttribute("id", "N-"+j);
    inputForPhoneNumber.setAttribute("class", "form-control");
    inputForPhoneNumber.value = countryCode + operatorCode + phoneNumber;
    inputForPhoneNumber.readOnly = true;
    phoneNumberTd.appendChild(inputForPhoneNumber);

    return phoneNumberTd;
};

phoneFunctionsAndParams.createPhoneTypeTd = function(phoneType){
    var phoneTypeTd = document.createElement("td");
    var inputForPhoneType = document.createElement("input");
    inputForPhoneType.setAttribute("type","text");
    inputForPhoneType.setAttribute("name","newPhoneType");
    inputForPhoneType.setAttribute("id", "T-"+j);
    inputForPhoneType.setAttribute("class", "form-control");
    inputForPhoneType.value = phoneType;
    inputForPhoneType.readOnly = true;
    phoneTypeTd.appendChild(inputForPhoneType);

    return phoneTypeTd;
};

phoneFunctionsAndParams.createPhoneCommentTd = function(phoneComment){
    var phoneCommentTd = document.createElement("td");
    var inputForPhoneComment = document.createElement("input");
    inputForPhoneComment.setAttribute("type","text");
    inputForPhoneComment.setAttribute("name","newPhoneComment");
    inputForPhoneComment.setAttribute("id", "C-"+j);
    inputForPhoneComment.setAttribute("class", "form-control");
    inputForPhoneComment.value = phoneComment;
    inputForPhoneComment.readOnly = true;
    phoneCommentTd.appendChild(inputForPhoneComment);

    return phoneCommentTd;
};

phoneFunctionsAndParams.deleteCheckedPhones = function(checkBoxName){
    var checkBoxes = document.getElementsByName(checkBoxName);
    var l = checkBoxes.length;
    var f = false;
    for(i=0; i < checkBoxes.length; i++){
        if(checkBoxes[i].checked){
            f = true;
            break;
        }
    }
    if(!f){
        alert("No select contacts!");
        return;
    }
    var count = 0;
    var table = document.getElementById("phone_table_body");
    var mainCheckbox = document.getElementById("main-check-box-phone");
    for(var i = 0; i < checkBoxes.length;){
        if(checkBoxes[i].checked){
            phoneFunctionsAndParams.createInputForDeletedPhone(checkBoxes[i].value);
            count++;
            table.deleteRow(i);
        } else i++;
    }
    if(count === l) {
        mainCheckbox.checked = false;
    }
};

phoneFunctionsAndParams.setCheckboxesChecked = function(mainCheckBoxId, checkBoxName){
    var mainCheckbox = document.getElementById(mainCheckBoxId).checked;
    var checkBoxes = document.getElementsByName(checkBoxName);
    for(var i = 0; i < checkBoxes.length; i++){
        checkBoxes[i].checked = mainCheckbox;
    }
};

phoneFunctionsAndParams.createInputForDeletedPhone = function(id) {
    var inputForDeletedPhone = document.createElement("input");
    var form = document.getElementById("editForm");
    inputForDeletedPhone.setAttribute("type", "text");
    inputForDeletedPhone.setAttribute("class", "phone");
    inputForDeletedPhone.setAttribute("name", "deletedPhoneInputName");
    inputForDeletedPhone.setAttribute("value", id);
    form.appendChild(inputForDeletedPhone);
};

phoneFunctionsAndParams.closePhoneModal = function(){
    phoneFunctionsAndParams.PHONE_MODAL.style.display = "none";
};