<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-08
  Time: 오전 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<h2 class="mb-4">공간 검색</h2>
<form action="${pageContext.request.contextPath}/space">
    <div class="input-group">
        <input type="text" name="keyword" class="form-control"
               placeholder="공간 이름 또는 주소로 검색"
               value="<c:out value="${keyword}"/>"
        />
        <button type="submit" class="btn btn-primary">검색</button>
    </div>
</form>
<p class="text-muted mb-3">
    총 <strong>${totalCount}</strong>개의 공간
</p>

<c:choose>
    <c:when test="${not empty spaces}">
        <%--row-cols-md-3 폭 너비--%>
        <div class="row row-cols-1 row-cols-md-3 g-4 mb-4">
            <c:forEach items="${spaces}" var="space">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/space/${space.id}"
                       class="text-decoration-none text-dark">
                        <div class="card h-100 shadow-sm">
                            <c:choose>
                                <c:when test="">
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>

    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
