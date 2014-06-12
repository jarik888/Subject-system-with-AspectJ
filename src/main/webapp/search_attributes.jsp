<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="backend.data.Subject_attribute_type"%>
<%@ page import="java.util.List" %>

<%
	List<Subject_attribute_type> satList = (List<Subject_attribute_type>) request.getAttribute("satList");//person
	List<Subject_attribute_type> eatList = (List<Subject_attribute_type>) request.getAttribute("eatList");//enterprise
	List<Subject_attribute_type> empatList = (List<Subject_attribute_type>) request.getAttribute("empatList");//employee
	List<Subject_attribute_type> catList = (List<Subject_attribute_type>) request.getAttribute("catList");//customer
%>

<tfoot id="person_attributes_table">
	<tr><td colspan="2" class="centered">---------- Person attributes ----------</td></tr>
	
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
	
</tfoot>

<tfoot id="enterprise_attributes_table" style="display: none">
	<tr><td colspan="2" class="centered">---------- Enterprise ----------</td></tr>
	
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
	
</tfoot>

<tfoot id="employee_attributes_table" style="display: none">
	<tr><td colspan="2" class="centered">------- Employee attributes ------</td></tr>
	
		<%
			for (Subject_attribute_type a : empatList) {
						String atr_name = a.getTypeName().replaceAll("[ %]", "_");
						
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

<tfoot id="customer_attributes_table" style="display: none">
	<tr><td colspan="2" class="centered">------- Customer attributes ------</td></tr>
	
		<%
			for (Subject_attribute_type a : catList) {				
				String atr_name = a.getTypeName().replaceAll("[ %]", "_");
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
