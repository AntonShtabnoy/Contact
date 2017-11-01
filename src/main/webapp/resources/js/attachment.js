var attachmentsFunctionsAndParams = {};

var isEditModalAttachment = 0;
attachmentsFunctionsAndParams.ATTACHMENT_MODAL = document.getElementById('attachmentModal');
attachmentsFunctionsAndParams.ATTACHMENT_NAME_ID = "name-attachment";
attachmentsFunctionsAndParams.ATTACHMENT_COMMENT_ID = "comment-attachment";
attachmentsFunctionsAndParams.ATTACHMENT_TABLE_BODY_ID = "attachment_table_body";

attachmentsFunctionsAndParams.showAttachmentModal = function(okOnclickFunction, clickedButton){
    document.getElementById("attachment-button").disabled = false;
    if(okOnclickFunction === "editAttachment"){
        document.getElementById("attachment-button").disabled = true;
        var id = clickedButton.id;
        id = id.substring(2, id.length);
        var attachmentName = document.getElementById("n-"+id).value;
        var comment = document.getElementById("c-"+id).value;


        document.getElementById(attachmentsFunctionsAndParams.ATTACHMENT_NAME_ID).value = attachmentName;
        document.getElementById(attachmentsFunctionsAndParams.ATTACHMENT_COMMENT_ID).value = comment;
        isEditModalAttachment = id;
    }
    attachmentsFunctionsAndParams.ATTACHMENT_MODAL.style.display = "block";

};
attachmentsFunctionsAndParams.addAttachment = function() {
    var att = document.getElementById("attachmentIdInput"+indexAttachmentIdInput);
    indexAttachmentIdInput--;

    var attachmentName = document.getElementById(attachmentsFunctionsAndParams.ATTACHMENT_NAME_ID).value;
    var attachmentComment = document.getElementById(attachmentsFunctionsAndParams.ATTACHMENT_COMMENT_ID).value;

    if(isEditModalAttachment !== 0){
        var idTr = isEditModalAttachment;
        var editAttachmentName = document.getElementById("n-"+idTr);
        var editComment = document.getElementById("c-"+idTr);
        var patentTr = editAttachmentName.parentNode;
        var editIdAttachment = document.createElement("input");

        editAttachmentName.value = attachmentName ;
        editComment.value = attachmentComment;
        isEditModalAttachment = 0;
        if(idTr > 0) {
            editIdAttachment.setAttribute("name", "editIdAttachment");
            editIdAttachment.setAttribute("value", "" + idTr);
            editIdAttachment.setAttribute("class","attachment hid");
            patentTr.appendChild(editIdAttachment);
        }
        attachmentsFunctionsAndParams.cancelAttachment();
    } else {
        var tableBody = document.getElementById(attachmentsFunctionsAndParams.ATTACHMENT_TABLE_BODY_ID);
        var name = document.getElementById("name-attachment").value;
        //var name = attachmentsFunctionsAndParams.getNameFile(document.getElementById("name-attachment").value);
        var date = attachmentsFunctionsAndParams.createDate();
        var comment = document.getElementById("comment-attachment").value;
        var tr = attachmentsFunctionsAndParams.createAttachmentTr(name, date, comment);
        tableBody.appendChild(tr);

        attachmentsFunctionsAndParams.cancelAttachment();
    }
};

attachmentsFunctionsAndParams.getNameFile = function (file) {
    var w = file.split ('.');  w.pop ();
    return w.join ('.');
};

attachmentsFunctionsAndParams.getNameFileFromPath = function(path){
    var lastIndex = path.lastIndexOf("\\");
    if(lastIndex !== -1){
        return path.substring(lastIndex+1, path.length);
    }
    return path;
};
attachmentsFunctionsAndParams.cancelAttachment = function(){
    document.getElementById("comment-attachment").value = "";
    document.getElementById("name-attachment").value = "";
    attachmentsFunctionsAndParams.ATTACHMENT_MODAL.style.display = "none";
};

var indexAttachmentIdInput = 0;
attachmentsFunctionsAndParams.uploadAttachment = function(){
    var fileName = document.getElementById("name-attachment");
    attachmentsFunctionsAndParams.createHiddenInputForAttachment();
    var att = document.getElementById("attachmentIdInput"+indexAttachmentIdInput);
    att.onchange = function (evt) {
        fileName.value = attachmentsFunctionsAndParams.getNameFile(attachmentsFunctionsAndParams.getNameFileFromPath(att.value));
    }
};

attachmentsFunctionsAndParams.createDate = function(){
    var date = new Date();
    return date.getFullYear() + '/' + ('0' + (date.getMonth() + 1)).slice(-2) + '/' + ('0' + date.getDate()).slice(-2);
};

attachmentsFunctionsAndParams.createHiddenInputForAttachment = function(){
    var inputAttachment = document.createElement("input");
    var form = document.getElementById("editForm");
    inputAttachment.setAttribute("type", "file");
    inputAttachment.setAttribute("id", "attachmentIdInput"+indexAttachmentIdInput);
    inputAttachment.setAttribute("class", "attachment");
    inputAttachment.setAttribute("name", "attachmentInputName");

    form.appendChild(inputAttachment);
    inputAttachment.click();
};

