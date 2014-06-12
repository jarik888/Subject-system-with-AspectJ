package frontend.controllers;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.data.Subject_attribute_type;
import services.MainService;
import services.SubjectService;

public class SubjectController {
	
	SubjectService subjectS;
	MainService mainS;

	public SubjectController(MainService ms, HttpServletRequest req,
			HttpServletResponse res) {
		subjectS = new SubjectService(ms.getFactory());
		this.mainS = ms;
		navigate(req, res);
	}
	
	public void navigate (HttpServletRequest req, HttpServletResponse res) {
		
		String page = "/main.jsp";
		String action = req.getParameter("action");
		
		if (action.equals("create_new_subject")) {
			//Subject_attribute_type lists
			req.setAttribute("satList", subjectS.getSubjectAtributeTypes(1)); //person
			req.setAttribute("eatList", subjectS.getSubjectAtributeTypes(2)); //enterprise
			req.setAttribute("empatList", subjectS.getSubjectAtributeTypes(3)); //employee
			req.setAttribute("catList", subjectS.getSubjectAtributeTypes(4)); //customer
			
			//Employee data lists
			req = subjectS.addEmployeeListsToReq(req);
			
			page = "/new_subject.jsp";
//-------------------------------------ADD---------------------------------------------------			
		} else if (action.equals("add_person")) {
			req.setAttribute("user_id", mainS.getSesson().getUserId());			
			try {
				long newPersonId = subjectS.addPersonToDB(req);
				req = subjectS.getPersonData(newPersonId, req);
				page = "/edit_person.jsp";
			} catch (ParseException e) {}			
		} else if (action.equals("add_enterprise")) {
			req.setAttribute("user_id", mainS.getSesson().getUserId());
			try {
				long newEnterpriseId = subjectS.addEnterpriseToDB(req);
				req = subjectS.getEnterpriseData(newEnterpriseId, req);
				page = "/edit_enterprise.jsp";
			} catch (ParseException e) {}
		} else if (action.equals("add_employee")) {
			req.setAttribute("user_id", mainS.getSesson().getUserId());
			try {
				long newPersonId = subjectS.addPersonToDB(req);
				req.setAttribute("newPersonId", newPersonId);
				long newEmployeeId = subjectS.addEmployeeToDB(req);
			req = subjectS.getEmployeeData(newEmployeeId, req);
			page = "/edit_employee.jsp";
			} catch (ParseException e) {}
//-------------------------------------EDIT---------------------------------------------------
		} else if (action.equals("edit_person")) {
			req.setAttribute("user_id", mainS.getSesson().getUserId());
			req = subjectS.getPersonData(Long.parseLong(req.getParameter("id")), req);
			page = "/edit_person.jsp";
		} else if (action.equals("edit_enterprise")) {
			req = subjectS.getEnterpriseData(Long.parseLong(req.getParameter("id")), req);
			page = "/edit_enterprise.jsp";
		} else if (action.equals("edit_employee")) {
			req = subjectS.getEmployeeData(Long.parseLong(req.getParameter("id")), req);
			page = "/edit_employee.jsp";
//-------------------------------------SAVE----------------------------------------------------
		} else if (action.equals("save_person_changes")) {
			try {
				req.setAttribute("user_id", mainS.getSesson().getUserId());
				req = subjectS.getPersonData(subjectS.addChangesInPersonToDB(req), req);
				page = "/edit_person.jsp";
			} catch (ParseException e) {}
		} else if (action.equals("save_enterprise_changes")) {
			try {
				req.setAttribute("user_id", mainS.getSesson().getUserId());
				req = subjectS.getEnterpriseData(subjectS.addChangesInEnterpriseToDB(req), req);
				page = "/edit_enterprise.jsp";
			} catch (ParseException e) {}
		} else if (action.equals("save_employee_changes")) {
			try {
				req.setAttribute("user_id", mainS.getSesson().getUserId());
				Long personId = subjectS.addChangesInPersonToDB(req);
				Long employeeId = subjectS.addChangesInEmployeeToDB(req);
				subjectS.getPersonData(personId, req);
				subjectS.getEmployeeData(employeeId, req);
				page = "/edit_employee.jsp";
			} catch (ParseException e) {}
//-------------------------------------AJAX---------------------------------------------------
		} else if (action.equals("delete_address")) {
			req = subjectS.deleteAddtessFromDB(req);
			page = "/ajax_answer.jsp";
		} else if (action.equals("delete_contact")) {
			req = subjectS.deleteContactFromDB(req);
			page = "/ajax_answer.jsp";
		} else if (action.equals("check_username")) {
			req = subjectS.checkUsername(req);
			page = "/ajax_answer.jsp";
		} else if (action.equals("delete_subject")) {
			req = subjectS.deleteSubjectFromDB(req);
			page = "/ajax_answer.jsp";
//-------------------------------------SEARCH---------------------------------------------------
		} else if (action.equals("search")) {
			//Subject_attribute_type lists
			req.setAttribute("satList", subjectS.getSubjectAtributeTypes(1)); //person
			req.setAttribute("eatList", subjectS.getSubjectAtributeTypes(2)); //enterprise
			req.setAttribute("empatList", subjectS.getSubjectAtributeTypes(3)); //employee
			req.setAttribute("catList", subjectS.getSubjectAtributeTypes(4)); //customer
			page = "/search.jsp";
		} else if(action.equals("search_subject")) {
			req = subjectS.searchSubject(req);
			page = "/ajax_answer.jsp";
		}
		new NavigationController().showPage(req, res, page);
	}
	
	

}
