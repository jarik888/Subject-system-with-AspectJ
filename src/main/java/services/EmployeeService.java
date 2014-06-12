package services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import backend.dao.GenericDAO;
import backend.data.Customer;
import backend.data.Employee;
import backend.data.Employee_role;
import backend.data.Employee_role_type;
import backend.data.Ent_per_relation_type;
import backend.data.Enterprise_person_relation;
import backend.data.Person;
import backend.data.User_account;

public class EmployeeService {
	
	EntityManager manager;
	SubjectService subjectS;
	LoginService loginS;

	public EmployeeService(EntityManager em, LoginService ls, SubjectService ss) {
		manager = em;
		loginS = ls;
		subjectS = ss;
	}
	
	public Employee getEmployeeById(long emplyeeId) {
		Employee emp = null;
		try {
			GenericDAO empDAO = new GenericDAO(manager, Employee.class);
			emp = (Employee) empDAO.find(emplyeeId);
		} catch (Exception e) {}
		return emp;
	}
	
	public List<Employee_role_type> getEmployeeRoleTypeList() {
		try {
		List<Employee_role_type> ertList = manager.createQuery("SELECT ertList FROM Employee_role_type ertList").getResultList();
		return ertList;
		} catch (Exception e) {}
		return null;
	}
	
	public Employee_role getEmplyeeRoleByEmpId(long employee_fk) {
		
		Employee_role empR = null;
		try {
			GenericDAO empRoleDAO = new GenericDAO(manager, Employee_role.class);
			String query = "SELECT empR FROM Employee_role empR WHERE empR.employee_fk = :employee_fk";
			String param_name = "employee_fk";
			Long param_value = employee_fk;
			empR = (Employee_role) empRoleDAO.findOneObjectByQuery(query, param_name, param_value);
		} catch (Exception e) {}
		return empR;
	}
	
	public Enterprise_person_relation getEnterprisePersonRelationByPersonId(Long person_fk) {
		Enterprise_person_relation epr = null;
		try {
			GenericDAO empRoleDAO = new GenericDAO(manager, Enterprise_person_relation.class);
			String query = "SELECT epr FROM Enterprise_person_relation epr WHERE epr.person_fk = :person_fk";
			String param_name = "person_fk";
			Long param_value = person_fk;
			epr = (Enterprise_person_relation) empRoleDAO.findOneObjectByQuery(query, param_name, param_value);
		} catch (Exception e) {}
		return epr;
	}
	
	public List<Ent_per_relation_type> getEmpPerRelationType() {
		try {
		List<Ent_per_relation_type> eprtList = manager.createQuery("SELECT eprtList FROM Ent_per_relation_type eprtList").getResultList();
		return eprtList;
		} catch (Exception e) {}
		return null;
	}

	public void addEmplyeeRole(long employeeId, String employee_role_type) {
		
		GenericDAO emprDAO = new GenericDAO(manager, Employee_role.class);
		Employee_role empr = new Employee_role();
		empr.setEmployeeFk(employeeId);
		empr.setEmployeeRoleTypeFk(Long.parseLong(employee_role_type));
		empr.setActive("Y");
		emprDAO.persist(empr);
	}

	public void addEnterprisePersonRelation(String employee_relation_type, Long personId, Long enterpriseId) {
		
		GenericDAO empPerRelDAO = new GenericDAO(manager, Enterprise_person_relation.class);
		Enterprise_person_relation empPerRel = new Enterprise_person_relation();
		empPerRel.setEntPerRelationTypeFk(Long.parseLong(employee_relation_type));
		empPerRel.setPersonFk(personId);
		empPerRel.setEnterpriseFk(enterpriseId);
		empPerRelDAO.persist(empPerRel);
	}
	
	public boolean usernameExists(String uname) {
		
		boolean answer = false;
		
		if (loginS.getUserAccByUserName(uname) != null) {
			System.out.println("gg: " + loginS.getUserAccByUserName(uname).getUsername());
			answer = true;
		}
		
		return answer;
	}

	public void addAccount(Long personId, HttpServletRequest req) throws ParseException {
		
		GenericDAO accountDAO = new GenericDAO(manager, User_account.class);
		User_account ua = new User_account();
		ua.setSubjectTypeFk(3L);
		ua.setSubjectFk(personId);
		ua.setUsername(req.getParameter("username"));
		System.out.println(usernameExists(req.getParameter("username")));
		ua.setPassw(loginS.generateMD5(req.getParameter("password")));
		ua.setStatus(1L);
		ua.setValidFrom(subjectS.parseDate(req.getParameter("valid_from")));
		ua.setValidTo(subjectS.parseDate(req.getParameter("valid_to")));
		ua.setCreatedBy(Long.parseLong((String) req.getAttribute("user_id")));
		ua.setCreated(new Date());
		ua.setPasswordNeverExpires(req.getParameter("password_never_expires"));
		accountDAO.persist(ua);
		
	}

	public HttpServletRequest addEmplyeeAccountToReq(HttpServletRequest req, long emplyeeId) {
		User_account ua = loginS.getUserAccByEmplyeeId(emplyeeId);
		req.setAttribute("account_id", ua.getUserAccount());
		req.setAttribute("username", ua.getUsername());
		req.setAttribute("valid_from", subjectS.parseDate(ua.getValidFrom()));
		req.setAttribute("valid_to", subjectS.parseDate(ua.getValidTo()));
		req.setAttribute("password_never_expires", ua.getPasswordNeverExpires());
		return req;
	}
	
	public void updateAccount(long employeeId, HttpServletRequest req) throws ParseException {
		
		User_account ua = loginS.getUserAccByEmplyeeId(employeeId);
		if (req.getParameter("password") != "") {
			ua.setPassw(loginS.generateMD5(req.getParameter("password")));
		}
		ua.setValidFrom(subjectS.parseDate(req.getParameter("valid_from")));
		ua.setValidTo(subjectS.parseDate(req.getParameter("valid_to")));
		ua.setPasswordNeverExpires(req.getParameter("password_never_expires"));
		manager.merge(ua);
	}
	

}
