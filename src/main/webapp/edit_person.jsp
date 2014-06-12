<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/new_subject.js" ></script>
<script type="text/javascript" src="static/js/subject.js" ></script>
<script type="text/javascript" src="static/js/validation.js" ></script>	

<%@ page import="backend.data.Subject_attribute_type"%>
<%@ page import="java.util.List" %>

<%
	List<Subject_attribute_type> satList = (List<Subject_attribute_type>) request.getAttribute("satList");
	List<Subject_attribute_type> catList = (List<Subject_attribute_type>) request.getAttribute("catList");
%>
<div class="error" id="submit_error" colspan="2"></div>
<button type="submit" onClick='validatePersonForm("form[id=\"p_form\"] ");' name="submit_button">Save changes</button>
<form id="p_form" method="post" action="?service=subject&action=save_person_changes">
<input type="hidden" name="subjectTypeFk" value=1 />
<div class="float-left">
	<input type="hidden" name="person_id" value="<%=request.getAttribute("person_id")%>" />
	<table>
		<tr>
			<th colspan="2" class="main">Edit person</th>
		</tr>
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="subject_type_fk"
				value="<%if (request.getAttribute("customer_checkbox") != null) out.print("4");
						 else out.print("1");%>" />
				---------- Person data ---------</td>
		</tr>
		
		<tr>
			<th>First name</th>
			<td><input type="text" id="first_name" name="first_name" 
					value="<%=request.getAttribute("first_name")%>" /></td>
		</tr>
		<tr>
			<td class="error" id="first_name_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Last name</th>
			<td><input type="text" id="last_name" name="last_name" 
					value="<%=request.getAttribute("last_name")%>" /></td>
		</tr>
		<tr>
			<td class="error" id="last_name_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Identity code</th>
			<td><input type="text" id="identity_code" name="identity_code" 
					value="<%=request.getAttribute("identity_code")%>" /></td>
		</tr>
		<tr>
			<td class="error" id="identity_code_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Date of birth</th>
			<td><input type="text" id="birthdate" name="birthdate" 
					value="<%=request.getAttribute("birthdate")%>" /></td>
		</tr>
		<tr>
			<td class="error" id="birthdate_error" colspan="2"></td>
		</tr>

		<tr>
			<td colspan="2" class="centered">
				------- Person attributes ------</td>
		</tr>
		<%
			for (Subject_attribute_type a : satList) {
				String atr_name = a.getTypeName().replace(' ', '_');
		%>
		<tr>
			<th><%=a.getTypeName()%></th>
			<td><input type="text" id="<%=atr_name%>" name="<%=atr_name%>" 
				 value="<%=request.getAttribute(a.getTypeName())%>" />
				<input type="hidden" id="<%=atr_name%>_id" name="<%=atr_name%>_id" 
				value="<%=a.getSubjectAttributeType()%>"/>
				<input type="hidden" id="<%=atr_name%>_subject_attribute_id" 
					name="<%=atr_name%>_subject_attribute_id" 
					value="<%=request.getAttribute(a.getTypeName()+"_subject_attribute_id")%>"/>
			</td>
		</tr>
		<tr>
			<td class="error" id="<%=atr_name%>_error" colspan="2"></td>
		</tr>
		<%
			}			
		%>
		<tr>
			<td colspan="2" class="centered">
			----------------------------------</td>
		</tr>

		<tr>
			<th>Customer ?</th>
			<td><input type="checkbox" id="customer" name="customer_checkbox"
			disabled <%if (request.getAttribute("customer_checkbox") != null) out.println("checked");%>/>
			</td>
		</tr>
		  <tfoot id="customer_attribute"
		  		<%if (request.getAttribute("customer_checkbox") == null) 
		  			out.println("style='display:none';");%>>
			<tr>
				<td colspan="2" class="centered">
					------ Customer attributes -----</td>
			</tr>
				<%
					for (Subject_attribute_type a : catList) {
						String atr_name = a.getTypeName().replaceAll("[ %]", "_");
				%>
			<tr>
				<th><%=a.getTypeName()%></th>
				<td><input type="text" id="<%=atr_name%>" name="<%=atr_name%>"
					 value="<%=request.getAttribute(a.getTypeName())%>" />
					<input type="hidden" id="<%=atr_name%>_id" name="<%=atr_name%>_id" 
					value="<%=a.getSubjectAttributeType()%>"/>
					<input type="hidden" id="<%=atr_name%>_subject_attribute_id" 
					name="<%=atr_name%>_subject_attribute_id" 
					value="<%=request.getAttribute(a.getTypeName()+"_subject_attribute_id")%>"/>
				</td>				
			</tr>
			<tr>
				<td class="error" id="<%=atr_name%>_error" colspan="2"></td>
			</tr>
			<%
				}			
			%>
		  </tfoot>
		
	</table>
</div>
<div class="float-left">
	<table>
		<tr>
			<th colspan="2" class="main">Addresses</th>
		</tr>
		<tr>
			<td colspan="2" class="centered">
				<input type="hidden" name="address_type_fk" value="1" />
				---------- Main address ---------
			</td>
		</tr>
			
			<jsp:include page="address_form.jsp" />
	</table>
</div>
<div class="float-left">
	<table>
	
		<jsp:include page="contact_form.jsp" />
		
	</table>
</div>
</form>