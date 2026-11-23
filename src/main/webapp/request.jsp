<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Request Blood - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/includes/nav.jsp"/>
  <div class="container">
    <div class="card">
      <h2>Request Blood</h2>
      <c:if test="${param.saved == '1'}"><div class="msg-ok">Request submitted.</div></c:if>
      <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
      <form method="post" action="request">
        <label>Blood group</label>
        <select name="bloodGroup" required>
          <option value="">-- choose --</option>
          <c:forEach items="${groups}" var="g"><option value="${g}">${g}</option></c:forEach>
        </select>
        <label>Units</label>
        <input type="number" name="units" min="1" max="20" value="1" required>
        <label>Urgency</label>
        <select name="urgency"><option value="NORMAL">Normal</option><option value="URGENT">Urgent</option></select>
        <button type="submit">Submit request</button>
      </form>
    </div>
    <div class="card">
      <h2>My requests</h2>
      <table>
        <tr><th>#</th><th>Group</th><th>Units</th><th>Urgency</th><th>Status</th></tr>
        <c:forEach items="${requests}" var="r">
          <tr>
            <td>${r.id}</td><td>${r.bloodGroup}</td><td>${r.units}</td>
            <td><span class="badge ${r.urgency}">${r.urgency}</span></td>
            <td><span class="badge ${r.status}">${r.status}</span></td>
          </tr>
        </c:forEach>
        <c:if test="${empty requests}"><tr><td colspan="5">No requests yet.</td></tr></c:if>
      </table>
    </div>
  </div>
</body>
</html>