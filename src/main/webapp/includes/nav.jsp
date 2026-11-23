<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="nav">
  <span class="brand">🩸 Blood Bank</span>
  <span>
    <a href="${pageContext.request.contextPath}/dashboard.jsp">Home</a>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
      <a href="${pageContext.request.contextPath}/admin">Stock</a>
      <a href="${pageContext.request.contextPath}/approvals">Requests</a>
    </c:if>
    <c:if test="${sessionScope.user.role == 'DONOR'}">
      <a href="${pageContext.request.contextPath}/donor">My Profile</a>
    </c:if>
    <c:if test="${sessionScope.user.role == 'HOSPITAL'}">
      <a href="${pageContext.request.contextPath}/request">Request Blood</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/logout">Logout (${sessionScope.user.name})</a>
  </span>
</div>
  