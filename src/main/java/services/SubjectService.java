package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import backend.dao.GenericDAO;
import backend.data.Customer;
import backend.data.Employee;
import backend.data.Employee_role;
import backend.data.Enterprise;
import backend.data.Enterprise_person_relation;
import backend.data.Person;
import backend.data.Subject_attribute;
import backend.data.Subject_attribute_type;

public class SubjectService {
	
	EntityManager manager;
	AddressService addressS;
	ContactService contactS;
	EmployeeService employeeS;
	LoginService loginS;
	SearchService searchS;
	DeleteSubjectService deleteSubjectS;

	public SubjectService(EntityManagerFactory factory) {
		manager = factory.createEntityManager();
		addressS = new AddressService(manager);
		contactS = new ContactService(manager);
		loginS = new LoginService(factory);
		employeeS = new EmployeeService(manager, loginS, this);
		searchS = new SearchService(manager, this, addressS, contactS);
		deleteSubjectS = new DeleteSubjectService(manager, this);
		
	}
	
	public EntityTransaction getTransactionsManager() {
		return manager.getTransaction();
	}	

	public List<Subject_attribute_type> getSubjectAtributeTypes(long subject_type_fk) {
		List<Subject_attribute_type> sat = null;
		try {
			GenericDAO personDAO = new GenericDAO(manager, Subject_attribute_type.class);
			String query = "SELECT sat FROM Subject_attribute_type sat WHERE sat.subject_type_fk = :subject_type_fk";
			String param_name = "subject_type_fk";
			Long param_value = subject_type_fk;
			sat = (List<Subject_attribute_type>) personDAO.findByQuery(query, param_name, param_value);
		} catch (Exception e) {}
		return sat;
	}
	
	public List<Enterprise> getEnterprisesList() {
		try {
		List<Enterprise> eList = manager.createQuery("SELECT eList FROM Enterprise eList").getResultList();
		return eList;
		} catch (Exception e) {}
		return null;
	}
	
	public Date parseDate(String date) throws ParseException {
		String[] formatStrings = {"d.M.y", "d/M/y", "d-M-y"};
		
		for (String formatString : formatStrings) {
//			try {
				return new SimpleDateFormat(formatString).parse(date);
//			} catch (ParseException e) {}
		}
		return null;
	}
	
