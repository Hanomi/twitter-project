<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
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

        <c:url var="firstUrl" value="/user/${userId}/pages/1" />
        <c:url var="page" value="/user/${userId}/pages"/>
        <c:url var="lastUrl" value="/user/${userId}/pages/${messagesList.totalPages}" />
        <c:url var="prevUrl" value="/user/${userId}/pages/${currentIndex - 1}" />
        <c:url var="nextUrl" value="/user/${userId}/pages/${currentIndex + 1}" />
        <c:if test="${messagesList.totalPages != 1}">
            <%@include file="templates/pagination.jspf" %>
        </c:if>
    </c:if>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>