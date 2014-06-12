<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<jsp:include page="header.jsp" />
<div class="float-left">
	<form method="post" action="?service=subject&action=create_new_subject">
		<button type="submit">Add new subject</button>
	</form>
</div>
<div class="float-left">	
	<form method="post" action="?service=subject&action=search">
		<button type="submit">Subject searching</button>
	</form>
</div>
</body>
</html>