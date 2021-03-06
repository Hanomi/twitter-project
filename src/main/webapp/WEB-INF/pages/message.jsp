<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <c:if test="${parentMessage != null}">
        <div class="row">
            <div class="col">
                <div class="blog-post">
                    <p class="blog-post-meta"><c:if test="${parentMessage.retweet != null}"><span class="badge badge-success">Retweet</span></c:if>
                        <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${parentMessage.date}" /> by <a href="${contextPath}/user/${parentMessage.user.id}" class="text-primary">#${parentMessage.user.id} ${empty parentMessage.user.nick ? 'Anon' : parentMessage.user.nick}</a>
                        <c:if test="${pageContext.request.userPrincipal.name == parentMessage.user.email}">
                            <a href="${contextPath}${currentUrl}/edit/${parentMessage.id}" class="text-secondary"> <i class="far fa-edit"></i></a>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name != parentMessage.user.email and pageContext.request.userPrincipal.name != null}">
                            <a href="${contextPath}${currentUrl}/retweet/${parentMessage.id}" class="text-secondary"> <i class="far fa-arrow-alt-circle-down"></i></a>
                        </c:if>
                        <c:choose>
                            <c:when test="${liked.contains(parentMessage.id)}">
                                <a href="${contextPath}${currentUrl}/like/${parentMessage.id}" class="text-danger"> <i class="far fa-heart"></i><span class="badge badge-light">${parentMessage.likes.size()}</span></a>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}${currentUrl}/like/${parentMessage.id}" class="text-secondary"> <i class="far fa-heart"></i><span class="badge badge-light">${parentMessage.likes.size()}</span></a>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="blog-post-text">${parentMessage.text}</p>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col">
                <div class="blog-post text-info">
                    <p class="blog-post-meta"><c:if test="${currentMessage.retweet != null}"><span class="badge badge-success">Retweet</span></c:if>
                        <fmt:formatDate pattern = "dd/MM/yyyy HH:mm:ss" value = "${currentMessage.date}" /> by <a href="${contextPath}/user/${currentMessage.user.id}" class="text-primary">#${currentMessage.user.id} ${empty currentMessage.user.nick ? 'Anon' : currentMessage.user.nick}</a>
                        <c:if test="${pageContext.request.userPrincipal.name == currentMessage.user.email}">
                            <a href="${contextPath}${currentUrl}/edit/${currentMessage.id}" class="text-secondary"> <i class="far fa-edit"></i></a>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name != currentMessage.user.email and pageContext.request.userPrincipal.name != null}">
                            <a href="${contextPath}${currentUrl}/retweet/${currentMessage.id}" class="text-secondary"> <i class="far fa-arrow-alt-circle-down"></i></a>
                        </c:if>
                        <c:choose>
                            <c:when test="${liked.contains(currentMessage.id)}">
                                <a href="${contextPath}${currentUrl}/like/${currentMessage.id}" class="text-danger"> <i class="far fa-heart"></i><span class="badge badge-light">${currentMessage.likes.size()}</span></a>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}${currentUrl}/like/${currentMessage.id}" class="text-secondary"> <i class="far fa-heart"></i><span class="badge badge-light">${currentMessage.likes.size()}</span></a>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="blog-post-text">${currentMessage.text}</p>
                </div>
            <h3 class="blog-post-title">Ответы</h3>
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
    <c:if test="${!empty messagesList.getContent()}">
        <c:forEach items="${messagesList.getContent()}" var="message">
            <div class="row">
                <div class="col-auto" style="padding: 0; width: ${15*(message.lvl - currentMessage.lvl - 1)}px; border-right: 1px solid lightgray"></div>
                <div class="col">
                    <div class="blog-post">
                        <p class="blog-post-meta">
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