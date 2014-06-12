<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<button type="submit" onClick='validateEmployeeForm("form[id=\"emp_form\"] ");' name="submit_button">Submit</button>
<form id="emp_form" method="post" action="?service=subject&action=add_employee">
	<div class="float-left">
	<table>
		<tr>
			<th colspan="2" class="main">New employee</th>
		</tr>
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="subject_type_fk"
				value="1" />---------- Person data ---------</td>
		</tr>
		
		<tr>
			<th>First name</th>
			<td><input type="text" id="first_name" name="first_name"/></td>
		</tr>
		<tr>
			<td class="error" id="first_name_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Last name</th>
			<td><input type="text" id="last_name" name="last_name"/></td>
		</tr>
		<tr>
			<td class="error" id="last_name_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Identity code</th>
			<td><input type="text" id="identity_code" name="identity_code"/></td>
		</tr>
		<tr>
			<td class="error" id="identity_code_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Date of birth</th>
			<td><input type="text" id="birthdate" name="birthdate"/></td>
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
			<td><input type="text" id="<%=atr_name%>" name="<%=atr_name%>"/>
				<input type="hidden" id="<%=atr_name%>_id" name="<%=atr_name%>_id" 
				value="<%=a.getSubjectAttributeType()%>"/>	
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
				--------------------------------</td>
		</tr>
		<tr>
			<th>Enterprise</th>
			<td><select id="enterprise" name="enterprise">
					<option value="empty">...</option>
					<%
						for (Enterprise enterprise : enterprisesList) {
					%>
					<option value="<%=enterprise.getEnterprise()%>">
							<%=enterprise.getName()%>
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
			<th>Role</th>
			<td>
				<select id="employee_role_type" name="employee_role_type">
						<option value="empty">...</option>
						<%
							for (Employee_role_type ert : employeeRoleTypeList) {
						%>
						<option value="<%=ert.getEmployeeRoleType()%>">
							<%=ert.getTypeName()%>
						</option>
						<%
							}
						%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="error" id="employee_role_type_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Relation type</th>
			<td>
				<select id="employee_relation_type" name="employee_relation_type">
						<option value="empty">...</option>
						<%
							for (Ent_per_relation_type erelt : employeeRelationTypeList) {

						%>
						<option value="<%=erelt.getEntPerRelationType()%>">
							<%=erelt.getTypeName()%>
						</option>
						<%
							}
						%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="error" id="employee_relation_type_error"  colspan="2"></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered">
				------- Employee attributes ------</td>
		</tr>
			<%
					for (Subject_attribute_type a : empatList) {
						String atr_name = a.getTypeName().replaceAll("[ %]", "_");
						//System.out.println(atr_name);
				%>
			<tr>
				<th><%=a.getTypeName()%></th>
				<td><input type="text" id="<%=atr_name%>" name="<%=atr_name%>"/>
					<input type="hidden" id="<%=atr_name%>_id" name="<%=atr_name%>_id" 
					value="<%=a.getSubjectAttributeType()%>"/>	
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