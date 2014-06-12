<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<tr>
	<th colspan="2" class="main">Account
		<input type="hidden" name="account_id" id="account_id"
			value="<%=request.getAttribute("account_id") != null ? request.getAttribute("account_id") : ""%>" />
	</th>
</tr>
<tr>
	<th>Username</th>
	<td><input type="text" name="username" id="username"
		value="<%=request.getAttribute("username") != null ? request.getAttribute("username") : ""%>" 
		<%if (request.getAttribute("username") != null) out.print("disabled");%>/>
	</td>
</tr>
<tr>
	<td class="error" id="username_error" colspan="2"></td>
</tr>
<tr>
	<th>Password</th>
	<td><input type="password" name="password" id="password" /></td>
</tr>
<tr>
	<td class="error" id="password_error" colspan="2"></td>
</tr>

<tr>
	<th>Valid from</th>
	<td><input type="text" name="valid_from" id="valid_from"
		value="<%=request.getAttribute("valid_from") != null ? request.getAttribute("valid_from") : ""%>" />
	</td>
<tr>
	<td class="error" id="valid_from_error" colspan="2"></td>
</tr>
<tr>
	<th>Valid to</th>
	<td><input type="text" name="valid_to" id="valid_to"
		value="<%=request.getAttribute("valid_to") != null ? request.getAttribute("valid_to") : ""%>" />
	</td>
<tr>
	<td class="error" id="valid_to_error" colspan="2"></td>
</tr>
	<%
		String selectedY = "", selectedN = "";
		Object pne = request.getAttribute("password_never_expires") != null ? request.getAttribute("password_never_expires") : "";
		if (!pne.equals("N")) selectedY = "selected";
		else selectedN = "selected";
	%>
<tr>
	<th>Password never expired</th>
	<td>
		<select name="password_never_expires" id="password_never_expires">
			<option value="Y" <%=selectedY%>>Yes</option>
			<option value="N" <%=selectedN%>>No</option>
		</select>
	</td>
</tr>