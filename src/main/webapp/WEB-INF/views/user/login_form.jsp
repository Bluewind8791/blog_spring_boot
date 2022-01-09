<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form action="/auth/login_proc" method="POST">
        <div class="form-group">
            <label for="username">Username:</label>
            <input name="username" type="username" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="pwd">Password:</label>
            <input name="password" type="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <div class="form-group form-check">
            <label class="form-check-label">
                <input name="remember" class="form-check-input" type="checkbox"> Remember me
            </label>
        </div>
        <button id="btn-login" class="btn btn-primary">Login</button>
    </form>
</div>

<%@ include file="../layout/footer.jsp"%>