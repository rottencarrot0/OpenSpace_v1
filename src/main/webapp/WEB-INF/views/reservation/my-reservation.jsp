<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-12
  Time: 오후 3:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="p-4"></div>
<h2 class="mb-4">내 예약</h2>

<c:if test="${empty reservations}">
    <div class="text-center text-muted">
        <p>예약 내역이 없습니다.</p>
        <a href="${pageContext.request.contextPath}/space" class="btn btn-dark">공간 둘러보기</a>
    </div>
</c:if>

<c:if test="${not empty reservations}">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>공간명</th>
                <th>시작 일시</th>
                <th>종료 일시</th>
                <th>총 금액</th>
                <th>상태</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reservations}" var="reservation">
                <tr>
                    <td><c:out value="${reservation.spaceName}"/></td>
                    <td>${reservation.startDate}</td>
                    <td>${reservation.endDate}</td>
                    <td><fmt:formatNumber value="${reservation.totalPrice}" type="number"/>원</td>
                    <td>
                        <c:choose>
                            <c:when test="${reservation.status == 'PENDING'}">
                                <span class="badge bg-warning text-dark">대기중</span>
                            </c:when>
                            <c:when test="${reservation.status == 'APPROVED'}">
                                <span class="badge bg-success">승인됨</span>
                            </c:when>
                            <c:when test="${reservation.status == 'REJECTED'}">
                                <span class="badge bg-danger">거절됨</span>
                            </c:when>
                            <c:when test="${reservation.status == 'CANCELED'}">
                                <span class="badge bg-secondary">취소됨</span>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${reservation.status == 'PENDING'}">
                            <button class="btn btn-sm btn-cancel" data-id="${reservation.id}">취소</button>

                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

<script>
    $(function () {
        $('.btn-cancel').on('click', function() {
            let id = $(this).data('id');
            if(!confirm('예약을 취소하시겠습니까?')) return;

            $.ajax({
                type: 'post',
                url: '/reservation/' + id + '/cancel'
            }).done(function() {
                location.reload();
            })
        })
    })
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
