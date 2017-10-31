<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="Fri, 01 Jan 1990 00:00:00 GMT"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/home.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/edit_contact_page.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
    <title>Contact's Profile</title>
    <%@ include file="header.jsp" %>
</head>
<body>

<div class="div1">Contact's Profile</div>

<div class="container">
    <form method="post" id="editForm" enctype="multipart/form-data"
          action="/main/save?contact=<c:out value="${contactProfile.id}"/>">
        <div style="height: 20%; width: 20%">
            <img src="/image?imageId=<c:out value="${contactProfile.id}"/>&im=<c:out value="${contactProfile.image}"/>"
                 class="pull-left img-rounded" width="304"
                 height="436" id="contactImage" onclick="photoFunctionsAndParams.showPhotoModal()">
        </div>
        <div class="row">
            <div class="col-md-offset-1 col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['name'] == null}">
                        <label for="name" class="letter">Name<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['name'] != null}">
                            <label for="name">Name <c:out value="${mistakeMap['name']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['name'] != null}">
                            <input type="text" value="${paramMap['name']}" name="name" id="name"
                                   class="form-control" autocomplete="off"
                                   pattern="<c:out value="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>" required
                            />
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.name}"/>" class="form-control"
                                   id="name"
                                   name="name" pattern="<c:out value="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>"
                                   autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['surname'] == null}">
                        <label for="name" class="letter">Surname<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['surname'] != null}">
                            <label for="surname">Surname <c:out value="${mistakeMap['surname']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['surname'] != null}">
                            <input type="text" value="${paramMap['surname']}" name="surname" id="surname"
                                   class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)" required/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.surname}"/>" name="surname"
                                   id="surname"
                                   class="form-control" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   autocomplete="off" required/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['date'] == null}">
                        <label for="date" class="letter">Date of birth<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['date'] != null}">
                            <label for="date">Date of birth <c:out value="${mistakeMap['date']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['date'] != null}">
                            <input type="date" value="${paramMap['date']}" name="date" id="date" class="form-control"
                                   autocomplete="off" required/>
                        </c:when>
                        <c:otherwise>
                            <input type="date" value="<c:out value="${contactProfile.date}"/>" name="date" id="date"
                                   class="form-control"
                                   autocomplete="off" required/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['email'] == null}">
                        <label for="email" class="letter">Email<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['email'] != null}">
                            <label for="webSite">Email <c:out value="${mistakeMap['email']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['email'] != null}">
                            <input type="text" value="${paramMap['email']}" name="email" id="email" class="form-control"
                                   autocomplete="off" pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$"
                                   required/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.email}"/>" name="email" id="email"
                                   class="form-control"
                                   autocomplete="off" required
                                   pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-2">
                <div class="form-group">
                    <label for="family_status">Family Status<span class="text-red">*</span></label>
                    <c:if test="${contactProfile.family_status == 'Free'}">
                        <select name="family_status" class="form-control" id="family_status">
                            <option selected value="Free">Free</option>
                            <option value="Married">Married</option>
                        </select>
                    </c:if>
                    <c:if test="${contactProfile.family_status == 'Married'}">
                        <select name="family_status" class="form-control" id="family_status">
                            <option value="Free">Free</option>
                            <option selected value="Married">Married</option>
                        </select>
                    </c:if>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label for="nationality">Nationality<span class="text-red">*</span></label>
                    <select name="nationality" id="nationality" class="form-control">
                        <c:forEach items="${nationalities}" var="nation">
                            <c:set var="option" value="${nation.key}"/>
                            <c:set var="nat" value="${contactProfile.nationality}"/>
                            <c:if test="${option==nat}">
                                <option selected>${nation.value}</option>
                            </c:if>
                            <c:if test="${option!=nat}">
                                <option>${nation.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <label for="sex">Sex<span class="text-red">*</span></label>
                    <c:if test="${contactProfile.sex == 'M'}">
                        <select name="sex" class="form-control" id="sex">
                            <option selected value="M">Male</option>
                            <option value="F">Female</option>
                        </select>
                    </c:if>
                    <c:if test="${contactProfile.sex == 'F'}">
                        <select name="sex" class="form-control" id="sex">
                            <option value="M">Male</option>
                            <option selected value="F">Female</option>
                        </select>
                    </c:if>
                </div>
            </div>
            <div class="col-md-offset-1 col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['webSite'] == null}">
                        <label for="webSite">Web site</label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['webSite'] != null}">
                            <label for="webSite">Web site <c:out value="${mistakeMap['webSite']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['webSite'] != null}">
                            <input type="text" value="${paramMap['webSite']}" name="webSite" id="webSite"
                                   class="form-control"
                                   autocomplete="off"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.webSite}"/>" name="webSite"
                                   id="webSite"
                                   class="form-control" autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['work'] == null}">
                        <label for="work" class="letter">Work<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['work'] != null}">
                            <label for="work">Work <c:out value="${mistakeMap['work']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['work'] != null}">
                            <input type="text" value="${paramMap['work']}" name="work" id="work"
                                   class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.workPlace}"/>" name="work"
                                   id="work"
                                   class="form-control" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3 mar">
                <div class="form-group">
                    <label for="country">Country<span class="text-red">*</span></label>
                    <select name="country" id="country" class="form-control">
                        <c:forEach items="${countries}" var="c">
                            <<c:set var="option" value="${c.key}"/>
                            <c:set var="countr" value="${contactProfile.country}"/>
                            <c:if test="${option==countr}">
                                <option selected>${c.value}</option>
                            </c:if>
                            <c:if test="${option!=countr}">
                                <option>${c.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-3 mar">
                <div class="form-group">
                    <c:if test="${mistakeMap['city'] == null}">
                        <label for="city" class="letter">City<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['city'] != null}">
                            <label for="city">City <c:out value="${mistakeMap['city']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['city'] != null}">
                            <input type="text" value="${paramMap['city']}" name="city" id="city" class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   required/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.city}"/>" name="city" id="city"
                                   required class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-2 mar">
                <div class="form-group">
                    <c:if test="${mistakeMap['street'] == null}">
                        <label for="street" class="letter">Street<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['street'] != null}">
                            <label for="street">Street<span class="text-red">*</span> <c:out
                                    value="${mistakeMap['street']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['street'] != null}">
                            <input type="text" value="${paramMap['street']}" name="street" id="street"
                                   class="form-control"
                                   autocomplete="off" required pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.street}"/>" name="street"
                                   id="street" required
                                   class="form-control" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <c:if test="${mistakeMap['home'] == null}">
                        <label for="home" class="letter">Home and flat<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['home'] != null}">
                            <label for="home">Home and flat <c:out value="${mistakeMap['home']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['home'] != null}">
                            <input type="text" value="${paramMap['home']}" name="home" id="home" class="form-control"
                                   autocomplete="off" pattern="(^[0-9][-_а-яА-Яa-zA-Z0-9 ]{1,15}$)"
                                   required/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.home}"/>" name="home" id="home"
                                   required class="form-control"
                                   autocomplete="off" pattern="(^[0-9][-_а-яА-Яa-zA-Z0-9 ]{1,15}$)"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <c:if test="${mistakeMap['index'] == null}">
                        <label for="index" class="letter">Index</label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['index'] != null}">
                            <label for="index">Index <c:out value="${mistakeMap['index']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['index'] != null}">
                            <input type="text" value="${paramMap['index']}" name="index" id="index" class="form-control"
                                   autocomplete="off" pattern="^[0-9]{1,15}$"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" value="<c:out value="${contactProfile.index}"/>" name="index" id="index"
                                   class="form-control"
                                   autocomplete="off" pattern="^[0-9]{1,15}$"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="row but">
            <div class="col-md-3">
                <div class="form-group">
                    <input type="button" class="form-control" value="Add phone"
                           onclick="phoneFunctionsAndParams.showPhoneModal(null,this)">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <input type="button" value="Delete phones" class="form-control"
                           onclick="phoneFunctionsAndParams.deleteCheckedPhones('checkBoxesPhone')">
                </div>
            </div>
        </div>
        <div class="text-red">
            <c:if test="${mistakeMap['phoneNumber'] != null}">
                <label>Phone number<c:out value="${mistakeMap['phoneNumber']}"/></label>
            </c:if>
        </div>
        <table class="table table-condensed">
            <thead>
            <tr>
                <th><input type="checkbox" id="main-check-box-phone"
                           onclick="phoneFunctionsAndParams.setCheckboxesChecked('main-check-box-phone','checkBoxesPhone')">
                </th>
                <th>Phone number:</th>
                <th>Type of phone number:</th>
                <th>Comments:</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="phone_table_body">
            <c:forEach items="${phones}" var="phone">
                <tr class="phoneItem">
                    <td><input type="checkbox" name="checkBoxesPhone" value="${phone.id}"></td>
                    <td><input type="text" readonly="readonly" class="form-control" id="N-${phone.id}"
                               name="phoneNumber" value="<c:out value="${phone.number}"/>"></td>
                    <td><input type="text" readonly="readonly" class="form-control" id="T-${phone.id}" name="phoneType"
                               value="<c:out value="${phone.type}"/>"></td>
                    <td><input type="text" readonly="readonly" class="form-control" id="C-${phone.id}"
                               name="phoneComment" value="<c:out value="${phone.comment}"/>"></td>
                    <td><input type="button" value="Edit" class="form-control" id="E-${phone.id}"
                               onclick="phoneFunctionsAndParams.showPhoneModal('editPhone',this)"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <input type="button" value="Add attachments" class="form-control"
                           onclick="attachmentsFunctionsAndParams.showAttachmentModal()">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <input type="button" value="Delete attachments" class="form-control"
                           onclick="attachmentsFunctionsAndParams.deleteCheckedAttachments('checkBoxesAttachment')">
                </div>
            </div>
        </div>

        <table class="table table-condensed">
            <thead>
            <tr>
                <th><input type="checkbox" id="main-check-box-attachment"
                           onclick="phoneFunctionsAndParams.setCheckboxesChecked('main-check-box-attachment','checkBoxesAttachment')">
                </th>
                <th>Attachment name:</th>
                <th>Date of upload:</th>
                <th>Comments:</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody id="attachment_table_body">
            <c:forEach items="${attachments}" var="attachment">
                <tr class="attachmentItem">
                    <td><input type="checkbox" name="checkBoxesAttachment" value="${attachment.id}"></td>
                    <td><input type="text" class="form-control" readonly="readonly" id="n-${attachment.id}"
                               name="attachmentName" value="<c:out value="${attachment.name}"/>"></td>
                    <td><input type="text" class="form-control" readonly="readonly" id="d-${attachment.id}"
                               name="attachmentDate" value="<c:out value="${attachment.date}"/>"></td>
                    <td><input type="text" class="form-control" readonly="readonly" id="c-${attachment.id}"
                               name="attachmentComment" value="<c:out value="${attachment.comment}"/>"></td>
                    <td><input type="button" class="form-control" value="Edit" id="e-${attachment.id}"
                               onclick="attachmentsFunctionsAndParams.showAttachmentModal('editAttachment',this)"></td>
                    <td>
                        <a href="/main/attachment?id=${attachment.contactId}&name=${attachment.contactId}-${attachment.id}attachment${attachment.type}"
                           download="${attachment.name}${attachment.type}"> Download </a>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row">
            <div class="col-md-offset-3 col-md-3">
                <div class="form-group">
                    <input type="submit" name="button" value="Save" class="form-control"/>
                </div>
            </div>
        </div>
    </form>
