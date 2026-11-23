<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Donor Profile - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/includes/nav.jsp"/>
  <div class="container">
    <div class="card">
      <h2>Donor Profile</h2>
      <c:if test="${param.saved == '1'}"><div class="msg-ok">Profile saved.</div></c:if>
      <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
      <form method="post" action="donor">
        <label>Blood group</label>
        <select name="bloodGroup" required>
          <option value="">-- choose --</option>
          <c:forEach items="${groups}" var="g">
            <option value="${g}" ${donor.bloodGroup == g ? 'selected' : ''}>${g}</option>
          </c:forEach>
        </select>
        <label>Date of birth</label>
        <input type="date" name="dob" value="${donor.dob}" required>
        <label>Phone (10 digits)</label>
        <input type="text" name="phone" value="${donor.phone}" pattern="\d{10}" maxlength="10" required>
        <label>City</label>
        <input type="text" name="city" value="${donor.city}">
        <button type="submit">Save profile</button>
      </form>
      <c:if test="${not empty donor.lastDonationDate}">
        <p class="muted">Last donation: ${donor.lastDonationDate} | Next eligible date: ${nextEligible}</p>
      </c:if>
    </div>
  </div>
</body>
</html>