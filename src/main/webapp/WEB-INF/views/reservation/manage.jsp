<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-13
  Time: 오후 2:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="p-4"></div>
<h2 class="mb-4">예약 관리</h2>
<c:if test="${empty reservations}">
    <div class="text-center text-muted">
        <p>들어온 예약이 없습니다.</p>
    </div>
</c:if>

<c:if test="${not empty reservations}">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>게스트 이름</th>
                <th>공간 이름</th>
                <th>시작 일시</th>
                <th>종료 일시</th>
                <th>총 금액</th>
                <th>상태</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${reservations}" var="res">
                <tr>
                    <td><c:out value="${res.guestName}"/></td>
                    <td><c:out value="${res.spaceName}"/></td>
                    <td><c:out value="${res.startDate}"/></td>
                    <td><c:out value="${res.endDate}"/></td>
                    <td><fmt:formatNumber value="${res.totalPrice}" type="number"/>원</td>
                    <td>
                        <c:choose>
                            <c:when test="${res.status == 'PENDING'}">
                                <span class="badge bg-warning text-dark">대기중</span>
                            </c:when>
                            <c:when test="${res.status == 'APPROVED'}">
                                <span class="badge bg-warning text-dark">승인됨</span>
                            </c:when>
                            <c:when test="${res.status == 'REJECTED'}">
                                <span class="badge bg-warning text-dark">거절됨</span>
                            </c:when>
                            <c:when test="${res.status == 'CANCELED'}">
                                <span class="badge bg-warning text-dark">취소됨</span>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${res.status == 'PENDING'}">
                            <button class="btn btn-sm btn-success btn-approved" data-id="${res.id}">승인</button>
                            <button class="btn btn-sm btn-dark btn-reject" data-id="${res.id}">거절</button>
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
    $('.btn-approved').on('click', function() {
        let id = $(this).data('id');
        if(!confirm('예약을 승인하시겠습니까?')) return;

        $.ajax({
            type: 'post',
            url: '/reservation/approve/' + id
        }).done(function() {
            location.reload();
        });
    });

    $('.btn-reject').on('click', function() {
        let id = $(this).data('id');
        if(!confirm('예약을 거절하시겠습니까?')) return;

        $.ajax({
            type: 'post',
            url: '/reservation/reject/' + id
        }).done(function() {
            location.reload();
        })
    })
})
</script>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
