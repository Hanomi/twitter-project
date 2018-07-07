<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <div class="row">
            <div class="col">
                <h4>Что у вас нового?</h4>
                <form:form method="POST" modelAttribute="messageForm">
                    <spring:bind path="text">
                        <div class="form-group">
                            <form:errors path="text" class="alert alert-danger" element="div"/>
                            <form:textarea path="text" title="Сообщение:" class="form-control"
                                           placeholder="Введите ваше сообщение..."/>
                        </div>
                    </spring:bind>
                    <div class="form-group">
                        <form:button type="submit" class="btn btn-default">Добавить</form:button>
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
                        <p class="blog-post-meta">${message.date} by ${message.user.username}</p>
                        <p>${message.text}</p>
                    </div>
                </c:forEach>
            </div>
        </div>

        <c:url var="firstUrl" value="/pages/1" />
        <c:url var="page" value="/pages"/>
        <c:url var="lastUrl" value="/pages/${messagesList.totalPages}" />
        <c:url var="prevUrl" value="/pages/${currentIndex - 1}" />
        <c:url var="nextUrl" value="/pages/${currentIndex + 1}" />
        <c:if test="${messagesList.totalPages != 1}">
            <%@include file="templates/pagination.jspf" %>
        </c:if>
    </c:if>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>