<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf"%>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <form:form method="POST" modelAttribute="userForm" class="form-signin">
            <h2 class="form-signin-heading">Create your account</h2>
            <spring:bind path="username">
                <div class="form-group">
                    <form:input type="text" path="username" class="form-control" placeholder="Username"
                                autofocus="true"/>
                    <form:errors path="username"/>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group">
                    <form:input type="password" path="password" class="form-control" placeholder="Password"/>
                    <form:errors path="password"/>
                </div>
            </spring:bind>

            <spring:bind path="confirmPassword">
                <div class="form-group">
                    <form:input type="password" path="confirmPassword" class="form-control"
                                placeholder="Confirm your password"/>
                    <form:errors path="confirmPassword"/>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group">
                    <form:input type="text" path="email" class="form-control" placeholder="Email"/>
                    <form:errors path="email"/>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>
    </div>
</main>

<%@include file="templates/footer.jspf"%>
<%@include file="templates/end.jspf" %>