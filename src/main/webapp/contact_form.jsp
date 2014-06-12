<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="backend.data.Contact"%>
<%@ page import="java.util.List" %>
<%
	List<Contact> sContList = (List<Contact>) request.getAttribute("sContList");
%>

<tr>
	<th colspan="2" class="main">Contact</th>
</tr>
<tr>
	<td colspan="2" class="centered">
		<input type="hidden" name="contact_id"
		value="<%=request.getAttribute("contact_id") != null ? request.getAttribute("contact_id") : ""%>" />
		<% if (!request.getParameter("action").equals("search")) { %>
				--------------- #1 --------------
		<% } %>
		<input type="hidden" name="contact_orderby" value=1 /></td>
</tr>
<tr>
	<th>Type</th>
	<td>
		<%
			String email = "";
			String phone = "";
			if(request.getAttribute("contact_type") != null 
					&& (Long) request.getAttribute("contact_type") == 2 ) phone = "selected";
			else email = "selected";
		%>
		
		<select id="contact_type" name="contact_type">
			<option value=1 <%=email%>>Email</option>
			<option value=2 <%=phone%>>Phone number</option>
		</select>
	</td>
</tr>
<tr>
	<th>Contact</th>
	<td>
		<input type="text" id="contact" name="contact"
		value="<%=request.getAttribute("contact") != null ? request.getAttribute("contact") : ""%>" /></td>
</tr>
<tr>
	<td class="error" id="contact_error" colspan="2"></td>
</tr>
<tr>
	<th>Note</th>
	<td>
		<input type="text" id="note" name="note"
		value="<%=request.getAttribute("note") != null ? request.getAttribute("note") : ""%>" /></td>
</tr>
	<%
		if (request.getAttribute("person_id") != null || 
			request.getAttribute("enterprise_id") != null) {
			for(int i = 0; i < sContList.size(); i++) {
				Contact c = sContList.get(i);
				int id = i + 1;
	%>
	<tr>
	<td colspan="2" class="centered">
		<input type="hidden" name="contact_id<%=id%>" id="contact_id<%=id%>"
		value="<%=c.getContact()%>" />
		<input type="hidden" name="contact_orderby<%=id%>" value=1 />
		--------------- #<%=id + 1%> --------------</td>
	</tr>
	<tr>
		<th>Type</th>
		<td>
			<%
				String Email = "", Phone = "";
				if(c.getContactTypeFk() == 2) Phone = "selected";
				else Email = "selected";
			%>
			
			<select id="contact_type<%=id%>" name="contact_type<%=id%>">
				<option value=1 <%=Email%>>Email</option>
				<option value=2 <%=Phone%>>Phone number</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>Contact</th>
		<td>
			<input type="text" id="contact<%=id%>" name="contact<%=id%>"
			value="<%=c.getValueText()%>" /></td>
	</tr>
	<tr>
		<td class="error" id="contact<%=id%>_error" colspan="2"></td>
	</tr>
	<tr>
		<th>Note</th>
		<td>
			<input type="text" id="note<%=id%>" name="note<%=id%>"
			value="<%=c.getNote() != null ? c.getNote() : ""%>" /></td>
	</tr>


	<tr>
		<th>&nbsp;</th>
		<td><a href="#" name="deleteContact" id="<%=c.getContact()%>">Delete</a>
		</td>
	</tr>
	<%		
			}
	%>
<tr>
	<td colspan="2" class="right"><input type="hidden"
		name="cont_counter" value=<%=sContList.size()%> /> <a href="#"
		class="additional_contact">Add new contact</a></td>
</tr>
 
 	<%
		}
	%>