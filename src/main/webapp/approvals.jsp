<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Approvals - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/includes/nav.jsp"/>
  <div class="container">
    <div class="card">
      <h2>Blood Requests</h2>
      <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
      <table>
        <tr><th>#</th><th>Hospital ID</th><th>Group</th><th>Units</th><th>Urgency</th><th>Status</th><th>Action</th></tr>
        <c:forEach items="${requests}" var="r">
          <tr>
            <td>${r.id}</td><td>${r.hospitalId}</td><td>${r.bloodGroup}</td><td>${r.units}</td>
            <td><span class="badge ${r.urgency}">${r.urgency}</span></td>
            <td><span class="badge ${r.status}">${r.status}</span></td>
            <td>
              <c:if test="${r.status == 'PENDING'}">
                <form method="post" action="approvals" style="display:inline">
                  <input type="hidden" name="id" value="${r.id}">
                  <button class="btn-small" name="action" value="approve">Approve</button>
                  <button class="btn-small btn-grey" name="action" value="reject">Reject</button>
                </form>
              </c:if>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty requests}"><tr><td colspan="7">No requests.</td></tr></c:if>
      </table>
    </div>
  </div>
</body>
</html>