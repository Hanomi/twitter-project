<%@ page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <div class="col">
            <h4>Вход в аккаунт</h4>
            <form class="form-horizontal" method="POST" action="${contextPath}/login">
                <div class="form-group">
                    <label for="username">Почта:</label>
                    <input name="username" type="email" class="form-control" id="username" placeholder="Введите адрес электронной почты">
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input name="password" type="password" class="form-control" id="password"
                           placeholder="Введите ваше пароль">
                    <p class="form-text text-danger">${error}</p>
                    <p class="form-text text-info">${message}</p>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="rememberCheck">
                    <label class="form-check-label" for="rememberCheck">Запомнить меня</label>
                </div>
                <div class="form-group">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-default">Войти</button>
                </div>
                <div class="form-group">
                    <a href="${contextPath}/registration" class="text-primary">Зарегистрироваться</a> или
                    <a href="#" class="text-primary">Восстановить пароль</a>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>