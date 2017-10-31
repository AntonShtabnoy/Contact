const birthdayText = "Happy Birthday, dear {name_contact, surname_contact}!\n" +
    "Amazing life will be your way,\n" +
    "Forever, not only today.\n" +
    "And let all your troubles disappear,\n" +
    "And all your friends be always near!"+
    "{sender_name, sender_surname} \n"+"{sender_phone} | {sender_email}.";

function selectTemplateName(templateName) {
    var textArea = document.getElementById("textarea");
    if(templateName === "Birthday"){
       textArea.value = birthdayText;
       textArea.readOnly = true;
    } else {
        textArea.value = "";
        textArea.readOnly = false;
    }

}

function checkBirthdayGroupSending() {
    var radioBirthday = document.getElementById("groupRadioId");
    var templateBirthday = document.getElementById("sel1");

    if(radioBirthday.checked && templateBirthday.value === "Birthday"){
        alert("You can send birthday template only as single messages!");
        return false;
    }
    return true;
}