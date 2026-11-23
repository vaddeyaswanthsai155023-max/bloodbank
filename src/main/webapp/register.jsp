<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Register - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth">
  <div class="card">
    <h1>🩸 Blood Bank</h1>
    <h2>Create account</h2>
    <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
    <form method="post" action="register">
      <label>Name</label>
      <input type="text" name="name" required>
      <label>Email</label>
      <input type="email" name="email" required>
      <label>Password (8+ characters)</label>
      <input type="password" name="password" minlength="8" required>
      <label>I am a</label>
      <select name="role">
        <option value="DONOR">Donor</option>
        <option value="HOSPITAL">Hospital</option>
      </select>
      <button type="submit" style="width:100%">Register</button>
    </form>
    <p><a href="login">Already registered? Login</a></p>
  </div>
</body>
</html>