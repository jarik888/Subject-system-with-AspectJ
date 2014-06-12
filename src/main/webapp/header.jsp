<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="status" scope="request" class="java.lang.String" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<script type="text/javascript" src="static/js/jquery-2.1.1.min.js"></script>

<title>Subjektid</title>
</head>
<body>
	<div>
		Welcome <strong><%=session.getAttribute("username")%></strong>.
		<form id="logout-form" method="post" action="?service=login&action=logout">
			<button type="submit">Quit</button>
		</form>
	</div>
	<ul>
		<li><a href="s">servlet</a> | </li>
		<li><a href="http://imbi.ld.ttu.ee/tomcat_webapp_logs/r29_subjectid/log.txt" target="_blank">logs</a></li>
	</ul>