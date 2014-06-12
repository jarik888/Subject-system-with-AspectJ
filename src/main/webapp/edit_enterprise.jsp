<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/new_subject.js" ></script>
<script type="text/javascript" src="static/js/subject.js" ></script>
<script type="text/javascript" src="static/js/validation.js" ></script>	
	
<%@ page import="backend.data.Subject_attribute_type"%>
<%@ page import="java.util.List" %>

<%
	List<Subject_attribute_type> eatList = (List<Subject_attribute_type>) request.getAttribute("eatList");
	List<Subject_attribute_type> catList = (List<Subject_attribute_type>) request.getAttribute("catList");
%>
<div class="error" id="submit_error" colspan="2"></div>
<button type="submit" onClick='validateEnterpriseForm("form[id=\"e_form\"] ");' name="submit_button">Save changes</button>
<form id="e_form" method="post" action="?service=subject&action=save_enterprise_changes">
<input type="hidden" name="subjectTypeFk" value=2 />
<div class="float-left">
	<input type="hidden" name="enterprise_id" value="<%=request.getAttribute("enterprise_id")%>" />
	<table>
		<tr>
			<th colspan="2" class="main">Edit enterprise</th>
		</tr>
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="subject_type_fk"
				value="<%if (request.getAttribute("customer_checkbox") != null) out.print("4");
						 else out.print("2");%>" />
				---------- Enterprise data ---------</td>
		</tr>
		
		<tr>
			<th>Name</th>
			<td><input type="text" id="name" name="name"
				value="<%=request.getAttribute("name")%>" /></td>
		</tr>
		<tr>
			<td class="error" id="name_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Full name</th>
			<td><input type="text" id="full_name" name="full_name"
				value="<%=request.getAttribute("full_name")%>" /></td>
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
			<%
			String customer = "";
			if (request.getAttribute("customer_checkbox") != null
		   		&& request.getAttribute("customer_checkbox").equals("on")) {
				customer = "checked";
			}
			%>
			<th>Customer ?</th>
			<!-- !!! id="customer_enterprise" name="customer_enterprise_checkbox" !!!!  -->
			<td><input type="checkbox" id="customer" name="customer_checkbox"
			disabled <%=customer%>/>
			</td>
		</tr>
		  <tfoot id="customer_attribute"
		  		<%if (customer == "") out.println("style='display:none';");%>>
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