</div>


<!-- The Modal for phone -->
<div id="phoneModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" onclick="phoneFunctionsAndParams.closePhoneModal()">&times;</span>
        <h3>New phone:</h3>
        <div class="form-group">
            <label>Country code:</label>
            <input type="hidden" id="phoneID" value="${contactProfile.id}">
            <input type="text" id="countryCode" class="form-control" maxlength="3" minlength="3" placeholder="XXX">
            <label for="operatorCode">Operator code:</label>
            <input type="text" id="operatorCode" class="form-control" maxlength="2" minlength="2" placeholder="XX">
            <label for="number">Phone number:</label>
            <input type="text" id="number" class="form-control" placeholder="XXXXXXX" maxlength="8">
            <label for="type">Mobile or home:</label>
            <select id="type" class="form-control">
                <option value="Mobile">Mobile</option>
                <option value="Home">Home</option>
                <option value="Work">Work</option>
            </select>
            <label for="phoneComment">Comment:</label>
            <input type="text" id="phoneComment" class="form-control">
            <input type="hidden" value="Save1" id="savePhone" class="btn btn-md">
            <input type="button" value="Save" class="btn btn-md bg-success"
                   onclick="phoneFunctionsAndParams.addPhone()">
            <input type="button" value="Cancel" class="btn btn-danger btn-md"
                   onclick="phoneFunctionsAndParams.cancelPhone()">
        </div>
    </div>
