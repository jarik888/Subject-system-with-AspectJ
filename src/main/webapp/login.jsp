<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<title>Login page</title>
</head>
<body>
	<%
		String username = (String) request.getAttribute("username") == null ? "" :
			(String) request.getAttribute("username");
		String LoginError = (String) request.getAttribute("LoginError")== null ? "" :
			(String) request.getAttribute("LoginError");
		
		
	%>
	<div id="login-form">
		<form method="post" action="?service=login&action=login">
			<table>
				<tr>
					<th>Username</th>
					<td><input type="text" name="username" value="<%=username%>" /></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td class="error" colspan="2"><%=LoginError%></td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="submit" name="LoginBtn">Login</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>