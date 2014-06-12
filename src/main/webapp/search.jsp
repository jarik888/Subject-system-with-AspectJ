<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/validation.js"></script>
<script type="text/javascript" src="static/js/search.js"></script>


<button onClick='searchSubject();' name="submit_button">Search</button>
<form id="s_form">
	<div class="float-left">
		<table>
			<tr>
				<th colspan="2" class="main">Subject data</th>
			</tr>
			<tr>
				<th>Subject type</th>
				<td>
					<select name="subject_type" id="subject_type">
							<option value=1>Person</option>
							<option value=2>Enterprise</option>
							<option value=3>Employee</option>
							<option value=4>Customer</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>First name</th>
				<td><input type="text" name="fname" id="fname" /></td>
			</tr>
			<tr>
				<td class="error" id="fname_error" colspan="2"></td>
			</tr>
			<tr>
				<th>Name</th>
				<td><input type="text" name="lname" id="lname" /></td>
			</tr>
			<tr>
				<td class="error" id="lname_error" colspan="2"></td>
			</tr>
						
		<jsp:include page="search_attributes.jsp" />
		</table>
		
		
		
	</div>
	
	<div class="float-left">
		<table>
		
			<tr>
				<th colspan="2" class="main">Address</th>
			</tr>

			<jsp:include page="address_form.jsp" />

			<jsp:include page="contact_form.jsp" />
		
		</table>
	</div>	
</form>
<div class="float-left" style="padding-left:5em;">
	&nbsp;
</div>

<div class="float-right">
	<table>
		
		<tr>
			<th colspan="2" class="main">Search results</th>
		</tr>
		<tr><th>&nbsp;</th></tr>
		<tbody id="answer"><tr><td class="centered">...</td></tr></tbody>
	</table>
</div>	

</body>
</html>