<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="backend.data.Address"%>
<%@ page import="java.util.List" %>
<%
	List<Address> sAddrList = (List<Address>) request.getAttribute("sAddrList");
%>

		<input type="hidden" id="address_id" name="address_id"
		value="<%=request.getAttribute("address_id") != null ? request.getAttribute("address_id") : ""%>" />
		<tr>
			<th>Country</th>
			<td><input type="text" id="country" name="country"
			value="<%=request.getAttribute("country") != null ? request.getAttribute("country") : ""%>"/></td>
		</tr>
		<tr>
			<td class="error" id="country_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>County</th>
			<td><input type="text" id="county" name="county"
			value="<%=request.getAttribute("county") != null ? request.getAttribute("county") : ""%>"/></td>
		</tr>
		<tr>
			<td class="error" id="county_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Town/village</th>
			<td><input type="text" id="town_village" name="town_village"
			value="<%=request.getAttribute("town_village") != null ? request.getAttribute("town_village") : ""%>"/></td>
		</tr>
		<tr>
			<td class="error" id="town_village_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>Street address</th>
			<td><input type="text" id="street_address" name="street_address"
			value="<%=request.getAttribute("street_address") != null ? request.getAttribute("street_address") : ""%>"/></td>
		</tr>
		<tr>
			<td class="error" id="street_address_error" colspan="2"></td>
		</tr>
		
		<tr>
			<th>ZipCode</th>
			<td><input type="text" id="zipcode" name="zipcode"
			value="<%=request.getAttribute("zipcode") != null ? request.getAttribute("zipcode") : ""%>"/></td>
		</tr>
		<tr>
			<td class="error" id="zipcode_error" colspan="2"></td>
		</tr>
		<%
		if (request.getAttribute("person_id") != null || 
			request.getAttribute("enterprise_id") != null) {
			for(int i = 0; i < sAddrList.size(); i++) {
				Address a = sAddrList.get(i);
				int id = i + 1;
		%>
			<input type="hidden" id="address_id<%=id%>" name="address_id<%=id%>"
			value="<%=a.getAddress()%>" />
			<tr>
			<td colspan="2" class="centered">
				<input type="hidden" name="address_type_fk" value="2" />
				---------- Additional address #<%=id%> ---------
			</td>
			</tr>
			<tr>
				<th>Country</th>
				<td>
				<input type="text" id="country<%=id%>" name="country<%=id%>"
					value="<%=a.getCountry()%>"/></td>
			</tr>
			<tr>
				<td class="error" id="country<%=id%>_error" colspan="2"></td>
			</tr>
			
			<tr>
				<th>County</th>
				<td><input type="text" id="county<%=id%>" name="county<%=id%>"
					value="<%=a.getCounty()%>"/></td>
			</tr>
			<tr>
				<td class="error" id="county<%=id%>_error" colspan="2"></td>
			</tr>
			
			<tr>
				<th>Town/village</th>
				<td><input type="text" id="town_village<%=id%>" name="town_village<%=id%>"
					value="<%=a.getTownVillage()%>"/></td>
			</tr>
			<tr>
				<td class="error" id="town_village<%=id%>_error" colspan="2"></td>
			</tr>
			
			<tr>
				<th>Street address</th>
				<td><input type="text" id="street_address<%=id%>" name="street_address<%=id%>"
					value="<%=a.getStreetAddress()%>"/></td>
			</tr>
			<tr>
				<td class="error" id="street_address<%=id%>_error" colspan="2"></td>
			</tr>
			
			<tr>
				<th>ZipCode</th>
				<td><input type="text" id="zipcode<%=id%>" name="zipcode<%=id%>"
					value="<%=a.getZipcode()%>"/></td>
			</tr>
			<tr>
				<td class="error" id="zipcode<%=id%>_error" colspan="2"></td>
			</tr>
			
			<tr>
				<th>&nbsp;</th>
				<td><a href="#" name="deleteAddress" id="<%=a.getAddress()%>">Delete</a>
				</td>
			</tr>			
		<%		
			}
		%>
		<tr>
			<td colspan="2" class="right">
				<input type="hidden" name="addr_counter" value=<%=sAddrList.size() - 1%>/> 
				<a href="#" class="additional_address">Add new address</a></td>
		</tr>
		<%
		}
		%>