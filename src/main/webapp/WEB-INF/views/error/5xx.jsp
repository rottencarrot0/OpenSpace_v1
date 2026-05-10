<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-07
  Time: 오후 6:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>


<div class="text-center py-5">
    <h1 class="display-1 text-muted">500</h1>
    <h2 class="mb-3">서버 오류가 발생했습니다.</h2>
    <p class="text-muted mb-4">일시적인 서버 오류입니다. 잠시 후 다시 시도해 주세요.</p>
    <a href="${pageContext.request.contextPath}/" class="btn btn-dark mt-3">홈으로 돌아가기</a>
</div>





<%@ include file="/WEB-INF/views/common/footer.jsp" %>