<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf"%>

<c:if test="${!empty messagesList}">
    <div class="row">
        <div class="col">
            <table class="table table-hover">
                <thead class="thead-light">
                <tr>
                    <th scope="col">Сообщения:</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messagesList}" var="message">
                    <tr>
                        <td>
                            <p>${message.date}</p>
                            <p>${message.text}</p>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<%@include file="templates/footer.jspf"%>
<%@include file="templates/end.jspf" %>