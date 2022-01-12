<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp"%>
<%@page import="java.util.*" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="board_update_date" value="${board.updateDate}"/>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">Back</button>

    <c:if test="${board.user.id == principal.user.id}">
        <a href="/board/${board.id}/update_form" class="btn btn-warning">Edit</a>
        <button id="btn-delete" class="btn btn-danger">Delete</button>
    </c:if>
    <br /><br />
    <div>
        <!-- number: <span id="id">${board.id} </span> -->
        writer: <span><i>${board.user.username}</i></span>
        date: <fmt:formatDate value="${board_update_date}" type="date" pattern="yyyy-MM-dd"/>
    </div>
    <br />
    <div>
        <h3>${board.title}</h3>
    </div>
    <hr />
    <div>
        <div>${board.content}</div>
    </div>
    <hr />

    <!-- write comment -->
    <div class="card">
        <form>
            <input type="hidden" id="userId" value="${principal.user.id}"/>
            <input type="hidden" id="boardId" value="${board.id}"/>
            <div class="card-body">
                <textarea id="reply-content" class="form-control" rows="1"></textarea>
            </div>
            <div class="card-footer">
                <button type="button" id="btn-reply-save" class="btn btn-primary">Add a comment</button>
            </div>
        </form>
    </div>
    <br>
    <!-- comment list -->
    <div class="card">
        <div class="card-header">Comments</div>
        <ul id="reply-box" class="list-group">
            <c:forEach var="reply" items="${board.replys}">
                <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                    <div>${reply.content}</div>
                    <div class="d-flex">
                        <div class="font-italic"> - ${reply.user.username} &nbsp;</div>
                        <!-- delete comment button -->
                        <c:if test="${reply.user.id == principal.user.id}">
                            <button class="badge" onclick="index.deleteComment(${board.id}, ${reply.id})">Delete</button>
                        </c:if>

                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>