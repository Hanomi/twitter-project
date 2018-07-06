<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <div class="col">
            <h4>Введите новый пароль.</h4>
            <form class="form-horizontal" method="POST">
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input name="password" type="password" class="form-control" id="password" required
                           placeholder="Новый пароль.">
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Повторите пароль:</label>
                    <input name="confirmPassword" type="password" class="form-control" id="confirmPassword" required
                           placeholder="Повторите ввод нового пароля.">
                    <p class="form-text text-danger">${message}</p>
                </div>
                <div class="form-group">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-default">Отправить</button>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>