var j = -1;
attachmentsFunctionsAndParams.createAttachmentTr = function(attachmentName, attachmentDate, attachmentComment){
    var tr = document.createElement("tr");
    var checkBoxTd = attachmentsFunctionsAndParams.createAttachmentCheckBoxTd();
    var attachmentNameTd = attachmentsFunctionsAndParams.createAttachmentNameTd(attachmentName);
    var attachmentDateTd = attachmentsFunctionsAndParams.createAttachmentDateTd(attachmentDate);
    var commentTd = attachmentsFunctionsAndParams.createAttachmentCommentTd(attachmentComment);
    var editButtonTd = attachmentsFunctionsAndParams.createAttachmentEditButtonTd();

    tr.appendChild(checkBoxTd);
    tr.appendChild(attachmentNameTd);
    tr.appendChild(attachmentDateTd);
    tr.appendChild(commentTd);
    tr.appendChild(editButtonTd);
    j--;

    return tr;
};

attachmentsFunctionsAndParams.createAttachmentCheckBoxTd = function(){
    var checkBoxTd = document.createElement("td");
    var checkBoxInput = document.createElement("input");
    checkBoxInput.setAttribute("type", "checkbox");
    checkBoxInput.setAttribute("name", "checkBoxesAttachment");
    checkBoxInput.setAttribute("value", "0");
    checkBoxTd.appendChild(checkBoxInput);

    return checkBoxTd;
};

attachmentsFunctionsAndParams.createAttachmentNameTd = function(attachmentName){
    var attachmentNameTd = document.createElement("td");
    var inputForAttachmentName = document.createElement("input");
    inputForAttachmentName.setAttribute("type","text");
    inputForAttachmentName.setAttribute("name","newAttachmentName");
    inputForAttachmentName.setAttribute("id", "n-"+j);
    inputForAttachmentName.setAttribute("class", "form-control");
    inputForAttachmentName.value = attachmentName;
    inputForAttachmentName.readOnly = true;
    attachmentNameTd.appendChild(inputForAttachmentName);

    return attachmentNameTd;
};

attachmentsFunctionsAndParams.createAttachmentDateTd = function(attachmentDate){
    var attachmentDateTd = document.createElement("td");
    var inputForAttachmentDate = document.createElement("input");
    inputForAttachmentDate.setAttribute("type","text");
    inputForAttachmentDate.setAttribute("name","newAttachmentDate");
    inputForAttachmentDate.setAttribute("id", "d-"+j);
    inputForAttachmentDate.setAttribute("class", "form-control");
    inputForAttachmentDate.value = attachmentDate;
    inputForAttachmentDate.readOnly = true;
    attachmentDateTd.appendChild(inputForAttachmentDate);

    return attachmentDateTd;
};

attachmentsFunctionsAndParams.createAttachmentCommentTd = function(attachmentComment){
    var attachmentCommentTd = document.createElement("td");
    var inputForAttachmentComment = document.createElement("input");
    inputForAttachmentComment.setAttribute("type","text");
    inputForAttachmentComment.setAttribute("name","newAttachmentComment");
    inputForAttachmentComment.setAttribute("id", "c-"+j);
    inputForAttachmentComment.setAttribute("class", "form-control");
    inputForAttachmentComment.value = attachmentComment;
    inputForAttachmentComment.readOnly = true;
    attachmentCommentTd.appendChild(inputForAttachmentComment);

    return attachmentCommentTd;
};

attachmentsFunctionsAndParams.deleteCheckedAttachments = function(checkBoxName){
    var checkBoxes = document.getElementsByName(checkBoxName);
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
    var table = document.getElementById("attachment_table_body");
    var mainCheckbox = document.getElementById("main-check-box-attachment");
    var l = checkBoxes.length;
    var count = 0;
    for(var i = 0; i < checkBoxes.length;){
        if(checkBoxes[i].checked){
            attachmentsFunctionsAndParams.createInputForDeletedAttachment(checkBoxes[i].value);
            count++;
            table.deleteRow(i);

        } else i++;
    }
    if(count === l) {
        mainCheckbox.checked = false;
    }
};

attachmentsFunctionsAndParams.createInputForDeletedAttachment = function(id) {
    var inputForDeletedAttachment = document.createElement("input");
    var form = document.getElementById("editForm");
    inputForDeletedAttachment.setAttribute("type", "text");
    inputForDeletedAttachment.setAttribute("class", "attachment");
    inputForDeletedAttachment.setAttribute("name", "deletedAttachmentInputName");
    inputForDeletedAttachment.setAttribute("value", id);
    form.appendChild(inputForDeletedAttachment);
};

attachmentsFunctionsAndParams.createAttachmentEditButtonTd = function() {
    var editButtonTd = document.createElement("td");
    var editButton = document.createElement("input");
    editButton.setAttribute("type", "button");
    editButton.setAttribute("id","e-"+indexAttachmentIdInput);
    editButton.setAttribute("name", "editAttachmentButton");
    editButton.setAttribute("value", "Edit");
    editButton.setAttribute("class", "form-control");
    editButton.onclick = function () {
        attachmentsFunctionsAndParams.showAttachmentModal('editAttachment', this);
    };
    editButtonTd.appendChild(editButton);

    return editButtonTd;
};

