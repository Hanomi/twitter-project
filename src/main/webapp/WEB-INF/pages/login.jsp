<%@include file="templates/start.jspf" %>
<%@include file="templates/header.jspf"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<main role="main" class="container">
    <div class="row justify-content-md-center">
        <div class="col col-sm-5">
            <form method="POST" action="${contextPath}/login">
                <h4>Log in</h4>
                <div class="form-group">
                    <input name="username" type="text" class="form-control" placeholder="Email"
                           autofocus/>
                </div>
                <div class="form-group">
                    <input name="password" type="password" class="form-control" placeholder="Password"/>
                </div>
                <div class="form-row">
                    <div class="col-sm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-sm btn-primary btn-block" type="submit">Log In</button>
                    </div>
                    <div class="col-sm">
                        <a href="${contextPath}/#" role="button" class="btn btn-sm btn-primary btn-block">Registration</a>
                    </div>
                    <div class="col-sm-2">
                        <a href="${contextPath}/#" role="button" class="btn btn-sm btn-primary btn-block">VK</a>
                    </div>
                </div>
                <div class="form-group">
                    <span style="color: red;">${error}</span>
                    <span style="color: darkblue;">${message}</span>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jspf"%>
<%@include file="templates/end.jspf" %>