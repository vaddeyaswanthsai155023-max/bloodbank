<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login - Blood Bank</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth">
  <div class="card">
    <h1>🩸 Blood Bank</h1>
    <h2>Login</h2>
    <c:if test="${not empty error}"><div class="msg-error">${error}</div></c:if>
    <form method="post" action="login">
      <label>Email</label>
      <input type="email" name="email" required>
      <label>Password</label>
      <input type="password" name="password" required>
      <button type="submit" style="width:100%">Login</button>
    </form>
    <p><a href="register">New user? Register</a></p>
  </div>
</body>
</html>