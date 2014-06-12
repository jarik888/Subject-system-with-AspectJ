<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="backend.data.Subject_attribute_type"%>
<%@ page import="java.util.List" %>

<%
	List<Subject_attribute_type> eatList = (List<Subject_attribute_type>) request.getAttribute("eatList");
	List<Subject_attribute_type> catList = (List<Subject_attribute_type>) request.getAttribute("catList");
%>
<div class="error" id="submit_error" colspan="2"></div>
<button type="submit" onClick='validateEnterpriseForm("form[id=\"e_form\"] ");' name="submit_button">Submit</button>
<form id="e_form" method="post" action="?service=subject&action=add_enterprise">
	<input type="hidden" name="subject_id" />
	<div class="float-left">
	<table id="e_table">
		<tr>
			<th colspan="2" class="main">New enterprise</th>
		</tr>
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="subject_type_fk"
				value="2" />---------- Enterprise data ---------</td>
		</tr>
		<tr>
			<th>Name</th>
			<td><input type="text" id="name" name="name"/></td>
		</tr>
		<tr>
			<td class="error" id="name_error" colspan="2"></td>
		</tr>
		<tr>
			<th>Full name</th>
			<td><input type="text" id="full_name" name="full_name" /></td>
		</tr>
		<tr>
			<td class="error" id="full_name_error" colspan="2"></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered">
				------- Enterprise attributes ------</td>
		</tr>
		<%
			for (Subject_attribute_type a : eatList) {
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
			----------------------------------</td>
		</tr>

		<tr>
			<th>Customer ?</th>
			<td><input type="checkbox" id="customer_enterprise" name="customer_enterprise_checkbox"/>
			</td>
		</tr>
		  <tfoot id="customer_enterprise_attribute" style="display:none;">
			<tr>
				<td colspan="2" class="centered">
					------ Customer attributes -----</td>
			</tr>
				<%
					for (Subject_attribute_type a : catList) {
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
		  </tfoot>
	</table>

</div>
<div class="float-left">
	<table>
		<tr>
			<th colspan="2" class="main">Address</th>
		</tr>
		<tr>
			<td colspan="2" class="centered">
				<input type="hidden" name="address_type_fk" value="3" />
				---------- Legal address ---------
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