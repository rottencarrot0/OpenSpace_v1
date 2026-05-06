<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-05
  Time: 오후 4:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="mb-4">공간 등록</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <c:out value="${errorMessage}"/>
            </div>
        </c:if>

        <form action="/space" method="post">

            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" name="title" id="title" placeholder="공간의 이름을 등록하세요" required />
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">설명</label>
                <input type="text" class="form-control" name="description" id="description" placeholder="공간에 대한 설명을 입력하세요" required />
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">주소</label>
                <input type="text" class="form-control" name="address" id="address" placeholder="공간의 주소를 입력하세요" required />
            </div>

            <div class="mb-3">
                <label for="pricePerHour" class="form-label">시간당 가격</label>
                <input type="number" class="form-control" name="pricePerHour" id="pricePerHour" placeholder="0" min="0" required />
            </div>

            <div class="mb-3">
                <label for="capacity" class="form-label">수용 인원</label>
                <input type="number" class="form-control" name="capacity" id="capacity" placeholder="공간의 이름을 등록하세요" min="1" max="1000" required />
                <div class="form-text">최대 1000명까지 등록 가능합니다.</div>
            </div>

            // 사진 등록 // TODO 임시
            <div class="mb-3">
                <label for="images" class="form-label">이미지</label>
                <input type="file" class="form-control" name="images" id="images" placeholder="공간의 이름을 등록하세요" required />
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">등록</button>
                <a type="reset" href="/home" class="btn btn-secondary">취소</a>
            </div>

        </form>


    </div>


</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
