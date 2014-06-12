package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import backend.data.Customer;
import backend.data.Employee;
import backend.data.Enterprise;
import backend.data.Person;

public class SearchService {

	EntityManager manager;
	SubjectService subjectS;

	public SearchService(EntityManager em, SubjectService ss, AddressService as, ContactService cs) {
		manager = em;
		subjectS = ss;
	}

	public String search(HttpServletRequest req) {
		
		Long subjectType = Long.parseLong(req.getParameter("subject_type"));
		
		String answer = "";
		if (subjectType == 1) answer = findAllPersons(req);
		else if (subjectType == 2) answer = findAllEnterprises(req);
		else if (subjectType == 3) answer = findAllEmployees(req);
		else if (subjectType == 4) answer = findAllCustomers(req);
		
		if (answer != "") {
			return answer;
		} else {
			return "<tr><td class='centered' >Nothing found</td></tr>";
		}
		
		
	}

	private String findAllPersons(HttpServletRequest req) {
		
		List<Person> pList = getPersonsList();
		List<Long> pIdList = new ArrayList<Long>();
		List<String> pNameList = new ArrayList<String>();
		for (Person p : pList) {

			if (!checkFirstAndLastName(p.getFirstName(), p.getLastName(), req)) continue;
			//check attributes
			
			//check addresses
			//check contacts
			pIdList.add(p.getPerson());
			pNameList.add(p.getFirstName() + " " + p.getLastName());
		}
			
		return generateHTML(pIdList, pNameList, "person");
	}
	
	private String findAllEnterprises(HttpServletRequest req) {
		
		List<Enterprise> eList = getEnterprisesList();
		List<Long> eIdList = new ArrayList<Long>();
		List<String> eNameList = new ArrayList<String>();
		for (Enterprise e : eList) {
			//check name
			String name = req.getParameter("lname").toLowerCase();
			if (name != "" && !e.getName().toLowerCase().contains(name)) continue;
			//check attributes
			
			//check addresses
			//check contacts
			eIdList.add(e.getEnterprise());
			eNameList.add(e.getName());
		}
			
		return generateHTML(eIdList, eNameList, "enterprise");
	}
	
	private String findAllEmployees(HttpServletRequest req) {
		
		List<Employee> empList = getEmployeeList();
		List<Long> empIdList = new ArrayList<Long>();
		List<String> empNameList = new ArrayList<String>();
		
		for (Employee emp : empList) {
			
			Person p = subjectS.getPersonById(emp.getPersonFk());
			
			if (!checkFirstAndLastName(p.getFirstName(), p.getLastName(), req)) continue;
			//check attributes
			
			//check addresses
			//check contacts
			
			empIdList.add(emp.getEmployee());
			empNameList.add(p.getFirstName() + " " + p.getLastName());
		}
		
		return generateHTML(empIdList, empNameList, "employee");
	}
	
	private String findAllCustomers(HttpServletRequest req) {
		
		List<Customer> cList = getCustomersList();
		
		List<Long> pIdList = new ArrayList<Long>(), eIdList = new ArrayList<Long>();
		List<String> pNameList = new ArrayList<String>(), eNameList = new ArrayList<String>();
		
		for (Customer c : cList) {

			if (c.getSubjectTypeFk() == 1) {
				
				Person p = subjectS.getPersonById(c.getSubjectFk());
				
				if (!checkFirstAndLastName(p.getFirstName(), p.getLastName(), req)) continue;
				//check attributes
				
				//check addresses
				//check contacts
				
				pIdList.add(p.getPerson());
				pNameList.add(p.getFirstName() + " " + p.getLastName());
				
			} else if (c.getSubjectTypeFk() == 2) {
				
				Enterprise e = subjectS.getEnterpriseById(c.getSubjectFk());
				
				String name = req.getParameter("lname").toLowerCase();
				if (name != "" && !e.getName().toLowerCase().contains(name)) continue;
				//check attributes
				
				//check addresses
				//check contacts
				eIdList.add(e.getEnterprise());
				eNameList.add(e.getName());
			}
			
			
		}
		
		return generateHTML(pIdList, pNameList, "person") + generateHTML(eIdList, eNameList, "enterprise");
	}

//-----------------------------------------GET-SUBJECTS-------------------------------------------
	private List<Person> getPersonsList() {
		try {
			String sql = "SELECT * FROM Person AS pList "
					   + "WHERE pList.person NOT IN(SELECT person_fk FROM Employee "
					   + "UNION SELECT subject_fk FROM Customer WHERE subject_type_fk = 1)";
			Query query = manager.createNativeQuery(sql, Person.class);
			List<Person> pList = (List<Person>) query.getResultList();
		return pList;
		} catch (Exception e) {}
		return null;
	}
	
	private List<Enterprise> getEnterprisesList() {
		try {
			String sql = "SELECT * FROM Enterprise AS eList "
					   + "WHERE eList.enterprise NOT IN"
					   + "(SELECT subject_fk FROM Customer WHERE subject_type_fk = 2)";
			Query query = manager.createNativeQuery(sql, Enterprise.class);
			List<Enterprise> eList = (List<Enterprise>) query.getResultList();
		return eList;
		} catch (Exception e) {}
		return null;
	}	

	private List<Employee> getEmployeeList() {
		try {
		List<Employee> empList = manager.createQuery("SELECT emp FROM Employee emp").getResultList();
		return empList;
		} catch (Exception e) {}
		return null;
	}
	
	private List<Customer> getCustomersList() {
		try {
		List<Customer> cList = manager.createQuery("SELECT c FROM Customer c").getResultList();
		return cList;
		} catch (Exception e) {}
		return null;
	}
//-----------------------------------------CHECK--------------------------------------------	
	private boolean checkFirstAndLastName(String f, String l, HttpServletRequest req) {
		
		boolean result = true;		
		String fname = req.getParameter("fname").toLowerCase();
		String lname = req.getParameter("lname").toLowerCase();
		if (fname != "" && !f.toLowerCase().contains(fname.toLowerCase())) result = false;
		if (lname != "" && !l.toLowerCase().contains(lname.toLowerCase())) result = false;
		return result;
	}
	
	
	
//-----------------------------------------HTML--------------------------------------------	
	private String generateHTML(List<Long> sIdList, List<String> name, String subject) {
		
		String HTML = "";
		
		if (sIdList.size() > 0) {
			for (int i = 0; i < sIdList.size(); i++) {
				HTML += "<tr><td>"
					 +	"<a href=\"s?service=subject&action=edit_" + subject + "&id=" + sIdList.get(i) + "\">"
					 +	name.get(i) + "</a></td>"
					 + "<td>&nbsp; <button name=\"deleteSubject\" id=\"" + sIdList.get(i) + "\">Delete subject</button></td>"
					 + "</td><td></td></tr>";
			}			
		}
		
		return HTML;
	}

}

