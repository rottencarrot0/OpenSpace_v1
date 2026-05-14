<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-05
  Time: 오후 3:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="text-center py-5">
    <h1 class="display-5 fw-bold">OpenSpace</h1>
    <p class="lead text-muted">원하는 공간을 찾으세요</p>
    <a href="${pageContext.request.contextPath}/space" class="btn btn-dark btn-lg mt-2">공간 검색하기</a>
</div>

<section class="mt-5">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4>최근 등록된 공간</h4>
        <a href="${pageContext.request.contextPath}/space" class="text-decoration-none">공간 더 보기</a>
    </div>

    <c:choose>
        <c:when test="${not empty space}">
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${space}" var="spa">
                    <div class="col">
                        <a href="${pageContext.request.contextPath}/space/${spa.id}"
                           class="text-decoration-none text-dark">
                            <div class="card h-100 shadow-sm">
                                <c:choose>
                                    <c:when test="${not empty spa.mainImageUrl}">
                                        <img src="<c:out value="${spa.mainImageUrl}"/>"
                                             class="card-img-top"
                                             style="height:200px; object-fit:cover;"
                                             alt=""/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://placehold.co/400x200"
                                             class="card-img-top"
                                             style="height:200px; object-fit:cover;" alt="">
                                    </c:otherwise>
                                </c:choose>
                                <div class="card-body">
                                    <h5 class="card-title"><c:out value="${spa.title}"/></h5>
                                    <p class="card-text text-muted small mb-1">
                                        <c:out value="${spa.address}"/>
                                    </p>
                                    <p class="card-text mb-1"> 시간당 <fmt:formatNumber type="number" value="${spa.pricePerHour}"/>원</p>
                                    <p class="card-text text-muted small">
                                        수용인원 ${spa.capacity}명
                                    </p>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-muted">등록된 공간이 없습니다.</p>
        </c:otherwise>
    </c:choose>
</section>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>