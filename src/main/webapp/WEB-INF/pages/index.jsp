<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <div class="row">
            <div class="col">
                <h4>Что у вас нового?</h4>
                <form:form method="POST" modelAttribute="messageForm">
                    <form:hidden path="id"/>
                    <form:hidden path="date"/>
                    <spring:bind path="text">
                        <div class="form-group">
                            <form:errors path="text" class="alert alert-danger" element="div"/>
                            <form:textarea path="text" title="Сообщение:" class="form-control"
                                           placeholder="Введите ваше сообщение..."/>
                        </div>
                    </spring:bind>
                    <div class="form-group">
                        <c:choose>
                        <c:when test="${editMode}">
                            <form:button type="submit" class="btn btn-secondary">Сохранить</form:button>
                            <a class="btn btn-secondary" href="${contextPath}${currentUrl}" role="button">Отмена</a>
                        </c:when>
                        <c:otherwise>
                            <form:button type="submit" class="btn btn-secondary">Добавить</form:button>
                        </c:otherwise>
                        </c:choose>

                    </div>
                </form:form>
            </div>
        </div>
    </c:if>
    <c:if test="${!empty messagesList.content}">
        <div class="row">
            <div class="col">
                <c:forEach items="${messagesList.content}" var="message">
                    <div class="blog-post">
                        <p class="blog-post-meta"><fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${message.date}" /> by ${message.user.username}
                            <c:if test="${pageContext.request.userPrincipal.name == message.user.username}">
                                <a href="${contextPath}${currentUrl}/edit/${message.id}" class="text-info"> <i class="far fa-edit"></i></a>
                            </c:if>
                        </p>
                        <p>${message.text}</p>
                    </div>
                </c:forEach>
            </div>
        </div>

        <c:url var="firstUrl" value="${pagePath}1"/>
        <c:url var="page" value="${pagePath}"/>
        <c:url var="lastUrl" value="${pagePath}${messagesList.totalPages}"/>
        <c:url var="prevUrl" value="${pagePath}${currentIndex - 1}"/>
        <c:url var="nextUrl" value="${pagePath}${currentIndex + 1}"/>
        <c:if test="${messagesList.totalPages != 1}">
            <%@include file="templates/pagination.jspf" %>
        </c:if>
    </c:if>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>