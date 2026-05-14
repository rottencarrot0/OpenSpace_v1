<%--
  Created by IntelliJ IDEA.
  User: qpwo3
  Date: 2026-05-10
  Time: 오후 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="p-4"></div>
<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="mb-4">공간 수정</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <c:out value="${errorMessage}"/>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/space/${spaceDetail.id}" method="post"
              enctype="multipart/form-data">
            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" name="title" id="title" value="<c:out value="${spaceDetail.title}"/>" required/>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">설명</label>
                <input type="text" class="form-control" name="description" id="description" value="<c:out value="${spaceDetail.description}"/>" required/>
            </div>
            <div class="mb-3">
                <label for="address" class="form-label">주소</label>
                <input type="text" class="form-control" name="address" id="address" value="<c:out value="${spaceDetail.address}"/>" required/>
            </div>
            <div class="mb-3">
                <label for="pricePerHour" class="form-label">시간당 가격</label>
                <input type="number" class="form-control" name="pricePerHour" id="pricePerHour" value="${spaceDetail.pricePerHour}" min="0"
                       required/>
            </div>
            <div class="mb-3">
                <label for="capacity" class="form-label">수용 인원</label>
                <input type="text" class="form-control" name="capacity" id="capacity" value="${spaceDetail.capacity}" min="1" max="1000" required/>
                <div class="form-text">최대 1000명까지 등록 가능합니다.</div>
            </div>

            <c:if test="${not empty spaceDetail.images}">
                <div class="mb-3">
                    <label class="form-label">기존 이미지</label>
                    <div class="d-flex flex-wrap gap-2">
                        <c:forEach items="${spaceDetail.images}" var="img">
                            <img src="${pageContext.request.contextPath}${img.imageUrl}" style="width: 120px;height: 90px;" alt="a" />
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <div class="mb-3">
                <label for="images" class="form-label">새 이미지 추가</label>
                <input type="file" class="form-control" name="images" id="images" multiple accept="image/*"/>
                <div class="form-text">새 이미지를 선택하면 기존 이미지에 추가됩니다.</div>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">저장</button>
                <a href="${pageContext.request.contextPath}/space/${spaceDetail.id}"
                   class="btn btn-secondary">취소</a>
            </div>
        </form>
    </div>
</div>



<%@ include file="/WEB-INF/views/common/footer.jsp" %>
