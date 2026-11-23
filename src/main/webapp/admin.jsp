<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Blood Stock - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/includes/nav.jsp"/>
  <div class="container">
    <div class="card">
      <h2>Record a donation</h2>
      <c:if test="${param.saved == '1'}"><div class="msg-ok">Donation recorded.</div></c:if>
      <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
      <form method="post" action="admin">
        <label>Donor</label>
        <select name="donorId" required>
          <option value="">-- choose --</option>
          <c:forEach items="${donors}" var="d">
            <option value="${d.id}">#${d.id} - ${d.bloodGroup} - ${d.city} - ${d.phone}</option>
          </c:forEach>
        </select>
        <label>Units (1 or 2)</label>
        <input type="number" name="units" min="1" max="2" value="1" required>
        <button type="submit">Record donation</button>
      </form>
    </div>
    <div class="card">
      <h2>Available stock (not expired)</h2>
      <table>
        <tr><th>Blood group</th><th>Units</th></tr>
        <c:forEach items="${summary}" var="row">
          <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
        </c:forEach>
        <c:if test="${empty summary}"><tr><td colspan="2">No stock yet.</td></tr></c:if>
      </table>
    </div>
  </div>
</body>
</html>