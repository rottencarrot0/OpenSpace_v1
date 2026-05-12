<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-12
  Time: 오후 2:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="row justify-content-center">
    <div class="col-12 col-md-8 col-lg-6">
        <h2 class="mb-4">예약 신청</h2>

        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title"><c:out value="${space.title}"/></h5>
                <p class="card-text text-muted mb-1">
                    <span><c:out value="${space.address}"/></span>
                </p>
                <p class="card-text">
                    시간당 <span><fmt:formatNumber value="${space.pricePerHour}" type="number"/>원</span>
                </p>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/reservation" method="post">

            <input type="hidden" name="spaceId" value="${space.id}"/>

            <div class="mb-3">
                <label for="startDate" class="form-label">시작 일시</label>
                <input type="datetime-local" class="form-control" name="startDate" id="startDate" required/>
            </div>

            <div class="mb-3">
                <label for="endDate" class="form-label">종료 일시</label>
                <input type="datetime-local" class="form-control" name="endDate" id="endDate" required/>
            </div>

            <div class="mb-3 p-3 bg-light rounded">
                <div class="d-flex justify-content-between align-items-center">
                    <span>총 금액</span>
                    <span id="totalPriceDisplay">날짜를 선택해주세요</span>
                </div>
            </div>

            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-dark">예약 신청</button>
                <a href="${pageContext.request.contextPath}/space/${space.id}"
                   class="btn btn-secondary">취소</a>
            </div>

        </form>
    </div>
</div>

<script>
$(function() {
    $('#startDate, #endDate').on('change', function() {
        let startDate = $('#startDate').val();
        let endDate = $('#endDate').val();
        if(!startDate || !endDate) {
            $('#totalPriceDisplay').text('날짜를 선택해주세요');
            return;
        }

        let startMs = new Date(startDate).getTime();
        let endMs = new Date(endDate).getTime();
        // 1시간 = 60분 = 60 * 60 * 1000ms
        let hours = (endMs - startMs) / 3600000;

        if(hours <= 0) {
            $('#totalPriceDisplay').text('종료 시간은 시작 시간 이후여야 합니다.')
            return;
        }

        let pricePerHour = ${space.pricePerHour};
        let totalPrice = Math.floor(hours) * pricePerHour;

        $('#totalPriceDisplay').text(totalPrice);
    })
})
</script>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>