</div>

<!-- The Modal for photo -->
<div id="photoModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close" onclick="photoFunctionsAndParams.closePhotoModal()">&times;</span>
        <label>Please choose photo:</label>
        <input type="button" id="photo-button" value="Choose photo" onclick="photoFunctionsAndParams.addPhoto()">
        <input type="hidden" value="Save1" id="savePhoto" class="btn btn-md">
    </div>
</div>

<!-- The Modal for attachments -->
<div id="attachmentModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="attachmentsFunctionsAndParams.cancelAttachment()">&times;</span>
        <label>Please choose attachment:</label>
        <input type="button" id="attachment-button" value="Add attachments"
               onclick="attachmentsFunctionsAndParams.uploadAttachment()">
        <input type="hidden" value="Save1" id="saveAttachment" class="btn btn-md">
        <label for="name-attachment">File name:</label>
        <input type="text" id="name-attachment">
        <p></p>
        <label for="comment-attachment">Comment:</label>
        <input type="text" id="comment-attachment">
        <p></p>
        <input type="button" value="Save" class="btn btn-md" onclick="attachmentsFunctionsAndParams.addAttachment()">
        <input type="button" value="Cancel" class="btn btn-danger btn-md"
               onclick="attachmentsFunctionsAndParams.cancelAttachment()">

    </div>

</div>


</body>
<script src="/resources/js/edit.js?v=3"></script>
<script src="/resources/js/phone.js?v=1"></script>
<script src="/resources/js/attachment.js?v=1"></script>
<script src="/resources/js/bootstrap.js"></script>

</html>
