<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-06
  Time: 오후 4:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="mb-4">회원 가입</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <c:out value="${errorMessage}"/>
            </div>
        </c:if>

        <form action="/user/register" method="post">

            <div class="mb-3">
                <label for="name" class="form-label">이름</label>
                <input type="text" class="form-control" name="name" id="name" placeholder="이름을 입력하세요" required />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">비밀번호</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="비밀번호를 입력하세요(3자 이상)" required />
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">이메일</label>
                <input type="email" class="form-control" name="email" id="email" placeholder="이메일을 입력하세요" required />
            </div>
            <div class="mb-3">
                <label for="role" class="form-label">회원 유형</label>

                <select class="form-select" name="role" id="role" required>
                    <option value="" disabled selected>회원 유형을 선택하세요</option>
                    <option value="GUEST">게스트(공간을 예약합니다)</option>
                    <option value="HOST">호스트(공간을 등록합니다)</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">회원 가입</button>
        </form>




    </div>
</div>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>