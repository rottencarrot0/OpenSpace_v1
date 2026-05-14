<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-05
  Time: 오후 4:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Open Space</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">OpenSpace</a>
        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarMain">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarMain">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/space">공간 검색</a>
                </li>
                <c:if test="${sessionScope.LOGIN_USER.role == 'HOST'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/space/register">공간 등록</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/space/my">내 공간 관리</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/reservation/manage">예약 관리</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.LOGIN_USER.role == 'GUEST'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/reservation/my">내 예약</a>
                    </li>
                </c:if>
            </ul>
            <ul class="navbar-nav ms-auto">
                <!--비로그인-->
                <c:if test="${empty sessionScope.LOGIN_USER}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/login">로그인</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/register">회원가입</a>
                    </li>
                </c:if>
                <%--                로그인 --%>
                <c:if test="${not empty sessionScope.LOGIN_USER}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/user/profile">${sessionScope.LOGIN_USER.name}님</a>
                    </li>
                    <li class="nav-item">
                        <form action="${pageContext.request.contextPath}/user/logout" method="post" class="d-inline">
                            <button type="submit" class="nav-link btn"
                                    style="color: rgba(255,255,255,0.75);text-decoration: none;">로그아웃
                            </button>
                        </form>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>


<main class="container">