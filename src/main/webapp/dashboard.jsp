<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Dashboard - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/includes/nav.jsp"/>
  <div class="container">
    <div class="card">
      <h2>Welcome, ${sessionScope.user.name}</h2>
      <p class="muted">Signed in as ${sessionScope.user.role}</p>
    </div>
    <div class="tiles">
      <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <a class="tile" href="admin"><h3>Manage Blood Stock</h3><span class="muted">Record donations and view stock</span></a>
        <a class="tile" href="approvals"><h3>Approve Requests</h3><span class="muted">Approve or reject hospital requests</span></a>
      </c:if>
      <c:if test="${sessionScope.user.role == 'DONOR'}">
        <a class="tile" href="donor"><h3>My Donor Profile</h3><span class="muted">Update your details and see your next eligible date</span></a>
      </c:if>
      <c:if test="${sessionScope.user.role == 'HOSPITAL'}">
        <a class="tile" href="request"><h3>Request Blood</h3><span class="muted">Submit a request and track its status</span></a>
      </c:if>
    </div>
  </div>
</body>
</html>