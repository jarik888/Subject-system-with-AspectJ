<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/new_subject.js" ></script>
<script type="text/javascript" src="static/js/subject.js" ></script>
<script type="text/javascript" src="static/js/validation.js" ></script>	

<%@ page import="backend.data.Subject_attribute_type"%>
<%@ page import="backend.data.Enterprise"%>
<%@ page import="backend.data.Employee_role_type"%>
<%@ page import="backend.data.Ent_per_relation_type"%>
<%@ page import="java.util.List" %>

<%
	List<Subject_attribute_type> satList = (List<Subject_attribute_type>) request.getAttribute("satList");
	List<Subject_attribute_type> empatList = (List<Subject_attribute_type>) request.getAttribute("empatList");
	
	List<Enterprise> enterprisesList = (List<Enterprise>) request.getAttribute("enterprisesList");
	List<Employee_role_type> employeeRoleTypeList = (List<Employee_role_type>) request.getAttribute("employeeRoleTypeList");
	List<Ent_per_relation_type> employeeRelationTypeList = (List<Ent_per_relation_type>) request.getAttribute("employeeRelationTypeList");
%>
<div class="error" id="submit_error" colspan="2"></div>
<button type="submit" onClick='validateEmployeeForm("form[id=\"emp_form\"] ");' name="submit_button">Save changes</button>
<form id="emp_form" method="post" action="?service=subject&action=save_employee_changes">
<input type="hidden" name="subjectTypeFk" value=1 />
<div class="float-left">
	<input type="hidden" name="person_id" value="<%=request.getAttribute("person_id")%>" />
	<input type="hidden" name="employee_id" value="<%=request.getAttribute("employee_id")%>" />
	<table>
		<tr>
			<th colspan="2" class="main">Edit Employee</th>
		</tr>
		<tr>
			<td colspan="2" class="centered">
			<input type="hidden" name="subject_type_fk" value=3 />
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
			<th>Enterprise</th>
			<td><select id="enterprise" name="enterprise">
					<%
						for (Enterprise enterprise : enterprisesList) {
					%>
					<option value="<%=enterprise.getEnterprise()%>"
					<%if (enterprise.getEnterprise() == (Long) request.getAttribute("enterprise"))
						out.print("selected"); %>>
							<%=enterprise.getName()%>
					</option>
					<%
						}
					%>
			</select></td>
		</tr>
		
		<tr>
			<th>Role</th>
			<td><select id="employee_role_type" name="employee_role_type">
					<%
						for (Employee_role_type ert : employeeRoleTypeList) {
					%>
					<option value="<%=ert.getEmployeeRoleType()%>"
					<%if (ert.getEmployeeRoleType() == (Long) request.getAttribute("employee_role_type_fk"))
						out.print("selected"); %>>
							<%=ert.getTypeName()%>
					</option>
					<%
						}
					%>
			</select></td>
		</tr>
		
		<tr>
			<th>Relation type</th>
			<td><select id="employee_relation_type" name="employee_relation_type">
					<%
					for (Ent_per_relation_type erelt : employeeRelationTypeList) {
					%>
					<option value="<%=erelt.getEntPerRelationType()%>"
					<%if (erelt.getEntPerRelationType() == (Long) request.getAttribute("employee_relation_type_fk"))
						out.print("selected"); %>>
							<%=erelt.getTypeName()%>
					</option>
					<%
						}
					%>
			</select></td>
		</tr>
		
		<tr>
			<td class="error" id="enterprise_error" colspan="2"></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered">
					------ Employee attributes -----</td>
		</tr>
				<%
					for (Subject_attribute_type a : empatList) {
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
<div class="float-right">
	<table>
		<jsp:include page="account_form.jsp" />
	</table>
</div>
</form>