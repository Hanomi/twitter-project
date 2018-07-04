<%@ page pageEncoding="UTF-8" %>

<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf" %>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <div class="col">
            <form:form method="POST" modelAttribute="userForm" class="form-horizontal">
                <h4>Регистрация</h4>
                <spring:bind path="username">
                    <div class="form-group">
                        <form:label path="username">Username:</form:label>
                        <form:input type="text" path="username" class="form-control" placeholder="Username"
                                    autofocus="true"/>
                        <form:errors path="username" class="form-text text-danger"/>
                    </div>
                </spring:bind>

                <spring:bind path="password">
                    <div class="form-group">
                        <form:label path="password">Password:</form:label>
                        <form:input type="password" path="password" class="form-control" placeholder="Password"/>
                        <form:errors path="password" class="form-text text-danger"/>
                    </div>
                </spring:bind>

                <spring:bind path="confirmPassword">
                    <div class="form-group">
                        <form:label path="confirmPassword">Confirm password:</form:label>
                        <form:input type="password" path="confirmPassword" class="form-control"
                                    placeholder="Confirm your password"/>
                        <form:errors path="confirmPassword" class="form-text text-danger"/>
                    </div>
                </spring:bind>

                <spring:bind path="email">
                    <div class="form-group">
                        <form:label path="email">Email:</form:label>
                        <form:input type="text" path="email" class="form-control" placeholder="Email"/>
                        <form:errors path="email" class="form-text text-danger"/>
                    </div>
                </spring:bind>

                <button type="submit" class="btn btn-default">Отправить</button>
            </form:form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jspf" %>
<%@include file="templates/end.jspf" %>