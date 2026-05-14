<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-14
  Time: 오후 2:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="p-4"></div>
<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="mb-4">내 프로필</h2>

        <div class="card shadow-sm">
            <div class="card-body">
                <div class="mb-3 row">
                    <label class="col-sm-3 col-form-label fw-semibold text-muted">이름</label>
                    <div class="col-sm-9 d-flex align-items-center">
                        <span><c:out value="${user.name}"/></span>
                    </div>
                </div>

                <div class="mb-3 row">
                    <label class="col-sm-3 col-form-label fw-semibold text-muted">이메일</label>
                    <div class="col-sm-9 d-flex align-items-center">
                        <span><c:out value="${user.email}"/></span>
                    </div>
                </div>

                <div class="mb-3 row">
                    <label class="col-sm-3 col-form-label fw-semibold text-muted">역할</label>
                    <div class="col-sm-9 d-flex align-items-center">
                        <c:choose>
                            <c:when test="${user.role == 'HOST'}">
                                <span class="badge bg-success fs-6">HOST</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-success fs-6">GUEST</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/user/logout" method="post">
                    <button type="submit" class="btn btn-outline-danger w-100">로그아웃</button>
                </form>
            </div>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
