<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<main role="main" class="container">
    <div class="row">
        <div class="col">
                <div class="blog-post">
                    <p class="blog-post-meta"><c:if test="${currentMessage.retweet != null}"><span class="badge badge-success">Retweet</span></c:if>
                        <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${currentMessage.date}" /> by ${currentMessage.user.username}
                        <c:if test="${pageContext.request.userPrincipal.name == currentMessage.user.username}">
                            <a href="${contextPath}${currentUrl}/edit/${currentMessage.id}" class="text-info"> <i class="far fa-edit"></i></a>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name != currentMessage.user.username and pageContext.request.userPrincipal.name != null}">
                            <a href="${contextPath}${currentUrl}/retweet/${currentMessage.id}" class="text-info"> <i class="far fa-arrow-alt-circle-down"></i></a>
                        </c:if>
                        <c:choose>
                            <c:when test="${liked.contains(currentMessage.id)}">
                                <a href="${contextPath}${currentUrl}/like/${currentMessage.id}" class="text-danger"> <i class="far fa-heart"></i><span class="badge badge-light">${message.likes.size()}</span></a>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}${currentUrl}/like/${currentMessage.id}" class="text-muted"> <i class="far fa-heart"></i><span class="badge badge-light">${message.likes.size()}</span></a>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p>${currentMessage.text}</p>
                </div>
            <p><h2 class="blog-post-title">Ответы</h2></p>
        </div>
    </div>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <div class="row">
            <div class="col">
                <div class="blog-post">
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
                            <a class="btn btn-secondary" href="${contextPath}${currentUrl}/" role="button">Отмена</a>
                        </c:when>
                        <c:otherwise>
                            <form:button type="submit" class="btn btn-secondary">Добавить</form:button>
                        </c:otherwise>
                        </c:choose>

                    </div>
                </form:form>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${!empty messagesList.content}">
        <div class="row">
            <div class="col">
                <c:forEach items="${messagesList.content}" var="message">
                    <div class="blog-post">
                        <p class="blog-post-meta"><c:if test="${message.retweet != null}"><span class="badge badge-success">Retweet</span></c:if>
                            <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${message.date}" /> by ${message.user.username}
                            <c:if test="${pageContext.request.userPrincipal.name == message.user.username}">
                                <a href="${contextPath}${currentUrl}/edit/${message.id}" class="text-info"> <i class="far fa-edit"></i></a>
                            </c:if>
                            <c:if test="${pageContext.request.userPrincipal.name != message.user.username and pageContext.request.userPrincipal.name != null}">
                                <a href="${contextPath}${currentUrl}/retweet/${message.id}" class="text-info"> <i class="far fa-arrow-alt-circle-down"></i></a>
                            </c:if>
                            <c:choose>
                                <c:when test="${liked.contains(message.id)}">
                                    <a href="${contextPath}${currentUrl}/like/${message.id}" class="text-danger"> <i class="far fa-heart"></i><span class="badge badge-light">${message.likes.size()}</span></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${contextPath}${currentUrl}/like/${message.id}" class="text-muted"> <i class="far fa-heart"></i><span class="badge badge-light">${message.likes.size()}</span></a>
                                </c:otherwise>
                            </c:choose>
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