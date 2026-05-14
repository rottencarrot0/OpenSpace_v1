<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-05
  Time: 오후 6:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="p-4"></div>
<div class="d-flex justify-content-between align-items-center mb-4">
    <h2>내 공간 관리</h2>
    <a href="${pageContext.request.contextPath}/space/register" class="btn btn-primary">새 공간 등록</a>
</div>

<c:choose>
    <c:when test="${not empty spaces}">
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-light">
                <tr>
                    <th>공간 이름</th>
                    <th>주소</th>
                    <th>시간당 가격</th>
                    <th>수용 인원</th>
                    <th class="text-align">관리</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${spaces}" var="space">
                    <tr id="spaceId-${space.id}">
                        <td>
                            <a href="${pageContext.request.contextPath}/space/${space.id}"><c:out value="${space.title}"/></a>
                        </td>
                        <td><c:out value="${space.address}"/></td>
                        <td>${space.pricePerHour}원</td>
                        <td>${space.capacity}명</td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/space/${space.id}/edit"
                               class="btn btn-sm btn-secondary">수정</a>
                            <button type="button" class="btn btn-sm deleteBtn" data-id="${space.id}">삭제</button>
                        </td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <div class="text-center py-5 text-muted">
            <p class="fs-5">등록된 공간이 없습니다.</p>
            <a href="${pageContext.request.contextPath}/space/register" class="btn btn-primary">공간 등록하기</a>
        </div>
    </c:otherwise>
</c:choose>

<script>
    document.querySelectorAll('.deleteBtn').forEach(function (btn) {
        btn.addEventListener('click', function () {
            let spaceId = this.dataset.id;
            if (!confirm("정말 삭제하시겠습니까?")) return;

            fetch('${pageContext.request.contextPath}/space/' + spaceId, {method: 'DELETE'})
            .then(function(response) {
                if(response.status !== 200) throw new Error("삭제 실패");

                let row = document.getElementById('spaceId-' + spaceId);
                if(row) row.remove();
            })
            .catch(function() {
                alert("삭제 중 오류가 발생했습니다.");
            })
        })
    })
</script>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>