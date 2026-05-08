<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-08
  Time: 오전 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                                <c:when test="${not empty space.mainImageUrl}">
                                    <img src="<c:out value="${space.mainImageUrl}"/>" class="card-img-top" alt=""/>
                                </c:when>
                                <c:otherwise>
                                    <img src="https://placehold.co/400x300" class="card-img-top" alt=""/>
                                </c:otherwise>
                            </c:choose>
                            <div class="card-body">
                                <h5 class="card-title"><c:out value="${space.title}"/></h5>
                                <p class="card-text text-muted small">
                                    <c:out value="${space.address}"/>
                                </p>
                                <p class="card-text text-muted small">
                                    시간당 <fmt:formatNumber value="${space.pricePerHour}" type="number"/>원
                                </p>
                                <p class="card-text text-muted small">
                                    수용 인원 <c:out value="${space.capacity}"/>명
                                </p>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="text-center">
            <p class="text-muted fs-5">검색 결과가 없습니다.</p>
        </div>
    </c:otherwise>
</c:choose>

<%--페이징--%>
<c:if test="${totalPage > 1}">
    <nav>
        <ul class="pagination justify-content-center">
            <c:forEach begin="1" end="${totalPage}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/space?keyword=<c:out value="${keyword}"/>&page${i}">
                    ${i}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