	public String parseDate(Date date) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(date);
	}
	
	public Long addPersonToDB(HttpServletRequest req) throws ParseException {
		
		Long newPersonId = null;
		
//		EntityTransaction tx = manager.getTransaction();
//		tx.begin();
//		try {
			GenericDAO personDAO = new GenericDAO(manager, Person.class);
			Person p = new Person();			
			p.setFirstName(req.getParameter("first_name"));
			p.setLastName(req.getParameter("last_name"));
			p.setIdentityCode(req.getParameter("identity_code"));
			p.setBirthDate(parseDate(req.getParameter("birthdate")));
			p.setCreatedBy(Long.parseLong((String) req.getAttribute("user_id")));
			p.setUpdatedBy(Long.parseLong((String) req.getAttribute("user_id")));
			p.setCreated(new Date());
			p.setUpdated(new Date());
			personDAO.persist(p);
			newPersonId = p.getPerson();
			
			if (req.getParameter("customer_checkbox") != null) {
				addSubjectAttributes(newPersonId, 4, req); //Customer attributes
				addCustomer(newPersonId, 1); //create customer
			}
			addSubjectAttributes(newPersonId, 1, req); //Person attributes
			
			addressS.addSubjectAddress(newPersonId, 1, req);
			contactS.addSubjectContact(newPersonId, 1, req);
			
//		} catch (Exception e) {
//			tx.rollback();
//		}
//		tx.commit();
		return newPersonId;
	}
	
	public long addEmployeeToDB(HttpServletRequest req) throws ParseException {

		Long employeeId = null;
		Long person_fk = (Long) req.getAttribute("newPersonId");
		
		GenericDAO employeeDAO = new GenericDAO(manager, Employee.class);
		Employee emp = new Employee();			
		emp.setEnterpriseFk(Long.parseLong((String) req.getParameter("enterprise")));
		emp.setPersonFk(person_fk);
		emp.setActive("Y");
		employeeDAO.persist(emp); //create employee
		employeeId = emp.getEmployee();
		
		employeeS.addEmplyeeRole(employeeId, req.getParameter("employee_role_type")); //create employee role
		
		employeeS.addEnterprisePersonRelation(req.getParameter("employee_relation_type"), person_fk,
				Long.parseLong((String) req.getParameter("enterprise")));//create employee enterprise person relation
		
		addSubjectAttributes(person_fk, 3, req); //Employee attributes
		
		employeeS.addAccount(employeeId, req);//create account for employee
		
		return employeeId;
	}
	
	public Long addEnterpriseToDB(HttpServletRequest req) throws ParseException {
		
		Long newEnterpriseId = null;
		
		GenericDAO enterpriseDAO = new GenericDAO(manager, Enterprise.class);
		Enterprise e = new Enterprise();			
		e.setName(req.getParameter("name"));
		e.setFullName(req.getParameter("full_name"));
		e.setCreatedBy(Long.parseLong((String) req.getAttribute("user_id")));
		e.setUpdatedBy(Long.parseLong((String) req.getAttribute("user_id")));
		e.setCreated(new Date());
		e.setUpdated(new Date());
		enterpriseDAO.persist(e);
		newEnterpriseId = e.getEnterprise();
		
		if (req.getParameter("customer_enterprise_checkbox") != null) {
			addSubjectAttributes(newEnterpriseId, 4, req); //Customer attributes
			addCustomer(newEnterpriseId, 2); //create customer
		}
		addSubjectAttributes(newEnterpriseId, 2, req); //Enterprise attributes
		
		addressS.addSubjectAddress(newEnterpriseId, 2, req);
		contactS.addSubjectContact(newEnterpriseId, 2, req);
		
		return newEnterpriseId;
	}
	
	public void addSubjectAttributes(long subjectId, long subject_type_fk, HttpServletRequest req) 
							throws ParseException {
		
		List<Subject_attribute_type> satList =  getSubjectAtributeTypes(subject_type_fk);
		for (int i = 0; i < satList.size(); i++) {
			Subject_attribute_type atr = satList.get(i);
			String atr_name = atr.getTypeName().replaceAll("[ %]", "_");
			
			if (req.getParameter(atr_name) != "") {				
				addSubjectAttribute(subjectId, atr, req.getParameter(atr_name));
			}
		}		
	}
	
	public void addSubjectAttribute(long subjectId, Subject_attribute_type atr, String atrValue) 
							throws ParseException {
		
		GenericDAO saDAO = new GenericDAO(manager, Subject_attribute.class);
		Subject_attribute sa = new Subject_attribute();
		sa.setSubjectFk(subjectId);
		sa.setSubjectTypeFk(atr.getSubjectTypeFk());
		sa.setSubjectAttributeTypeFk(atr.getSubjectAttributeType());
		sa.setOrderby(atr.getOrderby());
		sa.setDataType(atr.getDataType());
		
		if (atr.getDataType() == 1L) { //String
			sa.setValueText(atrValue);
		} else if (atr.getDataType() == 2L) { //Nubmer
			sa.setValueNumber(Long.parseLong(atrValue));
		} else { //Date
			sa.setValueDate(parseDate(atrValue));
		}
		saDAO.persist(sa);
	}
	
	public void addCustomer(long subjectId, long subject_type_fk) {
		GenericDAO customerDAO = new GenericDAO(manager, Customer.class);
		Customer c = new Customer();
		c.setSubjectTypeFk(subject_type_fk);
		c.setSubjectFk(subjectId);
		customerDAO.persist(c);
	}
	
	public HttpServletRequest getEnterpriseData(long id, HttpServletRequest req) {
		
		Enterprise e = getEnterpriseById(id);
		req.setAttribute("enterprise_id", id);
		req.setAttribute("name", e.getName());
		req.setAttribute("full_name", e.getFullName());
		req.setAttribute("eatList", getSubjectAtributeTypes(2));
		req.setAttribute("catList", getSubjectAtributeTypes(4));
		
		List<Subject_attribute> saList = getSubjectAtributes(id);
		List<Subject_attribute_type> satList = getSubjectAtributeTypes(2); //enterprise
		
		if (getCustomerBySubjectId(id, 2) != null) {
			req.setAttribute("customer_checkbox", "on");
			for (Subject_attribute_type sat : getSubjectAtributeTypes(4)) { //customer
				satList.add(sat);
			}
		}
		req = addSubjectAttributesToReq(req, saList, satList);
		req = addressS.addSubjectAddressesToReq(req, addressS.getSubjectAdresses(id, 2)); //enterprise
		req = contactS.addSubjectContactsToReq(req, contactS.getSubjectContacts(id, 2));
		
		return req;
	}
	
	public HttpServletRequest getPersonData(long id, HttpServletRequest req) {
		
		Person p = getPersonById(id);
		req.setAttribute("person_id", id);
		req.setAttribute("first_name", p.getFirstName());
		req.setAttribute("last_name", p.getLastName());
		req.setAttribute("identity_code", p.getIdentityCode());
		req.setAttribute("birthdate", parseDate(p.getBirthDate()));
		req.setAttribute("satList", getSubjectAtributeTypes(1));
		req.setAttribute("catList", getSubjectAtributeTypes(4));
		
		List<Subject_attribute> saList = getSubjectAtributes(id);
		List<Subject_attribute_type> satList = getSubjectAtributeTypes(1); //person
		
		if (getCustomerBySubjectId(id, 1) != null) {
			req.setAttribute("customer_checkbox", "on");
			for (Subject_attribute_type sat : getSubjectAtributeTypes(4)) { //customer
				satList.add(sat);
			}
		}
		req = addSubjectAttributesToReq(req, saList, satList);
		req = addressS.addSubjectAddressesToReq(req, addressS.getSubjectAdresses(id, 1)); //person
		req = contactS.addSubjectContactsToReq(req, contactS.getSubjectContacts(id, 1));
		return req; 
	}
	
	public HttpServletRequest getEmployeeData(long emplyeeId, HttpServletRequest req) {
				
		Employee emp = employeeS.getEmployeeById(emplyeeId);
		Long personId = emp.getPersonFk();
		
		req.setAttribute("employee_id", emplyeeId);
		req.setAttribute("enterprise", emp.getEnterpriseFk());
		
		Employee_role empRole = employeeS.getEmplyeeRoleByEmpId(emplyeeId);
		req.setAttribute("employee_role_type", empRole.getEmployeeRole());
		req.setAttribute("employee_role_type_fk", empRole.getEmployeeRoleTypeFk());
		
		Enterprise_person_relation epr = employeeS.getEnterprisePersonRelationByPersonId(personId);
		req.setAttribute("employee_relation_type", epr.getEnterprisePersonRelation());
		req.setAttribute("employee_relation_type_fk", epr.getEntPerRelationTypeFk());		
		
		Person p = getPersonById(personId);
		req.setAttribute("person_id", personId);
		req.setAttribute("first_name", p.getFirstName());
		req.setAttribute("last_name", p.getLastName());
		req.setAttribute("identity_code", p.getIdentityCode());
		req.setAttribute("birthdate", parseDate(p.getBirthDate()));
		
		req.setAttribute("satList", getSubjectAtributeTypes(1));
		req.setAttribute("empatList", getSubjectAtributeTypes(3));
		req = addEmployeeListsToReq(req);
		
		List<Subject_attribute> saList = getSubjectAtributes(personId);
		List<Subject_attribute_type> satList = getSubjectAtributeTypes(1); //person
		for (Subject_attribute_type sat : getSubjectAtributeTypes(3)) { //employee
			satList.add(sat);
		}
		
		req = addSubjectAttributesToReq(req, saList, satList);
		req = addressS.addSubjectAddressesToReq(req, addressS.getSubjectAdresses(personId, 1)); //person
		req = contactS.addSubjectContactsToReq(req, contactS.getSubjectContacts(personId, 1));
		
		req = employeeS.addEmplyeeAccountToReq(req, emplyeeId);
		return req;
	}
	
	public Person getPersonById(long id) {
		Person p = null;
		try {
			GenericDAO personDAO = new GenericDAO(manager, Person.class);
			p = (Person) personDAO.find(id);
		} catch (Exception e) {}
		return p;
	}
	
	public Enterprise getEnterpriseById(long id) {
		Enterprise e = null;
		try {
			GenericDAO enterpriseDAO = new GenericDAO(manager, Enterprise.class);
			e = (Enterprise) enterpriseDAO.find(id);
		} catch (Exception ex) {}
		return e;
	}
	
	public Customer getCustomerBySubjectId(long subject_fk, long subject_type_fk) {
		Customer c = null;
		try {
			GenericDAO customerDAO = new GenericDAO(manager, Customer.class);
			String query = "SELECT c FROM Customer c WHERE c.subject_fk = :subject_fk AND c.subject_type_fk = :subject_type_fk";
			List<String> paramNamesList = new ArrayList<String>();
			ArrayList<Long> paramValuesList = new ArrayList<Long>();
			paramNamesList.add("subject_fk");
			paramValuesList.add(subject_fk);
			paramNamesList.add("subject_type_fk");
			paramValuesList.add(subject_type_fk);
			c = (Customer) customerDAO.findOneObjectByQueryWithMultipleParams(query, paramNamesList, paramValuesList);
		} catch (Exception e) {}
		return c;
	}
	
	public List<Subject_attribute> getSubjectAtributes(long subject_fk) {
		List<Subject_attribute> sa = null;
		try {
			GenericDAO saDAO = new GenericDAO(manager, Subject_attribute.class);
			String query = "SELECT sa FROM Subject_attribute sa WHERE sa.subject_fk = :subject_fk";
			String param_name = "subject_fk";
			Long param_value = subject_fk;
			sa = (List<Subject_attribute>) saDAO.findByQuery(query, param_name, param_value);
		} catch (Exception e) {}
		return sa;
	}
	
	public Subject_attribute getSubjectAtribute(long id) {
		Subject_attribute sa = null;
		try {
			GenericDAO saDAO = new GenericDAO(manager, Subject_attribute.class);
			sa = (Subject_attribute) saDAO.find(id);
		} catch (Exception e) {}
		return sa;
	}
	
	public HttpServletRequest addSubjectAttributesToReq(HttpServletRequest req,
			List<Subject_attribute> saList, List<Subject_attribute_type> satList) {		
		for (Subject_attribute_type sat : satList) {
			for (Subject_attribute sa : saList) {
				if (sat.getSubjectAttributeType() == sa.getSubjectAttributeTypeFk()) {
					if (sa.getDataType() == 1) req.setAttribute(sat.getTypeName(), sa.getValueText().toString());
					else if (sa.getDataType() == 2)	req.setAttribute(sat.getTypeName(), sa.getValueNumber().toString());
					else if (sa.getDataType() == 3) req.setAttribute(sat.getTypeName(), parseDate(sa.getValueDate()));
					req.setAttribute(sat.getTypeName()+"_subject_attribute_id", sa.getSubjectAttribute()); //subject attribute id
					break;
				} else {
					req.setAttribute(sat.getTypeName(), "");
				}
			}
		}
		if (saList.size() < 1) {
			for (Subject_attribute_type sat : satList) {
				req.setAttribute(sat.getTypeName(), "");
			}
		}
		return req;
	}
	
	public HttpServletRequest addEmployeeListsToReq(HttpServletRequest req) {
		
		req.setAttribute("enterprisesList", getEnterprisesList());
		req.setAttribute("employeeRoleTypeList", employeeS.getEmployeeRoleTypeList());
		req.setAttribute("employeeRelationTypeList", employeeS.getEmpPerRelationType());
		
		return req;
	}

	public Long addChangesInPersonToDB(HttpServletRequest req) throws ParseException {
		
		long personId = Long.parseLong((String) req.getParameter("person_id"));
		Person p = getPersonById(personId);
		p.setFirstName((String) req.getParameter("first_name"));
		p.setLastName((String) req.getParameter("last_name"));
		p.setIdentityCode((String) req.getParameter("identity_code"));
		p.setBirthDate(parseDate((String) req.getParameter("birthdate")));
		p.setUpdatedBy(Long.parseLong((String) req.getAttribute("user_id")));
		p.setUpdated(new Date());
		
		updateSubjectAttributes(personId, req);
		addressS.updateSubjectAddresses(personId, req);
		contactS.updateSubjectContacts(personId, req);
		
		return personId;
	}
	
	public Long addChangesInEmployeeToDB(HttpServletRequest req) throws ParseException {
		
		long personId = Long.parseLong((String) req.getParameter("person_id"));
		long employeeId = Long.parseLong((String) req.getParameter("employee_id"));
		
		Employee emp = new Employee();
		emp = employeeS.getEmployeeById(employeeId);
		emp.setEnterpriseFk(Long.parseLong((String) req.getParameter("enterprise")));
		
		Employee_role empR = employeeS.getEmplyeeRoleByEmpId(employeeId);
		empR.setEmployeeRoleTypeFk(Long.parseLong((String) req.getParameter("employee_role_type")));
		
		Enterprise_person_relation epr = employeeS.getEnterprisePersonRelationByPersonId(personId);
		epr.setEntPerRelationTypeFk(Long.parseLong((String) req.getParameter("employee_relation_type")));
		
		updateSubjectAttributes(personId, req);
		employeeS.updateAccount(employeeId, req);
		
		return employeeId;
	}
	
	public Long addChangesInEnterpriseToDB(HttpServletRequest req) throws ParseException {
		
		long enterpriseId = Long.parseLong((String) req.getParameter("enterprise_id"));
		Enterprise e = getEnterpriseById(enterpriseId);
		e.setName((String) req.getParameter("name"));
		e.setFullName((String) req.getParameter("full_name"));
		e.setUpdatedBy(Long.parseLong((String) req.getAttribute("user_id")));
		e.setUpdated(new Date());
		
		updateSubjectAttributes(enterpriseId, req);
		addressS.updateSubjectAddresses(enterpriseId, req);
		contactS.updateSubjectContacts(enterpriseId, req);
		
		return enterpriseId;
	}
	
	public void updateSubjectAttributes(long subjectId, HttpServletRequest req) throws ParseException {
		
		long subject_type = Long.parseLong((String) req.getParameter("subjectTypeFk")); //person or enterprise
		
		List<Subject_attribute_type> satList = getSubjectAtributeTypes(subject_type);
		if (getCustomerBySubjectId(subjectId, subject_type) != null) {
			for (Subject_attribute_type sat : getSubjectAtributeTypes(4)) { //customer
				satList.add(sat);
			}
		} else if (req.getParameter("employee_id") != null) {
			for (Subject_attribute_type sat : getSubjectAtributeTypes(3)) { //employee
				satList.add(sat);
			}
		}
		
		for(Subject_attribute_type sat : satList) {
			String atr_name = sat.getTypeName().replaceAll("[ %]", "_");
			if (!req.getParameter(atr_name+"_subject_attribute_id").equals("null")) {
				long saId = Long.parseLong(req.getParameter(atr_name+"_subject_attribute_id"));
				Subject_attribute sa = getSubjectAtribute(saId); 
				if (req.getParameter(atr_name) != "") { //edit existing attribute
					if (sat.getDataType() == 1L) { //String
						sa.setValueText(req.getParameter(atr_name));
					} else if (sat.getDataType() == 2L) { //Nubmer
						sa.setValueNumber(Long.parseLong(req.getParameter(atr_name)));
					} else { //Date
						sa.setValueDate(parseDate(req.getParameter(atr_name)));
					}
				} else { //delete existing attribute
					manager.remove(sa);
				}
			} else if (req.getParameter(atr_name) != "") { //create new attribute
				addSubjectAttribute(subjectId, sat, req.getParameter(atr_name)); 
			}
		}
		
	}

	public HttpServletRequest deleteAddtessFromDB(HttpServletRequest req) {
		if (addressS.deleteAddress(Long.parseLong((String) req.getParameter("addressId")))) {
			req.setAttribute("answer", true);
		}
		return req;
	}
	
	public HttpServletRequest deleteContactFromDB(HttpServletRequest req) {
		if (contactS.deleteContact(Long.parseLong((String) req.getParameter("contactId")))) {
			req.setAttribute("answer", true);
		}
		return req;
	}
	
	public HttpServletRequest deleteSubjectFromDB(HttpServletRequest req) {
		
		Long subjectId = Long.parseLong(req.getParameter("subjectId"));
		Long subjectType = Long.parseLong(req.getParameter("subjectType"));

		req.setAttribute("answer", deleteSubjectS.deleteSubject(subjectId, subjectType));		
		
		return req;
	}

	public HttpServletRequest checkUsername(HttpServletRequest req) {
		if (employeeS.usernameExists(req.getParameter("uname"))) {
			req.setAttribute("answer", true);
		}
		return req;
	}

	public HttpServletRequest searchSubject(HttpServletRequest req) {
		req.setAttribute("answer", searchS.search(req));
		return req;
	}

}
