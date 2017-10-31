function validate(form)
{
    var elems = form.elements;
    var flag = false;
    for (var i = 0; i < elems.length-1; i++) {
        if (elems[i].value !== '') {
            flag = true;
            break;
        }
    }
    if(flag === false) {
        alert ('At least one field must be filled!');
        return false;
    }
    return true;
}