<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Something went wrong</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth">
  <div class="card">
    <h1>Sorry, something went wrong.</h1>
    <p>The page you asked for could not be shown. Please try again.</p>
    <p><a href="${pageContext.request.contextPath}/login">Go to login</a></p>
  </div>
</body>
</html>