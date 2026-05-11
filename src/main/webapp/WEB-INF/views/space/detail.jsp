<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-08
  Time: 오후 4:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row g-4">
    <div class="col-12 col-lg-7">
        <c:choose>
            <c:when test="${not empty space.images}">
                <div id="spaceCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner rounded">
                        <c:forEach items="${space.images}" var="img" varStatus="status">
                            <div class="carousel-item ${status.first ? 'active' : ''}">
                                <img src="<c:out value="${img.imageUrl}"/>" class="d-block w-100" alt="a"
                                     style="height:400px">
                            </div>
                        </c:forEach>
                    </div>
                    <button class="carousel-control-prev" type="button"
                            data-bs-target="#spaceCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon"></span>
                        <span class="visually-hidden">이전</span>
                    </button>
                    <button class="carousel-control-next" type="button"
                            data-bs-target="#spaceCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon"></span>
                        <span class="visually-hidden">다음</span>
                    </button>
                </div>
            </c:when>
            <c:otherwise>
                <img src="https://placehold.co/800x400"
                     class="img-fluid rounded w-100"
                     alt="이미지 없음"
                     style="height:400px">
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-12 col-lg-5">
        <h2 class="mb-3"><c:out value="${space.title}"/></h2>
        <ul class="mb-4 list-unstyled">
            <li class="mb-2">
                <span class="text-muted">주소</span>
                <c:out value="${space.address}"/>
            </li>
            <li class="mb-2">
                <span class="text-muted">시간당 가격</span>
                <span><fmt:formatNumber value="${space.pricePerHour}" type="number"/>원</span>
            </li>
            <li class="mb-2">
                <span class="text-muted">수용 인원</span>
                <c:out value="${space.capacity}"/>
            </li>
        </ul>

        <p class="mb-4"><c:out value="${space.description}"/></p>

        <div class="d-flex gap-2">
            <c:choose>
                <c:when test="${empty sessionScope.LOGIN_USER}">
                    <a href="${pageContext.request.contextPath}/user/login" class="btn btn-dark btn-lg">로그인 후 예야가하기</a>
                </c:when>
                <c:when test="${isMine}">
                    <button class="btn btn-secondary btn-lg" disabled>내 공간(예약 불가)</button>
                    <a href="${pageContext.request.contextPath}/space/${space.id}/edit"
                       class="btn btn-outline-dark btn-lg">공간 수정</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/reservation/new/${space.id}" class="btn btn-dark btn-lg">예약하기</a>
                </c:otherwise>
            </c:choose>
        </div>

    </div>

</div>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
