<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-06
  Time: 오후 6:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>


<div class="row justify-content-center">
  <div class="col-md-4">
    <h2 class="mb-4">로그인</h2>

    <c:if test="${not empty errorMessage}">
      <div class = "alert alert-danger">
        <c:out value="${errorMessage}" />
      </div>
    </c:if>

    <form action="/user/login" method="post">

      <input type="hidden" name="redirectURL" value="${redirectURL}">

      <div class="mb-3">
        <label for="email" class="form-label">이메일</label>
        <input type="email" class="form-control" name="email" id="email" placeholder="이메일을 입력하세요" required />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">비밀번호</label>
        <input type="password" class="form-control" name="password" id="password" placeholder="바밀번호를 입력하세요" required />
      </div>

      <button type="submit" class="btn btn-primary">로그인</button>
    </form>



  </div>
</div>





<%@ include file="/WEB-INF/views/common/footer.jsp" %>