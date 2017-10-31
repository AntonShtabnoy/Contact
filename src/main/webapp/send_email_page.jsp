<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/home.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/send.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
    <title>Send email-templates:</title>
    <%@ include file="header.jsp" %>
</head>
<body>
<div class="container container-margin">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <div class="text-left">
                <div class="panel-body">

                    <form action="/main/send_email?isSend=true" method="post"
                          onsubmit="return checkBirthdayGroupSending()">
                        <div class="row">
                            <div class=" col-md-offset-4 col-md-6">
                                <h2 class="letter">Sending email </h2><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6">
                                <div class="form-group">
                                    <table class="table table-condensed">
                                        <thead>
                                        <tr>
                                            <th class="letter">To:</th>
                                        </tr>
                                        </thead>
                                        <tbody id="receiver_table_body">
                                        <c:forEach items="${emailsInfo}" var="emailInfo">
                                            <tr class="emailItem">
                                                <td><input type="text" readonly="readonly" name="emails"
                                                           value="<c:out value="${emailInfo.email}"/>"></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6">
                                <div class="form-group">
                                    <label for="title" class="letter">Title:</label>
                                    <input type="text" name="title" class="form-control" id="title">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6 select-template">
                                <div class="form-group">
                                    <label for="sel1" class="letter">Select template:</label>
                                    <select class="form-control" id="sel1" name="selectTemplate"
                                            onchange="selectTemplateName(this.value)">
                                        <option selected value="Default">Default</option>
                                        <option value="Birthday">Birthday</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-offset-3 col-md-6 select-template">
                                <div class="form-group">
                                    <label for="textarea" class="letter">Text: </label>
                                    <textarea class="form-control" name="message" id="textarea" rows="3"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <select class="form-control" id="byeId" name="bye">
                                    <option selected>Sincerely yours,</option>
                                    <option>Best regards,</option>
                                    <option>Yours Faithfully,</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <input type="text" name="sender-name" class="form-control" required="required"
                                       placeholder="Your name" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)">
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <input type="text" name="sender-surname" class="form-control" required="required"
                                           placeholder="Your surname" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)">
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <input type="text" name="sender-phone" class="form-control" required="required"
                                           placeholder="Your phone" pattern="^[+0-9]{1,15}$">
                                </div>
                            </div>
                            <div class="col-md-2">
                                <input type="text" name="sender-email" class="form-control" required="required"
                                       placeholder="Your email" pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2 col-md-offset-3">
                                <label class="radio letter"><input type="radio" id="groupRadioId" name="radioEmail"
                                                                   value="Group">Group
                                    message</label>
                            </div>
                            <div class="col-md-2">
                                <label class="radio letter"><input type="radio" name="radioEmail" checked
                                                                   value="Single">Single
                                    message</label>
                            </div>
                            <div class="col-md-2">
                                <input type="submit" name="send-buttonId" value="Send" class="btn1">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="/resources/js/send_email.js"></script>
</html>
