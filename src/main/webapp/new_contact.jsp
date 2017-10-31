<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/home.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/new_contact_page.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css"/>">
    <title>Add contact</title>
    <%@ include file="header.jsp" %>
</head>
<body>
<div class="container top-buffer">
    <div class="row">
        <div class="col-md-offset-4 col-md-3 msf-title">
            <h1>Fill In The Form</h1>
        </div>
    </div>
    <form method="post" action="/main/create">
        <div class="row">
            <div class="col-md-4">
                <div class=form-group">
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
                                   pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   required/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" placeholder="Name" name="name" class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   required/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-4">
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
                            <input type="text" placeholder="Surname" name="surname" id="surname"
                                   class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)" required/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-4">
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
                            <input type="date" name="date" id="date" required class="form-control">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label for="sex" class="letter">Sex<span class="text-red">*</span></label>
                    <c:choose>
                        <c:when test="${paramMap['sex'] != null}">
                            <c:if test="${paramMap['sex'] == 'M'}">
                                <select name="sex" class="form-control" id="sex">
                                    <option selected value="M">Male</option>
                                    <option value="F">Female</option>
                                </select>
                            </c:if>
                            <c:if test="${paramMap['sex'] == 'F'}">
                                <select name="sex" class="form-control" id="sex">
                                    <option value="M">Male</option>
                                    <option selected value="F">Female</option>
                                </select>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <select name="sex" class="form-control" id="sex">
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="family_status" class="letter">Family status<span class="text-red">*</span></label>
                    <c:choose>
                        <c:when test="${paramMap['family_status'] != null}">
                            <c:if test="${paramMap['family_status'] == 'Free'}">
                                <select name="family_status" class="form-control" id="family_status">
                                    <option selected value="Free">Free</option>
                                    <option value="Married">Married</option>
                                </select>
                            </c:if>
                            <c:if test="${paramMap['family_status'] == 'Married'}">
                                <select name="family_status" class="form-control" id="family_status">
                                    <option value="Free">Free</option>
                                    <option selected value="Married">Married</option>
                                </select>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <select name="family_status" class="form-control" id="family_status">
                                <option value="Free">Free</option>
                                <option value="Married">Married</option>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="nationality" class="letter">Nationality<span class="text-red">*</span></label>
                    <select name="nationality" id="nationality" class="form-control">
                        <c:choose>
                            <c:when test="${paramMap['nationality'] != null}">
                                <c:forEach items="${nationalities}" var="nation">
                                    <<c:set var="option" value="${nation.key}"/>
                                    <c:set var="nat" value="${paramMap['nationality']}"/>
                                    <c:if test="${option==nat}">
                                        <option selected>${nation.value}</option>
                                    </c:if>
                                    <c:if test="${option!=nat}">
                                        <option>${nation.value}</option>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option value="">Choose nationality...</option>
                                <c:forEach items="${nationalities}" var="nationality">
                                    <option value="${nationality.key}">${nationality.value}</option>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
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
                            <input type="text" placeholder="https://xxx.com" name="webSite" id="webSite"
                                   class="form-control" autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-6">
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
                            <input type="text" placeholder="xxx@gmail.com" name="email" id="email" class="form-control"
                                   required pattern="^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$" autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
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
                            <input type="text" placeholder="Work place" name="work" id="work"
                                   class="form-control" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="country" class="letter">Country<span class="text-red">*</span></label>
                    <select name="country" id="country" class="form-control">
                        <c:choose>
                        <c:when test="${paramMap['country'] != null}">
                        <c:forEach items="${countries}" var="c">
                            <<c:set var="option" value="${c.key}"/>
                            <c:set var="countr" value="${paramMap['country']}"/>
                            <c:if test="${option==countr}">
                                <option selected>${c.value}</option>
                            </c:if>
                            <c:if test="${option!=countr}">
                                <option>${c.value}</option>
                            </c:if>
                        </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <option value="">Choose country...</option>
                            <c:forEach items="${countries}" var="country">
                                <option value="${country.key}">${country.value}</option>
                            </c:forEach>
                        </c:otherwise>
                        </c:choose>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
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
                            <input type="text" placeholder="City" name="city" id="city" required class="form-control"
                                   autocomplete="off" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <c:if test="${mistakeMap['street'] == null}">
                        <label for="street" class="letter">Street<span class="text-red">*</span></label>
                    </c:if>
                    <div class="text-red">
                        <c:if test="${mistakeMap['street'] != null}">
                            <label for="street">Street <c:out value="${mistakeMap['street']}"/></label>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${paramMap['street'] != null}">
                            <input type="text" value="${paramMap['street']}" name="street" id="street"
                                   class="form-control"
                                   autocomplete="off" required pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" placeholder="Street" name="street" id="street" required
                                   class="form-control" pattern="(^[a-zA-Zа-яА-Я][.-_a-zA-Zа-яА-Я0-9 ]{1,20}$)"
                                   autocomplete="off"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3">
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
                            <input type="text" placeholder="Home-flat" name="home" id="home" required class="form-control"
                                   autocomplete="off" pattern="(^[0-9][-_а-яА-Яa-zA-Z0-9 ]{1,15}$)"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-3">
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
                            <input type="text" placeholder="xxxxxxxx" name="index" id="index" class="form-control"
                                   autocomplete="off" pattern="^[0-9]{1,15}$"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <input type="submit" name="button" class="btn1" value="Add"/>
    </form>
</div>
</body>

</html>
