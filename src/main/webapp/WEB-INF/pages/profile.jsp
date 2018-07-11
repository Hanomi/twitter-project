<%@page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <div class="col">
            <h4>Введите новый ник.</h4>
            <form class="form-horizontal" method="POST">
                <div class="form-group">
                    <label for="nick">Ник:</label>
                    <input name="nick" type="text" class="form-control" id="nick" value="${currentNick}" required
                           placeholder="Новый ник.">
                    <p class="form-text text-danger">${error}</p>
                    <p class="form-text text-info">${message}</p>
                </div>
                <div class="form-group">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-secondary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>