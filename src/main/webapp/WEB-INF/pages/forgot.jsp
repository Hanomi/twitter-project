<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <div class="col">
            <h4>Введите адрес эл. почты</h4>
            <p>На указнный адрес будет отправлена информация для смены пароля.</p>
            <form class="form-horizontal" method="POST" action="${contextPath}/forgot-password">
                <div class="form-group">
                    <input name="email" type="email" class="form-control" id="email"
                           placeholder="Введите адрес электронной почты">
                    <p class="form-text text-danger">${message}</p>
                </div>
                <div class="form-group">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-secondary">Отправить</button>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>