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
    </c:if>
    <c:if test="${!empty messagesList.content}">
        <c:forEach items="${messagesList.content}" var="message">
            <div class="row">
                <div class="col">
                    <div class="blog-post">
                        <p class="blog-post-meta">
                            <c:if test="${message.retweet != null}"><a href="${contextPath}/message/${message.retweet}" class="badge badge-success">Retweet</a></c:if>
                            <c:if test="${message.parentId != null}"><a href="${contextPath}/message/${message.thread}" class="badge badge-primary">Reply</a></c:if>
                            <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${message.date}" /> by <a href="${contextPath}/user/${message.user.id}" class="text-primary">#${message.user.id} ${empty message.user.nick ? 'Anon' : message.user.nick}</a>
                            <a class="text-secondary" href="${contextPath}/message/${message.id}"> <i class="far fa-comment"></i></a>
                            <c:if test="${pageContext.request.userPrincipal.name == message.user.email}">
                                <a href="${contextPath}${currentUrl}/edit/${message.id}" class="text-secondary"> <i class="far fa-edit"></i></a>
                            </c:if>
                            <c:if test="${pageContext.request.userPrincipal.name != message.user.email and pageContext.request.userPrincipal.name != null}">
                                <a href="${contextPath}${currentUrl}/retweet/${message.id}" class="text-secondary"> <i class="far fa-arrow-alt-circle-down"></i></a>
                            </c:if>
                            <c:choose>
                                <c:when test="${liked.contains(message.id)}">
                                    <a href="${contextPath}${currentUrl}/like/${message.id}" class="text-danger"> <i class="far fa-heart"></i><span class="badge badge-light">${message.likes.size()}</span></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${contextPath}${currentUrl}/like/${message.id}" class="text-secondary"> <i class="far fa-heart"></i><span class="badge badge-light">${message.likes.size()}</span></a>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p class="blog-post-text">${message.text}</p>
                    </div>
                </div>
            </div>
        </c:forEach>

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